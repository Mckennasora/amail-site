package com.yyh.amailsite.mail.service;

import com.yyh.amailsite.mail.model.mailhistory.dto.MailHistoryListDto;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanListDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MailHistoryService {
    MailHistory getMailHistoryInfoById(String id);

    List<MailHistory> getMailHistoryListByMailPlanId(String mailPlanId);

    Page<MailHistory> findMailHistoryListPage(int page, int limit, MailHistoryListDto mailHistoryListDto);

}
