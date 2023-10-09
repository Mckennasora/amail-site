package com.yyh.amailsite.mail.model.mailhistory.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(schema = "mailhistroy")
@DynamicInsert
@DynamicUpdate
public class MailHistory {
    @Id
    private String id;
    private String userId;
    private String mailPlanId;
    private String arrSysScheduleId;
    private String arrDIYScheduleId;
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
