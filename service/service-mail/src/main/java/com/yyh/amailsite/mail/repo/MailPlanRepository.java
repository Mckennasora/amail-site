package com.yyh.amailsite.mail.repo;

import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailPlanRepository extends JpaRepository<MailPlan, String> {

    Page<MailPlan> findAll(Specification<MailPlan> specification, Pageable pageable);

}