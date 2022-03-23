# 谷粒商城 Environment

## DB SQL

```sql
drop table if exists `h_user`;
create table h_user
(
    id BIGINT(20) not null comment '主键ID',
    name VARCHAR(30) null default null comment '姓名',
    age int(11) null default null comment '年龄',
    email varchar(50) null default null comment '邮箱',
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间',
    PRIMARY key (id)
);


drop table if exists `h_user`;
create table h_user
(
    id BIGINT(20) not null comment '主键ID',
    name VARCHAR(30) null default null comment '姓名',
    age int(11) null default null comment '年龄',
    email varchar(50) null default null comment '邮箱',
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间',
    PRIMARY key (id)
);

truncate table h_user;
insert into h_user (id, name, age, email, create_time) values
                                                           (1, 'Jone', 18, 'test1@geekhall.com', now()),
                                                           (2, 'Jack', 19, 'test2@geekhall.com', now()),
                                                           (3, 'Tom', 20, 'test3@geekhall.com', now()),
                                                           (4, 'Jerry', 21, 'test4@geekhall.com', now()),
                                                           (5, 'Tony', 24, 'test5@geekhall.com', now());


select * from h_user;



select * from h_user;


```
