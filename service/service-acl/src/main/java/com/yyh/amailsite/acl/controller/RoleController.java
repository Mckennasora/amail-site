package com.yyh.amailsite.acl.controller;

import com.yyh.amailsite.acl.model.role.dto.RoleAddDto;
import com.yyh.amailsite.acl.model.role.dto.RoleListDto;
import com.yyh.amailsite.acl.model.role.dto.RoleUpdateDto;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import com.yyh.amailsite.acl.service.RoleService;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    public final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public Result<RoleVo> save(@Valid @RequestBody RoleAddDto roleAddDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        RoleVo role = roleService.addRole(roleAddDto);
        return Result.ok(role);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        return Result.ok(id);
    }

    @DeleteMapping("/")
    public Result<String[]> deleteRoleBatch(@RequestParam String[] roleId) {
        roleService.batchDeleteRole(roleId);
        return Result.ok(roleId);
    }

    @PutMapping("/")
    public Result<Boolean> updateUser(@Valid @RequestBody RoleUpdateDto roleUpdateDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        roleService.updateRole(roleUpdateDto);
        return Result.ok(true);
    }

    @GetMapping("/{id}")
    public Result<RoleVo> getRoleInfo(@PathVariable String id) {
        RoleVo roleInfo = roleService.getRoleVo(id);
        return Result.ok(roleInfo);
    }

    @PostMapping("/{page}")
    public Result<Page<RoleVo>> roleList(@PathVariable int page, @RequestParam(defaultValue = "15") int limit,
                                         @RequestBody RoleListDto roleListDto) {
        return Result.ok(roleService.findRoleListPage(page, limit, roleListDto));
    }

}
