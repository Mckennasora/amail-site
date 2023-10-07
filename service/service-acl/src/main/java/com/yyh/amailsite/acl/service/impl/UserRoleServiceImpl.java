package com.yyh.amailsite.acl.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.yyh.amailsite.acl.model.role.entity.Role;
import com.yyh.amailsite.acl.model.user.entity.User;
import com.yyh.amailsite.acl.model.userrole.dto.UserRoleAddDto;
import com.yyh.amailsite.acl.model.userrole.entity.UserRole;
import com.yyh.amailsite.acl.model.userrole.vo.UserRoleVo;
import com.yyh.amailsite.acl.repo.RoleRepository;
import com.yyh.amailsite.acl.repo.UserRepository;
import com.yyh.amailsite.acl.repo.UserRoleRepository;
import com.yyh.amailsite.acl.service.UserRoleService;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserRoleVo> addUserRole(UserRoleAddDto userRoleAddDto) {
        String[] roleIds = userRoleAddDto.getRoleIds();
        String userId = userRoleAddDto.getUserId();

        Optional<User> byUserId = userRepository.findById(userId);
        if (!byUserId.isPresent()) {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到用户信息");
        }

        ArrayList<UserRoleVo> userRoleVos = new ArrayList<>();
        for (String roleId : roleIds) {
            Optional<Role> byRoleId = roleRepository.findById(roleId);
            if (!byRoleId.isPresent()) {
                throw new AmailException(ResultCodeEnum.FAIL, "找不到角色信息");
            }
            UserRole byUserIdAndAndRoleId = userRoleRepository.findByUserIdAndAndRoleId(userId, roleId);
            if (byUserIdAndAndRoleId != null) {
                throw new AmailException(ResultCodeEnum.FAIL, "该用户角色已存在");
            }
            UserRole userRole = new UserRole();
            String shortUUID = ShortUUIDGenerator.generateShortUUID();
            userRole.setId(shortUUID);
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleRepository.save(userRole);
            userRoleVos.add(getSafetyUserRoleVo(userRole));
        }
        return userRoleVos;
    }

    @Override
    public void deleteByUserRoleIds(String[] userRoleIds) {
        List<UserRole> userRoleByIds = getUserRoleByIds(userRoleIds);
        userRoleByIds.forEach(role -> role.setIsDeleted(1));
        userRoleRepository.saveAll(userRoleByIds);
    }

    private List<UserRole> getUserRoleByIds(String[] ids) {
        List<UserRole> allById = userRoleRepository.findAllById(Arrays.asList(ids));
        if (!allById.isEmpty()) {
            return allById;
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到该记录");
        }
    }

    @Override
    public List<UserRoleVo> findUserRoleByUserId(String userId) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        userRoles.sort(Comparator.comparing(UserRole::getRoleId));

        List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        List<Role> allById = roleRepository.findByIdInOrderByIdAsc(roleIds);
        if (!allById.isEmpty()) {
            ArrayList<UserRoleVo> userRoleVos = new ArrayList<>();
            for (int i = 0; i < allById.size(); i++) {
                Role role = allById.get(i);
                UserRoleVo userRoleVo = new UserRoleVo();
                userRoleVo.setId(userRoles.get(i).getId());
                userRoleVo.setRoleId(role.getId());
                userRoleVo.setRoleName(role.getRoleName());
                userRoleVo.setRoleArrPermission(role.getRoleArrPermission().split(","));
                userRoleVo.setCreateTime(role.getCreateTime());
                userRoleVo.setUpdateTime(role.getUpdateTime());
                userRoleVos.add(userRoleVo);
            }
            return userRoleVos;
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "无权限");
        }
    }

    private UserRoleVo getSafetyUserRoleVo(UserRole userRole) {
        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setId(userRole.getId());
        userRoleVo.setUserId(userRole.getUserId());
        userRoleVo.setRoleId(userRole.getRoleId());
        userRoleVo.setCreateTime(userRole.getCreateTime());
        userRoleVo.setUpdateTime(userRole.getUpdateTime());
        return userRoleVo;
    }
}
