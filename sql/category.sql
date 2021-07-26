-- 属性名称，规定属性类型特点
CREATE TABLE `property_name` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '属性项名称',
  `is_key_property` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是关键属性，1是，0否',
  `is_sell_property` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是销售属性，1是，0否',
  `is_goods_property` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是商品属性，1是，0否',
  `input_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '1单选(不可自定义)，2单选(允许自定义)，3多选(不可自定义)，4多选(允许自定义)，5文本输入框',
  `modify_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '1可修改，2不可修改',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性项';


-- 属性值，一个属性项有多个属性值，属性值主要属性值名称
CREATE TABLE `property_value` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '属性值名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name_upper` varchar(128) NOT NULL COMMENT '属性值大写',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name_upper` (`name_upper`),
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性值';



-- 属性名称和属性值组成的属性对 关系表
CREATE TABLE `property_pair` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `property_name_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性项id',
  `property_value_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性值id',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_property_name_and_value_id` (`property_name_id`,`property_value_id`),
  KEY `idx_property_value_id` (`property_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性对';



-- 类目- 树形结构
CREATE TABLE `category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父类目ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '类目名称',
  `sort_order` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序值（倒序）',
  `level` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '层级，1一级类目，2二级类目，3三级类目，4四级类目',
  `path` varchar(32) NOT NULL DEFAULT '' COMMENT '类目路径(/父类目path/id/), 如：/20000/20001/20002/20003/',
  `path_name` varchar(100) NOT NULL DEFAULT '' COMMENT '类目路径名称，如零食/坚果/特产,饼干/膨化,饼干,其他',
  `is_leaf` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是叶子节点，1是，0否',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态，1可用，0不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tag` varchar(64) DEFAULT NULL COMMENT '类目标签',
  `channel` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '1:捕手，3:小区乐',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_parent_id_and_name` (`parent_id`,`name`,`channel`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_path` (`path`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='类目';


-- 类目属性项 - 属性项类型 -->
CREATE TABLE `category_property_name` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '类目id',
  `property_name_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性项id',
  `is_required` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否必填, 0否，1是',
  `input_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '1单选框(选项不可自定义)，2单选框(属性允许自定义)，3多选框(选项不可自定义)，4多选框(选项允许自定义)，5文本输入框',
  `property_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '属性类型，1关键属性，2销售属性，3商品属性',
  `sort_order` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序（倒序）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_category_id_and_property_name_id` (`category_id`,`property_name_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类目属性项';



-- 类目属性项对应的值
CREATE TABLE `category_property_value` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_property_name_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '类目属性项id',
  `property_pair_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '属性对id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_category_property_name_id_and_property_pair_id` (`category_property_name_id`,`property_pair_id`),
  KEY `idx_category_property_name_id` (`category_property_name_id`),
  KEY `idx_property_pair_id` (`property_pair_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类目属性值';


