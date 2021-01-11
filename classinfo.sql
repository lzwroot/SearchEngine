<<<<<<< HEAD
/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : studb

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-09 14:42:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classinfo
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `clsid` int(11) NOT NULL AUTO_INCREMENT,
  `clsName` varchar(255) DEFAULT NULL,
  `clsStuNum` int(11) DEFAULT NULL,
  `clsTeacher` varchar(255) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  PRIMARY KEY (`clsid`)
) ENGINE=InnoDB AUTO_INCREMENT=2008 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('2001', '软硕2001班', '30', '万琳', '2021-01-09');
INSERT INTO `classinfo` VALUES ('2002', '软硕2002班', '30', '区士颀', '2020-09-01');
INSERT INTO `classinfo` VALUES ('2003', '软硕2003班', '30', '胡雯蔷', '2021-01-09');
INSERT INTO `classinfo` VALUES ('2004', '软硕2004班', '30', '区士颀', '2020-09-01');
INSERT INTO `classinfo` VALUES ('2006', '软硕2005班', '40', '沈刚', '2021-01-09');
INSERT INTO `classinfo` VALUES ('2007', '软硕2006班', '30', '王运萍', '2021-01-09');
