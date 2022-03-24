# 谷粒商城 Environment

## 后台

### DB SQL

```sql
drop table if exists `h_user`;
create table h_user
(
    id BIGINT(20) not null comment '主键ID',
    name VARCHAR(30) null default null comment '姓名',
    age int(11) null default null comment '年龄',
    email varchar(50) null default null comment '邮箱',
    version int(11) null default null comment '版本号',
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


## 配置MybatisPlus 的自动填充功能

数据库添加插入和更新字段：
```sql
create table t_xxx
(
-- ...
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间',
-- ...
)
```

实体Bean上添加注解

```java
class User{
    // ...
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
```

新增Handler实现类实现`MetaObjectHandler`接口，
注意这里setFieldValByName方法的第一个参数应该是实体bean的字段名，而不是数据库表的字段名。

```java
@Component
public class UserMetaObjectHandler implements MetaObjectHandler {
    /**
     * 使用MybatisPlus添加时，该方法会被调用
     * @param metaObject 元数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 使用MybatisPlus修改时，该方法会被调用
     * @param metaObject 元数据对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
```

这里注意数据库的类型和实体Bean的类型需要对应上，不能混用，否则会爆类型转换错误。

* LocalTime 对应 time
* LocalDate 对应 date
* LocalDateTime 对应 timestamp或者 datetime类型


### 乐观锁

数据库表中添加`version`字段

```sql
create table h_user{
    -- ...
    version int(11) null default null comment '版本号',
}

```

对应实体类属性添加版本号属性

```java
public class User{
    // ...
    @Version
    @ApiModelProperty("版本号")
    private Integer version;    
}
```

配置乐观锁插件

SpringBoot 注解方式：
```java
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
```