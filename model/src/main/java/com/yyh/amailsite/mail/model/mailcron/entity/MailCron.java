package com.yyh.amailsite.mail.model.mailcron.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(schema = "mailcron")
@DynamicInsert
@DynamicUpdate
public class MailCron {
    @Id
    private String id;
    private String userId;
    private String cronExpr;
    private Integer useCount;
    private String remarks;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
}