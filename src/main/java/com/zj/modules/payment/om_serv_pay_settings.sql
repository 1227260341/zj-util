/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.2.223
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 192.168.2.223:3306
 Source Schema         : lz_cloud_om_sit

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 16/03/2020 14:55:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for om_serv_pay_settings
-- ----------------------------
DROP TABLE IF EXISTS `om_serv_pay_settings`;
CREATE TABLE `om_serv_pay_settings`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `company_id` bigint(20) NOT NULL,
  `termianl_type` smallint(6) NOT NULL DEFAULT 1 COMMENT '支付终端类型：0不区分终端类型 1 PC 2 APP 3 小程序  4 WAP支付',
  `pay_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付方式编码',
  `pay_name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付方式名称',
  `pay_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '付款方式描述',
  `pay_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '付款配置，json序列化存储，不同支付方式序列化的实体不同',
  `is_enabled` tinyint(4) NULL DEFAULT 0 COMMENT '是否启用：0 不启用 1 启用',
  `sort_order` int(11) NULL DEFAULT 50 COMMENT '排序：越小越靠前',
  `callback_url` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付回调地址',
  `logo_url` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式logo地址',
  `created_by` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CMP_ID`(`company_id`) USING BTREE,
  INDEX `TTPEY`(`termianl_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 454 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工程公司付款设置表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
