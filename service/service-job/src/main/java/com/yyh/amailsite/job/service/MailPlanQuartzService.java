package com.yyh.amailsite.job.service;

import org.quartz.SchedulerException;

public interface MailPlanQuartzService {

    void enableMailPlan(String mailPlanId) throws SchedulerException;

    void disableMailPlan(String mailPlanId) throws SchedulerException;
}
