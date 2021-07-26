-- 购物车表
create table if not exists cart_item(
	number bigint unsigned auto_increment,
	account_id bigint unsigned not null comment '用户id',
	type tinyint unsigned default 0 not null comment '用户类型，1：正式用户，2：临时用户',
	sku_id bigint unsigned not null comment '商品id',
	shop_id bigint unsigned not null comment '店铺id',
	channel int(11) unsigned default 0 not null comment '购物车渠道，0: 捕手/斑马',
	count int(11) unsigned default 0 not null comment '购物车商品数量',
	checked tinyint unsigned default 0 not null comment '是否选中，0：未选中，1：选中',
	split_id varchar(128) not null comment '分割字段id',
	extra varchar(256) default '' null comment '扩展字段，存放扩展信息',
	is_deleted tinyint(3) default 0 not null comment '是否逻辑删除',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '最后更新时间'
)charset=utf8;


-- 正向表
create table if not exists `order`(
	id bigint auto_increment comment '主键ID',
	order_id bigint default 0 not null comment '订单ID',
	parent_order_id bigint default 0 not null comment '父订单ID，商品级订单的父订单ID为店铺级订单ID，店铺级订单的父订单ID为支付级订单ID，支付级订单无父级订单(值为0)',
	level tinyint default 0 not null comment '订单级别，1:支付级订单(L1),2:店铺级订单(L2),3:商品级订单(L3)',
	type int default 0 not null comment '订单类型，1:默认商品购买,2:会员资格购买,500:手机话费充值,501:手机流量充值,502:捕手G币充值,1000:保险,1001:机票',
	marketplace smallint(6) default 0 not null comment '交易市场，1:斑马,2:环球捕手,3:本地生活,4:小区乐',
	platform tinyint default 0 not null comment '下单来源平台，0:全终端(未知平台下单),1:IOS下单,2:Android下单,3:H5下单',
	visible tinyint default 0 not null comment '订单可见状态，0:订单正常可见,1:订单初始删除,2:订单永久删除,99:订单初始不可见',
	status smallint(6) default 0 not null comment '订单主状态，0:订单已创建,1:订单已取消,2:订单已支付,3:订单已发货,4:订单已收货,5:订单已关闭,6:订单已完成',
	status_ex smallint(6) default 0 not null comment '订单子状态',
	reverse_status tinyint(6) default 0 not null comment '订单逆向状态，0:未进入退货/款流程,1:进入退货/款流程,2:退货/款流程完成,3:退货/款流程完结，未生过成功的售后行为,4:退货/款流程完结，发生过成功的售后行为',
	rate_status tinyint(6) default 0 not null comment '订单评价状态，0:初始不可评价,1:初始可评价,2:可追加评价,3:追加评价后,评价关闭',
	price bigint default 0 not null comment '订单货款，商品级订单表示购买数量*商品原价-商品级优惠，店铺级订单表示其子商品级订单货款总和减去店铺优惠，支付级订单表示其子店铺级订单货款总和减去平台优惠',
	quantity int default 0 not null comment '数量，商品级订单表示购买SKU数量，店铺级订单表示子商品级订单购买SKU数量总和，支付级订单表示子店铺级订单数量总和',
	buyer_user_id bigint default 0 not null comment '买家用户ID',
	seller_shop_id bigint default 0 not null comment '卖家店铺ID',
	item_id bigint default 0 not null comment '商品 ID，商品级订单专用',
	sku_id bigint default 0 not null comment 'SKU ID，商品级订单专用',
	pay_id bigint default 0 not null comment '支付ID',
	pay_time timestamp null comment '支付时间',
	expire_time timestamp null comment '当前状态到下一个状态的超时截止时间',
	settle_time timestamp null comment '结算时间',
	ship_expense int default 0 not null comment '店铺运费',
	ship_time timestamp null comment '发货时间',
	receive_time timestamp null comment '收货时间',
	receive_type tinyint default 0 not null comment '收货类型',
	cancel_time timestamp null comment '取消时间',
	cancel_reason varchar(1024) charset utf8 default '' not null comment '取消原因，JSON存储',
	seller_comment varchar(1024) charset utf8 default '' not null comment '卖家备注',
	buyer_comment varchar(1024) charset utf8 default '' not null comment '买家备注',
	promotion_amount bigint default 0 not null comment '优惠金额，支付级订单表示平台优惠金额，店铺级订单用于改价，商品级订单暂不启用',
	promotion varchar(4000) charset utf8 default '' not null comment '促销快照，JSON存储',
	source varchar(2000) charset utf8 default '' not null comment '订单来源，JSON存储',
	extra varchar(6000) charset utf8 default '' not null comment '订单扩展，JSON存储',
	extra_int bigint default 0 not null comment '预留字段',
	tags varchar(1024) charset utf8 default '' not null comment '订单标签',
	out_id varchar(128) charset utf8 default '' not null comment '预留字段，关联对接外部业务ID',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间，创建时间为下单时间',
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	version int default 0 not null comment '版本号',
	price_ex varchar(64) charset utf8 default '' not null comment '价格扩展',
	item_id_ex varchar(64) charset utf8 default '' not null comment '商品ID扩展',
	sku_id_ex varchar(64) charset utf8 default '' not null comment 'SKU ID扩展'
);


