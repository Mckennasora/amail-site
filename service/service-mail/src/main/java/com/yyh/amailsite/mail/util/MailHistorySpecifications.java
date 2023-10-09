package com.yyh.amailsite.mail.util;

import cn.hutool.core.util.StrUtil;
import com.yyh.amailsite.mail.model.mailhistory.dto.MailHistoryListDto;
import com.yyh.amailsite.mail.model.mailhistory.entity.MailHistory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class MailHistorySpecifications {

    public static Specification<MailHistory> withMailHistoryListDto(MailHistoryListDto mailHistoryListDto) {
        String userId = mailHistoryListDto.getUserId();
        String mailPlanId = mailHistoryListDto.getMailPlanId();

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StrUtil.isNotBlank(userId)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userId"), "%" + userId + "%"));
            }
            if (StrUtil.isNotBlank(mailPlanId)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("arrSysScheduleId"), "%" + mailPlanId + "%"));
            }
            return predicate;
        };
    }
}