package com.yyh.amailsite.acl.model.predi;

import com.yyh.amailsite.acl.model.dto.UserListDto;
import com.yyh.amailsite.acl.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Date;

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
            if (id != null && !id.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("id"), "%" + id + "%"));
            }
            if (username != null && !username.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (userNickname != null && !userNickname.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userNickname"), "%" + userNickname + "%"));
            }
            if (userEmail != null && !userEmail.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userEmail"), "%" + userEmail + "%"));
            }
            if (userPhone != null && !userPhone.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userPhone"), "%" + userPhone + "%"));
            }
            if (createTime != null && !createTime.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.function("date_format", String.class, root.get("createTime"), criteriaBuilder.literal("%Y-%m-%d %H:00:00")), "%" + createTime + "%"));
            }
            if (updateTime != null && !updateTime.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("updateTime"), "%" + updateTime + "%"));
            }
            if (gender != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), gender));
            }
            return predicate;
        };
    }
}