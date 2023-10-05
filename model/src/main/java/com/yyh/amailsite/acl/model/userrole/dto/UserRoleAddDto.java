package com.yyh.amailsite.acl.model.userrole.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRoleAddDto {
    @NonNull
    private String[] roleIds;

    @NonNull
    @Pattern(regexp = "[A-Za-z0-9]+", message = "只能包含字母和数字")
    @Size(min = 8, max = 8, message = "id长度为8")
    private String userId;
}
