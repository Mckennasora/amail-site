package com.yyh.amailsite.mail.service.impl;

import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import com.yyh.amailsite.mail.repo.MailPlanRepository;
import com.yyh.amailsite.mail.service.MailHistoryService;
import com.yyh.amailsite.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MailServiceImpl implements MailService {


    /**
     * 发件人邮箱
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发件人昵称
     */
    @Value("${spring.mail.nickname}")
    private String nickname;

    private final JavaMailSender javaMailSender;

    private final MailPlanRepository mailPlanRepository;

    private final MailHistoryService mailHistoryService;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, MailHistoryService mailHistoryService, MailPlanRepository mailPlanRepository) {
        this.javaMailSender = javaMailSender;
        this.mailHistoryService = mailHistoryService;
        this.mailPlanRepository = mailPlanRepository;
    }


    @Override
    public MailHistoryVo sendMailAndAddHistory(String planId, String cronId, String cronExpr) {
        //1.查出发送的内容
        Optional<MailPlan> byId = mailPlanRepository.findById(planId);
        if (!byId.isPresent()) {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到计划");
        }

        MailPlan mailPlan = byId.get();
        String toWho = mailPlan.getToWho();
        String subject = mailPlan.getSubject();
        String mainBody = mailPlan.getMainBody();

        //2.发送，尝试3次
        boolean sendSuccess = false;
        int tryCount = 1;
        MailHistoryVo mailHistoryVo = null;
        while (!sendSuccess) {

            sendSuccess = sendEmail(toWho, subject, mainBody);

            //3.发送后记录进入history
            mailHistoryVo = mailHistoryService.saveMailHistoryByMailPlan(mailPlan, cronExpr, cronId, tryCount, sendSuccess);
            tryCount++;
        }

        return mailHistoryVo;
    }

    private boolean sendEmail(String toWho, String subject, String mainBody) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置发件邮箱
        simpleMailMessage.setFrom(nickname + '<' + fromEmail + '>');
        //收件人邮箱
        simpleMailMessage.setTo(toWho);
        //主题标题
        simpleMailMessage.setSubject(subject);
        //信息内容
        simpleMailMessage.setText(mainBody);
        //执行发送
        try {//发送可能失败
            javaMailSender.send(simpleMailMessage);
            //没有异常返回true，表示发送成功
            return true;
        } catch (Exception e) {
            //发送失败，返回false
            return false;
        }
    }
}
