package com.yyh.amailsite.acl.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDto {
    private String id;
    @Size(min = 4, max = 16, message = "用户名长度为4-16")
    private String userNickname;
    @Email(message = "邮箱格式错误")
    private String userEmail;
    @Size(min = 1, max = 1, message = "性别男或者女")
    private String gender;
    @Size(min = 4, max = 11, message = "用户名长度为4-11")
    private String userPhone;
}
