/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-03-15 09:12:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `username` varchar(255) NOT NULL,
  `his_doi` varchar(255) NOT NULL,
  `access_time` datetime DEFAULT NULL,
  KEY `doi_bind` (`his_doi`),
  KEY `uname_bind` (`username`),
  CONSTRAINT `doi_bind` FOREIGN KEY (`his_doi`) REFERENCES `article` (`doi`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uname_bind` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------
INSERT INTO `history` VALUES ('123', 'arXiv:2001.00951', '2021-03-12 09:24:31');
INSERT INTO `history` VALUES ('123', '3', '2021-03-12 12:46:17');
INSERT INTO `history` VALUES ('123', 'arXiv:2001.11511', '2021-03-12 10:40:08');
INSERT INTO `history` VALUES ('123', 'arXiv:2001.11975', '2021-03-12 16:30:12');
INSERT INTO `history` VALUES ('123', '2', '2021-03-12 20:10:34');
INSERT INTO `history` VALUES ('123', 'arXiv:2001.00261', '2021-03-12 20:11:36');
