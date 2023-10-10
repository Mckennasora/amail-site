package com.yyh.amailsite.mail.client;

import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;
import com.yyh.amailsite.mail.model.mailplan.dto.SendMailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "service-mail")
public interface MailFeignClient {

    @GetMapping("/inner/mail/getCronMap/{planId}")
    Map<String, String> getCronMapByMailPlanId(@PathVariable String planId);

    @PostMapping("/inner/mail/send")
    MailHistoryVo sendMail(@RequestBody SendMailDto sendMailDto);
}