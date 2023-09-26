package com.yyh.amailsite.acl.controller;

import com.yyh.amailsite.acl.model.dto.UserListDto;
import com.yyh.amailsite.acl.model.dto.UserLoginDto;
import com.yyh.amailsite.acl.model.dto.UserRegisterDto;
import com.yyh.amailsite.acl.model.dto.UserUpdateDto;
import com.yyh.amailsite.acl.model.vo.UserVo;
import com.yyh.amailsite.acl.service.UserService;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
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

    @GetMapping("/info/{id}")
    public Result<UserVo> getUserInfo(@PathVariable String id) {
        UserVo userInfo = userService.getUserInfo(id);
        return Result.ok(userInfo);
    }

    @PostMapping("/register")
    public Result<UserVo> userRegister(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        UserVo userInfo = userService.register(userRegisterDto);
        return Result.ok(userInfo);
    }

    @PostMapping("/login")
    public Result<UserVo> userLogin(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        UserVo userInfo = userService.login(userLoginDto);
        return Result.ok(userInfo);
    }

    @GetMapping("/checklogin")
    public Result<Boolean> checkLogin() {
        userService.checkLogin();
        return Result.ok(true);
    }

    @PostMapping("/list/{page}")
    public Result<Page<UserVo>> userList(@PathVariable int page, @RequestParam(defaultValue = "10") int size,
                                         @RequestBody UserListDto userListDto) {
        return Result.ok(userService.findUserListPage(page, size, userListDto));
    }

    @PutMapping("/user/{id}")
    public Result<Boolean> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto,BindingResult bindingResult) {
        ValidateParams.validateRequestParams(bindingResult);
        //todo
        return null;
    }

    @DeleteMapping("/user/{id}")
    public Result<Boolean> deleteUser(@PathVariable int id) {
        //todo
        return null;
    }

    @DeleteMapping("/user")
    public Result<Boolean> deleteUserBatch(@RequestParam int[] userId) {
        //todo
        return null;
    }


}

