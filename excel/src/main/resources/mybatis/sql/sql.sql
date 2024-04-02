-- 工资表
DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary`
(
    `emp_no`    varchar(32)    NOT NULL COMMENT '工号',
    `salary`    decimal(10, 2) NOT NULL COMMENT '工资',
    `from_date` date           NOT NULL COMMENT '开始日期',
    `to_date`   date           NOT NULL COMMENT '结束日期'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工资表' ROW_FORMAT = DYNAMIC;