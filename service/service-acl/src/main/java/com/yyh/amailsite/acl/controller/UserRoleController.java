package com.yyh.amailsite.acl.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import com.yyh.amailsite.acl.model.userrole.dto.UserRoleAddDto;
import com.yyh.amailsite.acl.model.userrole.vo.UserRoleVo;
import com.yyh.amailsite.acl.service.UserRoleService;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/userrole")
public class UserRoleController {

    public final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/add")
    @SaCheckRole("admin")
    public Result<List<UserRoleVo>> saveByUserIdAndRoleId(@Valid @RequestBody UserRoleAddDto userRoleAddDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        List<UserRoleVo> role = userRoleService.addUserRole(userRoleAddDto);
        return Result.ok(role);
    }

    @DeleteMapping("/")
    @SaCheckRole("admin")
    public Result<Boolean> deleteByUserRoleIds(@RequestParam String[] ids) {
        userRoleService.deleteByUserRoleIds(ids);
        return Result.ok(true);
    }

    @GetMapping("/{userId}")
    public Result< List<UserRoleVo>> findUserRoleByUserId(@PathVariable String userId) {
        List<UserRoleVo> roleList = userRoleService.findUserRoleByUserId(userId);
        return Result.ok(roleList);
    }


}
