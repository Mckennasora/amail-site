package com.yyh.amailsite.acl.model.role.dto;

import lombok.Data;

@Data
public class RoleListDto {
    private String id;
    private String roleName;
    private String roleArrPermission;
    private String createTime;
    private String createTimeSort;
    private String updateTime;
    private String updateTimeSort;
}
