package com.yyh.amailsite.acl.service;

import com.yyh.amailsite.acl.model.user.dto.*;
import com.yyh.amailsite.acl.model.user.vo.UserVo;
import org.springframework.data.domain.Page;

public interface UserService {

    UserVo getUserVo(String id);

    UserVo register(UserRegisterDto userRegisterDto);

    UserVo login(UserLoginDto userLoginDto);

    Page<UserVo> findUserListPage(int page, int size, UserListDto userListDto);

    void checkLogin();

    void updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(String id);

    void batchDeleteUser(String[] userId);

    UserVo add(UserAddDto userAddDto);
}
