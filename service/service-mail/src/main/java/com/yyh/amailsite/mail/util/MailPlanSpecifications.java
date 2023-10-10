package com.yyh.amailsite.mail.util;

import cn.hutool.core.util.StrUtil;
import com.yyh.amailsite.mail.model.mailplan.dto.MailPlanListDto;
import com.yyh.amailsite.mail.model.mailplan.entity.MailPlan;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class MailPlanSpecifications {

    public static Specification<MailPlan> withMailPlanListDto(MailPlanListDto mailPlanListDto) {
        String id = mailPlanListDto.getId();
        String userId = mailPlanListDto.getUserId();
        String arrSysScheduleId = mailPlanListDto.getArrSysScheduleId();
        String arrDIYScheduleId = mailPlanListDto.getArrDIYScheduleId();
        String toWho = mailPlanListDto.getToWho();
        String subject = mailPlanListDto.getSubject();
        String mainBody = mailPlanListDto.getMainBody();
        String arrPhotoUrl = mailPlanListDto.getArrPhotoUrl();
        String remarks = mailPlanListDto.getRemarks();
        String createTime = mailPlanListDto.getCreateTime();
        String updateTime = mailPlanListDto.getUpdateTime();
        Integer isDeleted = mailPlanListDto.getIsDeleted();
        Integer isEnable = mailPlanListDto.getIsEnable();

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StrUtil.isNotBlank(id)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("id"), "%" + id + "%"));
            }
            if (StrUtil.isNotBlank(userId)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userId"), "%" + userId + "%"));
            }
            if (StrUtil.isNotBlank(arrSysScheduleId)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("arrSysScheduleId"), "%" + arrSysScheduleId + "%"));
            }
            if (StrUtil.isNotBlank(arrDIYScheduleId)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("arrDIYScheduleId"), "%" + arrDIYScheduleId + "%"));
            }
            if (StrUtil.isNotBlank(toWho)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("toWho"), "%" + toWho + "%"));
            }
            if (StrUtil.isNotBlank(subject)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("subject"), "%" + subject + "%"));
            }
            if (StrUtil.isNotBlank(mainBody)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("mainBody"), "%" + mainBody + "%"));
            }
            if (StrUtil.isNotBlank(arrPhotoUrl)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("arrPhotoUrl"), "%" + arrPhotoUrl + "%"));
            }
            if (StrUtil.isNotBlank(remarks)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("remarks"), "%" + remarks + "%"));
            }
            if (StrUtil.isNotBlank(createTime)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("createTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + createTime + "%"));
            }
            if (StrUtil.isNotBlank(updateTime)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("updateTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + updateTime + "%"));
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), isDeleted));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isEnable"), isEnable));
            return predicate;
        };
    }
}