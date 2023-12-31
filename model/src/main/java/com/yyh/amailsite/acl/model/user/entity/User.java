package com.yyh.amailsite.acl.model.user.entity;

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
@Table(schema = "user")
@DynamicInsert
@DynamicUpdate
@Where(clause = "isDeleted = 0")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String userNickname;
    private String gender;
    private String userEmail;
    private String userPhone;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
}
