package com.yyh.amailsite.job.job;

import cn.hutool.core.date.DateTime;
import com.yyh.amailsite.job.constant.JobConst;
import com.yyh.amailsite.job.service.MailPlanQuartzService;
import com.yyh.amailsite.mail.client.MailFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SendMailJob implements Job {

    @Autowired
    private MailFeignClient mailFeignClient;

    public SendMailJob() {
    }



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String mailPlanId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get(JobConst.MAIL_PLAN_ID_KEY);
        String cronId = (String) jobExecutionContext.getTrigger().getJobDataMap().get(JobConst.MAIL_PLAN_CRON_ID_KEY);
        String cronExpr = (String) jobExecutionContext.getTrigger().getJobDataMap().get(JobConst.MAIL_PLAN_CRON_EXPR_KEY);
        mailFeignClient.sendMail(mailPlanId, cronId, cronExpr);


        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        log.info("远程调用,触发时间:{}", new DateTime());
        log.info("远程调用,触发的计划id:{}", jobKey);
        log.info("远程调用,触发的计划id:{}", mailPlanId);
        log.info("远程调用,触发时间id:{}", triggerKey);

    }
}
