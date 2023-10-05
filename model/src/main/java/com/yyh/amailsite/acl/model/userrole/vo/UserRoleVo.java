package com.yyh.amailsite.acl.model.userrole.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserRoleVo {
    private String id;
    private String userId;
    private String roleId;
    private Date createTime;
    private Date updateTime;
}
