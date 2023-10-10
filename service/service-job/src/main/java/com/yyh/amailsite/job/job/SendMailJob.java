package com.yyh.amailsite.job.job;

import cn.hutool.core.date.DateTime;
import com.yyh.amailsite.job.constant.JobConst;
import com.yyh.amailsite.job.service.MailPlanQuartzService;
import com.yyh.amailsite.mail.client.MailFeignClient;
import com.yyh.amailsite.mail.model.mailplan.dto.SendMailDto;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisallowConcurrentExecution
public class SendMailJob implements Job {

    //手动注入
    private MailFeignClient mailFeignClient;

    public SendMailJob() {
        mailFeignClient = SpringUtil.getBean(MailFeignClient.class);
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String mailPlanId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(JobConst.MAIL_PLAN_ID_KEY);
        String cronId = (String) jobExecutionContext.getTrigger().getJobDataMap().get(JobConst.MAIL_PLAN_CRON_ID_KEY);
        String cronExpr = (String) jobExecutionContext.getTrigger().getJobDataMap().get(JobConst.MAIL_PLAN_CRON_EXPR_KEY);

        log.info("开始远程调用========================");
        mailFeignClient.sendMail(new SendMailDto(mailPlanId, cronId, cronExpr));


        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        log.info("远程调用,触发时间:{}", new DateTime());
        log.info("远程调用,触发的计划id:{}", jobKey);
        log.info("远程调用,触发的计划id:{}", mailPlanId);
        log.info("远程调用,触发时间id:{}", triggerKey);
        log.info("调用结束===========================");

    }
}
