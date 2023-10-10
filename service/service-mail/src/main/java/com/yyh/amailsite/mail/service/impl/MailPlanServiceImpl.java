package com.yyh.amailsite.mail.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yyh.aideasite.mq.constant.MqConst;
import com.yyh.aideasite.mq.service.RabbitService;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.PageRequestUtils;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;
import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.model.mailplan.constant.MailPlanConst;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanAddDto;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanListDto;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanUpdateDto;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import com.yyh.amailsite.mail.repo.MailCronRepository;
import com.yyh.amailsite.mail.repo.MailPlanRepository;
import com.yyh.amailsite.mail.service.MailPlanService;
import com.yyh.amailsite.mail.util.MailPlanSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MailPlanServiceImpl implements MailPlanService {

    private final MailPlanRepository mailPlanRepository;

    private final MailCronRepository mailCronRepository;

    private final RabbitService rabbitService;

    @Autowired
    public MailPlanServiceImpl(MailPlanRepository mailPlanRepository, MailCronRepository mailCronRepository,RabbitService rabbitService) {
        this.mailPlanRepository = mailPlanRepository;
        this.mailCronRepository = mailCronRepository;
        this.rabbitService = rabbitService;
    }

    @Override
    public MailPlan addMailPlan(MailPlanAddDto mailPlanAddDto) {
        String shortUUID = ShortUUIDGenerator.generateShortUUID();

        MailPlan mailPlan = new MailPlan();
        mailPlan.setId(shortUUID);
        mailPlan.setUserId((String) StpUtil.getLoginId());

        //todo 验证是否存在这个时间表
        mailPlan.setArrSysScheduleId(String.join(",", mailPlanAddDto.getArrSysScheduleId()));
        mailPlan.setArrDIYScheduleId(String.join(",", mailPlanAddDto.getArrDIYScheduleId()));
        mailPlan.setToWho(mailPlanAddDto.getToWho());
        mailPlan.setSubject(mailPlanAddDto.getSubject());
        mailPlan.setMainBody(mailPlanAddDto.getMainBody());
        mailPlan.setArrPhotoUrl(String.join(",", mailPlanAddDto.getArrPhotoUrl()));
        mailPlan.setRemarks(mailPlanAddDto.getRemarks());
        mailPlan.setIsEnable(MailPlanConst.MAIL_PLAN_DISABLE);

        mailPlanRepository.save(mailPlan);

        return mailPlan;
    }

    @Override
    public void deleteMailPlan(String id) {
        MailPlan mailPlanById = getMailPlanById(id);
        mailPlanById.setIsDeleted(1);
        mailPlanRepository.save(mailPlanById);
    }

    @Override
    public void batchDeleteMailPlan(String[] mailPlanIds) {
        List<MailPlan> mailPlanByIds = getMailPlanByIds(mailPlanIds);
        mailPlanByIds.forEach(mailPlan -> mailPlan.setIsDeleted(1));
        mailPlanRepository.saveAll(mailPlanByIds);
    }


    @Override
    public void updateMailPlan(MailPlanUpdateDto mailPlanUpdateDto) {
        MailPlan mailPlan = getMailPlanById(mailPlanUpdateDto.getId());
        saveMailPlan(mailPlan, mailPlanUpdateDto);
    }

    private void saveMailPlan(MailPlan mailPlan, MailPlanUpdateDto mailPlanUpdateDto) {

        mailPlan.setArrSysScheduleId(String.join(",", mailPlanUpdateDto.getArrSysScheduleId()));
        mailPlan.setArrDIYScheduleId(String.join(",", mailPlanUpdateDto.getArrDIYScheduleId()));
        mailPlan.setToWho(mailPlanUpdateDto.getToWho());
        mailPlan.setSubject(mailPlanUpdateDto.getSubject());
        mailPlan.setMainBody(mailPlanUpdateDto.getMainBody());
        mailPlan.setArrPhotoUrl(String.join(",", mailPlanUpdateDto.getArrPhotoUrl()));
        mailPlan.setRemarks(mailPlanUpdateDto.getRemarks());
        mailPlanRepository.save(mailPlan);
    }


    @Override
    public MailPlan getMailPlan(String id) {

        return getMailPlanById(id);
    }

    @Override
    public Page<MailPlan> findMailPlanListPage(int page, int size, MailPlanListDto mailPlanListDto) {
        if (!isOwner(mailPlanListDto.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }

        String createTimeSortStr = mailPlanListDto.getCreateTimeSort();
        String updateTimeSortStr = mailPlanListDto.getUpdateTimeSort();
        Sort sort = PageRequestUtils.pageRequestSortTime(createTimeSortStr, updateTimeSortStr);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<MailPlan> mailPlanSpecification = MailPlanSpecifications.withMailPlanListDto(mailPlanListDto);

        Page<MailPlan> mailPlanListPage = mailPlanRepository.findAll(mailPlanSpecification, pageable);
        return new PageImpl<>(mailPlanListPage.toList(), mailPlanListPage.getPageable(), mailPlanListPage.getTotalElements());
    }

    @Override
    public Map<String, String> getCronMapByMailPlanId(String planId) {
        MailPlan mailPlanById = getMailPlanById(planId);

//        String arrSysScheduleId = byId.get().getArrSysScheduleId();
//        String[] sysSchedule = arrSysScheduleId.split(",");
//        List<String> strings = Arrays.asList(sysSchedule);

        String arrDIYScheduleId = mailPlanById.getArrDIYScheduleId();
        String[] diyScheduleId = arrDIYScheduleId.split(",");
        List<String> diyScheduleIdList = Arrays.asList(diyScheduleId);

        List<MailCron> mailCronList = mailCronRepository.findAllById(diyScheduleIdList);

        if (mailCronList.isEmpty()) {
            throw new AmailException(ResultCodeEnum.FAIL, "没有该定时计划");
        }
        HashMap<String, String> cronMap = new HashMap<>();
        mailCronList.forEach(mailCron -> cronMap.put(mailCron.getId(), mailCron.getCronExpr()));

        return cronMap;
    }

    @Override
    public void enableMailPlan(String mailPlanId) {
        MailPlan mailPlanById = getMailPlanById(mailPlanId);
        mailPlanById.setIsEnable(MailPlanConst.MAIL_PLAN_ENABLE);

        mailPlanRepository.save(mailPlanById);

        rabbitService.sendMessage(MqConst.EXCHANGE_MAIL_PLAN_DIRECT, MqConst.ROUTING_MAIL_PLAN_ENABLE, mailPlanId);
    }

    @Override
    public void disableMailPlan(String mailPlanId) {
        MailPlan mailPlanById = getMailPlanById(mailPlanId);
        mailPlanById.setIsEnable(MailPlanConst.MAIL_PLAN_DISABLE);

        mailPlanRepository.save(mailPlanById);

        rabbitService.sendMessage(MqConst.EXCHANGE_MAIL_PLAN_DIRECT, MqConst.ROUTING_MAIL_PLAN_DISABLE, mailPlanId);

    }


    private MailPlan getMailPlanById(String id) {
        Optional<MailPlan> byId = mailPlanRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到该定时计划");
        }
        if (!isOwner(byId.get().getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
            throw new AmailException(ResultCodeEnum.PERMISSION);
        }
        return byId.get();
    }

    private List<MailPlan> getMailPlanByIds(String[] ids) {
        List<MailPlan> allById = mailPlanRepository.findAllById(Arrays.asList(ids));
        if (allById.isEmpty()) {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到计划");
        }
        allById.forEach(mailPlan -> {
            if (!isOwner(mailPlan.getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
                throw new AmailException(ResultCodeEnum.PERMISSION);
            }
        });
        return allById;
    }
    
    private boolean isOwner(String userId) {
        String loginId = (String) StpUtil.getLoginId();
        return loginId.equals(userId);
    }
}
