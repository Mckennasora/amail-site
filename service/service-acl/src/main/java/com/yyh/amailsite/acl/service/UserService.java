package com.yyh.amailsite.acl.service;

import com.yyh.amailsite.acl.model.user.dto.*;
import com.yyh.amailsite.acl.model.user.vo.UserVo;
import org.springframework.data.domain.Page;

public interface UserService {


    UserVo register(UserRegisterDto userRegisterDto);

    UserVo login(UserLoginDto userLoginDto);

    void logout();

    void checkLogin();

    void updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(String id);

    void batchDeleteUser(String[] userId);

    UserVo add(UserAddDto userAddDto);

    UserVo getUserVo(String id);

    Page<UserVo> findUserListPage(int page, int size, UserListDto userListDto);

    UserVo getSelfUserVo();

    void updateSelf(UserUpdateDto userUpdateDto);
}
