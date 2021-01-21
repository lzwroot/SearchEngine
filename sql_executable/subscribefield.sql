/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:38:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for subscribefield
-- ----------------------------
DROP TABLE IF EXISTS `subscribefield`;
CREATE TABLE `subscribefield` (
  `username` varchar(255) NOT NULL,
  `field` varchar(255) NOT NULL,
  KEY `uname_bid_field` (`username`),
  CONSTRAINT `uname_bid_field` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscribefield
-- ----------------------------
INSERT INTO `subscribefield` VALUES ('123', '高分子材料');
INSERT INTO `subscribefield` VALUES ('123', '计算机技术');
INSERT INTO `subscribefield` VALUES ('123', '机械');
INSERT INTO `subscribefield` VALUES ('123', '地球物理');
INSERT INTO `subscribefield` VALUES ('123', '工商管理');