create table if not exists order_ship(
	id bigint auto_increment comment '主键ID',
	order_id bigint not null comment '订单ID,取值店铺级订单ID',
	buyer_user_id bigint default 0 not null comment '买家用户ID(用于drds做RANGE_HASH)',
	ship_type int default 0 not null comment '发货类型，关联发货平台发货方式',
	ship_time timestamp null comment '发货时间',
	ship_express varchar(64) default '' not null comment '快递公司，发货承运快递公司',
	ship_express_id varchar(64) default '' not null comment '快递单号，发货承运快递单号',
	ship_to_name varchar(64) default '' not null comment '收货人姓名',
	ship_to_mobile varchar(32) default '' not null comment '收货方手机号',
	ship_to_zip varchar(32) default '' not null comment '收货方地址邮编',
	ship_to_province varchar(32) default '' not null comment '收货方地址省份，直辖市省市相同',
	ship_to_city varchar(32) default '' not null comment '收货方地址市，直辖市省市相同',
	ship_to_district varchar(32) default '' not null comment '收货方地址行政区，市/县级行政区',
	ship_to_town varchar(32) default '' not null comment '收货方地址镇，市级行政区对应街道/县级行政区对应城镇',
	ship_to_address varchar(256) charset utf8mb4 default '' not null comment '收货方详细收货地址，街道、小区、门牌号',
	extra varchar(512) default '' not null comment '扩展字段，扩展存储实名认证、发票信息等',
	extra_int bigint not null comment '预留字段',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '修改时间',
	extra_ex varchar(2048) default '' not null comment '扩展字段，扩展存储实名认证、发票信息等',
	ship_to_name_ex varchar(255) default '' not null comment '收货人姓名',
	ship_to_mobile_ex varchar(64) default '' not null comment '收货方手机号',
	ship_to_address_ex varchar(512) charset utf8mb4 default '' not null comment '收货方详细收货地址，街道、小区、门牌号'
)comment '交易物流单' charset=utf8;



create table if not exists item_order_ex(
	id bigint auto_increment comment '主键ID',
	order_id bigint not null comment '订单ID,商品级订单ID',
	buyer_user_id bigint default 0 not null comment '买家用户ID(用于drds做RANGE_HASH)',
	title varchar(128) default '' not null comment '商品标题',
	image varchar(256) default '' not null comment '商品主图',
	price bigint default 0 not null comment '商品原价(市场价)，单位分',
	now_price bigint default 0 not null comment '商品现价(购买价)，单位分',
	sku_attribute varchar(1024) default '' not null comment 'SKU属性，JSON结构化存储',
	deliver_area_id bigint not null comment '发货地址ID',
	deliver_code varchar(128) not null comment '发货编码',
	extra varchar(4000) default '' not null comment '业务扩展，JSON结构化存储',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	extra_ex varchar(2048) default '' not null comment '扩展信息',
	title_ex varchar(600) default '' not null comment '商品标题扩展',
	image_ex varchar(255) default '' not null comment '图片链接扩展',
	price_ex varchar(64) default '' not null comment '商品原价扩展',
	now_price_ex varchar(64) default '' not null comment '商品现价扩展',
	deliver_code_ex varchar(300) default '' not null comment '发货编码扩展',
	deliver_area_id_ex varchar(64) default '' not null comment '发货地ID扩展'
)
comment '交易商品级订单商品扩展' charset=utf8;



