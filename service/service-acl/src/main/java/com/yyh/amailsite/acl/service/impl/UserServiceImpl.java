package com.yyh.amailsite.acl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yyh.amailsite.acl.model.dto.UserListDto;
import com.yyh.amailsite.acl.model.dto.UserLoginDto;
import com.yyh.amailsite.acl.model.dto.UserRegisterDto;
import com.yyh.amailsite.acl.model.dto.UserUpdateDto;
import com.yyh.amailsite.acl.model.entity.User;
import com.yyh.amailsite.acl.util.UserSpecifications;
import com.yyh.amailsite.acl.model.vo.UserVo;
import com.yyh.amailsite.acl.repo.UserRepository;
import com.yyh.amailsite.acl.service.UserService;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.HashPasswordGenerator;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserVo register(UserRegisterDto userRegisterDto) {

        String password = userRegisterDto.getPassword();
        String checkPassword = userRegisterDto.getCheckPassword();
        if (!password.equals(checkPassword)) {
            throw new AmailException(ResultCodeEnum.FAIL, "密码不一致");
        }
        String username = userRegisterDto.getUsername();
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

        return getSafetyUser(user);
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
        StpUtil.login(byUsername.getUsername());

        return getSafetyUser(byUsername);
    }

    @Override
    public Page<UserVo> findUserListPage(int page, int size, UserListDto userListDto) {
        String createTimeSortStr = userListDto.getCreateTimeSort();
        String updateTimeSortStr = userListDto.getUpdateTimeSort();
        Sort.Order createTimeSortOrder = null;
        if (createTimeSortStr == null || createTimeSortStr.equals("asc")) {
            createTimeSortOrder = Sort.Order.asc("createTime");
        } else {
            createTimeSortOrder = Sort.Order.desc("createTime");
        }
        Sort.Order updateTimeSortOrder = null;
        if (updateTimeSortStr == null || updateTimeSortStr.equals("asc")) {
            updateTimeSortOrder = Sort.Order.asc("updateTime");
        } else {
            updateTimeSortOrder = Sort.Order.desc("updateTime");
        }
        Sort sort = Sort.by(createTimeSortOrder, updateTimeSortOrder);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<User> userSpecification = UserSpecifications.withUserListDto(userListDto);

        Page<User> userListPage = userRepository.findAll(userSpecification, pageable);
        List<UserVo> userVoListPage = userListPage.get().map(this::getSafetyUser).collect(Collectors.toList());
        return new PageImpl<>(userVoListPage, userListPage.getPageable(), userListPage.getTotalElements());
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

    private User getUserById(String id){
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }else {
            throw new AmailException(ResultCodeEnum.FAIL,"找不到用户");
        }
    }
    private List<User> getUserByIds(String[] ids){
        List<User> allById = userRepository.findAllById(Arrays.asList(ids));
        if(!allById.isEmpty()){
            return allById;
        }else {
            throw new AmailException(ResultCodeEnum.FAIL,"找不到用户");
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
