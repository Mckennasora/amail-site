package com.yyh.amailsite.mail.service;


import com.yyh.amailsite.mail.model.mailcron.dto.MailCronAddDto;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronListDto;
import com.yyh.amailsite.mail.model.mailcron.dto.MailCronUpdateDto;
import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;

import org.springframework.data.domain.Page;

public interface MailCronService {
    MailCron addMailCron(MailCronAddDto mailCronAddDto);

    void deleteMailCron(String id);

    void batchDeleteMailCron(String[] mailCronIds);

    void updateMailCron(MailCronUpdateDto mailCronUpdateDto);

    MailCron getMailCron(String id);

    Page<MailCron> findMailCronListPage(int page, int size, MailCronListDto mailCronListDto);
}
