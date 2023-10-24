create database if not exists amail_acl;

use amail_acl;

create table if not exists user
(
    id           varchar(256)                       not null primary key,
    username     varchar(256)                       not null comment '账号',
    password     varchar(256)                       not null comment '密码',
    userNickname varchar(256)                       null comment '账号',
    gender       varchar(10)                        null comment '性别',
    userEmail    varchar(512)                       null comment '邮箱',
    userPhone    varchar(128)                       null comment '电话',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted    tinyint  default 0                 null comment '是否删除'
)
    comment '用户表';


INSERT INTO amail_acl.User (id, username, password, userNickname, gender, userEmail, userPhone, createTime, updateTime, isDeleted) VALUES ('04d7f9d5', 'sora', '02a50ea28a35047cb557a0a72ae1699e', '', '', '', '', '2023-10-07 09:42:09', '2023-10-07 09:42:09', 0);


create table role
(
    id                varchar(256)                       not null
        primary key,
    roleName          varchar(256)                       not null comment '账号',
    roleArrPermission varchar(256)                       not null comment '密码',
    createTime        datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime        datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted         tinyint  default 0                 null comment '是否删除'
)
    comment '角色表';


INSERT INTO amail_acl.Role (id, roleName, roleArrPermission, createTime, updateTime, isDeleted) VALUES ('3ece9d14', 'root', 'all', '2023-09-28 17:46:49', '2023-10-07 10:02:18', 0);
INSERT INTO amail_acl.Role (id, roleName, roleArrPermission, createTime, updateTime, isDeleted) VALUES ('6f3f2191', 'user', 'user.selfUpdate,user.selfInfo,user.schedule', '2023-09-28 15:37:06', '2023-10-07 10:08:12', 0);
INSERT INTO amail_acl.Role (id, roleName, roleArrPermission, createTime, updateTime, isDeleted) VALUES ('8569a55b', 'admin', 'admin.dowhateeverhewant', '2023-10-05 21:18:41', '2023-10-07 10:10:00', 0);


create table if not exists userrole
(
    id         varchar(256)                       not null
        primary key,
    userId     varchar(256)                       not null comment '用户Id',
    roleId     varchar(256)                       not null comment '角色Id',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted  tinyint  default 0                 null comment '是否删除'
)
    comment '用户角色表';

INSERT INTO amail_acl.UserRole (id, userId, roleId, createTime, updateTime, isDeleted) VALUES ('71caad2f', '04d7f9d5', '6f3f2191', '2023-10-07 09:42:09', '2023-10-07 09:42:09', 0);
INSERT INTO amail_acl.UserRole (id, userId, roleId, createTime, updateTime, isDeleted) VALUES ('96ff81c2', '04d7f9d5', '8569a55b', '2023-10-07 09:53:33', '2023-10-07 09:53:33', 0);

create database if not exists amail_mail;

use amail_mail;

create table if not exists mailplan
(
    id               varchar(256)                       not null
        primary key,
    userId           varchar(256)                       not null comment '用户Id',
    arrSysScheduleId varchar(2048)                      null comment '系统时刻表ids',
    arrDIYScheduleId varchar(2048)                      null comment '自建时刻表ids',
    toWho            varchar(256)                       not null comment '收件人email',
    subject          varchar(256)                       not null comment '邮件主题',
    mainBody         varchar(2048)                      null comment '正文',
    arrPhotoUrl      varchar(4096)                      null comment '图片地址',
    sendCount        int      default 0                 null comment '发送次数',
    remarks          varchar(256)                       null comment '备注',
    isEnable         tinyint  default 0                 null comment '是否开启',
    createTime       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime       datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted        tinyint  default 0                 null comment '是否删除'
)
    comment '计划表';


create table if not exists mailcron
(
    id         varchar(256)                       not null
        primary key,
    userId     varchar(256)                       not null comment '用户Id',
    cronExpr   varchar(128)                       null comment 'cron表达式',
    useCount   int      default 0                 null comment '发送次数',
    remarks    varchar(256)                       null comment '备注',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted  tinyint  default 0                 null comment '是否删除'
)
    comment 'cron表达式';


create table if not exists mailhistory
(
    id               varchar(256)                       not null
        primary key,
    userId           varchar(256)                       not null comment '用户Id',
    mailPlanId       varchar(256)                       not null comment '计划Id',
    arrSysScheduleId varchar(2048)                      null comment '系统时刻表ids',
    arrDIYScheduleId varchar(2048)                      null comment '自建时刻表ids',
    sendByCronExpr   varchar(2048)                      null comment 'cron表达式',
    sendByCronExprId varchar(2048)                      null comment 'cron表达式id',
    toWho            varchar(256)                       not null comment '收件人email',
    subject          varchar(256)                       not null comment '邮件主题',
    mainBody         varchar(2048)                      null comment '正文',
    arrPhotoUrl      varchar(4096)                      null comment '图片地址',
    tryCount         tinyint  default 0                 null comment '尝试次数',
    remarks          varchar(256)                       null comment '备注',
    isSuccess        tinyint  default 0                 null comment '是否成功',
    createTime       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime       datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted        tinyint  default 0                 null comment '是否删除'
)
    comment '历史记录';
