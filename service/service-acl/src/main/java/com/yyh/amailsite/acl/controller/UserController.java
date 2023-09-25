package com.yyh.amailsite.acl.controller;

import com.yyh.amailsite.acl.model.dto.UserLoginDto;
import com.yyh.amailsite.acl.model.dto.UserRegisterDto;
import com.yyh.amailsite.acl.model.entity.User;
import com.yyh.amailsite.acl.model.vo.UserVo;
import com.yyh.amailsite.acl.service.UserService;
import com.yyh.amailsite.common.result.Result;
import com.yyh.amailsite.common.utils.ValidateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/info/{id}")
    public Result<UserVo> getUserInfo(@PathVariable String id){
        UserVo userInfo = userService.getUserInfo(id);
        return Result.ok(userInfo);
    }

    @PostMapping("/register")
    public Result<UserVo> userRegister(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult){
        ValidateParams.validateRequestParams(bindingResult);
        UserVo userInfo = userService.register(userRegisterDto);
        return Result.ok(userInfo);
    }

    @PostMapping("/login")
    public Result<UserVo> userLogin(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult){
        ValidateParams.validateRequestParams(bindingResult);
        UserVo userInfo = userService.login(userLoginDto);
        return Result.ok(userInfo);
    }


}

