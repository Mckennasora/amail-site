package com.yyh.amailsite.acl.service.impl;

import com.yyh.amailsite.acl.model.role.dto.*;
import com.yyh.amailsite.acl.model.role.entity.Role;
import com.yyh.amailsite.acl.model.role.vo.RoleVo;
import com.yyh.amailsite.acl.repo.RoleRepository;
import com.yyh.amailsite.acl.service.RoleService;
import com.yyh.amailsite.acl.util.RoleSpecifications;
import com.yyh.amailsite.common.exception.AmailException;
import com.yyh.amailsite.common.result.ResultCodeEnum;
import com.yyh.amailsite.common.utils.PageRequestUtils;
import com.yyh.amailsite.common.utils.ShortUUIDGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public RoleVo getRoleVo(String id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()) {
            return getSafetyRole(byId.get());
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "查询失败");
        }
    }


    @Override
    public Page<RoleVo> findRoleListPage(int page, int size, RoleListDto roleListDto) {
        String createTimeSortStr = roleListDto.getCreateTimeSort();
        String updateTimeSortStr = roleListDto.getUpdateTimeSort();
        Sort sort = PageRequestUtils.pageRequestSortTime(createTimeSortStr, updateTimeSortStr);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Role> roleSpecification = RoleSpecifications.withRoleListDto(roleListDto);

        Page<Role> roleListPage = roleRepository.findAll(roleSpecification, pageable);
        List<RoleVo> roleVoListPage = roleListPage.get().map(this::getSafetyRole).collect(Collectors.toList());
        return new PageImpl<>(roleVoListPage, roleListPage.getPageable(), roleListPage.getTotalElements());
    }


    @Override
    public void updateRole(RoleUpdateDto roleUpdateDto) {
        Role role = getRoleById(roleUpdateDto.getId());
        role.setRoleName(roleUpdateDto.getRoleName());
        role.setRoleArrPermission(String.join(",", roleUpdateDto.getRoleArrPermission()));

        roleRepository.save(role);
    }

    @Override
    public void deleteRole(String id) {
        Role roleById = getRoleById(id);
        roleById.setIsDeleted(1);
        roleRepository.save(roleById);
    }

    @Override
    public void batchDeleteRole(String[] roleIds) {
        List<Role> roleByIds = getRoleByIds(roleIds);
        roleByIds.forEach(role -> role.setIsDeleted(1));
        roleRepository.saveAll(roleByIds);
    }

    @Override
    public RoleVo addRole(RoleAddDto roleAddDto) {
        Role save = save(roleAddDto.getRoleName(), roleAddDto.getRoleArrPermission());

        return getSafetyRole(roleRepository.save(save));
    }

    private Role save(String roleName, String[] roleArrPermission) {

        String shortUUID = ShortUUIDGenerator.generateShortUUID();

        Role role = new Role();
        role.setId(shortUUID);
        role.setRoleName(roleName);
        role.setRoleArrPermission(String.join(",", roleArrPermission));

        roleRepository.save(role);
        return role;
    }

    private Role getRoleById(String id) {
        Optional<Role> opt = roleRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到角色");
        }
    }

    private List<Role> getRoleByIds(String[] ids) {
        List<Role> allById = roleRepository.findAllById(Arrays.asList(ids));
        if (!allById.isEmpty()) {
            return allById;
        } else {
            throw new AmailException(ResultCodeEnum.FAIL, "找不到角色");
        }
    }

    private RoleVo getSafetyRole(Role role) {
        RoleVo roleVo = new RoleVo();
        roleVo.setId(role.getId());
        roleVo.setRoleName(role.getRoleName());
        roleVo.setRoleArrPermission((role.getRoleArrPermission().split(",")));
        roleVo.setCreateTime(role.getCreateTime());
        roleVo.setUpdateTime(role.getUpdateTime());
        return roleVo;
    }
}
