/*
 Navicat Premium Data Transfer
 
 Source Server : root_connect
 Source Server Type : MySQL
 Source Server Version : 80022
 Source Host : localhost:3306
 Source Schema : nebulahomedb
 
 Target Server Type : MySQL
 Target Server Version : 80022
 File Encoding : 65001
 
 Date: 12/01/2021 19:03:07
 */
DROP DATABASE IF EXISTS nebulahomedb;
CREATE DATABASE IF NOT EXISTS nebulahomedb CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE nebulahomedb;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
 `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一确定一个用户',
 `personalid` char(20) CHARACTER SET ascii COLLATE ascii_general_ci NOT NULL COMMENT '个性账号',
 `password` char(20) CHARACTER SET utf8 NOT NULL COMMENT '密码',
 `phone` char(15) CHARACTER SET ascii COLLATE ascii_general_ci NOT NULL COMMENT '用户绑定手机，最长11位手机号+4位区号',
 PRIMARY KEY (`id`) USING BTREE,
 UNIQUE KEY userunique(`personalid`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of user
-- ----------------------------
-- ----------------------------
-- Table structure for userstatus
-- ----------------------------
DROP TABLE IF EXISTS `userstatus`;
CREATE TABLE `userstatus` (
 `id` bigint UNSIGNED NOT NULL,
 `lock` bit(1) NOT NULL DEFAULT 0 COMMENT '挂失标志',
 `ban` bit(1) NOT NULL DEFAULT 0 COMMENT '冻结标志',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of userstatus
-- ----------------------------
-- ----------------------------
-- Table structure for userdetails
-- ----------------------------
DROP TABLE IF EXISTS `userdetails`;
CREATE TABLE `userdetails` (
 `id` bigint UNSIGNED NOT NULL,
 `sex` bit(1) NULL DEFAULT NULL COMMENT '性别',
 `com` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在公司',
 `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
 `college` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在学校',
 `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
 `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of userdetails
