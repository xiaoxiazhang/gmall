-- 商品图片
CREATE TABLE `item_image`
(
    `id`          bigint(20)          NOT NULL AUTO_INCREMENT COMMENT '单表自增主键，没有业务含义',
    `item_id`     bigint(20)          NOT NULL COMMENT '商品id',
    `type`        tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '图片类型 0-商品主图，1-商品白底图',
    `url`         varchar(128)        NOT NULL DEFAULT '' COMMENT '图片url',
    `width`       int(11)             NOT NULL DEFAULT '0' COMMENT '图片宽度',
    `height`      int(11)             NOT NULL DEFAULT '0' COMMENT '图片高度',
    `sort_order`  int(11)             NOT NULL DEFAULT '0' COMMENT '排序（倒序）',
    `create_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(3)          NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_itemId` (`item_id`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_updateTime` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='商品图片表';


-- 商品销售属性 商品属性
CREATE TABLE `item_property`
(
    `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键，无业务含义',
    `item_id`           bigint(20)   NOT NULL DEFAULT '0' COMMENT '商品id',
    `property_pair_id`  bigint(20)   NOT NULL DEFAULT '0' COMMENT '属性对id',
    `property_name_id`  bigint(20)   NOT NULL DEFAULT '0' COMMENT '属性名id',
    `property_value_id` bigint(20)   NOT NULL DEFAULT '0' COMMENT '属性值id',
    `type`              tinyint(3)   NOT NULL DEFAULT '1' COMMENT '属性类型，1-关键属性，2-销售属性，3-商品属性',
    `property_name`     varchar(32)  NOT NULL DEFAULT '' COMMENT '属性名',
    `property_value`    varchar(128) NOT NULL DEFAULT '' COMMENT '属性值',
    `extra`             varchar(128) NOT NULL DEFAULT '' COMMENT '扩展字段',
    `create_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`        tinyint(3)   NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_itemId_propertyPairId` (`item_id`, `property_pair_id`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_updateTime` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品属性表';


-- 商品视频和详情视频封面图片
CREATE TABLE `item_video`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '单表自增主键',
    `item_id`     bigint(20)   NOT NULL COMMENT '商品id',
    `url`         varchar(128) NOT NULL DEFAULT '' COMMENT '视频url',
    `type`        tinyint(3)   NOT NULL DEFAULT '1' COMMENT '视频类型，1-主图视频，2-详情视频，3-详情视频封面',
    `width`       int(11)      NOT NULL DEFAULT '0' COMMENT '视频宽度',
    `height`      int(11)      NOT NULL DEFAULT '0' COMMENT '视频高度',
    `duration`    int(11)      NOT NULL DEFAULT '0' COMMENT '视频时长',
    `capacity`    int(11)      NOT NULL DEFAULT '0' COMMENT '视频文件大小',
    `sort_order`  int(11)      NOT NULL DEFAULT '0' COMMENT '排序（倒序）',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(3)   NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_itemId` (`item_id`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_updateTime` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='商品视频表';


-- 商品详情图片、文字内容
CREATE TABLE `item_detail_parsed`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `item_id`     bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id',
    `detail`      text       NOT NULL COMMENT '商品图片列表json字符串',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_itemId` (`item_id`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_updateTime` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品详情表 (已解析出图片宽高)';


-- sku表
CREATE TABLE `sku`
(
    `id`                      bigint(20)                      NOT NULL AUTO_INCREMENT COMMENT 'sku唯一标识',
    `item_id`                 bigint(20)                      NOT NULL COMMENT '商品id',
    `sku_name`                varchar(300)                    NOT NULL DEFAULT '' COMMENT 'Sku名称（商品名称+销售属性值拼接字符串）',
    `property_values`         varchar(300)                    NOT NULL DEFAULT '' COMMENT '销售属性值拼成的字符串',
    `channel`                 tinyint(3)                      NOT NULL DEFAULT '0' COMMENT '渠道id',
    `deliver_area_id`         bigint(20)                      NOT NULL DEFAULT '0' COMMENT '发货地id（冗余自item表）',
    `deliver_code`            varchar(64) CHARACTER SET utf8  NOT NULL DEFAULT '' COMMENT '发货编码',
    `price`                   int(11)                         NOT NULL DEFAULT '0' COMMENT '售价（单位：分）',
    `market_price`            int(11)                         NOT NULL DEFAULT '0' COMMENT '市场价（单位：分）',
    `first_property_pair_id`  bigint(20)                      NOT NULL DEFAULT '0' COMMENT '第一维销售属性对id',
    `second_property_pair_id` bigint(20)                      NOT NULL DEFAULT '0' COMMENT '第二维销售属性对id',
    `status`                  tinyint(3)                      NOT NULL DEFAULT '0' COMMENT '状态，1-可用，0-停用',
    `restriction`             int(11)                         NOT NULL DEFAULT '0' COMMENT '限购数量',
    `gross_weight`            int(11)                         NOT NULL DEFAULT '0' COMMENT '毛重（单位：克）',
    `extra`                   varchar(256) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '扩展字段',
    `is_deleted`              tinyint(3)                      NOT NULL DEFAULT '0' COMMENT '是否逻辑删除（0-否，1-是）',
    `version`                 bigint(20)                      NOT NULL DEFAULT '0' COMMENT '更新版本号（用于实现乐观锁）',
    `create_time`             timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`             timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_updateTime` (`update_time`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_deliverCode` (`deliver_code`),
    KEY `idx_deliverAreaId` (`deliver_area_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='sku表';


-- sku主图属性
CREATE TABLE `sku_property_image`
(
    `id`               int(11)      NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `item_id`          bigint(20)   NOT NULL DEFAULT '0' COMMENT '商品id',
    `property_pair_id` bigint(20)   NOT NULL DEFAULT '0' COMMENT '属性对id',
    `url`              varchar(128) NOT NULL DEFAULT '' COMMENT 'sku图片url',
    `width`            int(11)      NOT NULL DEFAULT '0' COMMENT '图片宽度',
    `height`           int(11)      NOT NULL DEFAULT '0' COMMENT '图片高度',
    `sort_order`       int(11)      NOT NULL DEFAULT '0' COMMENT '排序值（倒序）',
    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`       tinyint(3)   NOT NULL DEFAULT '0' COMMENT '是否删除，0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_itemId_pairId` (`item_id`, `property_pair_id`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_updateTime` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Sku属性图片';


-- 库存表
CREATE TABLE `sku_stock`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `sku_id`           bigint(20) unsigned NOT NULL COMMENT 'sku唯一标识',
    `item_id`          bigint(20) unsigned NOT NULL COMMENT '商品id',
    `avail_count`      int(11)             NOT NULL DEFAULT '0' COMMENT '变更后可用库存',
    `not_deliver_lock` int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '待发货锁定',
    `not_pay_lock`     int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '待付款锁定',
    `version`          int(11)             NOT NULL DEFAULT '0' COMMENT '更新版本号（用于实现乐观锁）',
    `is_deleted`       tinyint(3)          NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
    `create_time`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sku_id` (`sku_id`),
    KEY `idx_item_id` (`item_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='库存表';


-- 库存日志表
CREATE TABLE `stock_change_log`
(
    `id`               int(11) unsigned    NOT NULL AUTO_INCREMENT,
    `order_id`         bigint(20) unsigned NOT NULL COMMENT '订单id',
    `event_type`       tinyint(3) unsigned NOT NULL COMMENT '0-增加预售, 1-商家调库存, 2-仓库全量库存同步, 3-采购订单入库, 4-订单已发货, 5-提交订单,6-取消订单,7-支付成功,8-支付超时,9-待发货退款,10-支付超时后下单,11-商家库存创建',
    `item_id`          varchar(32)         NOT NULL DEFAULT '' COMMENT '商品id',
    `sku_id`           varchar(32)         NOT NULL DEFAULT '' COMMENT 'skuId',
    `activity_id`      bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '活动id',
    `change_count`     int(11)             NOT NULL DEFAULT '0' COMMENT '变更数量',
    `avail_count`      int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '变更后可用数量',
    `not_deliver_lock` int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '待发货数量',
    `not_pay_lock`     int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '待支付数量',
    `operator`         varchar(50)         NOT NULL DEFAULT '' COMMENT '操作来源',
    `is_deleted`       tinyint(3)          NOT NULL DEFAULT '0' COMMENT '是否逻辑删除0-未删除，1-删除',
    `create_time`      timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id_sku_id` (`order_id`, `sku_id`),
    KEY `sku_id` (`sku_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


-- 品牌 -
CREATE TABLE `brand`
(
    `id`           int(11) unsigned    NOT NULL AUTO_INCREMENT COMMENT '品牌id',
    `name`         varchar(50)         NOT NULL DEFAULT '' COMMENT '品牌名称',
    `name_en`      varchar(50)         NOT NULL DEFAULT '' COMMENT '英文名称',
    `image`        varchar(100)        NOT NULL DEFAULT '' COMMENT '品牌图片访问URL',
    `image_ad`     varchar(100)        NOT NULL DEFAULT '' COMMENT '品牌广告图',
    `hot_spots`    varchar(500)        NOT NULL DEFAULT '' COMMENT '品牌热点',
    `introduce`    varchar(500)        NOT NULL DEFAULT '' COMMENT '品牌介绍',
    `state_id`     int(11)             NOT NULL DEFAULT '0' COMMENT '品牌所属国',
    `is_self_own`  tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否自营品牌',
    `is_available` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用；0：否，1：是',
    `is_show`      tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否展示。0-不展示，1-展示',
    `rank`         int(8)              NOT NULL DEFAULT '0' COMMENT '品牌分值',
    `create_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `audit_status` tinyint(4)          NOT NULL DEFAULT '0' COMMENT '审核状态：0-待审核，1-审核通过，-1-审核不通过',
    `is_deleted`   tinyint(4)          NOT NULL DEFAULT '0' COMMENT '是否删除：0-正常，-1表示删除',
    `source`       tinyint(2)                   DEFAULT '0' COMMENT '来源, 1-商家自建,0-平台',
    `abandon_time` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '记录废除时间戳',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`, `abandon_time`),
    KEY `idx_name` (`name`(10)),
    KEY `idx_nameEn` (`name_en`(10))
) ENGINE = InnoDB
  AUTO_INCREMENT = 43347
  DEFAULT CHARSET = utf8 COMMENT ='品牌表';


-- 商品表
CREATE TABLE `item`
(
    `id`                  bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '商品id，商品唯一标识',
    `long_title`          varchar(128)  NOT NULL DEFAULT '' COMMENT '长名称',
    `short_title`         varchar(128)  NOT NULL DEFAULT '' COMMENT '短名称',
    `selling_point`       varchar(255)           DEFAULT '' COMMENT '卖点文案',
    `shop_id`             bigint(20)    NOT NULL DEFAULT '0' COMMENT '店铺id',
    `category_id`         bigint(20)    NOT NULL DEFAULT '0' COMMENT '叶子类目id',
    `category_path`       varchar(64)   NOT NULL DEFAULT '' COMMENT '类目id全路径，eg. /1/2/3/4',
    `channel`             tinyint(3)    NOT NULL COMMENT '1-斑马商城;2-本地生活;3-小区乐;4-积分商品(美食买手);5-斑马优选;6-小区乐心选;7-直播;8-分销代理',
    `type`                tinyint(3)    NOT NULL DEFAULT '0' COMMENT '商品类型',
    `sub_type`            tinyint(3)    NOT NULL DEFAULT '0' COMMENT '商品子类型',
    `brand_id`            bigint(20)    NOT NULL DEFAULT '0' COMMENT '品牌id',
    `shelf`               tinyint(3)    NOT NULL DEFAULT '0' COMMENT '上下架状态，0-下架，1-上架',
    `sale_time`           bigint(20)             DEFAULT '0' COMMENT '开售时间,时间戳(秒)',
    `first_shelf_time`    bigint(20)             DEFAULT '0' COMMENT '首次上架时间,时间戳(秒)',
    `status`              tinyint(3)    NOT NULL DEFAULT '0' COMMENT '商品状态，0-待提交审核，1-审核中，2-审核通过，-1-审核不通过，-2-强制下架',
    `deliver_area_id`     bigint(20)             DEFAULT '0' COMMENT '发货地id',
    `supplier_id`         bigint(20)             DEFAULT '0' COMMENT '供应商id',
    `express_template_id` bigint(20)             DEFAULT '0' COMMENT '运费模板id',
    `search_tags`         varchar(128)           DEFAULT '' COMMENT '搜索标签',
    `properties`          varchar(1024) NOT NULL DEFAULT '' COMMENT '字符串形式的商品属性',
    `sku_num`             int(11)       NOT NULL DEFAULT '0' COMMENT 'sku数量',
    `channel_data`        varchar(512)  NOT NULL DEFAULT '' COMMENT '渠道相关数据',
    `tag_info`            varchar(1024) NOT NULL DEFAULT '' COMMENT '标签',
    `extra`               varchar(1024) NOT NULL DEFAULT '' COMMENT '商品扩展信息',
    `create_time`         timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`             bigint(20)    NOT NULL DEFAULT '0' COMMENT '修改版本号，用于实现乐观锁',
    `supply_price`        int(11)                DEFAULT NULL,
    `is_deleted`          tinyint(3)    NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_shopId` (`shop_id`),
    KEY `idx_categoryId` (`category_id`),
    KEY `idx_updateTime` (`update_time`),
    KEY `idx_createTime` (`create_time`),
    KEY `idx_brandId` (`brand_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6123643948
  DEFAULT CHARSET = utf8mb4;


