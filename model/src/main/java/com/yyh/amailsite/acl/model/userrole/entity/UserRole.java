package com.yyh.amailsite.acl.model.userrole.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(schema = "userrole")
@DynamicInsert
@DynamicUpdate
@Where(clause = "isDeleted = 0")
public class UserRole {
    @Id
    private String id;
    private String userId;
    private String roleId;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
}
