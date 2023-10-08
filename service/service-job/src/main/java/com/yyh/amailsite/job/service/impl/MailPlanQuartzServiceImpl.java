package com.yyh.amailsite.job.service.impl;

import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.job.constant.JobConst;
import com.yyh.amailsite.job.job.SendMailJob;
import com.yyh.amailsite.job.service.MailPlanQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MailPlanQuartzServiceImpl implements MailPlanQuartzService {

    private static Scheduler scheduler;

    public MailPlanQuartzServiceImpl() throws SchedulerException {
        //todo 配置使用线程池 和 数据库
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
    }


    @Override
    public void enableMailPlan(String mailPlanId) {
        //todo 远程查出cron表达式
        Map<String, String> crons = new HashMap<>();

        // 创建触发器队列，设置触发时间
        List<Trigger> triggerList = crons.entrySet().stream().map(entry -> (Trigger) TriggerBuilder.newTrigger()
                .withIdentity(entry.getKey(), JobConst.MAIL_PLAN_CRON_GROUP)
                .usingJobData(JobConst.MAIL_PLAN_CRON_ID_KEY, entry.getKey())
                .usingJobData(JobConst.MAIL_PLAN_CRON_KEY, entry.getValue())
                .withSchedule(CronScheduleBuilder.cronSchedule(entry.getValue()))
                .build()).collect(Collectors.toList());

        // 创建一个任务
        JobDetail job = JobBuilder.newJob(SendMailJob.class)
                .withIdentity(mailPlanId, JobConst.MAIL_PLAN_GROUP)
                .usingJobData(JobConst.MAIL_PLAN_ID_KEY, mailPlanId)
                .build();

        // 将任务和触发器关联起来，将任务安排到调度器中
        triggerList.forEach(trigger -> {
            try {
                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException e) {
                log.error("mailPlan启动失败:{}", mailPlanId);
                throw new AmailException(ResultCodeEnum.SERVICE_ERROR, "mailPlan启动失败");
            }
        });

    }

    @Override
    public void disableMailPlan(String mailPlanId) throws SchedulerException {
        JobKey jobKey = new JobKey(mailPlanId, JobConst.MAIL_PLAN_GROUP);
        if (!scheduler.deleteJob(jobKey)) {
            log.error("mailPlan启动失败:{}", mailPlanId);
            throw new AmailException(ResultCodeEnum.SERVICE_ERROR,"mailPlan关闭失败");
        }
    }

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建一个调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();


        // 创建一个触发器，设置触发时间，这里设置为在2023年10月8日14:00:00触发一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("mailPlanCronId", "mailPlanIdCronGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                .build();

        // 创建一个任务
        JobDetail job = JobBuilder.newJob(SendMailJob.class)
                .withIdentity("mailPlanId", JobConst.MAIL_PLAN_GROUP)
                .usingJobData(JobConst.MAIL_PLAN_ID_KEY, "mailPlanId")
                .build();

        // 将任务和触发器关联起来，将任务安排到调度器中
        scheduler.scheduleJob(job, trigger);


        Thread.sleep(35000);

        JobKey jobKey = new JobKey("mailPlanId", "mailPlanGroup");
        scheduler.deleteJob(jobKey);
    }
}
