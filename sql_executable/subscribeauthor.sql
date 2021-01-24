/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:38:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for subscribeauthor
-- ----------------------------
DROP TABLE IF EXISTS `subscribeauthor`;
CREATE TABLE `subscribeauthor` (
  `username` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  KEY `uname_bid_author` (`username`),
  CONSTRAINT `uname_bid_author` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscribeauthor
-- ----------------------------
INSERT INTO `subscribeauthor` VALUES ('123', '王文静');
INSERT INTO `subscribeauthor` VALUES ('123', '何泰屹');
INSERT INTO `subscribeauthor` VALUES ('123', '武慧敏');
INSERT INTO `subscribeauthor` VALUES ('123', '郑强');
INSERT INTO `subscribeauthor` VALUES ('123', '黄庆学');
