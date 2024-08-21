# 数据库初始化
# @author <a href="https://github.com/liyuxin">程序员yuxin</a>
# @from <a href="https://yuxin.icu">编程导航知识星球</a>

-- 创建库
create database if not exists api;

-- 切换库
use api;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    accessKey    varchar(512) not null comment 'accessKey',
    secretKey    varchar(512) not null comment 'secretKey',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    interfaceId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_interfaceId (interfaceId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    interfaceId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_interfaceId (interfaceId),
    index idx_userId (userId)
) comment '帖子收藏';

-- 接口信息
create table if not exists api.`interface_info`
(
  `id` bigint not null auto_increment comment '主键' primary key,
  `name` varchar(256) not null comment '名称',
  `description` varchar(256) null comment '描述',
  `url` varchar(512) not null comment '接口地址',
  `requestParams` text not null comment '请求参数',
  `requestHeader` text null comment '请求头',
  `responseHeader` text null comment '响应头',
  `status` int default 0 not null comment '接口状态（0-关闭，1-开启）',
  `method` varchar(256) not null comment '请求类型',
  `userId` bigint not null comment '创建人',
  `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
  `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
  `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

-- 用户调用接口关系表
create table if not exists api.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';

insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('侯天磊', '唐鹏煊', 'www.toney-kub.info', '彭金鑫', '万志泽', 0, '方修洁', 162982980);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('谭琪', '董建辉', 'www.yetta-hirthe.org', '侯思淼', '胡熠彤', 0, '雷伟祺', 51701);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('苏立轩', '汪昊天', 'www.jeffie-wisoky.net', '何旭尧', '覃睿渊', 0, '钟子轩', 1);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('陈昊焱', '朱煜城', 'www.makeda-spinka.biz', '彭雨泽', '孔熠彤', 0, '陶楷瑞', 7789);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('萧文轩', '田晟睿', 'www.wanda-zulauf.com', '邹嘉熙', '叶志泽', 0, '林涛', 52);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邹皓轩', '陶明哲', 'www.ahmed-rice.com', '韦楷瑞', '薛展鹏', 0, '邓潇然', 959454);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('余展鹏', '黄立轩', 'www.hosea-stokes.io', '邹智渊', '郑智宸', 0, '何涛', 120526798);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('张鑫磊', '戴明哲', 'www.tyree-emard.net', '高远航', '田煜城', 0, '郝旭尧', 499);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('王思', '尹果', 'www.eartha-schulist.com', '江越泽', '戴修洁', 0, '梁金鑫', 2);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('董思源', '钱文博', 'www.wesley-schulist.biz', '刘黎昕', '雷智辉', 0, '邓擎宇', 788834);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('许智宸', '毛伟祺', 'www.rolland-weissnat.biz', '姚思聪', '朱鸿煊', 0, '郝天磊', 8);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('贾烨伟', '蒋昊天', 'www.alexis-beer.io', '杨晓博', '秦明哲', 0, '叶天宇', 84777841);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('钟晟睿', '曹懿轩', 'www.shannon-rolfson.io', '周思', '莫炎彬', 0, '罗志强', 629985);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吕梓晨', '曹鸿涛', 'www.jerrell-parisian.info', '陈熠彤', '杨擎苍', 0, '彭昊强', 1107671);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黎鹏涛', '秦锦程', 'www.kurt-streich.net', '蔡烨霖', '傅荣轩', 0, '余睿渊', 2973710000);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('尹弘文', '钟振家', 'www.ross-kunze.net', '丁伟祺', '卢炫明', 0, '邱思淼', 8496);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('余昊焱', '钱致远', 'www.willena-kris.com', '段语堂', '史绍辉', 0, '卢乐驹', 826040);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('蔡明辉', '严瑞霖', 'www.avery-kassulke.name', '孟文昊', '毛明轩', 0, '于伟泽', 286898205);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邓涛', '吴瑾瑜', 'www.rocio-simonis.co', '石智渊', '范君浩', 0, '高梓晨', 150);
insert into api.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('徐睿渊', '夏博超', 'www.floria-west.io', '曾浩宇', '方鑫磊', 0, '高擎苍', 5742366177);