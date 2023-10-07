package com.yyh.amailsite.mail.model.mailplan.dto;

import lombok.Data;

@Data
public class MailPlanListDto {
    private String id;
    private String userId;
    private String arrSysScheduleId;
    private String arrDIYScheduleId;
    private String toWho;
    private String subject;
    private String mainBody;
    private String arrPhotoUrl;
    private String remarks;
    private String createTime;
    private String createTimeSort;
    private String updateTime;
    private String updateTimeSort;
    private Integer isDeleted;

}
