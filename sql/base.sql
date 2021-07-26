CREATE TABLE `front_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(128) NOT NULL COMMENT '前台类目名称',
  `keyword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类目关键词【最多可以添加10个】',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='前台类目表';

CREATE TABLE `home_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '名称',
  `category_config` varchar(512) NOT NULL DEFAULT '' COMMENT '类目配置',
  `banner_config` varchar(512) NOT NULL DEFAULT '' COMMENT 'banner配置',
  `top_sale_config` varchar(512) NOT NULL DEFAULT '' COMMENT 'topSellling配置',
  `new_item_config` varchar(512) NOT NULL DEFAULT '' COMMENT '新品配置',
  `you_may_like_config` varchar(512) NOT NULL DEFAULT '' COMMENT '猜你喜欢配置',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，0-未删除，1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页资源';





