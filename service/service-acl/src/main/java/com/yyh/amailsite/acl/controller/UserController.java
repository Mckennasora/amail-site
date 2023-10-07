package com.yyh.amailsite.acl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.yyh.amailsite.acl.model.user.dto.*;
import com.yyh.amailsite.acl.model.user.vo.UserVo;
import com.yyh.amailsite.acl.service.UserService;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<UserVo> userLogin(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        UserVo userInfo = userService.login(userLoginDto);
        return Result.ok(userInfo);
    }

    @PostMapping("/register")
    public Result<UserVo> userRegister(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        UserVo userInfo = userService.register(userRegisterDto);
        return Result.ok(userInfo);
    }

    @GetMapping("/logout")
    public Result<Boolean> userLogout() {
        userService.logout();
        return Result.ok(true);
    }

    @GetMapping("/checklogin")
    @SaCheckLogin
    public Result<Boolean> checkLogin() {
        return Result.ok(true);
    }

    @GetMapping("/checkAdmin")
    @SaCheckRole("admin")
    public Result<Boolean> checkAdmin() {
        return Result.ok(true);
    }

//    @ApiOperation(value = "新增用户")
    @PostMapping("/")
    @SaCheckRole("admin")
    public Result<UserVo> addUser(@Valid @RequestBody UserAddDto userAddDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        UserVo user = userService.add(userAddDto);
        return Result.ok(user);
    }


    @PutMapping("/")
    @SaCheckRole("admin")
    public Result<Boolean> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto,BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        userService.updateUser(userUpdateDto);
        return Result.ok(true);
    }

    @PutMapping("/info")
    @SaCheckRole("user")
    public Result<Boolean> updateSelf(@Valid @RequestBody UserUpdateDto userUpdateDto,BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        userService.updateSelf(userUpdateDto);
        return Result.ok(true);
    }

    @DeleteMapping("/{id}")
    @SaCheckRole("admin")
    public Result<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return Result.ok(id);
    }

    @DeleteMapping("/")
    @SaCheckRole("admin")
    public Result<String[]> deleteUserBatch(@RequestParam String[] userId) {
        userService.batchDeleteUser(userId);
        return Result.ok(userId);
    }

    @PostMapping("/{page}")
    @SaCheckRole("admin")
    public Result<Page<UserVo>> userList(@PathVariable int page, @RequestParam(defaultValue = "10") int limit,
                                         @RequestBody UserListDto userListDto) {
        return Result.ok(userService.findUserListPage(page, limit, userListDto));
    }

    @GetMapping("/{id}")
    @SaCheckRole("admin")
    public Result<UserVo> getUserInfo(@PathVariable String id) {
        UserVo userInfo = userService.getUserVo(id);
        return Result.ok(userInfo);
    }

    @GetMapping("/info")
    @SaCheckRole("user")
    public Result<UserVo> getSelfInfo() {
        UserVo userInfo = userService.getSelfUserVo();
        return Result.ok(userInfo);
    }

    //todo 注销 批量删除所有信息 应该要用到rabbitmq

}

