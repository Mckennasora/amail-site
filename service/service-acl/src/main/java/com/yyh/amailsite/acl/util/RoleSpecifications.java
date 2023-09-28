package com.yyh.amailsite.acl.util;

import cn.hutool.core.util.StrUtil;
import com.yyh.amailsite.acl.model.role.dto.RoleListDto;
import com.yyh.amailsite.acl.model.role.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class RoleSpecifications {

    public static Specification<Role> withRoleListDto(RoleListDto roleListDto) {
        String id = roleListDto.getId();
        String roleName = roleListDto.getRoleName();
        String roleArrPermission = roleListDto.getRoleArrPermission();
        String createTime = roleListDto.getCreateTime();
        String updateTime = roleListDto.getUpdateTime();

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StrUtil.isNotBlank(id)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("id"), "%" + id + "%"));
            }
            if (StrUtil.isNotBlank(roleName)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("roleName"), "%" + roleName + "%"));
            }
            if (StrUtil.isNotBlank(roleArrPermission)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("roleArrPermission"), "%" + roleArrPermission + "%"));
            }
            if (StrUtil.isNotBlank(createTime)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("createTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + createTime + "%"));
            }
            if (StrUtil.isNotBlank(updateTime)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("updateTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + updateTime + "%"));
            }
            return predicate;
        };
    }
}