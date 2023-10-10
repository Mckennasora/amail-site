package com.yyh.amailsite.mail.repo;

import com.yyh.amailsite.mail.model.mailcron.entity.MailCron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailCronRepository extends JpaRepository<MailCron, String> {

    Page<MailCron> findAllByIsDeleted(Integer isDeleted, Pageable pageable);
}