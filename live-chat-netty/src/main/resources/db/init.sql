-- Table structure for clothes
CREATE TABLE IF NOT EXISTS `clothes`
(
    `id`          BIGINT PRIMARY KEY,
    `create_time` TIMESTAMP        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state`       TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0 删除, 1 正常, 2 禁用, 3 注销',
    `update_time` TIMESTAMP        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `colors`      INT              DEFAULT NULL,
    `name`        VARCHAR(32),
    `size`        INT              DEFAULT NULL
);

-- Table structure for user
CREATE TABLE IF NOT EXISTS `user`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `create_time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state`       TINYINT     NOT NULL DEFAULT 1 COMMENT '状态: 0 删除, 1 正常, 2 禁用, 3 注销',
    `update_time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `name`        VARCHAR(32) NOT NULL COMMENT '用户名',
    `sp_code`     VARCHAR(32)          DEFAULT NULL COMMENT 'code'
);

CREATE UNIQUE INDEX UK_user_name ON `user` (`name`);

-- Table structure for user_clothes
CREATE TABLE IF NOT EXISTS `user_clothes`
(
    `clothes_id`  BIGINT  NOT NULL,
    `user_id`     BIGINT  NOT NULL,
    `create_time` TIMESTAMP        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state`       TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0 删除, 1 正常, 2 禁用, 3 注销',
    `update_time` TIMESTAMP        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    PRIMARY KEY (`clothes_id`, `user_id`)
);