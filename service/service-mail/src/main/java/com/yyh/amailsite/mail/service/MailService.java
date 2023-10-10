package com.yyh.amailsite.mail.service;

import com.yyh.amailsite.mail.model.mailhistory.vo.MailHistoryVo;

public interface MailService {

    MailHistoryVo sendMailAndAddHistory(String planId, String cronId, String cronExpr);
}
