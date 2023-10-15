package com.yyh.amailsite.mail.model.mailcron.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MailCronUpdateDto {
    private String id;
    @Size(max = 256, message = "备注长度限制256")
    private String remarks;
    private String cronExpr;
}
