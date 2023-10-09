package com.yyh.amailsite.mail.repo;

import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailHistoryRepository extends JpaRepository<MailHistory, String> {

    Page<MailPlan> findAll(Specification<MailHistory> specification, Pageable pageable);

    
}