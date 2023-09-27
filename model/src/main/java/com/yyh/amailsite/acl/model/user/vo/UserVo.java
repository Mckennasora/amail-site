package com.yyh.amailsite.acl.model.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private String id;
    private String username;
    private String userNickname;
    private String gender;
    private String userEmail;
    private String userPhone;
    private Date createTime;
    private Date updateTime;
}
