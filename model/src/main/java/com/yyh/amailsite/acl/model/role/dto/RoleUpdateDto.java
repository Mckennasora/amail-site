package com.yyh.amailsite.acl.model.role.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;

@Data
public class RoleUpdateDto {
    private String id;
    @NonNull
    @Pattern(regexp = "[A-Za-z0-9]+", message = "只能包含字母和数字")
    private String roleName;

    @NonNull
    private String[] roleArrPermission;
}
