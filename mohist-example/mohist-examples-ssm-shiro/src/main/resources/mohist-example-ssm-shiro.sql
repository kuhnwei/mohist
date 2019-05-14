/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : mohist_examples

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-03-14 18:28:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_mohist_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_mohist_permission`;
CREATE TABLE `t_mohist_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `flag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mohist_permission
-- ----------------------------
INSERT INTO `t_mohist_permission` VALUES ('1', '超管执行添加操作', 'admin:add');
INSERT INTO `t_mohist_permission` VALUES ('2', '超管执行编辑操作', 'admin:edit');
INSERT INTO `t_mohist_permission` VALUES ('3', '超管执行删除操作', 'admin:remove');
INSERT INTO `t_mohist_permission` VALUES ('4', '部门增加', 'dept:add');
INSERT INTO `t_mohist_permission` VALUES ('5', '部门编辑', 'dept:edit');
INSERT INTO `t_mohist_permission` VALUES ('6', '部门列表', 'dept:list');
INSERT INTO `t_mohist_permission` VALUES ('7', '用户列表', 'user:list');

-- ----------------------------
-- Table structure for `t_mohist_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_mohist_role`;
CREATE TABLE `t_mohist_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `flag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mohist_role
-- ----------------------------
INSERT INTO `t_mohist_role` VALUES ('1', '超管', 'administrator');
INSERT INTO `t_mohist_role` VALUES ('2', '部门管理', 'dept');
INSERT INTO `t_mohist_role` VALUES ('3', '用户', 'user');

-- ----------------------------
-- Table structure for `t_mohist_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_mohist_role_permission`;
CREATE TABLE `t_mohist_role_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL,
  `permission_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mohist_role_permission
-- ----------------------------
INSERT INTO `t_mohist_role_permission` VALUES ('1', '1', '1');
INSERT INTO `t_mohist_role_permission` VALUES ('2', '1', '2');
INSERT INTO `t_mohist_role_permission` VALUES ('3', '1', '3');
INSERT INTO `t_mohist_role_permission` VALUES ('4', '2', '4');
INSERT INTO `t_mohist_role_permission` VALUES ('5', '2', '5');
INSERT INTO `t_mohist_role_permission` VALUES ('6', '2', '6');
INSERT INTO `t_mohist_role_permission` VALUES ('7', '3', '7');

-- ----------------------------
-- Table structure for `t_mohist_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_mohist_user`;
CREATE TABLE `t_mohist_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `locked` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mohist_user
-- ----------------------------
INSERT INTO `t_mohist_user` VALUES ('1', 'kuhnwei', '650901', '0');
INSERT INTO `t_mohist_user` VALUES ('2', 'admin', '123', '0');
INSERT INTO `t_mohist_user` VALUES ('3', 'emp', '123', '0');
INSERT INTO `t_mohist_user` VALUES ('4', 'test', '123', '0');

-- ----------------------------
-- Table structure for `t_mohist_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_mohist_user_role`;
CREATE TABLE `t_mohist_user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mohist_user_role
-- ----------------------------
INSERT INTO `t_mohist_user_role` VALUES ('1', '1', '2');
INSERT INTO `t_mohist_user_role` VALUES ('2', '1', '3');
INSERT INTO `t_mohist_user_role` VALUES ('3', '2', '1');
INSERT INTO `t_mohist_user_role` VALUES ('4', '2', '2');
INSERT INTO `t_mohist_user_role` VALUES ('5', '4', '3');
