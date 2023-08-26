/*
 Navicat Premium Data Transfer

 Source Server         : 轻量
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 101.35.50.204:3306
 Source Schema         : oauth

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 25/08/2023 21:42:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (733);

-- ----------------------------
-- Table structure for tb_client
-- ----------------------------
DROP TABLE IF EXISTS `tb_client`;
CREATE TABLE `tb_client`  (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `client_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `enable` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 728 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_client
-- ----------------------------
INSERT INTO `tb_client` VALUES ('gfU4-fXC-RNp6BiU', 'xTFANdTeVwNvhnjB', '[715,716,718,720,722,724,726,717,719,721,723,725]', '用于测试的客户端', 727, '测试客户端', 'http://localhost:8081', '2023-08-24 13:16:51', '2023-08-24 13:16:51', 1);

-- ----------------------------
-- Table structure for tb_consent
-- ----------------------------
DROP TABLE IF EXISTS `tb_consent`;
CREATE TABLE `tb_consent`  (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(0) NOT NULL,
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_consent
-- ----------------------------
INSERT INTO `tb_consent` VALUES ('gfU4-fXC-RNp6BiU', 666, '[721,720,726,717,716,719,718,715,725,724,723,722]');

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` int(0) NOT NULL COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单路径',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单icon',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `order_num` tinyint(1) NOT NULL COMMENT '排序',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父id',
  `is_hidden` tinyint(1) NOT NULL COMMENT '是否隐藏 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tb_property
-- ----------------------------
DROP TABLE IF EXISTS `tb_property`;
CREATE TABLE `tb_property`  (
  `id` int(0) NOT NULL COMMENT '字段id',
  `property` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `class_id` int(0) NULL DEFAULT NULL COMMENT '对应的class_id',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段描述',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `behavior` tinyint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_property
-- ----------------------------
INSERT INTO `tb_property` VALUES (715, 'username', 714, '用户名', '2023-08-24 13:10:14', '2023-08-24 13:10:14', 0);
INSERT INTO `tb_property` VALUES (716, 'nickName', 714, '昵称', '2023-08-24 13:10:14', '2023-08-24 13:10:14', 0);
INSERT INTO `tb_property` VALUES (717, 'nickName', 714, '昵称', '2023-08-24 13:10:15', '2023-08-24 13:10:15', 1);
INSERT INTO `tb_property` VALUES (718, 'birthday', 714, '生日', '2023-08-24 13:10:15', '2023-08-24 13:10:15', 0);
INSERT INTO `tb_property` VALUES (719, 'birthday', 714, '生日', '2023-08-24 13:10:15', '2023-08-24 13:10:15', 1);
INSERT INTO `tb_property` VALUES (720, 'userImage', 714, '头像', '2023-08-24 13:10:16', '2023-08-24 13:10:16', 0);
INSERT INTO `tb_property` VALUES (721, 'userImage', 714, '头像', '2023-08-24 13:10:16', '2023-08-24 13:10:16', 1);
INSERT INTO `tb_property` VALUES (722, 'sex', 714, '性别', '2023-08-24 13:10:16', '2023-08-24 13:10:16', 0);
INSERT INTO `tb_property` VALUES (723, 'sex', 714, '性别', '2023-08-24 13:10:17', '2023-08-24 13:10:17', 1);
INSERT INTO `tb_property` VALUES (724, 'description', 714, '描述', '2023-08-24 13:10:17', '2023-08-24 13:10:17', 0);
INSERT INTO `tb_property` VALUES (725, 'description', 714, '描述', '2023-08-24 13:10:17', '2023-08-24 13:10:17', 1);
INSERT INTO `tb_property` VALUES (726, 'role', 714, '角色', '2023-08-24 13:10:18', '2023-08-24 13:10:18', 0);

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` int(0) NOT NULL COMMENT '主键',
  `resource_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父权限id',
  `is_anonymous` tinyint(1) NOT NULL COMMENT '是否匿名访问 0否 1是',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT,
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '普通用户', 'USER', NULL, NULL);
INSERT INTO `tb_role` VALUES (2, '管理员', 'ADMIN', NULL, NULL);

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` int(0) NOT NULL COMMENT '主键',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int(0) NULL DEFAULT NULL COMMENT '目录id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resource`;
CREATE TABLE `tb_role_resource`  (
  `id` int(0) NOT NULL COMMENT '主键',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int(0) NULL DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_resource
-- ----------------------------

-- ----------------------------
-- Table structure for tb_scope
-- ----------------------------
DROP TABLE IF EXISTS `tb_scope`;
CREATE TABLE `tb_scope`  (
  `id` int(0) NOT NULL COMMENT '类id',
  `scope_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应的类名',
  `scope_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类的描述',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_scope
-- ----------------------------
INSERT INTO `tb_scope` VALUES (714, 'User', '个人信息', '2023-08-24 13:10:13', '2023-08-24 13:10:13');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '0 男 1 女',
  `age` int(0) NULL DEFAULT NULL,
  `description` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 733 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', 'g��_Y��vL���E', 'http://files.xyjxkl.top/xiexiaojia/config/f491854e80f727e49961b55f49e1c323.jpg', 0, 18, '有点傻', 2, '2023-08-04 22:09:08', '2023-08-18 22:09:12', '莴苣菜3号', '2023-08-08 11:21:00');
INSERT INTO `tb_user` VALUES (2, 'userdemo', 'O��cym���ĸW�', 'http://files.xyjxkl.top/xiexiaojia/config/f491854e80f727e49961b55f49e1c323.jpg', 0, 18, '有点傻', 1, NULL, '2023-08-23 07:58:44', '莴苣菜2号', '2023-08-01 00:00:00');
INSERT INTO `tb_user` VALUES (665, '111111', '&���K�����(ъ�6�', NULL, 0, NULL, '111111', 1, '2023-08-23 07:03:11', '2023-08-23 07:03:11', '111111', '2023-08-23 00:00:00');
INSERT INTO `tb_user` VALUES (666, 'testuser', 'K�Qq��\Z����V�', NULL, 1, NULL, '我是测试用户', 1, '2023-08-23 07:08:40', '2023-08-24 14:22:49', '我是测试的', '2011-08-01 00:00:00');
INSERT INTO `tb_user` VALUES (667, 'adminuser', '�d���S�8�����t�', NULL, 0, NULL, 'adminuser', 2, '2023-08-23 07:25:48', '2023-08-23 07:25:48', 'adminuser', '2021-06-01 00:00:00');
INSERT INTO `tb_user` VALUES (669, 'userdemo1', 'g��_Y��vL���E', 'http://files.xyjxkl.top/xiexiaojia/config/f491854e80f727e49961b55f49e1c323.jpg', 0, NULL, 'userdemo1', 1, NULL, '2023-08-23 10:27:07', 'userdemo1', '2023-08-07 00:00:00');
INSERT INTO `tb_user` VALUES (670, 'userdemo2', 'i&�$���E4s6��Z', NULL, 0, NULL, 'userdemo2', 1, '2023-08-23 08:45:49', '2023-08-23 08:45:49', 'userdemo2', '2023-08-16 22:08:49');
INSERT INTO `tb_user` VALUES (671, 'userdemo3', 'g��_Y��vL���E', NULL, 1, 11, '我是测试用户', 1, '2023-08-31 22:11:50', '2023-08-24 14:10:53', '我是测试的', '2023-08-24 22:11:57');
INSERT INTO `tb_user` VALUES (732, 'wojucai1', '�t���cj�D��9Pa�', NULL, 0, NULL, 'wojucai1', 1, '2023-08-24 15:51:49', '2023-08-24 15:51:49', 'wojucai1', '2023-08-29 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
