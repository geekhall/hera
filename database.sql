-- 数据库设计规约
-- 1. 库名与应用名称尽量一致
-- 2. 表名、字段名必须使用小写字母或者数字，禁止出现数字开头
-- 3. 表名不使用复数名词
-- 4. 表的命名最好是加上”业务名称_表的作用“，如edu_teacher
-- 5. 表必备三字段: id, create_time, update_time


-- h_user
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
    deleted tinyint(1) unsigned not null default 0 comment '逻辑删除',
    PRIMARY key (id)
) comment='用户';

truncate table h_user;
insert into h_user (id, name, age, email, create_time) values
                                                           (1, 'Jone', 18, 'test1@geekhall.com', now()),
                                                           (2, 'Jack', 19, 'test2@geekhall.com', now()),
                                                           (3, 'Tom', 20, 'test3@geekhall.com', now()),
                                                           (4, 'Jerry', 21, 'test4@geekhall.com', now()),
                                                           (5, 'Tony', 24, 'test5@geekhall.com', now());


select * from h_user;

-- 也可以使用下面的语句修改表添加逻辑删除字段
-- alter table `h_user` add column `deleted` tinyint(1) default 0 comment '逻辑删除'


-- h_chapter 章节表
drop table if exists `h_chapter`;
create table h_chapter
(
    id BIGINT(20) not null comment '章节ID',
    course_id BIGINT(20) null default null comment '课程ID',
    title varchar(255) not null comment '标题',
    sort int(10) unsigned not null default 0 comment '显示排序',
    version int(11) null default 1 comment '版本号',
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间',
    deleted tinyint(1) unsigned not null default 0 comment '逻辑删除',
    PRIMARY key (id),
    key `idx_course_id` (`course_id`)
) comment='课程';

insert into h_chapter (id, course_id, title, sort, create_time) values
   (1, 1, '十分钟Git入门教程', 1, now()),
   (2, 2, '十分钟Markdown入门教程', 2, now());


-- h_chapter 讲师表
drop table if exists `h_teacher`;
create table h_teacher
(
    id BIGINT(20) not null comment '讲师ID',
    name varchar(64) not null comment '讲师姓名',
    intro varchar(500) not null default '' comment '讲师简介',
    career varchar(500) default null comment '讲师资历，一句话说明讲师',
    level int(10) unsigned not null comment '头衔 1-高级讲师 2-资深讲师 3-专家讲师 4-首席讲师',
    avatar varchar(255) default null comment '讲师头像',
    sort int(10) unsigned not null default 0 comment '排序',
    version int(11) null default 1 comment '版本号',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',
    deleted tinyint(1) unsigned not null default 0 comment '逻辑删除',
    PRIMARY key (id),
    key `uk_name` (`name`)
) comment='讲师';
insert into h_teacher (id, name, intro, career, level, sort, create_time) values
(1, '张三', '十余年丰富前后端开发经验', '资深讲师', 2, 1, now()),
(2, '李四', '多年丰富前端开发经验', '高级讲师', 1, 2, now());


drop table  if EXISTS `h_product`;
CREATE TABLE `h_product`  (
                              `id` BIGINT(20) NOT NULL COMMENT '主键ID',
                              `name` varchar(255) NOT NULL  COMMENT '商品名称',
                              `description` varchar(255) NULL DEFAULT NULL COMMENT '商品描述',
                              `brand` varchar(255) NULL DEFAULT NULL COMMENT '品牌',
                              `price` decimal(17,2) DEFAULT NULL COMMENT '价格',
                              `is_deleted` tinyint(1) default 0,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;
delete from h_product;
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(1,'MacBookPro','Mac book pro', 'Apple', 15000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(2,'MacBookAir','Mac book air', 'Apple', 8000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(3,'iPhone13','iphone13 pro max', 'Apple', 9800.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(4,'iMac','iMac', 'Apple', 12000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(5,'iWatch','iWatch', 'Apple', 4000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(6,'MacMini','MacMini', 'Apple', 6000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(7,'AirPots','Air Pots Pro', 'Apple', 2000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(8,'Surface','Surface book', 'Microsoft', 8000.00);
insert into h_product (`id`, `name`, `description`, `brand`, `price`) values(9,'Honor','Honor phone', 'Huawei', 2000.00);

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

