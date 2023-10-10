package com.yyh.amailsite.mail.service;


import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanAddDto;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanListDto;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanUpdateDto;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface MailPlanService {
    MailPlan addMailPlan(MailPlanAddDto mailPlanAddDto);

    void deleteMailPlan(String id);

    void batchDeleteMailPlan(String[] mailPlanIds);

    void updateMailPlan(MailPlanUpdateDto mailPlanUpdateDto);

    MailPlan getMailPlan(String id);

    Page<MailPlan> findMailPlanListPage(int page, int size, MailPlanListDto mailPlanListDto);

    Map<String, String> getCronMapByMailPlanId(String planId);
}
