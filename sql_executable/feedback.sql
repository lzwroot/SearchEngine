/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-03-12 17:25:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `feedback_id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `feedback` text,
  `feedback_time` datetime DEFAULT NULL,
  `managerid` int(11) DEFAULT NULL,
  `result` text,
  `result_time` datetime DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `uname_bind_fb` (`username`),
  KEY `mgr_bind` (`managerid`),
  CONSTRAINT `mgr_bind` FOREIGN KEY (`managerid`) REFERENCES `manager` (`managerid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uname_bind_fb` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('00000000003', '123', '12345', '2021-03-12 13:59:19', null, null, '2021-03-02 16:06:07');
INSERT INTO `feedback` VALUES ('00000000004', '123', '1234', '2021-03-12 14:23:59', null, null, '2021-03-02 16:06:07');
INSERT INTO `feedback` VALUES ('00000000005', '123', '123412345125', '2021-03-12 14:24:18', '1234', '你好啊', '2021-03-12 17:00:13');
INSERT INTO `feedback` VALUES ('00000000007', '123', '125353465', '2021-03-12 14:24:23', '1234', '好的', '2021-03-12 17:00:09');
INSERT INTO `feedback` VALUES ('00000000030', '1234', '你好啊\r\n', '2021-03-12 17:02:21', '1234', '好的', '2021-03-12 17:06:37');
INSERT INTO `feedback` VALUES ('00000000031', '1234', '在不', '2021-03-12 17:02:48', '1234', '在', '2021-03-12 17:06:35');
INSERT INTO `feedback` VALUES ('00000000032', '1234', '在不', '2021-03-12 17:11:22', null, null, null);
INSERT INTO `feedback` VALUES ('00000000033', '12345', 'hello', '2021-03-12 17:15:47', '12345', 'how are you', '2021-03-12 17:16:37');
