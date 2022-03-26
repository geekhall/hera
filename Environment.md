# 工程环境
## 工程结构

### 父工程

管理依赖版本和公共依赖，使用pom类型管理依赖的版本
```xml
<packaging>pom</packaging>
<dependencyManagement>
 
</dependencyManagement>
```

### 子模块
gateway：API网关
service：
  - acl：用户权限管理API接口服务（用户管理、角色管理、权限管理等）
  - cms：CMS-API接口
  - edu：教学相关API接口
  - sms：短信API接口
  - blog：博客API接口
  - order：订单API接口
  - oss：阿里云OSS-API接口
  - statistics：统计报表API接口
  - ucenter：会员API接口
  - vod：视频点播API接口

## 后台

### DB SQL
参考`database.sql`文件



### 添加MySQL连接配置

* 驱动名
  -（SpringBoot2.0，内置JDBC5驱动）：com.mysql.jdbc.Driver
  -（SpringBoot2.1以上，内置JDBC8驱动）：com.mysql.cj.jdbc.Driver
* URL
    - MySQL 5.7 ：jdbc:mysql://localhost:3316/olympians?characterEncoding=UTF-8&useSSL=false
    - MySQL 8.0 ：jdbc:mysql://localhost:3316/olympians?userUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai

否则会报时区错误

### 配置MybatisPlus 的自动填充功能

数据库添加插入和更新字段：
```sql
create table t_xxx
(
-- ...
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间'
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

### 配置MybatisPlus分页插件

```java
@Configuration
public class MybatisPlusConfig {
    /**
     * 插件配置
     * @return 拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 创建并配置分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        // 添加分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
```

测试

```java
@SpringBootTest
class ServerApplicationTests {
    @Test
    void testPage(){
        // 1. 创建page对象
        // 2. 传入两个参数：当前页和每页显示的记录数
        Page<User> page = new Page<>(1, 3);
        // 调用分页查询的方法
        userMapper.selectPage(page, null);

        System.out.println(page.getPages());    // 总页数
        System.out.println(page.getCurrent());  // 当前页
        System.out.println(page.getRecords());  // 当前页内容
        System.out.println(page.getSize());     // 每页显示的记录数
        System.out.println(page.getTotal());    // 总记录数
        System.out.println(page.hasNext());     // 有下一页
        System.out.println(page.hasPrevious()); // 有上一页
    }
}
```


### 添加逻辑删除功能

表中添加逻辑删除字段

```sql
alter table `h_user` add column `deleted` tinyint default 0 comment '逻辑删除';
```

配置启用逻辑删除：

```yaml
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: deleted # 全局逻辑删除的实体字段名（since 3.3.0，配置后实体bean类可以不配置@TableLogic注解）
      logic-delete-value: 1       # 逻辑已删除值（默认为1）
      logic-not-delete-value: 0   # 逻辑未删除值（默认为0）
```

实体类添加逻辑删除字段，并添加`@TableLogic` 注解

```java
class User{
    // ...
    @TableLogic
    @ApiModelProperty("逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
```

元对象处理器接口添加`deleted`字段的默认值

```java
@Component
public class UserMetaObjectHandler implements MetaObjectHandler {
    /**
     * 使用MybatisPlus添加时，该方法会被调用
     * @param metaObject 元数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // ...
        this.setFieldValByName("deleted", 0, metaObject);
    }

}
```


### 性能分析插件

MybatisPlus3.2.0版本删除，官方推荐使用第三方工具分析


### 创建代码生成模块generator

创建Maven新模块generator，添加如下依赖项：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.1</version>
    </dependency>

    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.5.1</version>
    </dependency>

    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.31</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>io.swagger</groupId>
        <artifactId>swagger-core</artifactId>
        <version>2.0.0-rc2</version>
    </dependency>

    <dependency>
        <groupId>io.swagger</groupId>
        <artifactId>swagger-annotations</artifactId>
        <version>1.5.22</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.22</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### 添加Generator代码
