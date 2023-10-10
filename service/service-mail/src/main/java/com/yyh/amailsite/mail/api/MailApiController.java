package com.yyh.amailsite.mail.api;

import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;
import com.yyh.amailsite.mail.service.MailPlanService;
import com.yyh.amailsite.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
//todo 只接受信任ip
@RequestMapping("/inner/mail")
public class MailApiController {

    private MailPlanService mailPlanService;

    private MailService mailService;


    @Autowired
    public MailApiController(MailPlanService mailPlanService, MailService mailService) {
        this.mailPlanService = mailPlanService;
        this.mailService = mailService;
    }

    @GetMapping("/getCronMap/{planId}")
    public Map<String, String> getCronMapByMailPlanId(@PathVariable String planId) {
        return mailPlanService.getCronMapByMailPlanId(planId);
    }

    @GetMapping("/send/{planId}/{cronId}/{cronExpr}")
    public MailHistoryVo sendMail(@PathVariable String planId, @PathVariable String cronId, @PathVariable String cronExpr) {
        return mailService.sendMailAndAddHistory(planId, cronId, cronExpr);
    }


}
