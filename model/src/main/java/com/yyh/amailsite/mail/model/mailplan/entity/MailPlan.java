package com.yyh.amailsite.mail.model.mailplan.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(schema = "mailplan")
@DynamicInsert
@DynamicUpdate
public class MailPlan {
    @Id
    private String id;
    private String userId;
    private String arrSysScheduleId;
    private String arrDIYScheduleId;
    private String toWho;
    private String subject;
    private String mainBody;
    private String arrPhotoUrl;
    private Integer sendCount;
    private String remarks;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

}
