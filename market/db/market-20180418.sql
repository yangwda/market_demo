/*
Navicat MySQL Data Transfer

Source Server         : market
Source Server Version : 50624
Source Host           : localhost:3399
Source Database       : market

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-04-18 23:44:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for yj_market_animal
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_animal`;
CREATE TABLE `yj_market_animal` (
  `animalId` int(11) NOT NULL AUTO_INCREMENT,
  `animalName` varchar(200) NOT NULL,
  PRIMARY KEY (`animalId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='兽种类';

-- ----------------------------
-- Records of yj_market_animal
-- ----------------------------
INSERT INTO `yj_market_animal` VALUES ('1', '家禽');
INSERT INTO `yj_market_animal` VALUES ('2', '猪');
INSERT INTO `yj_market_animal` VALUES ('3', '貂');

-- ----------------------------
-- Table structure for yj_market_callback
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_callback`;
CREATE TABLE `yj_market_callback` (
  `callBackId` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `memberId` int(11) NOT NULL,
  `memberName` varchar(255) NOT NULL,
  `memberTel` varchar(255) DEFAULT NULL,
  `memberPhone` varchar(255) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `callBackRemarks` varchar(255) DEFAULT NULL,
  `callBackOver` varchar(255) DEFAULT NULL,
  `callBackOverTime` datetime DEFAULT NULL,
  PRIMARY KEY (`callBackId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='电话回访';

-- ----------------------------
-- Records of yj_market_callback
-- ----------------------------
INSERT INTO `yj_market_callback` VALUES ('1', '10', '4', '老王', '', '15566666666', '2017-10-06 07:00:52', '测试，回访备注', null, null);
INSERT INTO `yj_market_callback` VALUES ('3', '10', '4', '老王', '', '15566666666', '2017-10-06 07:22:16', '再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访，再访', null, null);
INSERT INTO `yj_market_callback` VALUES ('4', '11', '4', '老王', '', '15566666666', '2018-03-23 15:20:50', '跟踪消炎药使用情况', null, null);

-- ----------------------------
-- Table structure for yj_market_callback_record
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_callback_record`;
CREATE TABLE `yj_market_callback_record` (
  `callBackRecordId` int(11) NOT NULL AUTO_INCREMENT,
  `callBackId` int(11) NOT NULL,
  `callBackTalkAbout` varchar(255) DEFAULT NULL,
  `callBackTime` datetime NOT NULL,
  PRIMARY KEY (`callBackRecordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电话回访记录';

-- ----------------------------
-- Records of yj_market_callback_record
-- ----------------------------

-- ----------------------------
-- Table structure for yj_market_gift_config
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_gift_config`;
CREATE TABLE `yj_market_gift_config` (
  `giftConfigId` int(11) NOT NULL AUTO_INCREMENT,
  `giftConfigDesc` varchar(200) NOT NULL,
  `giftConfigType` varchar(20) NOT NULL COMMENT '按购买金额赠/暂时不用',
  `giftConfigBeginTime` datetime NOT NULL,
  `giftConfigEndTime` datetime NOT NULL,
  `giftConfigRemarks` varchar(2000) DEFAULT NULL,
  `goodsId` int(11) NOT NULL,
  `goodsName` varchar(200) NOT NULL,
  `goodsNo` varchar(200) NOT NULL,
  `buyLimit` decimal(9,2) NOT NULL,
  PRIMARY KEY (`giftConfigId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_gift_config
-- ----------------------------
INSERT INTO `yj_market_gift_config` VALUES ('3', '满200送药', '按购买金额赠', '2017-08-01 00:00:00', '2017-12-31 00:00:00', '优惠多多', '1', ' 消炎药-0001', 'xy0001', '200.50');
INSERT INTO `yj_market_gift_config` VALUES ('5', '满500送送送', '按购买金额赠', '2017-08-01 00:00:00', '2017-09-30 00:00:00', '送送送送送', '6', '消炎药-0002', 'xy0002', '500.66');

-- ----------------------------
-- Table structure for yj_market_gift_config_line
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_gift_config_line`;
CREATE TABLE `yj_market_gift_config_line` (
  `giftConfigLineId` int(11) NOT NULL AUTO_INCREMENT,
  `giftConfigId` int(11) NOT NULL,
  `giftGoodsId` int(11) NOT NULL,
  `giftGoodsName` varchar(200) NOT NULL,
  `giftGoodsNo` varchar(200) NOT NULL,
  `giftGoodsCount` int(11) NOT NULL,
  `giftGoodsCountUnit` varchar(255) NOT NULL,
  PRIMARY KEY (`giftConfigLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_gift_config_line
-- ----------------------------
INSERT INTO `yj_market_gift_config_line` VALUES ('1', '2', '1', ' 消炎药-0001', 'xy0001', '1', '袋');
INSERT INTO `yj_market_gift_config_line` VALUES ('2', '3', '1', ' 消炎药-0001', 'xy0001', '2', '包');
INSERT INTO `yj_market_gift_config_line` VALUES ('3', '4', '1', ' 消炎药-0001', 'xy0001', '1', '袋');
INSERT INTO `yj_market_gift_config_line` VALUES ('4', '5', '5', '热水壶', 'zp0001', '1', '个');

-- ----------------------------
-- Table structure for yj_market_gift_ct_config
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_gift_ct_config`;
CREATE TABLE `yj_market_gift_ct_config` (
  `giftConfigId` int(11) NOT NULL AUTO_INCREMENT,
  `giftConfigDesc` varchar(200) NOT NULL,
  `giftConfigType` varchar(20) NOT NULL COMMENT '按购买数量赠/暂时不用',
  `giftConfigBeginTime` datetime NOT NULL,
  `giftConfigEndTime` datetime NOT NULL,
  `giftConfigRemarks` varchar(2000) DEFAULT NULL,
  `goodsId` int(11) NOT NULL,
  `goodsName` varchar(200) NOT NULL,
  `goodsNo` varchar(200) NOT NULL,
  `giftAmount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '等值产品金额',
  PRIMARY KEY (`giftConfigId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='2018-03-24，将饲料的惠赠策略调整为：只提供等值产品金额项，订单付款时提示即可';

-- ----------------------------
-- Records of yj_market_gift_ct_config
-- ----------------------------
INSERT INTO `yj_market_gift_ct_config` VALUES ('2', '快啊啊啊啊啊啊啊啊啊--哈哈哈', '按购买金额赠', '2018-03-02 00:00:00', '2018-12-31 00:00:00', '好哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦----ddddddddddd', '2', '猪长的快', 'CC007', '66.88');
INSERT INTO `yj_market_gift_ct_config` VALUES ('3', 'sad擦 ', '按购买金额赠', '2018-03-10 00:00:00', '2018-12-31 00:00:00', '哈哈哈哈哈', '4', '小鸡快长', 'zz003', '66.66');
INSERT INTO `yj_market_gift_ct_config` VALUES ('4', 'asd但是', '按购买金额赠', '2018-03-01 00:00:00', '2018-12-31 00:00:00', '反倒是三方框架', '7', '貂喜欢', 'FD0003', '45.67');

-- ----------------------------
-- Table structure for yj_market_gift_ct_config_line
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_gift_ct_config_line`;
CREATE TABLE `yj_market_gift_ct_config_line` (
  `giftConfigLineId` int(11) NOT NULL AUTO_INCREMENT,
  `giftConfigId` int(11) NOT NULL,
  `checkType` varchar(20) NOT NULL COMMENT '当时兑现/累积',
  `buyLimit` int(11) NOT NULL COMMENT '购买数量',
  `buyLimitPunit` varchar(50) NOT NULL COMMENT '购买数量单位',
  `giftGoodsId` int(11) NOT NULL,
  `giftGoodsName` varchar(200) NOT NULL,
  `giftGoodsNo` varchar(200) NOT NULL,
  `giftGoodsCount` int(11) NOT NULL,
  `giftGoodsCountUnit` varchar(50) NOT NULL,
  PRIMARY KEY (`giftConfigLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_gift_ct_config_line
-- ----------------------------
INSERT INTO `yj_market_gift_ct_config_line` VALUES ('5', '2', '当时兑现', '111', '袋', '2', '猪长的快', 'CC007', '5', '袋');
INSERT INTO `yj_market_gift_ct_config_line` VALUES ('6', '2', '累积', '5', '箱', '2', '猪长的快', 'CC007', '2', '包');

-- ----------------------------
-- Table structure for yj_market_goods
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_goods`;
CREATE TABLE `yj_market_goods` (
  `goodsId` int(11) NOT NULL AUTO_INCREMENT,
  `goodsName` varchar(200) NOT NULL,
  `goodsNo` varchar(200) NOT NULL,
  `goodsManufacturer` varchar(200) NOT NULL,
  `goodsUsage` varchar(2000) NOT NULL,
  `goodsType` varchar(100) NOT NULL COMMENT '药品/饲料/赠品',
  `goodsStatus` varchar(100) NOT NULL COMMENT '在售/停售',
  `goodsRemark` varchar(2000) DEFAULT NULL,
  `common` varchar(2000) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`goodsId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='产品信息';

-- ----------------------------
-- Records of yj_market_goods
-- ----------------------------
INSERT INTO `yj_market_goods` VALUES ('1', ' 消炎药-0001', 'xy0001', '测试药品', '消炎', '药品', '在售', '好东西，专用药，这个是编辑', '测试啦,gn,修改好用', '2017-07-15 21:47:35');
INSERT INTO `yj_market_goods` VALUES ('2', '猪长的快', 'CC007', '自家田自家地', '养猪', '饲料', '在售', '好', 'good', '2017-07-20 21:10:51');
INSERT INTO `yj_market_goods` VALUES ('3', '老王家养鸡饲料', 'FD002', '大连', '养鸡', '饲料', '在售', '好东西', 'feed', '2017-07-20 21:14:11');
INSERT INTO `yj_market_goods` VALUES ('4', '小鸡快长', 'zz003', '普兰店', '喂鸡', '饲料', '在售', '好，爱谁谁', 'G', '2017-07-20 21:18:24');
INSERT INTO `yj_market_goods` VALUES ('5', '热水壶', 'zp0001', 'made in china', '烧水的', '赠品', '在售', '热的快', 'gff', '2017-07-20 22:43:17');
INSERT INTO `yj_market_goods` VALUES ('6', '消炎药-0002', 'xy0002', '开发测试', '消炎', '药品', '在售', '这药倍儿棒', '哈哈哈哈哈', '2017-08-02 07:44:57');
INSERT INTO `yj_market_goods` VALUES ('7', '貂喜欢', 'FD0003', '自家产', '喂貂，长的快，毛光', '饲料', '在售', '好，好，好', '呵呵呵', '2017-08-02 07:57:09');
INSERT INTO `yj_market_goods` VALUES ('8', '代乳粉', 'DRF-0001', '测试使用', '听说好，用途广', '饲料', '在售', '好东西啊', '利润薄，不打折', '2018-04-18 21:32:30');

-- ----------------------------
-- Table structure for yj_market_goods_unit_price
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_goods_unit_price`;
CREATE TABLE `yj_market_goods_unit_price` (
  `goodsUnitId` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(11) NOT NULL,
  `goodsUnitName` varchar(255) NOT NULL,
  `goodsUnitRate` int(11) NOT NULL,
  `goodsUnitPrice` decimal(9,2) NOT NULL,
  PRIMARY KEY (`goodsUnitId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='产品包装规格和价格信息';

-- ----------------------------
-- Records of yj_market_goods_unit_price
-- ----------------------------
INSERT INTO `yj_market_goods_unit_price` VALUES ('4', '1', '箱', '1', '245.66');
INSERT INTO `yj_market_goods_unit_price` VALUES ('5', '1', '包', '4', '70.88');
INSERT INTO `yj_market_goods_unit_price` VALUES ('6', '1', '袋', '20', '15.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('13', '6', '箱', '1', '12.34');
INSERT INTO `yj_market_goods_unit_price` VALUES ('14', '6', '盒', '4', '45.67');
INSERT INTO `yj_market_goods_unit_price` VALUES ('15', '6', '袋', '20', '56.78');
INSERT INTO `yj_market_goods_unit_price` VALUES ('22', '2', '桶', '1', '730.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('23', '2', '袋', '50', '15.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('24', '7', '桶', '1', '456.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('25', '7', '袋', '30', '15.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('26', '8', '吨', '1', '20000.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('27', '8', '包', '50', '400.00');
INSERT INTO `yj_market_goods_unit_price` VALUES ('28', '8', '袋', '20', '20.00');

-- ----------------------------
-- Table structure for yj_market_illness
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_illness`;
CREATE TABLE `yj_market_illness` (
  `illnessId` int(11) NOT NULL AUTO_INCREMENT,
  `illnessName` varchar(500) NOT NULL,
  `callBack` varchar(10) NOT NULL,
  PRIMARY KEY (`illnessId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='疾病种类';

-- ----------------------------
-- Records of yj_market_illness
-- ----------------------------
INSERT INTO `yj_market_illness` VALUES ('1', '脱毛', '不回访');
INSERT INTO `yj_market_illness` VALUES ('2', '拉稀', '回访');

-- ----------------------------
-- Table structure for yj_market_member
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_member`;
CREATE TABLE `yj_market_member` (
  `memberId` int(11) NOT NULL AUTO_INCREMENT,
  `memberName` varchar(100) NOT NULL,
  `memberNo` varchar(60) NOT NULL,
  `memberTel` varchar(200) DEFAULT NULL,
  `memberPhone` varchar(200) DEFAULT NULL,
  `memberQQ` varchar(200) DEFAULT NULL,
  `memberWeixin` varchar(200) DEFAULT NULL,
  `memberAddress` varchar(1000) DEFAULT NULL,
  `memberBusiRemark` varchar(2000) DEFAULT NULL,
  `common` varchar(2000) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`memberId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='客户信息';

-- ----------------------------
-- Records of yj_market_member
-- ----------------------------
INSERT INTO `yj_market_member` VALUES ('1', '老张', '20170200000000000100', '0411-83030011', '186', '', '', '', '貂', '不容易啊', '2017-02-28 23:14:49');
INSERT INTO `yj_market_member` VALUES ('2', '测试', '20170300000000000101', '', '123456', '', '', '大连普兰店城子坦', '', '', '2017-03-02 11:46:27');
INSERT INTO `yj_market_member` VALUES ('3', '老李', '20170300000000000102', '', '1332222222', '', '', '下午', '', '勤劳', '2017-03-02 14:43:25');
INSERT INTO `yj_market_member` VALUES ('4', '老王', '20170300000000000103', '', '15566666666', '', '', '', '', '热情', '2017-03-02 14:45:14');
INSERT INTO `yj_market_member` VALUES ('5', '小强', '20170900000000000104', '', '333', '', '', '', '', '', '2017-09-05 02:31:37');
INSERT INTO `yj_market_member` VALUES ('6', '大强', '20170900000000000105', '', '222222', '', '', '哈哈哈哈哈', '', '', '2017-09-05 02:33:07');
INSERT INTO `yj_market_member` VALUES ('7', '怡亚通', '20170900000000000106', '', '12344322', '', '', '地球，中国', '', '不在火星', '2017-09-05 02:34:51');
INSERT INTO `yj_market_member` VALUES ('8', '乔峰', '20170900000000000107', '', '132222222222', '', '', '丐帮总舵', '', '帮主', '2017-09-05 02:36:43');

-- ----------------------------
-- Table structure for yj_market_member_gift_accumulation
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_member_gift_accumulation`;
CREATE TABLE `yj_market_member_gift_accumulation` (
  `giftAccumulationId` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `orderLineId` int(11) NOT NULL,
  `goodsId` int(11) NOT NULL,
  `goodsName` varchar(200) NOT NULL,
  `buyTime` datetime NOT NULL,
  `buyCount` int(11) NOT NULL,
  `checkTime` datetime DEFAULT NULL,
  `checkCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`giftAccumulationId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_member_gift_accumulation
-- ----------------------------
INSERT INTO `yj_market_member_gift_accumulation` VALUES ('2', '10', '15', '2', '猪长的快', '2017-09-29 21:11:36', '3333', null, '0');
INSERT INTO `yj_market_member_gift_accumulation` VALUES ('6', '9', '31', '2', '猪长的快', '2017-10-05 19:02:04', '3333', null, '0');

-- ----------------------------
-- Table structure for yj_market_member_no_seq
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_member_no_seq`;
CREATE TABLE `yj_market_member_no_seq` (
  `memberNoSeqId` int(11) NOT NULL,
  `memberNoSeq` int(11) NOT NULL,
  PRIMARY KEY (`memberNoSeqId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_member_no_seq
-- ----------------------------
INSERT INTO `yj_market_member_no_seq` VALUES ('1', '108');

-- ----------------------------
-- Table structure for yj_market_member_voucher
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_member_voucher`;
CREATE TABLE `yj_market_member_voucher` (
  `memberVoucherId` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `sourceOrderId` int(11) NOT NULL,
  `sourceVoucherMoney` decimal(9,2) NOT NULL COMMENT '已使用/未使用',
  `remainingMoney` decimal(9,2) NOT NULL,
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`memberVoucherId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='会员代金券记录';

-- ----------------------------
-- Records of yj_market_member_voucher
-- ----------------------------
INSERT INTO `yj_market_member_voucher` VALUES ('1', '1', '2017-10-06 07:22:16', '10', '11.11', '0.00', null);
INSERT INTO `yj_market_member_voucher` VALUES ('2', '1', '2017-10-06 08:21:08', '10', '99.99', '0.00', null);
INSERT INTO `yj_market_member_voucher` VALUES ('3', '1', '2018-03-24 09:57:33', '15', '112.20', '0.00', null);
INSERT INTO `yj_market_member_voucher` VALUES ('4', '1', '2018-03-28 00:39:06', '21', '133.76', '46.06', '等值产品金额生成的代金券');
INSERT INTO `yj_market_member_voucher` VALUES ('5', '1', '2018-03-28 00:56:47', '21', '10.00', '10.00', '单次购买活动生成的代金券');
INSERT INTO `yj_market_member_voucher` VALUES ('6', '1', '2018-03-28 01:00:45', '22', '179.43', '179.43', '等值产品金额生成的代金券');
INSERT INTO `yj_market_member_voucher` VALUES ('7', '1', '2018-03-28 01:01:51', '22', '12.89', '12.89', '单次购买活动生成的代金券');

-- ----------------------------
-- Table structure for yj_market_member_year_accumulation
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_member_year_accumulation`;
CREATE TABLE `yj_market_member_year_accumulation` (
  `yearAccumulationId` int(11) NOT NULL AUTO_INCREMENT,
  `accumulationYear` varchar(255) NOT NULL,
  `memberId` int(11) NOT NULL,
  `memberName` varchar(255) NOT NULL,
  `memberNo` varchar(60) NOT NULL,
  `accumulationMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `checkStatus` varchar(255) NOT NULL COMMENT '已兑换/未兑换',
  `orderId` int(11) DEFAULT NULL,
  `checkTime` datetime DEFAULT NULL,
  PRIMARY KEY (`yearAccumulationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_member_year_accumulation
-- ----------------------------

-- ----------------------------
-- Table structure for yj_market_member_year_accumulation_line
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_member_year_accumulation_line`;
CREATE TABLE `yj_market_member_year_accumulation_line` (
  `yearAccumulationLineId` int(11) NOT NULL AUTO_INCREMENT,
  `yearAccumulationId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `orderNo` varchar(60) NOT NULL,
  `yearAccumulationMoney` decimal(9,2) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`yearAccumulationLineId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_member_year_accumulation_line
-- ----------------------------

-- ----------------------------
-- Table structure for yj_market_once_buy
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_once_buy`;
CREATE TABLE `yj_market_once_buy` (
  `onceById` int(11) NOT NULL AUTO_INCREMENT,
  `beginAmount` decimal(9,2) NOT NULL,
  `endAmount` decimal(9,2) NOT NULL,
  `perRate` int(11) NOT NULL,
  PRIMARY KEY (`onceById`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_once_buy
-- ----------------------------
INSERT INTO `yj_market_once_buy` VALUES ('1', '3000.00', '4000.00', '3');
INSERT INTO `yj_market_once_buy` VALUES ('2', '2000.00', '3000.00', '2');
INSERT INTO `yj_market_once_buy` VALUES ('3', '1000.00', '2000.00', '1');
INSERT INTO `yj_market_once_buy` VALUES ('4', '8000.00', '9000.00', '8');
INSERT INTO `yj_market_once_buy` VALUES ('5', '5000.00', '6000.00', '5');
INSERT INTO `yj_market_once_buy` VALUES ('6', '6000.00', '7000.00', '6');
INSERT INTO `yj_market_once_buy` VALUES ('13', '7001.00', '7001.00', '3');

-- ----------------------------
-- Table structure for yj_market_order
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_order`;
CREATE TABLE `yj_market_order` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(60) NOT NULL,
  `orderType` varchar(20) NOT NULL COMMENT '销售/服务/消费累积兑换',
  `memberId` int(11) NOT NULL,
  `memberName` varchar(100) NOT NULL,
  `memberNo` varchar(60) NOT NULL,
  `callBackId` int(11) DEFAULT NULL,
  `orderStatus` varchar(20) NOT NULL COMMENT '新建/生效/废弃',
  `orderRemark` varchar(2000) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `orderTotalMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `orderChargeMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `orderCutMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `yearAccumulationMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `payOffVoucherTotalMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `payOffCashTotalMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `payOffStatus` varchar(20) NOT NULL COMMENT '赠品兑换/已付款/部分付款/未付款',
  `salesReturn` varchar(20) NOT NULL COMMENT '有退货/无退货',
  `orderTotalGiftAmount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '等值产品金额',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='销售单价信息；2018-03-24，调整，等值产品金额，记录到order和order line，付款的时候，显示，供参考';

-- ----------------------------
-- Records of yj_market_order
-- ----------------------------
INSERT INTO `yj_market_order` VALUES ('1', '20170927011300000001', '销售', '1', '老张', '20170200000000000100', null, '新建', null, '2017-09-27 01:13:32', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('2', '20170927020300000002', '销售', '1', '老张', '20170200000000000100', null, '新建', null, '2017-09-27 02:02:50', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('3', '20170927020900000003', '销售', '3', '老李', '20170300000000000102', null, '新建', null, '2017-09-27 02:09:26', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('6', '20170927022200000006', '销售', '4', '老王', '20170300000000000103', null, '生效', null, '2017-09-27 02:22:37', '147232.58', '0.00', '7232.58', '0.00', '0.00', '140000.00', '已付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('7', '20170927022600000007', '销售', '4', '老王', '20170300000000000103', null, '新建', null, '2017-09-27 02:26:48', '142830.16', '0.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('8', '20170927022900000008', '销售', '4', '老王', '20170300000000000103', null, '生效', null, '2017-09-27 02:29:27', '142830.16', '0.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('9', '20170929210600000009', '销售', '4', '老王', '20170300000000000103', null, '生效', null, '2017-09-29 21:06:42', '147232.58', '0.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('10', '20170929211100000010', '销售', '4', '老王', '20170300000000000103', '3', '生效', '测试，的地地道道的的;;还没给全', '2017-09-29 21:11:35', '147232.58', '0.00', '32.58', '0.00', '0.00', '147200.00', '已付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('11', '20180323151900000011', '销售', '4', '老王', '20170300000000000103', '4', '生效', 'null;;小幅惠赠', '2018-03-23 15:19:41', '1395.00', '1395.00', '5.00', '0.00', '0.00', '90.00', '部分付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('12', '20180324094500000012', '销售', '4', '老王', '20170300000000000103', null, '生效', null, '2018-03-24 09:45:02', '75.00', '75.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('13', '20180324094900000013', '销售', '4', '老王', '20170300000000000103', null, '生效', 'null;;手套10双，白大褂2套', '2018-03-24 09:49:14', '90.00', '90.00', '0.00', '0.00', '0.00', '90.00', '已付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('14', '20180324095300000014', '销售', '4', '老王', '20170300000000000103', null, '生效', 'null;;赠水壶1个', '2018-03-24 09:53:33', '454.24', '454.24', '0.00', '0.00', '0.00', '0.00', '部分付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('15', '20180324095600000015', '销售', '4', '老王', '20170300000000000103', null, '生效', 'null;;现在好了没', '2018-03-24 09:56:43', '3747.48', '3747.48', '7.48', '0.00', '0.00', '3740.00', '已付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('16', '20180324100000000016', '销售', '1', '老张', '20170200000000000100', null, '生效', '美玉呵呵呵呵呵呵', '2018-03-24 10:00:46', '990.00', '990.00', '0.00', '0.00', '0.00', '0.00', '部分付款', '无退货', '0.00');
INSERT INTO `yj_market_order` VALUES ('17', '20180324224500000017', '销售', '1', '老张', '20170200000000000100', null, '生效', null, '2018-03-24 22:45:06', '6120.00', '360.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '1471.36');
INSERT INTO `yj_market_order` VALUES ('18', '20180327232400000018', '销售', '1', '老张', '20170200000000000100', null, '生效', null, '2018-03-27 23:24:33', '7920.00', '7920.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '133.76');
INSERT INTO `yj_market_order` VALUES ('19', '20180327234400000019', '销售', '1', '老张', '20170200000000000100', null, '生效', null, '2018-03-27 23:44:07', '10180.00', '10180.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '133.76');
INSERT INTO `yj_market_order` VALUES ('20', '20180327235000000020', '销售', '1', '老张', '20170200000000000100', null, '生效', null, '2018-03-27 23:50:32', '10180.00', '10180.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '133.76');
INSERT INTO `yj_market_order` VALUES ('21', '20180327235400000021', '销售', '1', '老张', '20170200000000000100', null, '生效', '试试;;再试试', '2018-03-27 23:54:40', '1790.00', '1790.00', '90.00', '0.00', '200.00', '1500.00', '已付款', '无退货', '133.76');
INSERT INTO `yj_market_order` VALUES ('22', '20180328005800000022', '销售', '1', '老张', '20170200000000000100', null, '生效', ';;号了好了', '2018-03-28 00:58:28', '2246.00', '2246.00', '6.00', '0.00', '111.00', '2129.00', '已付款', '无退货', '179.43');
INSERT INTO `yj_market_order` VALUES ('23', '20180418212500000023', '销售', '4', '老王', '20170300000000000103', null, '生效', null, '2018-04-18 21:25:37', '2190.00', '2190.00', '0.00', '0.00', '0.00', '0.00', '未付款', '无退货', '200.64');
INSERT INTO `yj_market_order` VALUES ('25', '20180418214400000025', '销售', '4', '老王', '20170300000000000103', null, '生效', '试试代乳粉', '2018-04-18 21:44:16', '27330.00', '27330.00', '30.00', '22260.00', '0.00', '27300.00', '已付款', '无退货', '66.88');

-- ----------------------------
-- Table structure for yj_market_order_gift_line
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_order_gift_line`;
CREATE TABLE `yj_market_order_gift_line` (
  `giftLineId` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `orderLineId` int(11) NOT NULL,
  `goodsId` int(11) NOT NULL,
  `goodsName` varchar(255) NOT NULL,
  `goodsCount` int(11) NOT NULL,
  `goodsCountUnit` varchar(500) NOT NULL,
  `giftLogic` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`giftLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_order_gift_line
-- ----------------------------
INSERT INTO `yj_market_order_gift_line` VALUES ('2', '7', '5', '1', ' 消炎药-0001', '166', '包', '无');
INSERT INTO `yj_market_order_gift_line` VALUES ('3', '7', '6', '5', '热水壶', '252', '个', '无');
INSERT INTO `yj_market_order_gift_line` VALUES ('9', '10', '13', '1', ' 消炎药-0001', '332', '包', '满 200.50元 送  消炎药-0001 2包');
INSERT INTO `yj_market_order_gift_line` VALUES ('10', '10', '14', '5', '热水壶', '126', '个', '满 500.66元 送 热水壶 1个');
INSERT INTO `yj_market_order_gift_line` VALUES ('11', '10', '15', '2', '猪长的快', '16', '包', '累积[累积 5 箱 ，送猪长的快 2包]');
INSERT INTO `yj_market_order_gift_line` VALUES ('21', '9', '29', '1', ' 消炎药-0001', '332', '包', '满 200.50元 送  消炎药-0001 2包');
INSERT INTO `yj_market_order_gift_line` VALUES ('22', '9', '31', '2', '猪长的快', '16', '包', '累积[累积 5 箱 ，送猪长的快 2包]');
INSERT INTO `yj_market_order_gift_line` VALUES ('23', '8', '33', '1', ' 消炎药-0001', '166', '包', '满 200.50元 送  消炎药-0001 2包');
INSERT INTO `yj_market_order_gift_line` VALUES ('24', '6', '35', '1', ' 消炎药-0001', '332', '包', '满 200.50元 送  消炎药-0001 2包');
INSERT INTO `yj_market_order_gift_line` VALUES ('25', '6', '37', '2', '猪长的快', '150', '袋', '当时兑现[买 111 袋 ，送猪长的快 5袋]');

-- ----------------------------
-- Table structure for yj_market_order_line
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_order_line`;
CREATE TABLE `yj_market_order_line` (
  `orderLineId` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `goodsId` int(11) NOT NULL,
  `goodsName` varchar(200) NOT NULL,
  `goodsCount` int(11) NOT NULL,
  `goodsCountUnit` varchar(50) NOT NULL,
  `goodsPrice` varchar(200) NOT NULL,
  `goodsOrderPrice` decimal(9,2) NOT NULL,
  `goodsDrfDiffAmount` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '代乳粉特殊的，1吨*50包*20袋，0.55*0.70*0.85按这个比例计算活动金额',
  `goodsGiftConfigId` int(11) NOT NULL DEFAULT '0' COMMENT '是config line id',
  `goodsGift` varchar(20) NOT NULL COMMENT '无赠送/赠送',
  `goodsGiftCheck` varchar(20) NOT NULL COMMENT '当时兑现/累积/无赠送',
  `giftAmount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '等值产品金额',
  PRIMARY KEY (`orderLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='2018-03-24，调整，等值产品金额，记录到order和order line，付款的时候，显示，供参考';

-- ----------------------------
-- Records of yj_market_order_line
-- ----------------------------
INSERT INTO `yj_market_order_line` VALUES ('5', '7', '1', ' 消炎药-0001', '1111', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '16665.00', '0.00', '0', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('6', '7', '6', '消炎药-0002', '2222', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '126165.16', '0.00', '0', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('13', '10', '1', ' 消炎药-0001', '2222', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '33330.00', '0.00', '2', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('14', '10', '6', '消炎药-0002', '1111', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '63082.58', '0.00', '4', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('15', '10', '2', '猪长的快', '3333', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '49995.00', '0.00', '6', '赠送', '累积', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('16', '10', '7', '貂喜欢', '55', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '825.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('29', '9', '1', ' 消炎药-0001', '2222', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '33330.00', '0.00', '2', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('30', '9', '6', '消炎药-0002', '1111', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '63082.58', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('31', '9', '2', '猪长的快', '3333', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '49995.00', '0.00', '6', '赠送', '累积', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('32', '9', '7', '貂喜欢', '55', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '825.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('33', '8', '1', ' 消炎药-0001', '1111', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '16665.00', '0.00', '2', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('34', '8', '6', '消炎药-0002', '2222', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '126165.16', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('35', '6', '1', ' 消炎药-0001', '2222', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '33330.00', '0.00', '2', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('36', '6', '6', '消炎药-0002', '1111', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '63082.58', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('37', '6', '2', '猪长的快', '3333', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '49995.00', '0.00', '5', '赠送', '当时兑现', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('38', '6', '7', '貂喜欢', '55', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '825.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('39', '11', '1', ' 消炎药-0001', '5', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '75.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('40', '11', '2', '猪长的快', '88', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '1320.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('41', '12', '1', ' 消炎药-0001', '5', '袋', '1*4*20<br>箱*包*袋<br>245.66*70.88*15.00', '75.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('42', '13', '2', '猪长的快', '6', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '90.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('43', '14', '6', '消炎药-0002', '8', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '454.24', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('44', '15', '6', '消炎药-0002', '66', '袋', '1*4*20<br>箱*盒*袋<br>12.34*45.67*56.78', '3747.48', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('45', '16', '2', '猪长的快', '66', '袋', '1*4*20<br>箱*包*袋<br>245.00*70.00*15.00', '990.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('54', '17', '2', '猪长的快', '22', '桶', '1*50<br>桶*袋<br>730.00*15.00', '3960.00', '0.00', '0', '无赠送', '无赠送', '1471.36');
INSERT INTO `yj_market_order_line` VALUES ('55', '17', '2', '猪长的快', '12', '袋', '1*50<br>桶*袋<br>730.00*15.00', '2160.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('56', '18', '2', '猪长的快', '2', '桶', '1*50<br>桶*袋<br>730.00*15.00', '660.00', '0.00', '0', '无赠送', '无赠送', '133.76');
INSERT INTO `yj_market_order_line` VALUES ('57', '18', '2', '猪长的快', '22', '袋', '1*50<br>桶*袋<br>730.00*15.00', '7260.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('58', '19', '2', '猪长的快', '2', '桶', '1*50<br>桶*袋<br>730.00*15.00', '2920.00', '0.00', '0', '无赠送', '无赠送', '133.76');
INSERT INTO `yj_market_order_line` VALUES ('59', '19', '2', '猪长的快', '22', '袋', '1*50<br>桶*袋<br>730.00*15.00', '7260.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('60', '20', '2', '猪长的快', '2', '桶', '1*50<br>桶*袋<br>730.00*15.00', '2920.00', '0.00', '0', '无赠送', '无赠送', '133.76');
INSERT INTO `yj_market_order_line` VALUES ('61', '20', '2', '猪长的快', '22', '袋', '1*50<br>桶*袋<br>730.00*15.00', '7260.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('62', '21', '2', '猪长的快', '2', '桶', '1*50<br>桶*袋<br>730.00*15.00', '1460.00', '0.00', '0', '无赠送', '无赠送', '133.76');
INSERT INTO `yj_market_order_line` VALUES ('63', '21', '2', '猪长的快', '22', '袋', '1*50<br>桶*袋<br>730.00*15.00', '330.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('64', '22', '2', '猪长的快', '2', '桶', '1*50<br>桶*袋<br>730.00*15.00', '1460.00', '0.00', '0', '无赠送', '无赠送', '133.76');
INSERT INTO `yj_market_order_line` VALUES ('65', '22', '2', '猪长的快', '22', '袋', '1*50<br>桶*袋<br>730.00*15.00', '330.00', '0.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('66', '22', '7', '貂喜欢', '1', '桶', '1*30<br>桶*袋<br>456.00*15.00', '456.00', '0.00', '0', '无赠送', '无赠送', '45.67');
INSERT INTO `yj_market_order_line` VALUES ('67', '23', '2', '猪长的快', '3', '桶', '1*50<br>桶*袋<br>730.00*15.00', '2190.00', '0.00', '0', '无赠送', '无赠送', '200.64');
INSERT INTO `yj_market_order_line` VALUES ('77', '25', '8', '代乳粉', '1', '吨', '1*20*50<br>吨*袋*包<br>20000.00*20.00*400.00', '20000.00', '3000.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('78', '25', '8', '代乳粉', '15', '包', '1*20*50<br>吨*袋*包<br>20000.00*20.00*400.00', '6000.00', '1800.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('79', '25', '8', '代乳粉', '30', '袋', '1*20*50<br>吨*袋*包<br>20000.00*20.00*400.00', '600.00', '270.00', '0', '无赠送', '无赠送', '0.00');
INSERT INTO `yj_market_order_line` VALUES ('80', '25', '2', '猪长的快', '1', '桶', '1*50<br>桶*袋<br>730.00*15.00', '730.00', '0.00', '0', '无赠送', '无赠送', '66.88');

-- ----------------------------
-- Table structure for yj_market_payoff
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_payoff`;
CREATE TABLE `yj_market_payoff` (
  `payOffId` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `memberId` int(11) NOT NULL,
  `payOffWay` varchar(20) NOT NULL COMMENT '现金/微信支付/银行到款/代金券',
  `payOffTime` datetime NOT NULL,
  `payOffMoney` decimal(9,2) NOT NULL,
  `payOffType` varchar(255) NOT NULL COMMENT '销售付款/退货退款',
  PRIMARY KEY (`payOffId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yj_market_payoff
-- ----------------------------
INSERT INTO `yj_market_payoff` VALUES ('1', '10', '4', '现金', '2017-10-06 07:22:16', '1111.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('2', '10', '4', '现金', '2017-10-06 08:21:08', '3333.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('3', '10', '4', '现金', '2017-10-06 08:23:14', '66666.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('4', '10', '4', '现金', '2017-10-06 08:31:10', '111.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('5', '10', '4', '现金', '2017-10-06 08:37:43', '222.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('6', '10', '4', '现金', '2017-10-06 08:38:52', '333.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('7', '10', '4', '现金', '2017-10-06 08:40:13', '555.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('8', '10', '4', '现金', '2017-10-06 08:42:10', '666.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('9', '6', '4', '现金', '2017-10-06 08:51:02', '140000.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('10', '10', '4', '现金', '2017-10-06 08:52:21', '73204.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('11', '11', '4', '现金', '2018-03-23 15:20:50', '90.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('12', '13', '4', '现金', '2018-03-24 09:49:47', '90.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('13', '14', '4', '现金', '2018-03-24 09:53:59', '0.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('14', '15', '4', '现金', '2018-03-24 09:57:33', '3740.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('15', '16', '1', '现金', '2018-03-24 10:00:55', '0.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('16', '21', '1', '现金', '2018-03-28 00:39:06', '500.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('17', '21', '1', '现金', '2018-03-28 00:56:47', '1000.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('18', '22', '1', '现金', '2018-03-28 01:00:45', '840.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('19', '22', '1', '现金', '2018-03-28 01:01:51', '1289.00', '销售付款');
INSERT INTO `yj_market_payoff` VALUES ('20', '25', '4', '现金', '2018-04-18 22:52:32', '27300.00', '销售付款');

-- ----------------------------
-- Table structure for yj_market_user
-- ----------------------------
DROP TABLE IF EXISTS `yj_market_user`;
CREATE TABLE `yj_market_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(50) NOT NULL,
  `passwd` varchar(100) NOT NULL,
  `defaultpwd` varchar(100) DEFAULT NULL,
  `userName` varchar(100) NOT NULL,
  `sex` varchar(20) NOT NULL,
  `comm` varchar(1000) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `role` varchar(255) NOT NULL DEFAULT 'saler' COMMENT 'saler,admin,guest',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '用户创建时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of yj_market_user
-- ----------------------------
INSERT INTO `yj_market_user` VALUES ('1', 'admin', 'admin!@#', null, '管理员', '男', '系统管理员，超级用户', '15566951545', 'admin', '2017-01-18 22:35:44');
INSERT INTO `yj_market_user` VALUES ('2', 'lige', 'xxxxxxxxx', null, '李哥', '男', '业主', '13888888888', 'saler', '2017-01-18 22:33:03');
