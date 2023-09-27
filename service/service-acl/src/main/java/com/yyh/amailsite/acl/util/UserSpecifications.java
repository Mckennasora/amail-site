package com.yyh.amailsite.acl.util;

import cn.hutool.core.util.StrUtil;
import com.yyh.amailsite.acl.model.user.dto.UserListDto;
import com.yyh.amailsite.acl.model.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class UserSpecifications {

    public static Specification<User> withUserListDto(UserListDto userListDto) {
        String id = userListDto.getId();
        String username = userListDto.getUsername();
        String userNickname = userListDto.getUserNickname();
        String gender = userListDto.getGender();
        String userEmail = userListDto.getUserEmail();
        String userPhone = userListDto.getUserPhone();
        String createTime = userListDto.getCreateTime();
        String updateTime = userListDto.getUpdateTime();

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StrUtil.isNotBlank(id)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("id"), "%" + id + "%"));
            }
            if (StrUtil.isNotBlank(username)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (StrUtil.isNotBlank(userNickname)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userNickname"), "%" + userNickname + "%"));
            }
            if (StrUtil.isNotBlank(userEmail)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userEmail"), "%" + userEmail + "%"));
            }
            if (StrUtil.isNotBlank(userPhone)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userPhone"), "%" + userPhone + "%"));
            }
            if (StrUtil.isNotBlank(createTime)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("createTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + createTime + "%"));
            }
            if (StrUtil.isNotBlank(updateTime)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("updateTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + updateTime + "%"));
            }
            if (StrUtil.isNotBlank(gender)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), gender));
            }
            return predicate;
        };
    }
}