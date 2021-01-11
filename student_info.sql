/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : studb

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-09 14:42:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info` (
  `stuid` int(32) NOT NULL AUTO_INCREMENT,
  `stu_name` varchar(255) DEFAULT NULL,
  `stu_image_url` varchar(255) DEFAULT NULL,
  `stu_sex` tinyint(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `stu_phone` varchar(255) DEFAULT NULL,
  `stu_address` varchar(255) DEFAULT NULL,
  `stu_birthday` date DEFAULT NULL,
  `stu_email` varchar(255) DEFAULT NULL,
  `stu_education` varchar(255) DEFAULT NULL,
  `stu_interest` varchar(255) DEFAULT NULL,
  `my_color` varchar(255) DEFAULT NULL,
  `lucky_number` int(11) DEFAULT NULL,
  `re_mark` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `clsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`stuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1045 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_info
-- ----------------------------
INSERT INTO `student_info` VALUES ('1001', 'Donald J. Trump', '16093259690212.jpg', '1', 'trump', '001548946516', 'US White House', '1946-06-14', 'trump@us.gov', '本科', '电影,篮球,看书', '#1894c9', '6', 'I am Donald', null, '2002');
INSERT INTO `student_info` VALUES ('1003', 'Hilary Clinton', '16093259820923.jpg', '0', 'xilali', '00145487874', 'Washington D.C.', '1947-10-26', 'hilary@us.gov', '博士生', '看书,游戏,做饭', '#5b2281', '8', 'I am Hilary Clinton', null, '2003');
INSERT INTO `student_info` VALUES ('1004', 'Barack Obama', '1609326010809obama.jpg', '1', 'obama', '00156878935', 'Chicago', '1961-08-04', 'obama@protonmail.com', '本科', '电影,篮球,看书', '#39677f', '7', 'I am Obama', null, '2003');
INSERT INTO `student_info` VALUES ('1006', '郭竞兴', '1609316852913MTV-蓝底白字.png', '1', '19990905', '15874755101', '湖南省衡阳市祁东县', '1999-09-05', '784830122@qq.com', '研究生', '电影,看书', '#0aadff', '6', 'I am guojingxing', null, '2001');
INSERT INTO `student_info` VALUES ('1022', 'Joe Biden', '16093259997781.jpg', '1', 'biden', '001545673895', 'Commonwealth of Pennsylvania', '1942-11-20', 'biden@us.gov', '本科', '电影,看书', '#049a3e', '6', 'I am Joe Biden, keep the faith', null, '2003');
INSERT INTO `student_info` VALUES ('1024', '张振东', null, '1', 'zzd', '18112918803', '山西省忻州市', '1995-07-28', '616136518@qq.com', '研究生', '电影,游戏', '#d11f1f', '1', '腹有诗书气自华', null, '2004');
INSERT INTO `student_info` VALUES ('1028', '马化腾', '160931688001529492.jpg', '1', 'mahuateng', '1343586757345', '深圳市', '1971-10-29', 'mahuateng@tencent.com', '本科', '篮球,看书,游戏', '#ff3333', '4', '我是马化腾', null, '2001');
INSERT INTO `student_info` VALUES ('1030', 'Taylor Swift', '16093259899084.jpg', '0', 'taylor', '00124345543', 'New York', '1989-12-13', 'taylorswift@gmail.com', '研究生', '电影', '#ff3232', '6', 'I am Taylor Alison Swift', null, '2003');
INSERT INTO `student_info` VALUES ('1031', '袁哲', '1609325259989', '1', 'yuanzhe', '123', '湖北省仙桃市', '1999-08-26', '2607912272@qq.com', '研究生', '电影,看书,游戏', '#296489', '2', '4', null, '2001');
INSERT INTO `student_info` VALUES ('1032', '汤慰慈', '1609319457685', '1', '1234', '18154413421', '5235325252', '1998-01-05', 'hilary@us.gov', '研究生', '电影,篮球', '#c00c0c', '6', '3523525', null, '2001');
INSERT INTO `student_info` VALUES ('1033', '邹俊杰', null, '1', '234345235', '15927052605', 'Chicago', '1998-10-04', 'trump@us.gov', '研究生', '篮球,看书,游戏', '#000000', '6', '23526526', null, '2001');
INSERT INTO `student_info` VALUES ('1034', '彭玉林', '1609325764945ff25-ietnfsp9435719.jpg', '1', '3532525312', '18390826833', 'US White House', '1998-10-21', 'hilary@us.gov', '研究生', '篮球,游戏', '#da0b0b', '3', 'fwewgwwe', null, '2001');
INSERT INTO `student_info` VALUES ('1035', 'wangximing', '16093167189891.png', '1', 'wangximing', '15608655750', '湖南省衡阳市祁东县', '1985-12-01', '784830122@qq.com', '本科', '篮球,游戏', '#eb6060', '3', '3', null, '2001');
INSERT INTO `student_info` VALUES ('1043', '周杰伦', null, '1', 'jaychou', '886-2-2547-5588', '台北市松山區長春路451號11樓', '1979-01-18', 'jaychou@jvrmusic.com.tw', '本科', '电影,篮球', '#000000', '1', '周杰倫，臺灣華語流行歌曲男歌手、音樂家、編曲家、唱片製片人、魔術師。同時是演員、導演、電子競技職業戰隊J Team的老闆。', null, '2001');
INSERT INTO `student_info` VALUES ('1044', '郭竞兴', '1610171913043', '1', '123456', '15608655750', '湖南省衡阳市祁东县', '2021-01-09', '784830122@qq.com', '研究生', '篮球,游戏', '#c02a2a', '3', 'abcdefg', '2021-01-09', '2006');
