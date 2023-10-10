package com.yyh.amailsite.job.service;

import com.rabbitmq.client.Channel;
import org.quartz.SchedulerException;
import org.springframework.amqp.core.Message;

import java.io.IOException;

public interface MailPlanQuartzService {

    void enableMailPlan(String mailPlanId) throws SchedulerException;

    void disableMailPlan(String mailPlanId) throws SchedulerException;
}
