-- 创建数据库并指定字符编码及排序规则
CREATE DATABASE IF NOT EXISTS `mybatis_relation` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `order_id`    bigint(20) NOT NULL COMMENT '订单id',
    `item_id`     varchar(64) NOT NULL COMMENT '商品id',
    `item_num`    int(11) NOT NULL COMMENT '商品购买数量',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`    tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单详情表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
BEGIN;
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 1, '1', 1, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (3, 3, '5', 20, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (10, 10, '2', 1, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (11, 11, '5', 2, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (12, 1, '3', 1, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (13, 1, '5', 2, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
COMMIT;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) NOT NULL COMMENT '商品名称',
    `price`        decimal(10, 2)        DEFAULT NULL COMMENT '商品定价',
    `detail`       varchar(255)          DEFAULT NULL COMMENT '商品描述',
    `pic`          varchar(255)          DEFAULT NULL COMMENT '商品图片',
    `produce_time` datetime     NOT NULL COMMENT '生产日期',
    `create_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`     tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品表';

-- ----------------------------
-- Records of item
-- ----------------------------
BEGIN;
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 'iPhone 13 Pro Max', 12999.00, 'A15高性能处理器', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00',
        '2021-09-20 00:00:00', 1);
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (2, 'iPhone14 Pro', 8999.00, 'A16高性能处理器', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00',
        '2020-10-02 00:00:00', 1);
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (3, '华为Nova', 5699.00, '海思处理器', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00', '2023-07-20 00:00:00',
        1);
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (5, '香草冰淇淋', 2.50, '可可脂', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
COMMIT;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) NOT NULL COMMENT '下单用户id',
    `number`      varchar(64) NOT NULL COMMENT '订单号',
    `note`        varchar(255)         DEFAULT NULL COMMENT '备注',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`    tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Records of order
-- ----------------------------
BEGIN;
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 1, 'G1292110291', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (3, 1, 'G1292110293', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (10, 2, 'G1292110301', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (11, 2, 'G1292110302', NULL, '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(32) NOT NULL COMMENT '用户名',
    `birthday`    datetime             DEFAULT NULL COMMENT '生日',
    `sex`         tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别',
    `address`     varchar(255)         DEFAULT NULL COMMENT '地址',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `del_flag`    tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `birthday`, `sex`, `address`, `create_time`, `update_time`, `del_flag`)
VALUES (1, '高羲之', '2018-10-29 00:00:00', 0, '山东曹县', '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
INSERT INTO `sys_user` (`id`, `user_name`, `birthday`, `sex`, `address`, `create_time`, `update_time`, `del_flag`)
VALUES (2, '赵赵', '2018-03-12 00:00:00', 1, '山东曹县', '2023-07-20 00:00:00', '2023-07-20 00:00:00', 1);
COMMIT;

-- ----------------------------
-- Table structure for broker_message_log
-- ----------------------------
DROP TABLE IF EXISTS `broker_message_log`;
CREATE TABLE `broker_message_log`
(
    `message_id`  varchar(128) NOT NULL COMMENT '消息id，订单号',
    `message`     varchar(4096)         DEFAULT NULL COMMENT '消息内容',
    `try_count`   int(11) DEFAULT '0' COMMENT '重试次数',
    `status`      varchar(10)           DEFAULT NULL COMMENT '消息状态',
    `next_retry`  datetime              DEFAULT NULL COMMENT '下次重试时间',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    PRIMARY KEY (`message_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息投递日志';
