package com.yyh.amailsite.acl.controller;

import com.yyh.amailsite.acl.model.role.dto.RoleAddDto;
import com.yyh.amailsite.acl.model.role.dto.RoleListDto;
import com.yyh.amailsite.acl.model.role.dto.RoleUpdateDto;
import com.yyh.amailsite.acl.model.role.entity.Role;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import com.yyh.amailsite.acl.model.userrole.dto.UserRoleAddDto;
import com.yyh.amailsite.acl.model.userrole.vo.UserRoleVo;
import com.yyh.amailsite.acl.service.RoleService;
import com.yyh.amailsite.acl.service.UserRoleService;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Result<List<UserRoleVo>> saveByUserIdAndRoleId(@Valid @RequestBody UserRoleAddDto userRoleAddDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        List<UserRoleVo> role = userRoleService.addUserRole(userRoleAddDto);
        return Result.ok(role);
    }

    @DeleteMapping("/")
    public Result<Boolean> DeleteByUserRoleIds(@RequestParam String[] ids) {
        userRoleService.deleteByUserRoleIds(ids);
        return Result.ok(true);
    }

    @GetMapping("/{userId}")
    public Result<List<RoleVo>> findUserRoleByUserId(@PathVariable String userId) {
        List<RoleVo> roleList = userRoleService.findUserRoleByUserId(userId);
        return Result.ok(roleList);
    }


}
