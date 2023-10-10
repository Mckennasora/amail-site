package com.yyh.amailsite.job.job;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
public class SendMailJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //todo 根据mailPlanId远程调用邮件系统




        log.info("远程调用,触发时间:{}", new DateTime());
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        String mailPlanId = (String)jobExecutionContext.getJobDetail().getJobDataMap().get("mailPlanId");
        log.info("远程调用,触发的计划id:{}", jobKey);
        log.info("远程调用,触发的计划id:{}", mailPlanId);
        log.info("远程调用,触发时间id:{}", triggerKey);

    }
}
