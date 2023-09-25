package com.yyh.amailsite.acl.service;

import com.yyh.amailsite.acl.model.dto.UserLoginDto;
import com.yyh.amailsite.acl.model.dto.UserRegisterDto;
import com.yyh.amailsite.acl.model.entity.User;
import com.yyh.amailsite.acl.model.vo.UserVo;

public interface UserService {

    UserVo getUserInfo(String id);

    UserVo register(UserRegisterDto userRegisterDto);

    UserVo login(UserLoginDto userLoginDto);
}