-- ----------------------------
-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
 `id` bigint UNSIGNED NOT NULL,
 `name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
 `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户创建时间',
 `point` int NOT NULL DEFAULT 0 COMMENT '积分',
 `signature` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户签名',
 `icon` char(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of userinfo
-- ----------------------------
-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
 `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '流水号',
 `masterid` bigint UNSIGNED NOT NULL COMMENT '话题发起人',
 `name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '话题名称',
 `maxsize` tinyint UNSIGNED NOT NULL DEFAULT 99 COMMENT '用户容量',
 `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发起时间',
 `ban` bit(1) NOT NULL DEFAULT b'0' COMMENT '冻结标志',
 `del` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of topic
-- ----------------------------
-- ----------------------------
-- Table structure for topicinf
-- ----------------------------
DROP TABLE IF EXISTS `topicinf`;
CREATE TABLE `topicinf` (
 `id` bigint UNSIGNED NOT NULL,
 `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '话题描述',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of topicinf
-- ----------------------------
-- ----------------------------
-- Table structure for topicuser
-- ----------------------------
DROP TABLE IF EXISTS `topicuser`;
CREATE TABLE `topicuser` (
 `topicid` bigint UNSIGNED NOT NULL,
 `userid` bigint UNSIGNED NOT NULL,
 `jointime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `del` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户进入话题后退出仍有记录',
 PRIMARY KEY (`topicid`, `userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of topicuser
-- ----------------------------
-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
 `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
 `authorid` bigint UNSIGNED NULL DEFAULT NULL,
 `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `title` char(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
 `del` bit(1) NOT NULL DEFAULT b'0' COMMENT '文章是否已经删除',
 `draft` bit(1) NOT NULL DEFAULT b'0' COMMENT '文章是否为草稿',
 `type` int NULL COMMENT '人工智能为文章贴的类别标签',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of article
-- ----------------------------
-- ----------------------------
-- Table structure for articleinf
-- ----------------------------
DROP TABLE IF EXISTS `articleinf`;
CREATE TABLE `articleinf` (
 `id` bigint UNSIGNED NOT NULL,
 `modifytime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `text` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
 `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原帖链接，空值表示原创',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of articleinf
-- ----------------------------
-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
 `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
 `articleid` bigint UNSIGNED NOT NULL,
 `userid` bigint UNSIGNED NOT NULL,
 `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of comment
-- ----------------------------
-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
 `userid` bigint UNSIGNED NOT NULL,
 `articleid` bigint UNSIGNED NOT NULL,
 `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 PRIMARY KEY (`userid`, `articleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of favorite
-- ----------------------------
-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like` (
 `userid` bigint UNSIGNED NOT NULL,
 `articleid` bigint UNSIGNED NOT NULL,
 `date` date NOT NULL,
 PRIMARY KEY (`userid`, `articleid`, `date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of like
-- ----------------------------
-- ----------------------------
-- Table structure for visit
-- ----------------------------
DROP TABLE IF EXISTS `visit`;
CREATE TABLE `visit` (
 `articleid` bigint UNSIGNED NOT NULL,
 `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `userid` bigint UNSIGNED NOT NULL,
 PRIMARY KEY (`articleid`, `time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of visit
-- ----------------------------
-- ----------------------------
-- Table structure for visitinfo
-- ----------------------------
DROP TABLE IF EXISTS `visitinfo`;
CREATE TABLE `visitinfo` (
 `articleid` bigint UNSIGNED NOT NULL,
 `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `ipv4` int UNSIGNED NULL DEFAULT NULL,
 `mac` binary(6) NULL DEFAULT NULL,
 `ipv6` binary(16) NULL DEFAULT NULL,
 PRIMARY KEY (`articleid`, `time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of visitinfo
-- ----------------------------
-- ----------------------------
-- Table structure for focus
-- ----------------------------
DROP TABLE IF EXISTS `focus`;
CREATE TABLE `focus` (
 `userid` bigint UNSIGNED NOT NULL,
 `fanid` bigint UNSIGNED NOT NULL,
 `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 PRIMARY KEY (`userid`, `fanid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of focus
-- ----------------------------
-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
 `userid` bigint UNSIGNED NOT NULL,
 `senderid` bigint UNSIGNED NOT NULL,
 `datetime` datetime NOT NULL,
 `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
 `del` bit(1) NOT NULL DEFAULT b'0',
 PRIMARY KEY (`userid`, `senderid`, `datetime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of msg
-- ----------------------------
-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
 `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
 `userid` bigint UNSIGNED NOT NULL,
 `senderid` bigint UNSIGNED NOT NULL,
 `datetime` datetime NOT NULL,
 `cause` tinyint NOT NULL,
 `del` bit(1) NOT NULL DEFAULT b'0',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of report
-- ----------------------------
-- ----------------------------
-- Table structure for blacklist
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist` (
 `userid` bigint UNSIGNED NOT NULL,
 `blackid` bigint UNSIGNED NOT NULL,
 `firstblack` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `lastblack` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `del` bit(1) NOT NULL DEFAULT 0,
 PRIMARY KEY (`userid`, `blackid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for signindata
-- ----------------------------
DROP TABLE IF EXISTS `signindata`;
CREATE TABLE `signindata` (
 `userid` bigint UNSIGNED NOT NULL,
 `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 `method` tinyint NOT NULL COMMENT '登陆方式',
 `ipv4` int UNSIGNED NULL DEFAULT NULL,
 `mac` binary(6) NULL DEFAULT NULL,
 `ipv6` binary(16) NULL DEFAULT NULL,
 `location` varchar(63) NULL DEFAULT NULL,
 PRIMARY KEY (`userid`, `time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of signindata
-- ----------------------------

-- ALTER TABLE `userinfo` ADD FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `postbar` ADD FOREIGN KEY (`masterid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
-- ALTER TABLE `barinf` ADD FOREIGN KEY (`id`) REFERENCES `postbar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `baruser` ADD FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `baruser` ADD FOREIGN KEY (`barid`) REFERENCES `postbar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `article` ADD FOREIGN KEY (`authorid`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
-- ALTER TABLE `articleinf` ADD FOREIGN KEY (`id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `post` ADD FOREIGN KEY (`barid`,`userid`) REFERENCES `baruser` (`barid`,`userid`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `comment` ADD FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `comment` ADD FOREIGN KEY (`articleid`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `favorite` ADD FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `favorite` ADD FOREIGN KEY (`articleid`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `focus` ADD FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `focus` ADD FOREIGN KEY (`fanid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `msg` ADD FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE `msg` ADD FOREIGN KEY (`senderid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE OR REPLACE ALGORITHM = MERGE DEFINER = `root`@`localhost` SQL SECURITY INVOKER VIEW `nebulahomedb`.`fulluserdata` AS SELECT
	`user`.`id` AS `id`,
	`user`.`personalid` AS `personalid`,
	`user`.`password` AS `password`,
	`user`.`phone` AS `phone`,
	`userstatus`.`lock` AS `lock`,
	`userstatus`.`ban` AS `ban`,
	`userdetails`.`sex` AS `sex`,
	`userdetails`.`com` AS `com`,
	`userdetails`.`address` AS `address`,
	`userdetails`.`college` AS `college`,
	`userdetails`.`birthday` AS `birthday`,
	`userdetails`.`intro` AS `intro`,
	`userinfo`.`name` AS `name`,
	`userinfo`.`createtime` AS `createtime`,
	`userinfo`.`point` AS `point`,
	`userinfo`.`signature` AS `signature`,
	`userinfo`.`icon` AS `icon` 
FROM (
        (
            (
				`user`
				JOIN `userstatus` ON (
						`user`.`id` = `userstatus`.`id` 
                    )
            )
			JOIN `userinfo` ON (
					`user`.`id` = `userinfo`.`id` 
			)
        )
		JOIN `userdetails` ON (
				`user`.`id` = `userdetails`.`id` 
			)
    ) 
WHERE (`user`.`id` = `userstatus`.`id`);

CREATE OR REPLACE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `nebulahomedb`.`userdata` AS SELECT
	`user`.`personalid` AS `personalid`,
	`user`.`id` AS `id`,
	`userstatus`.`ban` AS `ban`,
	`userinfo`.`createtime` AS `createtime`,
	`userinfo`.`point` AS `point`,
	`userinfo`.`icon` AS `icon`,
	`userinfo`.`signature` AS `signature`,
	`userinfo`.`name` AS `name` 
FROM (
        (
			`user`
			JOIN `userinfo` ON (
					`user`.`id` = `userinfo`.`id` 
				)
        )
		JOIN `userstatus` ON (
			`user`.`id` = `userstatus`.`id` 
	    )
    );

SET FOREIGN_KEY_CHECKS = 1;