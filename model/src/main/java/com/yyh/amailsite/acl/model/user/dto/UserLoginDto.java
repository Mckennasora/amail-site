package com.yyh.amailsite.acl.model.user.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserLoginDto {
    @NonNull
    @Size(min = 4, max = 16, message = "用户名长度为4-16")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "只能包含字母和数字")
    private String username;
    @NonNull
    @Size(min = 4, max = 16, message = "密码长度为4-16")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "只能包含字母和数字")
    private String password;
}
