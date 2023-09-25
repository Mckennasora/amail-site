package com.yyh.amailsite.acl.service.impl;

import com.yyh.amailsite.acl.model.dto.UserLoginDto;
import com.yyh.amailsite.acl.model.dto.UserRegisterDto;
import com.yyh.amailsite.acl.model.entity.User;
import com.yyh.amailsite.acl.model.vo.UserVo;
import com.yyh.amailsite.acl.repo.UserRepository;
import com.yyh.amailsite.acl.service.UserService;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;

import java.util.Optional;

import com.yyh.amailsite.common.utils.HashPasswordGenerator;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserVo getUserInfo(String id) {
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


        return null;
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
