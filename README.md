# 美国礼品协会电商系统（gmall）
#### 系统介绍
gmall就是一套对接美国礼品协会的电商系统。主要功能包括后台功能和前台用户功能。主要功能如下：
* 后台管理功能：用于基础数据管理，用户管理，商品类目管理和订单管理模块。
* 首页：展示商品信息，猜你喜欢模块，热销品模块，top50等。
* 登录注册功能：提供用户基本的登录和注册功能。
* 商品详情页：主要承载商品信息展示功能，里面包含价格和制作工艺信息。
* 购物车模块：保存用户商品购物车，里面包含商品对应的价格和促销信息。
* 交易模块：用户询盘订单信息。



#### 技术框架

| 技术                                                      | 描述       |
| --------------------------------------------------------- | ---------- |
| [Spring Boot](https://spring.io/projects/spring-boot)     | 核心框架   |
| [MyBatis](http://www.mybatis.org/mybatis-3/zh/index.html) | ORM框架    |
| [Druid](https://github.com/alibaba/druid/wiki/常见问题)   | 数据连接池 |
| [Maven](http://maven.apache.org/)                         | 项目管理   |



#### 项目结构

```
byron-mall // 拜式电商系统
|
├── gmall-common // 通用包
|
├── gmall-generator // mybatis-generator集成，用于生成mapper和对应xml
|
├── gmall-dal // 数据访问模块，不需要单独部署
|
├── gmall-business // 业务逻辑模块，不需要单独部署
|
├── gmall-web-admin // 后台web系统模块（部署模块）
|
├── gmall-web-distributor // 前台分销商web系统模块（部署模块）

```



#### 模块介绍

> gmall-common 

说明：公共基础模块，包含公共的工具类、枚举、通用配置

> gmall-dal

说明：主要包含基础实体、Mapper等数据访问层

> mall-trade-generator

说明：集成了mybatis-generator，方便快速生成对应mapper和xml代码

> gmall-business

说明：电商业务层代码，主要包含service和manager模块

> gmall-web-admin 

说明：电商后台发布模块。后台商品，交易，用户等数据处理的http接口实现。

> gmall-web-admin 

说明：电商前台发布模块。包含前台用户访问的商品，交易，用户数据等http接口实现。# gmall
