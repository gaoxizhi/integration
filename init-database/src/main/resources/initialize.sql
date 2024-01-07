CREATE TABLE IF NOT EXISTS `user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(32) NOT NULL COMMENT '用户名',
    `passwd`      varchar(32) NOT NULL COMMENT '密码',
    `nick_name`   varchar(32)          DEFAULT NULL COMMENT '昵称',
    `member`      int(11) NOT NULL DEFAULT '5' COMMENT '会员等级[1,5]',
    `phone`       varchar(11) NOT NULL COMMENT '手机号码',
    `email`       varchar(64)          DEFAULT NULL COMMENT '邮箱',
    `remark`      varchar(255)         DEFAULT NULL COMMENT '备注',
    `avatar`      text                 DEFAULT NULL COMMENT '头像',
    `salt`        varchar(5)  NOT NULL COMMENT '盐',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
    `state`       tinyint(4) NOT NULL DEFAULT '0' COMMENT '账户状态: 0 删除, 1 正常, 2 注销',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `phone_UNIQUE` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';
