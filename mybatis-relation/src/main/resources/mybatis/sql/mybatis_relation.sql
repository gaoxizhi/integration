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
    `del_flag`    tinyint(4) NOT NULL COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单详情表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
BEGIN;
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 1, '1', 1, '2024-04-07 20:12:54', '2024-04-07 23:24:04', 0);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (3, 3, '5', 20, '2024-04-07 20:13:14', '2024-04-07 23:24:06', 0);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (10, 10, '2', 1, '2024-04-07 20:13:34', '2024-04-07 23:24:07', 0);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (11, 11, '5', 2, '2024-04-07 20:13:44', '2024-04-07 23:24:09', 0);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (12, 1, '3', 1, '2024-04-07 20:12:54', '2024-04-07 20:12:54', 0);
INSERT INTO `order_detail` (`id`, `order_id`, `item_id`, `item_num`, `create_time`, `update_time`, `del_flag`)
VALUES (13, 1, '5', 2, '2024-04-07 23:22:41', '2024-04-07 23:22:46', 0);
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
    `state`       tinyint(4) NOT NULL COMMENT '账户状态: 0 删除, 1 正常, 2 注销',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `birthday`, `sex`, `address`, `create_time`, `update_time`, `state`)
VALUES (1, '高羲之', '2018-10-29 00:00:00', 0, '山东曹县', '2024-04-07 20:06:16', '2024-04-07 23:24:30', 0);
INSERT INTO `sys_user` (`id`, `user_name`, `birthday`, `sex`, `address`, `create_time`, `update_time`, `state`)
VALUES (2, '赵赵', '2018-03-12 00:00:00', 1, '山东曹县', '2024-04-07 20:06:40', '2024-04-07 23:24:32', 0);
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
    `del_flag`     tinyint(4) NOT NULL COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品表';

-- ----------------------------
-- Records of item
-- ----------------------------
BEGIN;
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 'iPhone 13 Pro Max', 12999.00, 'A15高性能处理器', NULL, '2024-04-07 20:07:32', '2024-04-07 20:07:32',
        '2024-04-07 23:24:58', 0);
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (2, 'iPhone14 Pro', 8999.00, 'A16高性能处理器', NULL, '2024-04-07 20:10:02', '2024-04-07 20:10:07',
        '2024-04-07 23:25:00', 0);
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (3, '华为Nova', 5699.00, '海思处理器', NULL, '2024-04-07 20:10:20', '2024-04-07 20:10:49', '2024-04-07 23:25:03',
        0);
INSERT INTO `item` (`id`, `name`, `price`, `detail`, `pic`, `produce_time`, `create_time`, `update_time`, `del_flag`)
VALUES (5, '香草冰淇淋', 2.50, '可可脂', NULL, '2024-04-07 20:11:15', '2024-04-07 20:11:18', '2024-04-07 23:25:02', 0);
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
    `del_flag`    tinyint(4) NOT NULL COMMENT '删除标志：删除0；正常1（默认）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Records of order
-- ----------------------------
BEGIN;
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (1, 1, 'G1292110291', NULL, '2024-04-07 20:11:53', '2024-04-07 23:25:21', 0);
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (3, 1, 'G1292110293', NULL, '2024-04-07 20:12:12', '2024-04-07 23:25:22', 0);
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (10, 2, 'G1292110301', NULL, '2024-04-07 20:12:28', '2024-04-07 23:25:25', 0);
INSERT INTO `order` (`id`, `user_id`, `number`, `note`, `create_time`, `update_time`, `del_flag`)
VALUES (11, 2, 'G1292110302', NULL, '2024-04-07 20:12:35', '2024-04-07 23:25:24', 0);
COMMIT;
