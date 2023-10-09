package com.yyh.amailsite.mail.model.mailhistory.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MailHistoryVo {
    private String id;
    private String userId;
    private String mailPlanId;
    private String arrSysScheduleId;
    private List<String> sysScheduleIdList;
    private String arrDIYScheduleId;
    private List<String> DIYScheduleIdList;
    private String sendByCronExpr;
    private String toWho;
    private String subject;
    private String mainBody;
    private String arrPhotoUrl;
    private Integer tryCount;
    private String remarks;
    private Date createTime;
    private Date updateTime;
    private Integer isSuccess;
    private Integer isDeleted;
}
