CREATE TABLE `acl_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint DEFAULT '0' COMMENT '父节点(0表示无父节点)',
  `permission` varchar(50) NOT NULL COMMENT '权限标识',
  `perm_url` varchar(200) DEFAULT NULL COMMENT '权限url',
  `level` tinyint DEFAULT NULL COMMENT '等级',
  `order_num` tinyint DEFAULT NULL COMMENT '菜单排序',
  `menu_icon` varchar(50) DEFAULT NULL COMMENT '菜单icon',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `is_active` tinyint NOT NULL DEFAULT '0' COMMENT '0:未启用 1:启用',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '0:未删除 1:删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='权限菜单表';


CREATE TABLE `acl_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` varchar(50) NOT NULL DEFAULT '0' COMMENT '角色标识',
  `description` varchar(50) DEFAULT '0' COMMENT '描述',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '0:未删除 1:删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';


CREATE TABLE `acl_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名(唯一)',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户邮箱',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '加密盐值',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '标志位，0:未删除 1已删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='后台用户表';



CREATE TABLE `acl_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_user_id` bigint NOT NULL COMMENT '关联auth_user的主键',
  `auth_role_id` bigint NOT NULL COMMENT '关联auth_role的主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_id_auth_role_id` (`auth_user_id`,`auth_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';


CREATE TABLE `acl_role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_role_id` bigint NOT NULL COMMENT '关联auth_role的主键',
  `auth_permission_id` bigint NOT NULL COMMENT '关联auth_permission的主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';