
-- 必备字段
CREATE TABLE `necessary`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  `creator` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `modifier` bigint(20) NULL DEFAULT 0 COMMENT '最后编辑人id',
  `able` tinyint(4) NOT NULL DEFAULT 0 COMMENT '禁用/启用 (0,1)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '必要字段' ROW_FORMAT = Dynamic;
