/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:38:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for trends
-- ----------------------------
DROP TABLE IF EXISTS `trends`;
CREATE TABLE `trends` (
  `rank` int(3) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  `volume` bigint(20) DEFAULT NULL,
  `field` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trends
-- ----------------------------
INSERT INTO `trends` VALUES ('1', 'Election results', '100', 'Politics');
INSERT INTO `trends` VALUES ('2', 'Coronavirus', '98', 'Medical');
INSERT INTO `trends` VALUES ('3', 'Kobe Bryant', '96', 'Sports');
INSERT INTO `trends` VALUES ('4', 'Coronavirus update', '95', 'Medical');
INSERT INTO `trends` VALUES ('5', 'Coronavirus symptoms', '93', 'Medical');
INSERT INTO `trends` VALUES ('6', 'Zoom', '90', 'Social Online');
INSERT INTO `trends` VALUES ('7', 'Who is winning the election', '88', 'Politics');
INSERT INTO `trends` VALUES ('8', 'Naya Rivera', '85', 'Entertainment');
INSERT INTO `trends` VALUES ('9', 'Chadwick Boseman', '83', 'Entertainment');
INSERT INTO `trends` VALUES ('10', 'PlayStation 5', '82', 'Game');
