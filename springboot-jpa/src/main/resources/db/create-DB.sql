-- 创建数据库jpa并指定字符编码及排序规则
CREATE DATABASE IF NOT EXISTS `jpa` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

--  privileges - 用户的操作权限,如SELECT , INSERT , UPDATE 等。如果要授予所的权限则使用 ALL;
--  databasename - 数据库名
--  tablename - 表名
--  补充：如果要授予该用户对所有数据库和表的相应操作权限则可用*表示, 如*.*
--  username - 用户名
--  ipaddress - 授权白名单的IP地址列表。如果设为%所有IP都可访问
--  password - 设置用户密码
grant all privileges on jpa.* to 'jpauser'@'%' identified by 'password';