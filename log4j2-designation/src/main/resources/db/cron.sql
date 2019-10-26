/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : cron

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 26/10/2019 22:54:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cron
-- ----------------------------
DROP TABLE IF EXISTS `cron`;
CREATE TABLE `cron`
(
    `id`          int(11) UNSIGNED                                             NOT NULL AUTO_INCREMENT COMMENT 'id',
    `cron`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'cron表达式',
    `name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'cron名称',
    `cron_desc`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'cron描述',
    `create_time` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `able`        tinyint(1) UNSIGNED                                          NOT NULL DEFAULT 0 COMMENT '状态：默认0可用，1禁用，2删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '定时表'
  ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of cron
-- ----------------------------
INSERT INTO `cron`
VALUES (1, '0/5 * * * * ?', 'c_5s', ' 每5s执行一次', '2019-10-26 20:59:49', '2019-10-26 22:54:20', 0);

SET FOREIGN_KEY_CHECKS = 1;
