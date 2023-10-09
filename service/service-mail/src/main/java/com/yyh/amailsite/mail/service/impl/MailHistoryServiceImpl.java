package com.yyh.amailsite.mail.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.model.mailhistory.dto.MailHistoryListDto;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import com.yyh.amailsite.mail.repo.MailHistoryRepository;
import com.yyh.amailsite.mail.service.MailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailHistoryServiceImpl implements MailHistoryService {

    private MailHistoryRepository mailHistoryRepository;

    @Autowired
    public MailHistoryServiceImpl(MailHistoryRepository mailHistoryRepository){
        this.mailHistoryRepository = mailHistoryRepository;
    }


    @Override
    public MailHistory getMailHistoryInfoById(String id) {
        Optional<MailHistory> byId = mailHistoryRepository.findById(id);
        //todo  抽象
        if (byId.isPresent()) {
            if (!isOwner(byId.get().getUserId()) && !StpUtil.hasRoleOr("admin", "root")) {
                throw new AmailException(ResultCodeEnum.PERMISSION);
            }
            return byId.get();
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "查询失败");
        }
    }

    @Override
    public List<MailHistory> getMailHistoryListByMailPlanId(String mailPlanId) {
        Optional<MailHistory> byId = mailHistoryRepository.findById(mailPlanId);
        //todo
        return null;
    }

    @Override
    public Page<MailHistory> findMailHistoryListPage(int page, int limit, MailHistoryListDto mailHistoryListDto) {
        return null;
    }

    private boolean isOwner(String userId) {
        String loginId = (String) StpUtil.getLoginId();
        return loginId.equals(userId);
    }
}
