package com.yyh.amailsite.mail.model.mailplan.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class MailPlanAddDto {
    private String[] arrSysScheduleId;
    private String[] arrDIYScheduleId;
    private String[] arrPhotoUrl;
    @Email
    private String toWho;
    @Size(min = 0, max = 32, message = "主题长度限制32")
    private String subject;
    @Size(min = 0, max = 1024, message = "正文限制1024")
    private String mainBody;
    @Size(min = 0, max = 256, message = "备注长度限制256")
    private String remarks;
}
