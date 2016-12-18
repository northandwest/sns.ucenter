-- --------------------------------------------------------
-- 主机:                           192.168.1.110
-- 服务器版本:                        5.1.73-log - Source distribution
-- 服务器操作系统:                      redhat-linux-gnu
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 bucuoa_ucenter 的数据库结构
CREATE DATABASE IF NOT EXISTS `bucuoa_ucenter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bucuoa_ucenter`;


-- 导出  表 bucuoa_ucenter.ulewo_user 结构
CREATE TABLE IF NOT EXISTS `ulewo_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `nick_name` varchar(50) DEFAULT '' COMMENT 'nickname',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `age` varchar(5) DEFAULT NULL COMMENT '年龄',
  `user_bg` varchar(200) DEFAULT NULL COMMENT '背景',
  `sex` varchar(1) DEFAULT 'M' COMMENT '性别 M男 F女',
  `characters` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `mark` int(11) DEFAULT '0' COMMENT '积分',
  `address` varchar(50) DEFAULT NULL COMMENT '籍贯',
  `work` varchar(50) DEFAULT NULL COMMENT '职业',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `pre_visit_time` datetime DEFAULT NULL COMMENT '访问时间',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `isvalid` varchar(1) DEFAULT 'Y' COMMENT '是否有效 Y有效 N无效',
  `center_theme` varchar(1) DEFAULT '0' COMMENT '主题',
  `activation_code` varchar(25) DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`user_id`,`email`(1),`password`),
  KEY `user_index_userid` (`user_id`),
  KEY `user_index_username` (`user_name`),
  KEY `user_index_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 bucuoa_ucenter.ulewo_user_password 结构
CREATE TABLE IF NOT EXISTS `ulewo_user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `isvalid` varchar(1) DEFAULT 'Y' COMMENT '是否有效 Y有效 N无效',
  `activation_code` varchar(25) DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`userid`,`email`(1),`password`),
  KEY `user_index_userid` (`userid`),
  KEY `user_index_username` (`username`),
  KEY `user_index_email` (`email`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
