/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : localhost:3306
 Source Schema         : weather-monitoring

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 22/03/2025 14:24:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarms
-- ----------------------------
DROP TABLE IF EXISTS `alarms`;
CREATE TABLE `alarms`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sensor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` decimal(10, 2) NOT NULL,
  `dateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 743 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gas_greenhouse
-- ----------------------------
DROP TABLE IF EXISTS `gas_greenhouse`;
CREATE TABLE `gas_greenhouse`  (
  `cavity_pressure` decimal(10, 4) NOT NULL COMMENT '光腔压力',
  `cavity_temp` decimal(10, 4) NOT NULL COMMENT '光腔温度',
  `mpv_position` int NOT NULL COMMENT '多口阀位置',
  `co2` decimal(10, 4) NOT NULL COMMENT 'CO2浓度',
  `co2_dry` decimal(10, 4) NOT NULL COMMENT 'CO2水汽校正后浓度',
  `ch4` decimal(10, 4) NOT NULL COMMENT 'CH4浓度',
  `ch4_dry` decimal(10, 4) NOT NULL COMMENT 'CH4水汽校正后浓度',
  `h2o` decimal(10, 4) NOT NULL COMMENT 'H2O含量'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pressure_monitor
-- ----------------------------
DROP TABLE IF EXISTS `pressure_monitor`;
CREATE TABLE `pressure_monitor`  (
  `wh_main_upper_limit` float NULL DEFAULT NULL COMMENT 'WH主上限',
  `wh_main_lower_limit` float NULL DEFAULT NULL COMMENT 'WH主下限',
  `wh_main_alarm_upper` float NULL DEFAULT NULL COMMENT 'WH主报警上限',
  `wh_main_alarm_lower` float NULL DEFAULT NULL COMMENT 'WH主报警下限',
  `wh_backup_upper_limit` float NULL DEFAULT NULL COMMENT 'WH备上限',
  `wh_backup_lower_limit` float NULL DEFAULT NULL COMMENT 'WH备下限',
  `wh_backup_alarm_upper` float NULL DEFAULT NULL COMMENT 'WH备报警上限',
  `wh_backup_alarm_lower` float NULL DEFAULT NULL COMMENT 'WH备报警下限',
  `inlet_pressure_upper_limit` float NULL DEFAULT NULL COMMENT '进气压力上限',
  `inlet_pressure_lower_limit` float NULL DEFAULT NULL COMMENT '进气压力下限',
  `inlet_pressure_alarm_upper` float NULL DEFAULT NULL COMMENT '进气压力报警上限',
  `inlet_pressure_alarm_lower` float NULL DEFAULT NULL COMMENT '进气压力报警下限',
  `relief_flow_upper_limit` float NULL DEFAULT NULL COMMENT '泄压流量上限',
  `relief_flow_lower_limit` float NULL DEFAULT NULL COMMENT '泄压流量下限',
  `relief_flow_alarm_upper` float NULL DEFAULT NULL COMMENT '泄压流量报警上限',
  `relief_flow_alarm_lower` float NULL DEFAULT NULL COMMENT '泄压流量报警下限',
  `bypass_flow_upper_limit` float NULL DEFAULT NULL COMMENT '旁路流量上限',
  `bypass_flow_lower_limit` float NULL DEFAULT NULL COMMENT '旁路流量下限',
  `bypass_flow_alarm_upper` float NULL DEFAULT NULL COMMENT '旁路流量报警上限',
  `bypass_flow_alarm_lower` float NULL DEFAULT NULL COMMENT '旁路流量报警下限',
  `dry_gas_pressure_upper_limit` float NULL DEFAULT NULL COMMENT '干燥气压力上限',
  `dry_gas_pressure_lower_limit` float NULL DEFAULT NULL COMMENT '干燥气压力下限',
  `dry_gas_pressure_alarm_upper` float NULL DEFAULT NULL COMMENT '干燥气压力报警上限',
  `dry_gas_pressure_alarm_lower` float NULL DEFAULT NULL COMMENT '干燥气压力报警下限',
  `dry_gas_flow_upper_limit` float NULL DEFAULT NULL COMMENT '干燥气流量上限',
  `dry_gas_flow_lower_limit` float NULL DEFAULT NULL COMMENT '干燥气流量下限',
  `dry_gas_flow_alarm_upper` float NULL DEFAULT NULL COMMENT '干燥气流量报警上限',
  `dry_gas_flow_alarm_lower` float NULL DEFAULT NULL COMMENT '干燥气流量报警下限',
  `id` int UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1693278211 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
