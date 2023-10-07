package com.yyh.amailsite.acl.config;

import cn.dev33.satoken.stp.StpInterface;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import com.yyh.amailsite.acl.model.userrole.entity.UserRole;
import com.yyh.amailsite.acl.model.userrole.vo.UserRoleVo;
import com.yyh.amailsite.acl.service.RoleService;
import com.yyh.amailsite.acl.service.UserRoleService;
import com.yyh.amailsite.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    private final UserRoleService userRoleService;

    @Autowired
    public StpInterfaceImpl( UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }


    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<UserRoleVo> userRolesByUserId = userRoleService.findUserRoleByUserId((String) loginId);
        ArrayList<String> permissionList = new ArrayList<>();
        userRolesByUserId.forEach(roleVo -> {
            String[] roleArrPermission = roleVo.getRoleArrPermission();
            List<String> permissions = Arrays.asList(roleArrPermission);
            permissionList.addAll(permissions);
        });
        return permissionList;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        List<UserRoleVo> userRolesByUserId = userRoleService.findUserRoleByUserId((String) loginId);

        return userRolesByUserId.stream().map(UserRoleVo::getRoleName).collect(Collectors.toList());
    }

}
