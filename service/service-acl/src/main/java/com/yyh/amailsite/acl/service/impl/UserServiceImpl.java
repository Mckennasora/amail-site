package com.yyh.amailsite.acl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yyh.amailsite.acl.model.user.dto.*;
import com.yyh.amailsite.acl.model.user.entity.User;
import com.yyh.amailsite.acl.model.userrole.dto.UserRoleAddDto;
import com.yyh.amailsite.acl.service.UserRoleService;
import com.yyh.amailsite.acl.util.UserSpecifications;
import com.yyh.amailsite.acl.model.user.vo.UserVo;
import com.yyh.amailsite.acl.repo.UserRepository;
import com.yyh.amailsite.acl.service.UserService;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.HashPasswordGenerator;
import com.yyh.amailsite.common.utils.PageRequestUtils;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }


    @Override
    public UserVo register(UserRegisterDto userRegisterDto) {

        String password = userRegisterDto.getPassword();
        String checkPassword = userRegisterDto.getCheckPassword();
        if (!password.equals(checkPassword)) {
            throw new AmailException(ResultCodeEnum.FAIL, "密码不一致");
        }

        String username = userRegisterDto.getUsername();
        User save = save(username, password);
        //todo 角色信息加载到内存
        userRoleService.addUserRole(new UserRoleAddDto(new String[]{"6f3f2191"}, save.getId()));

        return getSafetyUser(save);
    }

    @Override
    public UserVo login(UserLoginDto userLoginDto) {
        User byUsername = userRepository.findByUsername(userLoginDto.getUsername());
        if (byUsername == null) {
            throw new AmailException(ResultCodeEnum.FAIL, "用户不存在");
        }

        String encryptionPassword = HashPasswordGenerator.encryptionPassword(
                userLoginDto.getUsername(), userLoginDto.getPassword());
        if (!byUsername.getPassword().equals(encryptionPassword)) {
            throw new AmailException(ResultCodeEnum.FAIL, "用户名与密码不匹配");
        }

        StpUtil.login(byUsername.getId());

        return getSafetyUser(byUsername);
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public void checkLogin() {
        StpUtil.checkLogin();
    }

    @Override
    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = getUserById(userUpdateDto.getId());
        user.setUserNickname(userUpdateDto.getUserNickname());
        user.setGender(userUpdateDto.getGender());
        user.setUserEmail(userUpdateDto.getUserEmail());
        user.setUserPhone(userUpdateDto.getUserPhone());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        User userById = getUserById(id);
        userById.setIsDeleted(1);
        userRepository.save(userById);
    }

    @Override
    public void batchDeleteUser(String[] userIds) {
        List<User> userByIds = getUserByIds(userIds);
        userByIds.forEach(user -> user.setIsDeleted(1));
        userRepository.saveAll(userByIds);
    }

    @Override
    public UserVo add(UserAddDto userAddDto) {
        User save = save(userAddDto.getUsername(), userAddDto.getPassword());
        return getSafetyUser(save);
    }

    @Override
    public UserVo getUserVo(String id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return getSafetyUser(byId.get());
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "查询失败");
        }
    }

    @Override
    public Page<UserVo> findUserListPage(int page, int size, UserListDto userListDto) {
        String createTimeSortStr = userListDto.getCreateTimeSort();
        String updateTimeSortStr = userListDto.getUpdateTimeSort();
        Sort sort = PageRequestUtils.pageRequestSortTime(createTimeSortStr, updateTimeSortStr);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<User> userSpecification = UserSpecifications.withUserListDto(userListDto);

        Page<User> userListPage = userRepository.findAll(userSpecification, pageable);
        List<UserVo> userVoListPage = userListPage.get().map(this::getSafetyUser).collect(Collectors.toList());
        return new PageImpl<>(userVoListPage, userListPage.getPageable(), userListPage.getTotalElements());
    }

    @Override
    public UserVo getSelfUserVo() {
        String loginId = (String) StpUtil.getLoginId();
        return getUserVo(loginId);
    }

    @Override
    public void updateSelf(UserUpdateDto userUpdateDto) {
        String loginId = (String) StpUtil.getLoginId();
        userUpdateDto.setId(loginId);
        updateUser(userUpdateDto);
    }


    private User save(String username, String password) {

        String shortUUID = ShortUUIDGenerator.generateShortUUID();
        String encryptionPassword = HashPasswordGenerator.encryptionPassword(username, password);

        User user = new User();
        user.setId(shortUUID);
        user.setUsername(username);
        user.setPassword(encryptionPassword);
        user.setUserNickname("");
        user.setGender("");
        user.setUserEmail("");
        user.setUserPhone("");

        synchronized (username.intern()) {
            User byUsername = userRepository.findByUsername(username);
            if (byUsername != null) {
                throw new AmailException(ResultCodeEnum.FAIL, "用户名重复");
            }
            try {
                userRepository.save(user);
            } catch (Exception e) {
                throw new AmailException(ResultCodeEnum.SERVICE_ERROR, "注册失败");
            }
        }
        return user;
    }

    private User getUserById(String id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到用户");
        }
    }

    private List<User> getUserByIds(String[] ids) {
        List<User> allById = userRepository.findAllById(Arrays.asList(ids));
        if (!allById.isEmpty()) {
            return allById;
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到用户");
        }
    }

    private UserVo getSafetyUser(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setUserNickname(user.getUserNickname());
        userVo.setGender(user.getGender());
        userVo.setUserEmail(user.getUserEmail());
        userVo.setUserPhone(user.getUserPhone());
        userVo.setCreateTime(user.getCreateTime());
        userVo.setUpdateTime(user.getUpdateTime());
        return userVo;
    }
}