CREATE TABLE `order_operate_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `order_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单级别',
  `operator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operate_type` varchar(128) NOT NULL DEFAULT '0' COMMENT '操作类型',
  `remark` varchar(128) NOT NULL DEFAULT '' COMMENT '修改备注',
  `before` varchar(2048) NOT NULL DEFAULT '' COMMENT '操作前数据',
  `after` varchar(2048) NOT NULL DEFAULT '' COMMENT '操作后数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:正常,1:已删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_id_operate_type` (`order_id`,`operate_type`),
  KEY `idx_order_id_level` (`order_id`,`order_level`),
  KEY `idx_operator_id_type` (`operator_id`,`operate_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=185835671 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='交易订单操作日志表';


CREATE TABLE `order_snapshot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `order_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单级别',
  `detail` text COMMENT '快照内容，压缩存储',
  `md5` varchar(256) NOT NULL DEFAULT '' COMMENT '快照内容md5值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:正常,1:已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`,`order_level`,`deleted`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=223111202 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='交易订单快照(商品全量信息快照)';



-- 逆向表
CREATE TABLE `refund` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `refund_number` bigint(20) unsigned NOT NULL COMMENT '退款编号',
  `pay_order_id` bigint(20) unsigned NOT NULL COMMENT '支付级订单',
  `shop_order_id` bigint(20) unsigned NOT NULL COMMENT '店铺级订单',
  `item_order_id` bigint(20) unsigned NOT NULL COMMENT '商品级订单',
  `seller_shop_id` bigint(20) unsigned NOT NULL COMMENT '商家id',
  `buyer_user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `order_type` int(11) NOT NULL DEFAULT '0' COMMENT '订单类型',
  `order_status_snapshot` tinyint(4) NOT NULL DEFAULT '0' COMMENT '售后发起时，订单状态快照',
  `item_id` bigint(20) unsigned NOT NULL COMMENT '商品id，商品展示最小单元ID，SPU',
  `sku_id` bigint(20) unsigned NOT NULL COMMENT 'sku id，商品交易最小单元ID，SKU',
  `order_marketplace` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单市场',
  `type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '售后类型',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '售后主状态 1-退款待处理 10-退款已同意 20-退货中 40-退款中 50-退款成功 60-已取消',
  `status_ext` tinyint(4) NOT NULL COMMENT '状态扩展 1-申请中 20-退款拒绝 30-待退货 40-待收货 50-退货拒绝 60-待仲裁 80-仲裁完成 90-用户撤销 100-退款取消 110-退款中 120-退款成功 130-退款失败',
  `reason` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '申请原因',
  `remark` varchar(200) NOT NULL DEFAULT '',
  `proof_pic_paths` varchar(512) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '凭证图片,以逗号隔开',
  `apply_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '售后申请的数量',
  `apply_amount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '申请金额，发货以后可以编辑',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `finish_time` datetime DEFAULT NULL COMMENT '结束时间，售后流程的完结时间',
  `modify_times` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改次数',
  `source` varchar(5000) NOT NULL DEFAULT '' COMMENT '来源，json格式',
  `order_extra_int` bigint(20) NOT NULL DEFAULT '0' COMMENT '预留字段',
  `extra` varchar(5000) NOT NULL DEFAULT '' COMMENT '扩展字段，json格式',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_refund_number` (`refund_number`),
  KEY `idx_shop_order_id` (`shop_order_id`),
  KEY `idx_pay_order_id` (`pay_order_id`),
  KEY `idx_item_order_id` (`item_order_id`,`status`,`status_ext`),
  KEY `idx_buyer_user_id` (`buyer_user_id`,`type`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=9906367 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='退款主表';


CREATE TABLE `refund_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `order_level` tinyint(4) unsigned NOT NULL COMMENT '订单级别',
  `refund_number` bigint(20) unsigned NOT NULL COMMENT '售后id',
  `resource_type` tinyint(4) unsigned NOT NULL COMMENT '资源类型，1-现金 2-G币 3-运费 20-平台会员 21-平台优惠券 40-店铺优惠券',
  `use_type` tinyint(4) unsigned NOT NULL COMMENT '资金用途，1-货款 2-运费',
  `return_type` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '退还类型，1-资源原路退回 2-资源被挪用',
  `outer_id` bigint(20) unsigned NOT NULL COMMENT '外部id',
  `amount` int(11) unsigned NOT NULL COMMENT '需要退还的数量',
  `status` tinyint(4) unsigned NOT NULL COMMENT '退还状态 0-初始 2-待退还 4-退还成功 8-无需退还 16-取消退还',
  `settle_status` tinyint(4) unsigned NOT NULL COMMENT '结算状态，0-初始 1-已结算',
  `settle_time` datetime DEFAULT NULL COMMENT '结算时间',
  `refund_start_time` datetime DEFAULT NULL COMMENT '退还发起时间',
  `refund_success_time` datetime DEFAULT NULL COMMENT '退还成功时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_refund_number` (`refund_number`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22520870 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='售后资源表';


CREATE TABLE `refund_flow_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `flow_number` bigint(20) unsigned NOT NULL COMMENT '流程编号',
  `event_id` varchar(50) NOT NULL DEFAULT '' COMMENT '事件唯一id',
  `outer_id` bigint(20) NOT NULL COMMENT '外部依赖id',
  `operator_type` tinyint(4) NOT NULL COMMENT '操作人类型 1-买家 2-商家 3-系统 4-平台客服',
  `operator` bigint(20) NOT NULL COMMENT '操作人id',
  `user_account_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '事件触发者用户账号id',
  `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '事件触发者用户名',
  `subject_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '主体类型 0-无主体 1-店铺',
  `subject_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主体id',
  `subject_account_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主体所属账号id',
  `event_type` varchar(200) NOT NULL DEFAULT '' COMMENT '事件类型',
  `event_trigger_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '事件触发时间',
  `seq_number` int(11) NOT NULL DEFAULT '0' COMMENT '序列号',
  `last_flow_state` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '上一个流程状态',
  `current_flow_state` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '当前流程所处状态',
  `step_expire_time` datetime DEFAULT NULL COMMENT '当前状态超时时间',
  `extra` varchar(5000) DEFAULT NULL COMMENT '扩展字段，json格式',
  `extra_version` int(11) NOT NULL DEFAULT '0' COMMENT 'extra的版本号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_refund_number` (`flow_number`,`seq_number`),
  KEY `idx_event_id` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32247233 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='流程日志表';



-- 履约单表
create table if not exists seller_trade.consign_order
(
	id bigint auto_increment,
	shop_order_id bigint(23) not null comment '店铺订单id',
	marketplace int not null comment '参考order表',
	order_type int not null comment '参考order表',
	consign_code varchar(32) default '' not null comment '履约单号',
	buyer_user_id bigint(11) not null comment '用户id',
	seller_shop_id bigint(11) not null comment '店铺id',
	shop_deliver_id bigint not null,
	supplier_id bigint unsigned default 0 not null comment '供应商id',
	is_handwork tinyint unsigned default 0 not null comment '是否手工单：0:否 1:是',
	status int not null comment '100待发货200已发货300无需发货',
	send_time datetime null comment '发货时间',
	freeze_status int not null comment '1未冻结 2已冻结',
	pay_time datetime not null comment '支付时间',
	extra varchar(1024) null comment '扩展信息字段',
	environment varchar(16) not null comment '订单所属环境',
	version int default 0 null comment 'sync version lock',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间'
);

create table seller_trade.consign_order_sku
(
	id bigint auto_increment,
	shop_order_id bigint(19) not null comment '店铺订单id',
	consign_code varchar(32) default '' not null comment '履约单号',
	buyer_user_id bigint(19) not null comment '买家id',
	seller_shop_id bigint(19) not null comment '店铺id',
	item_order_id bigint(19) not null comment '商品级订单编号',
	item_id bigint(19) not null comment '商品id',
	sku_id bigint(19) not null comment 'sku_id',
	supplier_id bigint unsigned default 0 not null comment '供应商id',
	is_handwork tinyint unsigned default 0 not null comment '是否手工单：0:否 1:是',
	deliver_code varchar(64) not null comment '发货编码',
	quantity int not null comment '下单时候数量',
	extra varchar(1024) null comment '扩展信息字段',
	environment varchar(16) default '' not null comment '订单所属环境',
	version int default 0 null comment 'sync version lock',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间'
);


create table seller_trade.consign_package(
	id bigint auto_increment,
	shop_order_id bigint(19) not null comment '店铺级订单',
	consign_code varchar(32) default '' not null comment '履约单号',
	seller_shop_id bigint(19) not null comment '店铺id',
	supplier_id bigint unsigned default 0 not null comment '供应商id',
	is_handwork tinyint unsigned default 0 not null comment '是否手工单：0:否 1:是',
	package_code varchar(64) null,
	logistics_channel varchar(32) not null comment '物流公司',
	logistics_number varchar(32) not null comment '物流编号',
	logistics_number_ex varchar(256) default '' not null comment '物流单号加密字段',
	status int default 1 not null comment '100初始 110首次揽件 120已签收',
	first_time datetime null comment '首次物流更新时间',
	taking_time datetime null comment '揽件时间',
	confirm_time datetime null comment '确认收货时间',
	delete_status int default 0 not null comment '0未删除 1已删除',
	extra varchar(1024) null comment '扩展信息字段',
	environment varchar(16) not null comment '订单所属环境',
	version int default 0 null comment 'sync version lock',
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间'
);










