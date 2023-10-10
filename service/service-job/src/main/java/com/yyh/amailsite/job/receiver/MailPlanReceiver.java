package com.yyh.amailsite.job.receiver;

import com.rabbitmq.client.Channel;
import com.yyh.aideasite.mq.constant.MqConst;
import com.yyh.amailsite.job.service.MailPlanQuartzService;
import org.quartz.SchedulerException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailPlanReceiver {

    private final MailPlanQuartzService mailPlanQuartzService;

    @Autowired
    public MailPlanReceiver(MailPlanQuartzService mailPlanQuartzService){
        this.mailPlanQuartzService = mailPlanQuartzService;
    }

    /**
     * 激活计划
     *
     * @param mailPlanId
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MAIL_PLAN_ENABLE, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_MAIL_PLAN_DIRECT),
            key = {MqConst.ROUTING_MAIL_PLAN_ENABLE}
    ))
    public void enableMailPlan(String mailPlanId ,Message message, Channel channel) throws IOException, SchedulerException {
        if (null != mailPlanId) {
            mailPlanQuartzService.enableMailPlan(mailPlanId);
        }
        /**
         * 第一个参数：表示收到的消息的标号
         * 第二个参数：如果为true表示可以签收多个消息
         */
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 取消计划
     * @param mailPlanId
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MAIL_PLAN_DISABLE, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_MAIL_PLAN_DIRECT),
            key = {MqConst.ROUTING_MAIL_PLAN_DISABLE}
    ))
    public void disableMailPlan(String mailPlanId, Message message, Channel channel) throws IOException, SchedulerException {
        if (null != mailPlanId) {
            mailPlanQuartzService.disableMailPlan(mailPlanId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}