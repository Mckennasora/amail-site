package com.yyh.amailsite.acl.model.user.dto;

import lombok.Data;

@Data
public class UserListDto {
    private String id;
    private String username;
    private String userNickname;
    private String gender;
    private String userEmail;
    private String userPhone;
    private String createTime;
    private String createTimeSort;
    private String updateTime;
    private String updateTimeSort;
}
