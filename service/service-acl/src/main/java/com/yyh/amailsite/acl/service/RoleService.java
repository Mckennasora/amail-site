package com.yyh.amailsite.acl.service;

import com.yyh.amailsite.acl.model.role.dto.RoleAddDto;
import com.yyh.amailsite.acl.model.role.dto.RoleListDto;
import com.yyh.amailsite.acl.model.role.dto.RoleUpdateDto;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import org.springframework.data.domain.Page;

public interface RoleService {

    RoleVo getRoleVo(String id);

    Page<RoleVo> findRoleListPage(int page, int size, RoleListDto roleListDto);

    void updateRole(RoleUpdateDto roleUpdateDto);

    void deleteRole(String id);

    void batchDeleteRole(String[] roleIds);

    RoleVo addRole(RoleAddDto roleAddDto);
}
