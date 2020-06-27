 
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for restaurant
-- ----------------------------
use diancan;
DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '饭店id',
  `name` varchar(32) NOT NULL COMMENT '饭店名',
  `address` varchar(256) NOT NULL COMMENT '饭店地址',
  `phone` varchar(11) NOT NULL COMMENT '饭店电话',
  `classification` varchar(32) DEFAULT '' COMMENT '饭店分类',
  `scoring_times` bigint(20) DEFAULT null COMMENT '评分次数',
  `total_score` bigint(20) DEFAULT null COMMENT '总分数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='饭店的信息，分数用总分数除以评分次数求出';

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `account` varchar(32) NOT NULL COMMENT '管理员账号',
  `password` varchar(32) NOT NULL  COMMENT '密码',
  `restaurant_id` bigint(20) DEFAULT NULL COMMENT '饭店id',
  PRIMARY KEY (`id`),
  FOREIGN KEY(restaurant_id) REFERENCES restaurant(id),
  UNIQUE KEY `account` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个饭店都有一个管理员，管理饭店和菜品的信息';

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜品id',
  `name` varchar(32) NOT NULL COMMENT '菜品名',
  `image` varchar(128) DEFAULT '' COMMENT '菜品图片地址',
  `classification` varchar(32) DEFAULT '' COMMENT '菜品分类',
  `cuisine` varchar(32) DEFAULT '' COMMENT '菜品特色',
  `sales_volume` bigint(20) DEFAULT null COMMENT '菜品销量',
  `price` bigint(20) NOT NULL COMMENT '菜品价格',
  `scoring_times` bigint(20) DEFAULT null COMMENT '评分次数',
  `total_score` bigint(20) DEFAULT null COMMENT '总分数',
  `restaurant_id` bigint(20) NOT NULL COMMENT '饭店id',
  PRIMARY KEY (`id`),
  FOREIGN KEY(restaurant_id) REFERENCES restaurant(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜品信息，分数用总分数除以评分次数求出';

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '顾客id',
  `nickname` varchar(32) NOT NULL COMMENT '顾客网名',
  `account` varchar(32) NOT NULL COMMENT '顾客账号',
  `password` varchar(32) NOT NULL  COMMENT '顾客密码',
  `phone` varchar(11) NOT NULL COMMENT '顾客电话',
  `address` varchar(256) DEFAULT '' COMMENT '顾客地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='顾客信息';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `price` bigint(20) NOT NULL COMMENT '订单总价',
  `date` datetime DEFAULT NULL COMMENT '订单时间',
  `state` int(1) DEFAULT NULL COMMENT '订单状态：1、未付款 2、已付款,未发货 3、已发货,未确认 4、交易成功 5、交易关闭 6、已评分',
  `dish_id_list` varchar(32) NOT NULL COMMENT '菜品id列表,逗号隔开',
  `restaurant_id` bigint(20) NOT NULL COMMENT '饭店id',
  `customer_id` bigint(20) NOT NULL COMMENT '顾客id',
  PRIMARY KEY (`id`),
  FOREIGN KEY(restaurant_id) REFERENCES restaurant(id),
  FOREIGN KEY(customer_id) REFERENCES customer(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';