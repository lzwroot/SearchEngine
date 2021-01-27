/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:38:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bookmark
-- ----------------------------
DROP TABLE IF EXISTS `bookmark`;
CREATE TABLE `bookmark` (
  `username` varchar(255) NOT NULL,
  `doi` varchar(255) NOT NULL,
  `insert_time` datetime NOT NULL,
  KEY `uname_bid_bmk` (`username`),
  KEY `doi_bid_article` (`doi`),
  CONSTRAINT `doi_bid_article` FOREIGN KEY (`doi`) REFERENCES `article` (`doi`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uname_bid_bmk` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bookmark
-- ----------------------------
INSERT INTO `bookmark` VALUES ('123', '1', '2021-01-20 11:15:01');
INSERT INTO `bookmark` VALUES ('123', '2', '2021-01-20 11:16:02');
INSERT INTO `bookmark` VALUES ('guojingxing123', '1', '2021-01-20 11:17:03');
INSERT INTO `bookmark` VALUES ('guojingxing123', '2', '2021-01-20 11:18:00');
INSERT INTO `bookmark` VALUES ('123', '3', '2021-01-21 16:54:17');
