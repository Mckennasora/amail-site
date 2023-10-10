package com.yyh.amailsite.mail.repo;

import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailHistoryRepository extends JpaRepository<MailHistory, String> {

    Page<MailHistory> findAll(Specification<MailHistory> specification, Pageable pageable);

    List<MailHistory> findAllByMailPlanId(String mailPlanId);


}