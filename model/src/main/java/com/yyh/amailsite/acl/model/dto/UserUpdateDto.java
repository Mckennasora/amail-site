package com.yyh.amailsite.acl.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
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
    @Size(min = 4, max = 11, message = "手机长度为4-11")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "号码格式错误")
    private String userPhone;
}
