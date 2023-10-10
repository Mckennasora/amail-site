package com.yyh.amailsite.mail.service;

import com.yyh.amailsite.mail.model.mailhistory.dto.MailHistoryListDto;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MailHistoryService {
    MailHistory getMailHistoryInfoById(String id);

    List<MailHistoryVo> getMailHistoryListByMailPlanId(String mailPlanId);

    Page<MailHistoryVo> findMailHistoryListPage(int page, int limit, MailHistoryListDto mailHistoryListDto);

    MailHistoryVo saveMailHistoryByMailPlan(MailPlan mailPlan, String cronExpr, String cronExprId, int tryCount, boolean success);
}
