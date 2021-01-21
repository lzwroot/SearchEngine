/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:38:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123', '123', '1', '华中科技大学', '1234@qq.com');
INSERT INTO `user` VALUES ('1234', '123', null, null, '123456@qq.com');
INSERT INTO `user` VALUES ('12345', '123', '0', '', '123@qq.com');
INSERT INTO `user` VALUES ('123456', '123', null, null, '123@qq.com');
INSERT INTO `user` VALUES ('234', '234', null, null, 'guojingxing0905@gmail.com');
INSERT INTO `user` VALUES ('guojingxing123', '123', '1', '华中科技大学', '784830122@qq.com');
