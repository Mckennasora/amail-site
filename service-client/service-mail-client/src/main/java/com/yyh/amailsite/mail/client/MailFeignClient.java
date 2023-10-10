package com.yyh.amailsite.mail.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "service-mail")
public interface MailFeignClient {

    @GetMapping("/getCronMap/{planId}")
    Map<String, String> getCronMapByMailPlanId(@PathVariable String planId);

    @GetMapping("/send/{planId}/{cronId}/{cronExpr}")
    MailHistoryVo sendMail(@PathVariable String planId, @PathVariable String cronId, @PathVariable String cronExpr);
}