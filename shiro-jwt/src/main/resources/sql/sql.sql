-- Table structure for clothes
DROP TABLE IF EXISTS `clothes`;

CREATE TABLE `clothes` (
    `id` bigint(20) NOT NULL,
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0 删除, 1 正常, 2 禁用, 3 注销',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `colors` int(11) DEFAULT NULL,
    `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `size` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '衣物表';

-- Table structure for user
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0 删除, 1 正常, 2 禁用, 3 注销',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
    `sp_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'code',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表';

-- Table structure for user_clothes
DROP TABLE IF EXISTS `user_clothes`;

CREATE TABLE `user_clothes` (
    `clothes_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0 删除, 1 正常, 2 禁用, 3 注销',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    PRIMARY KEY (`clothes_id`, `user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户衣物关系表';