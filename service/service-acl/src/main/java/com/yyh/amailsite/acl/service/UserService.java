package com.yyh.amailsite.acl.service;

import com.yyh.amailsite.acl.model.dto.UserListDto;
import com.yyh.amailsite.acl.model.dto.UserLoginDto;
import com.yyh.amailsite.acl.model.dto.UserRegisterDto;
import com.yyh.amailsite.acl.model.dto.UserUpdateDto;
import com.yyh.amailsite.acl.model.entity.User;
import com.yyh.amailsite.acl.model.vo.UserVo;
import com.yyh.amailsite.common.result.Result;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserVo getUserInfo(String id);

    UserVo register(UserRegisterDto userRegisterDto);

    UserVo login(UserLoginDto userLoginDto);

    Page<UserVo> findUserListPage(int page, int size, UserListDto userListDto);

    void checkLogin();

    void updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(String id);

    void batchDeleteUser(String[] userId);
}
