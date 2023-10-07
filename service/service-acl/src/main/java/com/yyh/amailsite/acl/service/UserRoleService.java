package com.yyh.amailsite.acl.service;

import com.yyh.amailsite.acl.model.role.entity.Role;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import com.yyh.amailsite.acl.model.userrole.dto.UserRoleAddDto;
import com.yyh.amailsite.acl.model.userrole.vo.UserRoleVo;

import java.util.List;

public interface UserRoleService {

    List<UserRoleVo> addUserRole(UserRoleAddDto userRoleAddDto);

    void deleteByUserRoleIds(String[] ids);

    List<UserRoleVo>  findUserRoleByUserId(String userId);
}
