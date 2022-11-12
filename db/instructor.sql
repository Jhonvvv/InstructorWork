/*
 Navicat Premium Data Transfer

 Source Server         : myconnect
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : clm

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 30/04/2022 15:11:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for daily_work
-- ----------------------------
DROP TABLE IF EXISTS `daily_work`;
CREATE TABLE `daily_work`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `work_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作ID',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `work_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作名称',
  `remake` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` int(11) NULL DEFAULT NULL COMMENT '工作类型（0:工作,1:会议）',
  `is_provisional` int(11) NULL DEFAULT NULL COMMENT '是否临时工作（0:否,1:是）',
  `is_finish` int(11) NULL DEFAULT NULL COMMENT '完成工作（0:未完成,1:完成）',
  `start_time` datetime NULL DEFAULT NULL COMMENT '工作开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '工作结束时间',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工作表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of daily_work
-- ----------------------------
INSERT INTO `daily_work` VALUES ('1469957992160968706', '1469955027815645186', '1463444095002333185', '打卡', '每日打卡', 0, 0, 0, '2021-12-12 07:00:10', '2021-12-12 20:17:28', 0, 'admin', '2021-12-12 17:11:10', 'admin', '2021-12-12 20:17:32');
INSERT INTO `daily_work` VALUES ('1469958013338009602', '1469955232308936706', '1463444095002333185', '开会', '每日早会', 1, 0, 0, '2021-12-12 08:30:16', '2021-12-12 20:17:29', 0, 'admin', '2021-12-12 17:11:16', 'admin', '2021-12-12 20:17:32');
INSERT INTO `daily_work` VALUES ('1470005135072575490', NULL, '1463444095002333185', '临时工作', '临时工作', 0, 1, 0, '2021-12-12 10:00:15', NULL, 0, 'admin', '2021-12-12 20:18:30', 'admin', '2021-12-13 15:50:15');
INSERT INTO `daily_work` VALUES ('1470005255830781954', NULL, '1463444095002333185', '临时会议', '临时会议', 1, 1, 0, '2021-12-13 20:25:03', NULL, 0, 'admin', '2021-12-12 20:18:59', 'admin', '2021-12-13 15:50:03');
INSERT INTO `daily_work` VALUES ('1470300155931590657', '1470300155738652674', '1463444095002333185', '日常工作', '日常工作', 0, 0, 0, '2021-12-13 20:25:00', NULL, 0, 'admin', '2021-12-13 15:50:49', NULL, NULL);
INSERT INTO `daily_work` VALUES ('1470369184356499457', '1469955027815645186', '1463444095002333185', '打卡', '每日打卡', 0, 0, 1, '2021-12-13 07:00:06', '2021-12-13 20:25:06', 0, 'admin', '2021-12-13 20:25:06', NULL, NULL);
INSERT INTO `daily_work` VALUES ('1470404235005902849', '1470404234871685122', '1470332784399642625', '123', '123', 0, 0, 0, '2021-12-13 07:30:23', '2021-12-13 22:46:21', 0, 'admin', '2021-12-13 22:44:23', 'admin', '2021-12-13 22:49:58');
INSERT INTO `daily_work` VALUES ('1470404365406814209', '1470404365209681922', '1470332784399642625', '456', '456', 1, 0, 0, '2021-12-13 07:15:54', '2021-12-13 22:46:20', 0, 'admin', '2021-12-13 22:44:54', 'admin', '2021-12-13 22:50:01');
INSERT INTO `daily_work` VALUES ('1470404473393364993', NULL, '1470332784399642625', '789', '789', 0, 1, 0, '2021-12-13 23:00:43', NULL, 0, 'admin', '2021-12-13 22:45:20', 'admin', '2021-12-13 22:53:43');
INSERT INTO `daily_work` VALUES ('1470734130735075329', '1470734130537943041', '1463444095002333185', '工作', '工作', 0, 0, 1, '2021-12-14 20:45:16', '2021-12-14 20:57:30', 0, 'admin', '2021-12-14 20:35:16', 'admin', '2021-12-14 20:57:30');
INSERT INTO `daily_work` VALUES ('1470738303711039490', NULL, '1463444095002333185', '临时', '临时', 1, 1, 0, '2021-12-14 20:58:51', NULL, 0, 'admin', '2021-12-14 20:51:51', NULL, NULL);
INSERT INTO `daily_work` VALUES ('1470956208499261441', '1469955232308936706', '1463444095002333185', '开会', '每日早会', 1, 0, 1, '2021-12-15 07:30:44', '2021-12-15 11:17:44', 0, 'admin', '2021-12-15 11:17:44', NULL, NULL);
INSERT INTO `daily_work` VALUES ('1470956442201686018', NULL, '1463444095002333185', '临时', NULL, 0, 1, 0, '2021-12-15 11:30:40', NULL, 0, 'admin', '2021-12-15 11:18:40', NULL, NULL);
INSERT INTO `daily_work` VALUES ('1476815652365815809', '1469955027815645186', '1463444095002333185', '打卡', '每日打卡', 0, 0, 0, '2021-12-31 07:00:04', '2021-12-31 15:21:04', 0, 'admin', '2021-12-31 15:21:04', 'admin', '2021-12-31 15:21:26');
INSERT INTO `daily_work` VALUES ('1476815681285541889', '1469955232308936706', '1463444095002333185', '开会', '每日早会', 1, 0, 1, '2021-12-31 07:30:11', '2021-12-31 15:21:11', 0, 'admin', '2021-12-31 15:21:11', NULL, NULL);
INSERT INTO `daily_work` VALUES ('1476817159312134145', '1476817158959812609', '1463444095002333185', 'test', NULL, 0, 0, 0, '2021-12-31 15:30:03', NULL, 0, 'admin', '2021-12-31 15:27:03', 'admin', '2021-12-31 15:27:16');

-- ----------------------------
-- Table structure for punishment
-- ----------------------------
DROP TABLE IF EXISTS `punishment`;
CREATE TABLE `punishment`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `student_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生ID',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `depart_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `punishment_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处罚ID',
  `approve_type` int(11) NULL DEFAULT NULL COMMENT '处罚申请类型(0:处罚申请,1:撤销处罚申请)',
  `is_revoke` int(11) NULL DEFAULT NULL COMMENT '撤销处罚（0:正常,1:撤销）',
  `type` int(11) NULL DEFAULT NULL COMMENT '处罚类型（0:警告,1:严重警告,2:记过,3:留校查看,4:劝退,5:开除学籍）',
  `objection` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤销申请原因',
  `prove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证明材料',
  `approve_status` int(11) NULL DEFAULT NULL COMMENT '审核状态（0，待审核，1，审核中，2，通过  3，拒绝）',
  `reason` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处罚原因',
  `counselor_opinion` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '辅导员意见',
  `audit_opinion` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '惩罚表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of punishment
-- ----------------------------
INSERT INTO `punishment` VALUES ('1476201463280631809', '1467493627272900609', '1467493626018803714', '1467485964803780610', NULL, 0, 0, 0, NULL, NULL, 1, '屡次出校未申请', NULL, NULL, 0, 'admin', '2021-12-29 22:40:30', NULL, NULL);
INSERT INTO `punishment` VALUES ('1476370258829795329', '1468153060927856642', '1468153060726530050', '1467485964803780610', NULL, 0, 0, 1, NULL, 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/30/85c904d6572842578c86a76c12b25d7e.jpg', 1, '多次出校未申请', NULL, NULL, 0, 'admin', '2021-12-30 09:51:14', NULL, NULL);
INSERT INTO `punishment` VALUES ('1476377874507259906', '1467493627272900609', '1467493626018803714', '1467485964803780610', NULL, 0, 1, 0, NULL, NULL, 2, '出校未申请', NULL, '同意', 0, 'admin', '2021-12-30 00:00:00', 'admin', '2021-12-31 10:30:04');
INSERT INTO `punishment` VALUES ('1476735835566096386', '1468162010473684993', '1468162010343661569', '1467485964803780610', NULL, 0, 0, 1, NULL, '\r\nhttps://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/31/83f58b98c41e4d27881773f7db78f3e6.jpg', 2, '出校未申请', NULL, '同意', 0, 'admin', '2021-12-31 00:00:00', 'admin', '2021-12-31 10:09:00');
INSERT INTO `punishment` VALUES ('1476737354646536193', '1467493627272900609', '1467493626018803714', '1467485964803780610', '1476377874507259906', 1, 0, 0, '遵守纪律', 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/31/83f58b98c41e4d27881773f7db78f3e6.jpg', 2, '出校未申请', '通过', '同意', 0, 'admin', '2021-12-31 00:00:00', 'admin', '2021-12-31 10:30:04');
INSERT INTO `punishment` VALUES ('1476749236430290946', '1468153060927856642', '1468153060726530050', '1467485964803780610', NULL, 0, 0, 1, NULL, NULL, 2, '出校未申请', NULL, '同意', 0, 'admin', '2021-12-31 00:00:00', 'admin', '2021-12-31 10:57:44');
INSERT INTO `punishment` VALUES ('1476814582050738177', '1467493627272900609', '1467493626018803714', '1467485964803780610', NULL, 0, 1, 2, NULL, 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/31/44763ce3f7dc40d6a8529a629579ccc3.jpg', 2, 'test', NULL, '同意', 0, 'admin', '2021-12-31 00:00:00', 'admin', '2021-12-31 15:18:34');
INSERT INTO `punishment` VALUES ('1476814830655524866', '1467493627272900609', '1467493626018803714', '1467485964803780610', '1476814582050738177', 1, 0, 2, 'test', 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/31/44763ce3f7dc40d6a8529a629579ccc3.jpg', 2, 'test', '同意', '同意', 0, 'admin', '2021-12-31 00:00:00', 'admin', '2021-12-31 15:18:34');

-- ----------------------------
-- Table structure for punishment_approve
-- ----------------------------
DROP TABLE IF EXISTS `punishment_approve`;
CREATE TABLE `punishment_approve`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `punishment_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处罚申请表ID',
  `counselor_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '辅导员姓名',
  `before_status` int(11) NULL DEFAULT NULL COMMENT '审核前状态(0，辅导员通过 1，辅导员拒绝)',
  `current_status` int(11) NULL DEFAULT NULL COMMENT '当前审核状态(0，待审核 1，已审核)',
  `after_status` int(11) NULL DEFAULT NULL COMMENT '审核后状态(0,审核通过，1，审核拒绝)',
  `remake` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of punishment_approve
-- ----------------------------
INSERT INTO `punishment_approve` VALUES ('1476201463544872962', '1476201463280631809', '张伟', 0, 0, NULL, NULL, 0, 'admin', '2021-12-29 22:40:30', NULL, NULL);
INSERT INTO `punishment_approve` VALUES ('1476370259039510529', '1476370258829795329', '张伟', 0, 0, NULL, NULL, 0, 'admin', '2021-12-30 09:51:14', NULL, NULL);
INSERT INTO `punishment_approve` VALUES ('1476377874641477633', '1476377874507259906', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-30 10:21:30', 'admin', '2021-12-30 11:29:17');
INSERT INTO `punishment_approve` VALUES ('1476735835683536898', '1476735835566096386', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-31 10:03:54', 'admin', '2021-12-31 10:09:00');
INSERT INTO `punishment_approve` VALUES ('1476740913635115009', '1476737354646536193', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-31 10:24:05', 'admin', '2021-12-31 10:30:04');
INSERT INTO `punishment_approve` VALUES ('1476749236690337793', '1476749236430290946', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-31 10:57:09', 'admin', '2021-12-31 10:57:45');
INSERT INTO `punishment_approve` VALUES ('1476814582319173634', '1476814582050738177', '管理员', 0, 1, 0, NULL, 0, 'admin', '2021-12-31 15:16:49', 'admin', '2021-12-31 15:17:21');
INSERT INTO `punishment_approve` VALUES ('1476814937115348994', '1476814830655524866', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-31 15:18:14', 'admin', '2021-12-31 15:18:34');

-- ----------------------------
-- Table structure for reward
-- ----------------------------
DROP TABLE IF EXISTS `reward`;
CREATE TABLE `reward`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `student_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生ID',
  `type` int(11) NULL DEFAULT NULL COMMENT '奖励类型（0:三好学生,1:优秀学生干部,2:优秀党员,3:优秀团员）',
  `reason` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖励原因',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '奖励表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reward
-- ----------------------------
INSERT INTO `reward` VALUES ('1468422311693619201', '1467493627272900609', 0, '成绩优异', 0, 'admin', '2021-12-08 11:28:56', NULL, NULL);
INSERT INTO `reward` VALUES ('1468422465343557633', '1467493627272900609', 0, '成绩优异', 0, 'admin', '2020-08-01 11:29:32', NULL, NULL);
INSERT INTO `reward` VALUES ('1468578027599302657', '1467493627272900609', 1, '积极为班级服务', 0, 'admin', '2021-12-08 21:47:41', NULL, NULL);
INSERT INTO `reward` VALUES ('1470748385412763649', '1467493627272900609', 3, '积极参加活动', 0, 'admin', '2021-12-14 21:31:55', NULL, NULL);
INSERT INTO `reward` VALUES ('1470955245931659266', '1467493627272900609', 1, '1211', 0, 'admin', '2021-12-15 11:13:54', NULL, NULL);

-- ----------------------------
-- Table structure for scholarship
-- ----------------------------
DROP TABLE IF EXISTS `scholarship`;
CREATE TABLE `scholarship`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `depart_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `level` int(11) NULL DEFAULT NULL COMMENT '奖学金等级（0，一等，1，二等，2，三等）',
  `ranking` int(11) NULL DEFAULT NULL COMMENT '综测排名',
  `prove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证明材料',
  `counselor_opinion` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '辅导员意见',
  `audit_opinion` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `approve_status` int(11) NULL DEFAULT NULL COMMENT '审核状态（0，待审核，1，审核中，2，通过  3，拒绝）',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '奖学金表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scholarship
-- ----------------------------
INSERT INTO `scholarship` VALUES ('1475398292853497857', '1467493626018803714', '1467485964803780610', 0, 1, 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/26/bc636bb4abfe4921b084f891596c33d6.jpg,https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg', NULL, NULL, 0, 0, 'admin', '2021-12-27 17:28:59', NULL, NULL);
INSERT INTO `scholarship` VALUES ('1475402362813153281', '1467493626018803714', '1467485964803780610', 1, 10, NULL, '通过', '同意申请', 2, 0, 'admin', '2021-12-27 17:45:10', 'admin', '2021-12-28 19:55:41');
INSERT INTO `scholarship` VALUES ('1475635108504506369', '1468153060726530050', '1467485964803780610', 0, 2, NULL, '通过', '通过', 2, 0, 'admin', '2021-12-28 09:10:01', 'admin', '2021-12-29 20:19:03');
INSERT INTO `scholarship` VALUES ('1476710377411596290', '1467493626018803714', '1467485964803780610', 0, 1, NULL, '同意', NULL, 1, 0, 'admin', '2021-12-31 08:22:45', 'admin', '2021-12-31 08:23:51');
INSERT INTO `scholarship` VALUES ('1476815881097990145', '1467493626018803714', '1467485964803780610', 2, 10, 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/31/993f4af40dea470792fbb516958132f6.jpg', '同意', '同意', 2, 0, 'admin', '2021-12-31 15:21:59', 'admin', '2021-12-31 15:22:36');

-- ----------------------------
-- Table structure for scholarship_approve
-- ----------------------------
DROP TABLE IF EXISTS `scholarship_approve`;
CREATE TABLE `scholarship_approve`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `scholarship_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖学金申请表ID',
  `counselor_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '辅导员姓名',
  `before_status` int(11) NULL DEFAULT NULL COMMENT '审核前状态(0，辅导员通过 1，辅导员拒绝)',
  `current_status` int(11) NULL DEFAULT NULL COMMENT '当前审核状态(0，待审核 1，已审核)',
  `after_status` int(11) NULL DEFAULT NULL COMMENT '审核后状态(0,审核通过，1，审核拒绝)',
  `remake` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scholarship_approve
-- ----------------------------
INSERT INTO `scholarship_approve` VALUES ('1475747237962199041', '1475402362813153281', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-28 16:35:34', 'admin', '2021-12-28 19:55:41');
INSERT INTO `scholarship_approve` VALUES ('1476165765848743937', '1475635108504506369', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-29 20:18:39', 'admin', '2021-12-29 20:19:03');
INSERT INTO `scholarship_approve` VALUES ('1476710654407626754', '1476710377411596290', '张伟', 0, 0, NULL, NULL, 0, 'admin', '2021-12-31 08:23:51', NULL, NULL);
INSERT INTO `scholarship_approve` VALUES ('1476815977134968833', '1476815881097990145', '张伟', 0, 1, 0, NULL, 0, 'admin', '2021-12-31 15:22:22', 'admin', '2021-12-31 15:22:36');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户表ID',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生家庭住址',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `guardian` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监护人姓名',
  `guardian_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监护人电话',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态 0正常 1已删除',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1467493627272900609', '1467493626018803714', '厦门市集美区', '350212201011193017', '', '', 0, 'admin', '2021-12-05 21:58:40', 'admin', '2021-12-15 08:33:39');
INSERT INTO `student` VALUES ('1468153060927856642', '1468153060726530050', NULL, NULL, NULL, NULL, 0, 'admin', '2021-12-07 17:39:01', NULL, NULL);
INSERT INTO `student` VALUES ('1468162010473684993', '1468162010343661569', NULL, NULL, NULL, NULL, 0, 'admin', '2021-12-07 18:14:35', NULL, NULL);
INSERT INTO `student` VALUES ('1468165490168946690', '1468165489778876418', NULL, NULL, NULL, NULL, 0, 'admin', '2021-12-07 18:28:25', NULL, NULL);
INSERT INTO `student` VALUES ('1468176827687231490', '1468176827158749185', '厦门理工学院', NULL, '管理员', '', 1, 'admin', '2021-12-07 19:13:28', NULL, NULL);
INSERT INTO `student` VALUES ('1468177836274737153', '1468177835876278273', NULL, NULL, NULL, NULL, 0, 'admin', '2021-12-07 19:17:28', NULL, NULL);
INSERT INTO `student` VALUES ('1470403570284216321', '1470403569957060610', NULL, NULL, NULL, NULL, 1, 'admin', '2021-12-13 22:41:45', 'admin', '2021-12-13 22:42:08');
INSERT INTO `student` VALUES ('1470688597165297666', '1470688597362429953', NULL, NULL, NULL, NULL, 0, 'admin', '2021-12-14 17:34:20', 'admin', '2021-12-14 18:15:14');

-- ----------------------------
-- Table structure for sys_depart
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart`;
CREATE TABLE `sys_depart`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `parent_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父机构ID',
  `depart_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构/部门名称',
  `depart_order` int(11) NULL DEFAULT 0 COMMENT '排序',
  `org_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构类型 1一级部门 2子部门',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `remake` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1启用，0不启用）',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态（0，正常，1已删除）',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_depart_parent_id`(`parent_id`) USING BTREE,
  INDEX `index_depart_depart_order`(`depart_order`) USING BTREE,
  INDEX `idx_sd_parent_id`(`parent_id`) USING BTREE,
  INDEX `idx_sd_depart_order`(`depart_order`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_depart
-- ----------------------------
INSERT INTO `sys_depart` VALUES ('1462722550017568769', '', '厦门理工学院', 0, '1', '', '', 1, 0, 'admin', '2021-11-22 18:00:07', '', NULL);
INSERT INTO `sys_depart` VALUES ('1466718101755179009', '1462722550017568769', '软件工程学院', 0, '2', '', '', 1, 0, 'admin', '2021-12-03 19:15:52', '', NULL);
INSERT INTO `sys_depart` VALUES ('1466728711498805250', '1466718101755179009', '19软件工程', 0, '2', '', '', 1, 0, 'admin', '2021-12-03 19:19:10', '', NULL);
INSERT INTO `sys_depart` VALUES ('1467485964803780610', '1466728711498805250', '软件二班', 0, '2', '软件二班', '软件二班', 1, 0, 'admin', '2021-12-05 21:28:13', 'admin', '2021-12-26 18:43:08');
INSERT INTO `sys_depart` VALUES ('1475053043220279297', '1466728711498805250', '软件三班', 0, '2', NULL, NULL, 1, 0, 'admin', '2021-12-26 18:37:05', NULL, NULL);
INSERT INTO `sys_depart` VALUES ('1475075563755405314', NULL, '测试一级部门', 0, '1', '测试一级部门', '测试一级部门', 1, 1, 'admin', '2021-12-26 20:06:35', NULL, NULL);
INSERT INTO `sys_depart` VALUES ('1475075670336864257', '1466728711498805250', '软件四班', 0, '2', NULL, NULL, 1, 0, 'admin', '2021-12-26 20:07:00', NULL, NULL);
INSERT INTO `sys_depart` VALUES ('1476089218248740866', NULL, '111', 0, '1', NULL, NULL, 1, 1, 'admin', '2021-12-29 15:14:29', NULL, NULL);
INSERT INTO `sys_depart` VALUES ('1476089250930757633', '1476089218248740866', '222', 0, '2', NULL, NULL, 1, 0, 'admin', '2021-12-29 15:14:36', NULL, NULL);
INSERT INTO `sys_depart` VALUES ('1476089274800541697', '1476089250930757633', '333', 0, '2', NULL, NULL, 1, 0, 'admin', '2021-12-29 15:14:42', NULL, NULL);
INSERT INTO `sys_depart` VALUES ('1476815181219647489', '1466728711498805250', '软件一班', 0, '2', NULL, NULL, 1, 0, 'admin', '2021-12-31 15:19:12', NULL, NULL);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端组件',
  `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级菜单跳转地址',
  `menu_type` int(11) NULL DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单)',
  `is_route` int(11) NOT NULL DEFAULT 1 COMMENT '是否路由菜单: 0:不是  1:是（默认值1）',
  `is_leaf` int(11) NULL DEFAULT NULL COMMENT '是否叶子节点:    1:是   0:不是',
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '删除状态 0正常 1已删除',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_prem_pid`(`parent_id`) USING BTREE,
  INDEX `index_prem_is_route`(`is_route`) USING BTREE,
  INDEX `index_prem_is_leaf`(`is_leaf`) USING BTREE,
  INDEX `index_prem_del_flag`(`del_flag`) USING BTREE,
  INDEX `index_menu_type`(`menu_type`) USING BTREE,
  INDEX `idx_sp_parent_id`(`parent_id`) USING BTREE,
  INDEX `idx_sp_is_route`(`is_route`) USING BTREE,
  INDEX `idx_sp_is_leaf`(`is_leaf`) USING BTREE,
  INDEX `idx_sp_del_flag`(`del_flag`) USING BTREE,
  INDEX `idx_sp_menu_type`(`menu_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1465270629178564610', NULL, 'Permission', '权限管理', '/permission', 'el-icon-lock', 'Layout', '', 0, 1, 0, 0, 'admin', '2021-11-29 18:45:16', 'admin', '2021-12-09 18:02:15');
INSERT INTO `sys_permission` VALUES ('1465272997085470722', '1465270629178564610', 'Role', '角色', '/role', 'el-icon-setting', '/permission/role', '', 1, 1, 1, 0, 'admin', '2021-11-29 18:54:41', '', NULL);
INSERT INTO `sys_permission` VALUES ('1465273618534522882', '1465270629178564610', 'User', '用户', '/user', 'el-icon-user', '/permission/user', '', 1, 1, 1, 0, 'admin', '2021-11-29 18:57:09', '', NULL);
INSERT INTO `sys_permission` VALUES ('1465639615896612865', '1465270629178564610', 'RouterTree', '路由', '/routerTree', 'el-icon-set-up', '/permission/routerTree', '', 1, 1, 1, 0, 'admin', '2021-11-30 22:46:01', '', '2021-11-30 22:46:20');
INSERT INTO `sys_permission` VALUES ('1467432408855883778', NULL, 'Student', '学生管理', '/student/student', 'el-icon-s-custom', 'Layout', '', 0, 1, 1, 0, 'admin', '2021-12-05 17:55:25', 'admin', '2021-12-06 17:53:48');
INSERT INTO `sys_permission` VALUES ('1468883039093358593', NULL, 'Work', '工作管理', '/work', 'el-icon-menu', 'Layout', NULL, 0, 1, 0, 0, 'admin', '2021-12-09 17:59:42', 'admin', '2021-12-09 18:01:23');
INSERT INTO `sys_permission` VALUES ('1468884140240764930', '1468883039093358593', 'daily', '看板', '/workBoard', 'el-icon-notebook-2', '/work/workBoard', NULL, 1, 1, 1, 0, 'admin', '2021-12-09 18:04:04', 'admin', '2021-12-10 10:16:13');
INSERT INTO `sys_permission` VALUES ('1469103743839551490', '1468883039093358593', 'Board', '工作', '/workTable', 'el-icon-tickets', '/work/workTable', NULL, 1, 1, 1, 0, 'admin', '2021-12-10 08:36:42', 'admin', '2021-12-10 16:05:43');
INSERT INTO `sys_permission` VALUES ('1470029418435985409', '1468883039093358593', 'Flow', '流程', '/workTimeLine', 'el-icon-time', '/work/workTimeLine', NULL, 1, 1, 1, 0, 'admin', '2021-12-12 21:55:00', 'admin', '2021-12-12 21:55:40');
INSERT INTO `sys_permission` VALUES ('1470368663625269249', NULL, 'Test', '测试', '/test', 'el-icon-setting', '/test', NULL, 0, 1, 1, 1, 'admin', '2021-12-13 20:23:02', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1474936034583121921', NULL, 'Depart', '部门管理', '/depart/departTree', 'el-icon-office-building', 'Layout', NULL, 0, 1, 1, 0, 'admin', '2021-12-26 10:52:08', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1475376526106042370', NULL, 'Apply', '学生申请', '/apply', 'el-icon-edit-outline', 'Layout', NULL, 0, 1, 0, 0, 'admin', '2021-12-27 16:02:30', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1475377420776251393', '1475376526106042370', 'Scholarship', '奖学金', '/scholarship', 'el-icon-document-add', '/apply/scholarship', NULL, 1, 1, 1, 0, 'admin', '2021-12-27 16:06:03', 'admin', '2021-12-27 18:20:19');
INSERT INTO `sys_permission` VALUES ('1475410204047704066', '1475376526106042370', 'Punishment', '处罚', '/punishment', 'el-icon-document-remove', '/apply/punishment', NULL, 1, 1, 1, 0, 'admin', '2021-12-27 18:16:19', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1475452899499819010', NULL, 'Examine', '审核管理', '/examine', 'el-icon-s-check', 'Layout', NULL, 0, 1, 0, 0, 'admin', '2021-12-27 21:05:58', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1475453881872924674', '1475452899499819010', 'ScholarshipApprove', '评定', '/scholarshipApprove', 'el-icon-folder-add', '/examine/scholarshipApprove', NULL, 1, 1, 1, 0, 'admin', '2021-12-27 21:09:53', 'admin', '2021-12-28 15:58:02');
INSERT INTO `sys_permission` VALUES ('1476039696839630850', '1475452899499819010', 'RevokePunishment', '撤销', '/revokePunishment', 'el-icon-folder-remove', '/examine/revokePunishment', NULL, 1, 1, 1, 0, 'admin', '2021-12-29 11:57:42', 'admin', '2021-12-29 12:04:05');
INSERT INTO `sys_permission` VALUES ('1476041681877884930', NULL, 'DepartApprove', '申请管理', '/departApprove', 'el-icon-school', 'Layout', NULL, 0, 1, 0, 0, 'admin', '2021-12-29 12:05:35', 'admin', '2021-12-29 16:17:27');
INSERT INTO `sys_permission` VALUES ('1476041920114352129', '1476041681877884930', 'ScholarshipRecord', '奖学金记录', '/scholarshipRecord', 'el-icon-folder-checked', '/departApprove/scholarshipRecord', NULL, 1, 1, 1, 0, 'admin', '2021-12-29 12:06:32', 'admin', '2021-12-29 12:10:20');
INSERT INTO `sys_permission` VALUES ('1476104012565688321', '1476041681877884930', 'RevokePunishment', '处罚记录', '/punishmentRecord', 'el-icon-folder-delete', '/departApprove/punishmentRecord', NULL, 1, 1, 1, 0, 'admin', '2021-12-29 16:13:16', 'admin', '2021-12-30 11:15:05');
INSERT INTO `sys_permission` VALUES ('1476813665402376194', NULL, 'Test', '测试', '/test', 'el-icon-lock', '/test', NULL, 0, 1, 1, 0, 'admin', '2021-12-31 15:13:10', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `role_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_sys_role_role_code`(`role_code`) USING BTREE,
  INDEX `idx_sr_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1462394142624624642', '管理员', 'admin', '管理员', 'admin', '2021-11-21 20:15:08', 'admin', '2021-12-31 15:13:31');
INSERT INTO `sys_role` VALUES ('1465970449476268033', '测试', 'test', '用来测试', 'admin', '2021-12-01 17:06:06', 'admin', '2021-12-02 11:41:48');
INSERT INTO `sys_role` VALUES ('1467463966245531650', '学生', 'student', '学生', 'admin', '2021-12-05 20:00:48', 'admin', '2021-12-27 18:16:52');
INSERT INTO `sys_role` VALUES ('1467464182604509185', '辅导员', 'counselor', '辅导员', 'admin', '2021-12-05 20:01:40', 'admin', '2021-12-29 12:02:25');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_group_role_per_id`(`role_id`, `permission_id`) USING BTREE,
  INDEX `index_group_role_id`(`role_id`) USING BTREE,
  INDEX `index_group_per_id`(`permission_id`) USING BTREE,
  INDEX `idx_srp_role_per_id`(`role_id`, `permission_id`) USING BTREE,
  INDEX `idx_srp_role_id`(`role_id`) USING BTREE,
  INDEX `idx_srp_permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1476813752014753794', '1462394142624624642', '1465270629178564610');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753795', '1462394142624624642', '1465272997085470722');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753796', '1462394142624624642', '1465273618534522882');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753797', '1462394142624624642', '1465639615896612865');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753798', '1462394142624624642', '1467432408855883778');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753799', '1462394142624624642', '1468883039093358593');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753800', '1462394142624624642', '1468884140240764930');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753801', '1462394142624624642', '1469103743839551490');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753802', '1462394142624624642', '1470029418435985409');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753803', '1462394142624624642', '1474936034583121921');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753804', '1462394142624624642', '1476041681877884930');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753805', '1462394142624624642', '1476041920114352129');
INSERT INTO `sys_role_permission` VALUES ('1476813752014753806', '1462394142624624642', '1476104012565688321');
INSERT INTO `sys_role_permission` VALUES ('1466251225685614594', '1465970449476268033', '1465272997085470722');
INSERT INTO `sys_role_permission` VALUES ('1466251225685614595', '1465970449476268033', '1465639615896612865');
INSERT INTO `sys_role_permission` VALUES ('1475410341218222081', '1467463966245531650', '1475376526106042370');
INSERT INTO `sys_role_permission` VALUES ('1475410341218222082', '1467463966245531650', '1475377420776251393');
INSERT INTO `sys_role_permission` VALUES ('1475410341218222083', '1467463966245531650', '1475410204047704066');
INSERT INTO `sys_role_permission` VALUES ('1476040883555033089', '1467464182604509185', '1467432408855883778');
INSERT INTO `sys_role_permission` VALUES ('1476040883555033090', '1467464182604509185', '1468883039093358593');
INSERT INTO `sys_role_permission` VALUES ('1476040883622141953', '1467464182604509185', '1468884140240764930');
INSERT INTO `sys_role_permission` VALUES ('1476040883622141954', '1467464182604509185', '1469103743839551490');
INSERT INTO `sys_role_permission` VALUES ('1476040883622141955', '1467464182604509185', '1470029418435985409');
INSERT INTO `sys_role_permission` VALUES ('1476040883622141956', '1467464182604509185', '1475452899499819010');
INSERT INTO `sys_role_permission` VALUES ('1476040883622141957', '1467464182604509185', '1475453881872924674');
INSERT INTO `sys_role_permission` VALUES ('1476040883622141959', '1467464182604509185', '1476039696839630850');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5密码盐',
  `depart_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门',
  `depart_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '负责部门',
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属角色',
  `sex` int(11) NOT NULL COMMENT '性别(0-默认未知,1-男,2-女)',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态(1-正常,2-冻结)',
  `user_identity` int(11) NULL DEFAULT NULL COMMENT '身份（1学生 2辅导员 3管理员 4其他）',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_user_name`(`username`) USING BTREE,
  UNIQUE INDEX `uniq_sys_user_username`(`username`) USING BTREE,
  UNIQUE INDEX `uniq_sys_user_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `uniq_sys_user_email`(`email`) USING BTREE,
  INDEX `index_user_status`(`status`) USING BTREE,
  INDEX `index_user_del_flag`(`del_flag`) USING BTREE,
  INDEX `idx_su_username`(`username`) USING BTREE,
  INDEX `idx_su_status`(`status`) USING BTREE,
  INDEX `idx_su_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1463444095002333185', 'admin', '管理员', 'G4Njnwj2GGc=', 'iKeM3sXB', '1462722550017568769', '1462722550017568769,1466718101755179009,1466728711498805250,1467485964803780610,1475053043220279297,1475075670336864257', '1462394142624624642', 0, '1314819874@qq.com', '13316046212', 1, 3, '2001-03-09 00:00:00', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 'admin', '2021-11-24 17:47:16', 'admin', '2021-12-26 20:35:48');
INSERT INTO `sys_user` VALUES ('1467111457366851586', 'test', '测试用户', 'U/BLpOwgNzY=', 'kOuPR7Ve', '1466718101755179009', '1462722550017568769,1466718101755179009', '1462394142624624642', 0, NULL, NULL, 1, 3, '2017-09-12 00:00:00', NULL, 0, 'admin', '2021-12-04 20:40:04', 'admin', '2021-12-07 19:05:44');
INSERT INTO `sys_user` VALUES ('1467118284183814146', 'test2', '测试用户2', 'A7brldH4qyM=', '6hwuGaqX', '1466718101755179009', '1466718101755179009', '1462394142624624642', 0, NULL, NULL, 1, 3, '2021-12-01 00:00:00', NULL, 1, 'admin', '2021-12-04 21:07:11', NULL, NULL);
INSERT INTO `sys_user` VALUES ('1467493626018803714', '01', '张三', 'ktfK9/k3Kqc=', 'YFVFWxnY', '1467485964803780610', '', '1467463966245531650', 1, '', '', 1, 1, '2003-12-17 00:00:00', 'https://edufile-guli.oss-cn-beijing.aliyuncs.com/2021/12/26/bc636bb4abfe4921b084f891596c33d6.jpg', 0, 'admin', '2021-12-05 21:58:40', 'admin', '2021-12-27 15:56:15');
INSERT INTO `sys_user` VALUES ('1468153060726530050', '02', '王五', 'MuwOfy6uTvk=', 'kQ22CuyT', '1467485964803780610', '', '1467463966245531650', 1, NULL, NULL, 1, 1, '2008-12-01 00:00:00', NULL, 0, 'admin', '2021-12-07 17:39:01', 'admin', '2021-12-07 18:31:32');
INSERT INTO `sys_user` VALUES ('1468162010343661569', '03', '赵六', 'D86Gj29zDqY=', 'apTYcYNR', '1467485964803780610', '', '1467463966245531650', 1, NULL, NULL, 1, 1, '2017-12-12 00:00:00', NULL, 0, 'admin', '2021-12-07 18:14:35', NULL, NULL);
INSERT INTO `sys_user` VALUES ('1468165489778876418', '04', '韩梅', 'iPSB7jz+Dpc=', 'pgpyqFLZ', '1467485964803780610', '', '1467463966245531650', 2, NULL, NULL, 1, 1, '2016-12-21 00:00:00', NULL, 0, 'admin', '2021-12-07 18:28:25', NULL, NULL);
INSERT INTO `sys_user` VALUES ('1468176827158749185', '06', '测试学生', '2Du6rWesMRI=', 'NbCr1uYy', '1475075563755405314', '', '1462394142624624642', 1, NULL, NULL, 2, 1, '2002-12-04 00:00:00', NULL, 0, 'admin', '2021-12-07 19:13:28', 'admin', '2022-04-26 16:20:58');
INSERT INTO `sys_user` VALUES ('1468177835876278273', '05', '赵敏', 'f0tD4D9I4+E=', 'A0YAsBWR', '1467485964803780610', '', '1467463966245531650', 1, NULL, NULL, 1, 1, NULL, NULL, 0, 'admin', '2021-12-07 19:17:28', NULL, NULL);
INSERT INTO `sys_user` VALUES ('1470332784399642625', 'fdy', '张伟', 'z2IV0iEf+1k=', 'z64mmjby', '1466728711498805250', '1466728711498805250,1467485964803780610,1475053043220279297,1475075670336864257', '1467464182604509185', 1, NULL, NULL, 1, 2, NULL, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 'admin', '2021-12-13 18:00:28', 'admin', '2021-12-26 20:37:51');
INSERT INTO `sys_user` VALUES ('1470403569957060610', '08', 'lrc', 'yQr/O8REHjk=', 'iOVGoFCC', '1467485964803780610', '', '1467463966245531650', 1, NULL, NULL, 1, 1, NULL, 'user/93a3ab65195d82875c6ae06e8e0910cb6131d3fb_raw_1639406478658.jpg', 1, 'admin', '2021-12-13 22:41:45', 'admin', '2021-12-13 22:42:08');
INSERT INTO `sys_user` VALUES ('1470660553264394241', '09', '王富贵', 'fgQgSKsoWpE=', 'iPu0QaEX', '1462722550017568769', '', '1465970449476268033', 1, NULL, NULL, 1, 4, NULL, NULL, 0, 'admin', '2021-12-14 15:42:54', NULL, NULL);
INSERT INTO `sys_user` VALUES ('1470688597362429953', '07', '学生', 'mB9xlB0MUCk=', 'VQcSLt9k', '1475053043220279297', '', '1467463966245531650', 1, NULL, NULL, 1, 1, NULL, NULL, 0, 'admin', '2021-12-14 17:34:20', 'admin', '2021-12-31 15:29:58');
INSERT INTO `sys_user` VALUES ('1476817727229284353', '10', 'test1', 'SHrftmTiV88=', 'CnttTuqk', '1475053043220279297', '', '1465970449476268033', 0, NULL, NULL, 1, 4, NULL, NULL, 0, 'admin', '2021-12-31 15:29:18', 'admin', '2022-04-26 16:52:20');

-- ----------------------------
-- Table structure for sys_user_depart
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_depart`;
CREATE TABLE `sys_user_depart`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `dep_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_depart_groupk_userid`(`user_id`) USING BTREE,
  INDEX `index_depart_groupkorgid`(`dep_id`) USING BTREE,
  INDEX `index_depart_groupk_uidanddid`(`user_id`, `dep_id`) USING BTREE,
  INDEX `idx_sud_user_id`(`user_id`) USING BTREE,
  INDEX `idx_sud_dep_id`(`dep_id`) USING BTREE,
  INDEX `idx_sud_user_dep_id`(`user_id`, `dep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_depart
-- ----------------------------
INSERT INTO `sys_user_depart` VALUES ('1475082918983421953', '1463444095002333185', '1462722550017568769');
INSERT INTO `sys_user_depart` VALUES ('1468174884646543362', '1467111457366851586', '1466718101755179009');
INSERT INTO `sys_user_depart` VALUES ('1475374954181566465', '1467493626018803714', '1467485964803780610');
INSERT INTO `sys_user_depart` VALUES ('1468166276290568194', '1468153060726530050', '1467485964803780610');
INSERT INTO `sys_user_depart` VALUES ('1468162010930864129', '1468162010343661569', '1467485964803780610');
INSERT INTO `sys_user_depart` VALUES ('1468164759986757633', '1468164759785431041', '1467485964803780610');
INSERT INTO `sys_user_depart` VALUES ('1468165489976008705', '1468165489778876418', '1467485964803780610');
INSERT INTO `sys_user_depart` VALUES ('1518867719326289923', '1468176827158749185', '1475075563755405314');
INSERT INTO `sys_user_depart` VALUES ('1468177836077604865', '1468177835876278273', '1467485964803780610');
INSERT INTO `sys_user_depart` VALUES ('1475083434526298114', '1470332784399642625', '1466728711498805250');
INSERT INTO `sys_user_depart` VALUES ('1476817892065431554', '1470688597362429953', '1475053043220279297');
INSERT INTO `sys_user_depart` VALUES ('1518875611328696322', '1476817727229284353', '1475053043220279297');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index2_groupuu_user_id`(`user_id`) USING BTREE,
  INDEX `index2_groupuu_ole_id`(`role_id`) USING BTREE,
  INDEX `index2_groupuu_useridandroleid`(`user_id`, `role_id`) USING BTREE,
  INDEX `idx_sur_user_id`(`user_id`) USING BTREE,
  INDEX `idx_sur_role_id`(`role_id`) USING BTREE,
  INDEX `idx_sur_user_role_id`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1475082918643683329', '1463444095002333185', '1462394142624624642');
INSERT INTO `sys_user_role` VALUES ('1468174884176781313', '1467111457366851586', '1462394142624624642');
INSERT INTO `sys_user_role` VALUES ('1475374953724387330', '1467493626018803714', '1467463966245531650');
INSERT INTO `sys_user_role` VALUES ('1468166275900497921', '1468153060726530050', '1467463966245531650');
INSERT INTO `sys_user_role` VALUES ('1468162010733731842', '1468162010343661569', '1467463966245531650');
INSERT INTO `sys_user_role` VALUES ('1518867719326289922', '1468176827158749185', '1467463966245531650');
INSERT INTO `sys_user_role` VALUES ('1475083434056536065', '1470332784399642625', '1467464182604509185');
INSERT INTO `sys_user_role` VALUES ('1476817891398537217', '1470688597362429953', '1467463966245531650');
INSERT INTO `sys_user_role` VALUES ('1518875611320307714', '1476817727229284353', '1465970449476268033');

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `work_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作名称',
  `remake` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` int(11) NULL DEFAULT NULL COMMENT '工作类型（0:工作,1:会议）',
  `start_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作开始时间',
  `del_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日常工作表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES ('1469955027815645186', '1463444095002333185', '打卡', '每日打卡', 0, '07:00', 0, 'admin', '2021-12-12 16:59:24', NULL, NULL);
INSERT INTO `work` VALUES ('1469955232308936706', '1463444095002333185', '开会', '每日早会', 1, '07:30', 0, 'admin', '2021-12-12 17:00:12', NULL, NULL);
INSERT INTO `work` VALUES ('1470300155738652674', '1463444095002333185', '日常工作', '日常工作', 0, '10:30', 0, 'admin', '2021-12-13 15:50:49', NULL, NULL);
INSERT INTO `work` VALUES ('1470404234871685122', '1470332784399642625', '123', '123', 0, '07:30', 0, 'admin', '2021-12-13 22:44:23', NULL, NULL);
INSERT INTO `work` VALUES ('1470404365209681922', '1470332784399642625', '456', '456', 1, '07:15', 0, 'admin', '2021-12-13 22:44:54', NULL, NULL);
INSERT INTO `work` VALUES ('1470734130537943041', '1463444095002333185', '工作', '工作', 0, '20:45', 0, 'admin', '2021-12-14 20:35:16', 'admin', '2021-12-14 20:38:38');
INSERT INTO `work` VALUES ('1476817158959812609', '1463444095002333185', 'test', NULL, 0, '15:30', 0, 'admin', '2021-12-31 15:27:03', 'admin', '2021-12-31 15:27:16');

SET FOREIGN_KEY_CHECKS = 1;
