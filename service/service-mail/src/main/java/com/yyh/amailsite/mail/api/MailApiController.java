package com.yyh.amailsite.mail.api;

import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;
import com.yyh.amailsite.mail.model.mailplan.dto.SendMailDto;
import com.yyh.amailsite.mail.service.MailPlanService;
import com.yyh.amailsite.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
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

    @PostMapping("/send")
    public MailHistoryVo sendMail(@RequestBody SendMailDto sendMailDto) {
        return mailService.sendMailAndAddHistory(sendMailDto.getMailPlanId(), sendMailDto.getCronId(), sendMailDto.getCronExpr());
    }


}
