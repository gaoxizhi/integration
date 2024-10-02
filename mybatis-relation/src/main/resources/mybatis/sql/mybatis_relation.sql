-- 创建数据库并指定字符编码及排序规则
CREATE DATABASE IF NOT EXISTS `mybatis_relation` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)  NOT NULL COMMENT '下单用户id',
    `number`      varchar(64) NOT NULL COMMENT '订单号',
    `note`        varchar(255)         DEFAULT NULL COMMENT '备注',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`    tinyint(4)  NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='订单表';

-- ----------------------------
-- Records of order
-- ----------------------------
BEGIN;
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 1, 'G1292110291', NULL, '2024-04-07 20:11:53', '2024-04-07 23:25:21', 1),
       (3, 1, 'G1292110293', NULL, '2024-04-07 20:12:12', '2024-04-07 23:25:22', 1),
       (10, 2, 'G1292110301', NULL, '2024-04-07 20:12:28', '2024-04-07 23:25:25', 1),
       (11, 2, 'G1292110302', NULL, '2024-04-07 20:12:35', '2024-04-07 23:25:24', 1),
       (12, 1, 'ffddef26dc3b4bda', NULL, '2024-04-10 11:15:05', '2024-04-10 11:15:05', 1),
       (13, 1, 'd9a915a2cebd4176', NULL, '2024-04-10 11:15:19', '2024-04-10 11:15:19', 1),
       (14, 1, '62288edbd7274038', NULL, '2024-04-10 11:28:02', '2024-04-10 11:28:02', 1),
       (15, 1, 'efdc3ab03a0b4275', NULL, '2024-04-10 11:36:59', '2024-04-10 11:36:59', 1);
COMMIT;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `order_id`    bigint(20)  NOT NULL COMMENT '订单id',
    `item_id`     varchar(64) NOT NULL COMMENT '商品id',
    `item_num`    int(11)     NOT NULL COMMENT '商品购买数量',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`    tinyint(4)  NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='订单详情表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
BEGIN;
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 1, '1', 1, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1),
       (3, 3, '5', 20, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1),
       (10, 10, '2', 1, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1),
       (11, 11, '5', 2, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1),
       (12, 1, '3', 1, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1),
       (13, 1, '5', 2, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
COMMIT;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) NOT NULL COMMENT '商品名称',
    `price`        decimal(10, 2)        DEFAULT NULL COMMENT '商品定价',
    `detail`       varchar(255)          DEFAULT NULL COMMENT '商品描述',
    `pic`          varchar(255)          DEFAULT NULL COMMENT '商品图片',
    `produce_time` datetime     NOT NULL COMMENT '生产日期',
    `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`     tinyint(4)   NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='商品表';

-- ----------------------------
-- Records of item
-- ----------------------------
BEGIN;
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 'iPhone 13 Pro Max', 12999.00, 'A15高性能处理器', NULL, '2024-04-07 20:07:32', '2024-04-07 20:07:32',
        '2024-04-07 23:24:58', 1),
       (2, 'iPhone14 Pro', 8999.00, 'A16高性能处理器', NULL, '2024-04-07 20:10:02', '2024-04-07 20:10:07',
        '2024-04-07 23:25:00', 1),
       (3, '华为Nova', 5699.00, '海思处理器', NULL, '2024-04-07 20:10:20', '2024-04-07 20:10:49', '2024-04-07 23:25:03',
        1),
       (5, '香草冰淇淋', 2.50, '可可脂', NULL, '2024-04-07 20:11:15', '2024-04-07 20:11:18', '2024-04-07 23:25:02', 1),
       (6, '小浣熊干脆面', 1.20, '酥脆好吃', NULL, '2023-12-01 00:00:00', '2024-04-10 11:13:14', '2024-04-10 11:13:14',
        1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(32) NOT NULL COMMENT '用户名',
    `birthday`    datetime             DEFAULT NULL COMMENT '生日',
    `sex`         tinyint(4)  NOT NULL DEFAULT '0' COMMENT '性别',
    `address`     varchar(255)         DEFAULT NULL COMMENT '地址',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`    tinyint(4)  NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `birthday`, `sex`, `address`, `create_time`, `update_time`, `del_flag`)
VALUES (1, '高羲之', '2018-10-29 00:00:00', 0, '山东曹县', '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1),
       (2, '赵赵', '2018-03-12 00:00:00', 1, '山东曹县', '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
COMMIT;

-- ----------------------------
-- Table structure for broker_message_log
-- ----------------------------
DROP TABLE IF EXISTS `broker_message_log`;
CREATE TABLE `broker_message_log`
(
    `message_id`  varchar(128) NOT NULL COMMENT '消息id，订单号',
    `message`     varchar(4096)         DEFAULT NULL COMMENT '消息内容',
    `try_count`   int(11)               DEFAULT '0' COMMENT '重试次数',
    `status`      varchar(10)           DEFAULT NULL COMMENT '消息状态',
    `next_retry`  datetime              DEFAULT NULL COMMENT '下次重试时间',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='消息投递日志';

-- ----------------------------
-- Records of broker_message_log
-- ----------------------------
BEGIN;
INSERT INTO `broker_message_log` (`message_id`, `message`, `try_count`, `status`, `next_retry`, `create_time`,
                                  `update_time`)
VALUES ('62288edbd7274038',
        '{\"createTime\":1712719681852,\"id\":14,\"number\":\"62288edbd7274038\",\"updateTime\":1712719681852,\"userId\":1}',
        3, '2', '2024-04-10 11:29:02', '2024-04-10 11:28:02', '2024-05-17 10:18:06'),
       ('d9a915a2cebd4176',
        '{\"createTime\":1712718918726,\"id\":13,\"number\":\"d9a915a2cebd4176\",\"updateTime\":1712718918726,\"userId\":1}',
        3, '2', '2024-04-10 11:16:19', '2024-04-10 11:15:19', '2024-04-10 11:16:49'),
       ('efdc3ab03a0b4275',
        '{\"createTime\":1712720219051,\"id\":15,\"number\":\"efdc3ab03a0b4275\",\"updateTime\":1712720219051,\"userId\":1}',
        3, '2', '2024-04-10 11:37:59', '2024-04-10 11:36:59', '2024-05-17 10:18:16'),
       ('ffddef26dc3b4bda',
        '{\"createTime\":1712718904638,\"id\":12,\"number\":\"ffddef26dc3b4bda\",\"updateTime\":1712718904638,\"userId\":1}',
        3, '2', '2024-04-10 11:16:05', '2024-04-10 11:15:05', '2024-04-10 11:16:39');
COMMIT;
