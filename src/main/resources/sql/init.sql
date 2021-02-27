SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_student
-- ----------------------------
BEGIN;
INSERT INTO `t_student` VALUES (1, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (2, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (3, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (4, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (5, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (6, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (7, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (8, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (9, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (10, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (11, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (12, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (13, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (14, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (15, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (16, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (17, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (18, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (19, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (20, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (21, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (22, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (23, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (24, '李四', '物联网工程');
INSERT INTO `t_student` VALUES (25, '张三', '计算机技术');
INSERT INTO `t_student` VALUES (26, '李四', '物联网工程');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;