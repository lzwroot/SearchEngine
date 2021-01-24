/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:38:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for subscribejournal
-- ----------------------------
DROP TABLE IF EXISTS `subscribejournal`;
CREATE TABLE `subscribejournal` (
  `username` varchar(255) NOT NULL,
  `journal` varchar(255) NOT NULL,
  KEY `uname_bid_journal` (`username`),
  CONSTRAINT `uname_bid_journal` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscribejournal
-- ----------------------------
INSERT INTO `subscribejournal` VALUES ('123', 'Nature');
INSERT INTO `subscribejournal` VALUES ('123', 'Science');
INSERT INTO `subscribejournal` VALUES ('123', 'The New England Journal of Medicine');
INSERT INTO `subscribejournal` VALUES ('123', 'Advanced Materials');
INSERT INTO `subscribejournal` VALUES ('123', 'Chemical Reviews');
