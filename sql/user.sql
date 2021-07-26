CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '登录名',
  `image` varchar(200) NOT NULL DEFAULT '' COMMENT '用户头像URL',
  `password` varchar(16) NOT NULL DEFAULT '' COMMENT '账号密码',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱号',
  `mobile` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '用户状态',
  `salt` varchar(32) NOT NULL DEFAULT '' COMMENT '盐值',
  `version` varchar(15) NOT NULL DEFAULT '' COMMENT '注册版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mobile` (`mobile`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表[分销商]';


