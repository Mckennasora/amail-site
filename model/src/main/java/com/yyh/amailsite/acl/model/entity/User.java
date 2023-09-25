package com.yyh.amailsite.acl.model.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(schema = "user")
@DynamicInsert
@DynamicUpdate
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
