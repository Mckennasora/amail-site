package com.yyh.amailsite.acl.model.role.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleVo {
    private String id;
    private String roleName;
    private String[] roleArrPermission;
    private Date createTime;
    private Date updateTime;
}
