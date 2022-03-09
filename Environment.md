## 工具及软件版本

* IDE: IDEA 2021.2
* JDK: JDK8+
* 构建工具： IDEA自带 Maven 3.6.3
* MySQL：MAMP MySQL 5.7.34
* SpringBoot ： 2.6.4
* MyBatisPlus： 3.5.1

## 创建数据库
### 准备工作

开始使用MAMP之前需要使用自带的PMA（phpMyAdmin）网页工具执行以下命令，来修改root用户支持远程登陆，

这样我们就可以通过NaviCat等工具来远程连接管理MySQL了。

```sql
update mysql.user set authentication_string=PASSWORD('your_password'),plugin='mysql_native_password' where user='root';

-- （1）修改host允许远程登录
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'your_password' WITH GRANT OPTION;

-- （2）修改验证方式允许密码登录
update mysql.user set authentication_string=PASSWORD('your_password'),plugin='mysql_native_password' where user='root';

```

```sql

-- 创建数据库
create database olympians default charset=utf8;

-- 创建用户并授权;
use mysql;
CREATE USER 'zeus'@'%' IDENTIFIED BY 'yy123456';
flush privileges;

-- 为用户添加权限
GRANT ALL ON olympians.* TO 'zeus'@'%';
flush privileges;
```

添加数据

```sql

use olympians;

drop table  if EXISTS `h_product`;
CREATE TABLE `h_product`  (
    `id` BIGINT(20) NOT NULL COMMENT '主键ID',
    `name` varchar(255) NOT NULL  COMMENT '商品名称',
    `description` varchar(255) NULL DEFAULT NULL COMMENT '商品描述',
    `brand` varchar(255) NULL DEFAULT NULL COMMENT '品牌',
    `is_deleted` tinyint(1) default 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;
delete from h_product;
insert into h_product (`id`, `name`, `description`, `brand`) values(1,'MacBookPro','Mac book pro', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(2,'MacBookAir','Mac book air', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(3,'iPhone13','iphone13 pro max', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(4,'iMac','iphone13 pro max', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(5,'iWatch','iphone13 pro max', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(6,'MacMini','iphone13 pro max', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(7,'AirPots','iphone13 pro max', 'Apple');
insert into h_product (`id`, `name`, `description`, `brand`) values(8,'Surface','Surface book', 'Microsoft');
insert into h_product (`id`, `name`, `description`, `brand`) values(9,'Honor','Honor phone', 'Huawei');

drop table if EXISTS `h_role`;
create table `h_role`(
   `id` BIGINT(20) NOT NULL COMMENT '主键ID',
   `name` varchar(255) NOT NULL  COMMENT '角色名称',
   `description` varchar(255) NULL DEFAULT NULL COMMENT '角色描述',
	PRIMARY key (`id`) using BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;
delete from h_role;
insert into h_role(`id`, `name`, `description`) values(1, 'Ares','战神');


drop table if exists `h_weapon`;
create table `h_weapon`(
 `id` BIGINT(20) NOT NULL COMMENT '主键ID',
 `name` varchar(255) NOT NULL  COMMENT '武器名称',
 `description` varchar(255) NULL DEFAULT NULL COMMENT '武器描述',
  PRIMARY key (`id`) using BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;
delete from h_weapon;
insert into h_weapon(`id`,`name`,`description`) values(1, '雷神之锤','雷神Thor的锤子');


DROP TABLE IF EXISTS h_player;
CREATE TABLE h_player
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NOT NULL  COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id) using BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;

DELETE FROM h_player;

INSERT INTO h_player (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@geekhall.cn'),
(2, 'Jack', 20, 'test2@geekhall.cn'),
(3, 'Tom', 28, 'test3@geekhall.cn'),
(4, 'Sandy', 21, 'test4@geekhall.cn'),
(5, 'Billie', 24, 'test5@geekhall.cn');


DROP TABLE IF EXISTS h_article;
CREATE TABLE h_article
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  title VARCHAR(255) NOT NULL  COMMENT '标题',
  subtitle VARCHAR(255) NOT NULL  COMMENT '副标题',
  summary TINYTEXT NULL DEFAULT NULL COMMENT '摘要',
  content TEXT NOT NULL  COMMENT '正文',
  author_id BIGINT(20) NULL DEFAULT NULL COMMENT '作者ID',
  create_date BIGINT(20) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (id) using BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;

```
建表时需要注意，最好不要使用SQL关键字作为表的字段，比如使用describe，desc等的话
会报SQL语句错误。

## 创建工程

### 创建父工程

创建SpringBoot2Starter父工程并添加依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>    
</dependencies>
```

删除src等文件夹

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


创建SpringBoot工程，引入Spring Boot Starter工程
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.4</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

引入依赖：
* spring-boot-starter
* spring-boot-starter-test
* spring-boot-starter-web
* mybatis-plus-boot-starter
* druid-spring-boot-starter
* lombok

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
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
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.2.8</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

### 添加MySQL连接配置

* 驱动名
  -（SpringBoot2.0，内置JDBC5驱动）：com.mysql.jdbc.Driver
  -（SpringBoot2.1以上，内置JDBC8驱动）：com.mysql.cj.jdbc.Driver
* URL
    - MySQL 5.7 ：jdbc:mysql://localhost:3316/olympians?characterEncoding=UTF-8&useSSL=false
    - MySQL 8.0 ：jdbc:mysql://localhost:3316/olympians?userUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai

否则会报时区错误


创建HelloController测试项目是否正常

```java
@Controller
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }
}
```



## 添加和配置Mapper

在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹：

```java
@SpringBootApplication
@MapperScan("cn.geekhall.hera.mapper")
public class HeraApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeraApplication.class, args);
    }

}
```

### 设置自定义表名称

可以给实体JavaBean类加上`@TableName("tablename")`注解来自定义实体Bean所对应的数据库表名称。

### 设置自定义主键

可以在实体Bean的属性前加上`@TableId(value="uid")` 注解来自定义主键

### 设置主键自增

`@TableId(value="uid", type=IdType.AUTO)`

### @TableField

指定属性所对应的字段名

### @TableLogic

指定属性所对应的字段为逻辑删除字段

### 雪花算法

雪花算法是由Twitter公布的分布式主键生成算法。它能够保证不同表的主键的不重复性，以及相同表的主键有序性。

* 核心思想

1bit 符号位 + 41bit毫秒时间戳 + 10bit机器ID（5bit数据中心+5bit机器id，可部署在1024个节点） + 12bit作为毫秒内流水号（毫秒内4096个ID）

### Wrapper

* Wrapper
  - AbstractWrapper : 用于查询条件封装，生成sql的where条件
    - QueryWrapper： 查询条件封装
    - UpdateWrapper： 更新条件封装
    - AbstractLambdaWrapper： 使用Lambda语法
      - LambdaQueryWrapper： 用于Lambda语法使用的查询Wrapper
      - LambdaUpdateWrapper： Lambda更新封装Wrapper
