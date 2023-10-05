package com.yyh.amailsite.acl.repo;

import com.yyh.amailsite.acl.model.userrole.entity.UserRole;
import com.yyh.amailsite.acl.model.userrole.vo.UserRoleVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    UserRole findByUserIdAndAndRoleId(String UserId, String RoleId);

    List<UserRole> findByUserId(String userId);
}
