/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : mohist_examples

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-03-14 11:22:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_mohist_example`
-- ----------------------------
DROP TABLE IF EXISTS `t_mohist_example`;
CREATE TABLE `t_mohist_example` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `example_str` varchar(255) NOT NULL,
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(3) unsigned NOT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

