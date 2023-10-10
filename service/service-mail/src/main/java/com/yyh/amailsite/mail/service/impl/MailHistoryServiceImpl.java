package com.yyh.amailsite.mail.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;
import com.yyh.amailsite.mail.model.mailhistory.dto.MailHistoryListDto;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import com.yyh.amailsite.mail.repo.MailHistoryRepository;
import com.yyh.amailsite.mail.service.MailHistoryService;
import com.yyh.amailsite.mail.util.MailHistorySpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailHistoryServiceImpl implements MailHistoryService {

    private final MailHistoryRepository mailHistoryRepository;

    @Autowired
    public MailHistoryServiceImpl(MailHistoryRepository mailHistoryRepository) {
        this.mailHistoryRepository = mailHistoryRepository;
    }


    @Override
    public MailHistory getMailHistoryInfoById(String id) {
        Optional<MailHistory> byId = mailHistoryRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到该记录");
        }
        if (!isOwner(byId.get().getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }
        return byId.get();

    }

    @Override
    public List<MailHistoryVo> getMailHistoryListByMailPlanId(String mailPlanId) {
        List<MailHistory> allByMailPlanIds = mailHistoryRepository.findAllByMailPlanId(mailPlanId);

        return allByMailPlanIds.stream().map(mailHistory -> {
            if (!isOwner(mailHistory.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
                throw new AmailException(ResultCodeEnum.PERMISSION);
            }
            return getMailHistoryVo(mailHistory);
        }).collect(Collectors.toList());
    }

    @Override
    public Page<MailHistoryVo> findMailHistoryListPage(int page, int limit, MailHistoryListDto mailHistoryListDto) {
        //todo 将来支持所有字段搜索
        String userId = mailHistoryListDto.getUserId();
        if (!isOwner(userId) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }

        Pageable pageable = PageRequest.of(page, limit);

        Page<MailHistory> mailHistoryPage = mailHistoryRepository.findAll(
                MailHistorySpecifications.withMailHistoryListDto(mailHistoryListDto), pageable);
        List<MailHistoryVo> mailHistoryVoList = mailHistoryPage.get().map(this::getMailHistoryVo).collect(Collectors.toList());
        return new PageImpl<>(mailHistoryVoList,
                mailHistoryPage.getPageable(), mailHistoryPage.getTotalElements());
    }

    @Override
    public MailHistoryVo saveMailHistoryByMailPlan(MailPlan mailPlan, String cronExpr,String cronExprId, int tryCount, boolean success) {
        MailHistory mailHistory = new MailHistory();
        mailHistory.setId(ShortUUIDGenerator.generateShortUUID());
        mailHistory.setUserId(mailPlan.getUserId());
        mailHistory.setMailPlanId(mailPlan.getId());
        mailHistory.setArrSysScheduleId(mailPlan.getArrSysScheduleId());
        mailHistory.setArrDIYScheduleId(mailPlan.getArrDIYScheduleId());
        mailHistory.setSendByCronExpr(cronExpr);
        mailHistory.setSendByCronExprId(cronExprId);
        mailHistory.setToWho(mailPlan.getToWho());
        mailHistory.setSubject(mailPlan.getSubject());
        mailHistory.setMainBody(mailPlan.getMainBody());
        mailHistory.setArrPhotoUrl(mailPlan.getArrPhotoUrl());
        mailHistory.setTryCount(tryCount);
        mailHistory.setRemarks(mailPlan.getRemarks());
        mailHistory.setIsSuccess(success ? 0 : 1);
        mailHistory.setIsDeleted(mailPlan.getIsDeleted());

        mailHistoryRepository.save(mailHistory);

        return getMailHistoryVo(mailHistory);
    }

    private MailHistoryVo getMailHistoryVo(MailHistory mailHistory) {
        MailHistoryVo mailHistoryVo = new MailHistoryVo();
        mailHistoryVo.setId(mailHistory.getId());
        mailHistoryVo.setUserId(mailHistory.getUserId());
        mailHistoryVo.setMailPlanId(mailHistory.getMailPlanId());
        mailHistoryVo.setArrSysScheduleId(mailHistory.getArrSysScheduleId());
        mailHistoryVo.setSysScheduleIdList(Arrays.asList(mailHistory.getArrSysScheduleId().split(",")));
        mailHistoryVo.setArrDIYScheduleId(mailHistory.getArrDIYScheduleId());
        mailHistoryVo.setDIYScheduleIdList(Arrays.asList(mailHistory.getArrDIYScheduleId().split(",")));
        mailHistoryVo.setSendByCronExpr(mailHistory.getSendByCronExpr());
        mailHistoryVo.setSendByCronExprId(mailHistory.getSendByCronExprId());
        mailHistoryVo.setToWho(mailHistory.getToWho());
        mailHistoryVo.setSubject(mailHistory.getSubject());
        mailHistoryVo.setMainBody(mailHistory.getMainBody());
        mailHistoryVo.setArrPhotoUrl(mailHistory.getArrPhotoUrl());
        mailHistoryVo.setTryCount(mailHistory.getTryCount());
        mailHistoryVo.setRemarks(mailHistory.getRemarks());
        mailHistoryVo.setCreateTime(mailHistory.getCreateTime());
        mailHistoryVo.setUpdateTime(mailHistory.getUpdateTime());
        mailHistoryVo.setIsSuccess(mailHistory.getIsSuccess());
        mailHistoryVo.setIsDeleted(mailHistory.getIsDeleted());
        return mailHistoryVo;

    }

    private boolean isOwner(String userId) {
        String loginId = (String) StpUtil.getLoginId();
        return loginId.equals(userId);
    }
}
