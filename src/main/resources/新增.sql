DROP TABLE IF EXISTS `um_app_token`;
CREATE TABLE `um_app_token` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `appId` int(11) DEFAULT NULL,
  `appSecret` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `appType` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `makeTime` datetime DEFAULT NULL,
  `makeUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `modifyUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


---------2017-10-23----新增表 
DROP TABLE IF EXISTS `tg_record_score_config`;
CREATE TABLE `tg_record_score_config` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
  `name` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '表名',
  `scoreType` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分数类型对应syscode表',
  `schoolId` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '',
  `score` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分数',
  `year` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学年',
  `term` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学期',
  `makeTime` datetime DEFAULT NULL,
  `makeUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `modifyUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tg_record_score_standard`;
CREATE TABLE `tg_record_score_standard` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
  `name` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '表名',
  `scoreType` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分数类型对应syscode表',
  `schoolId` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '',
  `score` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分数',
  `content` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '等级',
  `order` int(11) DEFAULT NULL COMMENT '排序号',
  `year` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学年',
  `term` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学期',
  `validFlag` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效',
  `makeTime` datetime DEFAULT NULL,
  `makeUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `modifyUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='档案分数标准';

DROP TABLE IF EXISTS `tg_teach_config`;
CREATE TABLE `tg_teach_config` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `demand` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '要求',
  `teachType` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '教学类型对应syscode表',
  `schoolId` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '',
  `order` int(11) DEFAULT NULL COMMENT '排序号',
  `validFlag` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  `evaluateType` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '评价类型',
  `makeTime` datetime DEFAULT NULL,
  `makeUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `modifyUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


------2017-10-23----新加字段

-- table tg_teacher_lesson add myGrade varchar(20) not null default null comment ''; 
alter table tg_teacher_lesson add myGrade varchar(20) default null comment '自评等级'; 
alter table tg_teacher_lesson add submitTime datetime default null comment '送审时间'; 

alter table tg_class_teach add myGrade varchar(36) default null comment '自评等级';
alter table ta_jobmgr add myGrade varchar(36) default null comment '自评等级';
alter table tg_counseling add myGrade varchar(36) default null comment '自评等级';
alter table tg_testmgr add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_honorarytitle add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_honorarytitle add unit varchar(36) default null comment '授奖单位';
alter table tg_teacher_winningrecord add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_devmaterials add unit varchar(36) default null comment '授奖单位';
alter table tg_teacher_devmaterials add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_train add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_train add toReviewDate datetime default null comment '送审时间';
--- 2017-10-30
alter table tg_teacher_endtext add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_endtext add toReviewDate datetime default null comment '送审时间';
alter table tg_teacher_thesis add myGrade varchar(36) default null comment '自评等级';
alter table tg_teacher_thesis add unit varchar(36) default null comment '单位';
alter table tg_teacher_thesis add bear varchar(36) default null comment '承担';
alter table tg_record_score_standard add nameOrder int(11) default null comment 'name排序';
alter table tg_teacher_lesson add checkFlag varchar(2) default null comment '是否提交审核';
alter table tg_teacher_lesson add checkState varchar(2) default null comment '审核状态';
alter table tg_teacher_endtext add checkFlag varchar(2) default null comment '是否提交审核';
alter table tg_teacher_endtext add checkState varchar(2) default null comment '审核状态';
alter table tg_teacher_train add checkFlag varchar(2) default null comment '是否提交审核';
alter table tg_teacher_train add checkState varchar(2) default null comment '审核状态';
alter table tg_record_score_standard add tableName varchar(36) default null comment '表名';
alter table tg_teacher_lesson add name varchar(36) default null;
alter table tg_teacher_endtext add name varchar(36) default null;
alter table tg_teacher_train add name varchar(36) default null;
alter table tg_class_teach add complete varchar(36) default null comment '完成情况';
---- 2017-11-1----
ALTER TABLE tg_teacher_lesson DROP submitTime;
alter table tg_teacher_lesson add toReviewDate datetime default null comment '送审时间';
alter table tg_teacher_lesson add grade varchar(36) default null comment '等级';
alter table tg_teacher_endtext add grade varchar(36) default null comment '等级';
alter table tg_teacher_train add grade varchar(36) default null comment '等级';
ALTER TABLE tg_teacher_endtext DROP term;
alter table tg_teacher_endtext add eduPeriod varchar(3) default null comment '学期';
ALTER TABLE tg_teacher_train DROP term;
alter table tg_teacher_train add eduPeriod varchar(3) default null comment '学期';
ALTER TABLE tg_teacher_lesson DROP term;
alter table tg_teacher_lesson add eduPeriod varchar(3) default null comment '学期';
---- 2017-11-2
alter table tg_class_teach modify column grade varchar(36);
alter table ta_jobmgr modify column grade varchar(36);
alter table ta_jobmgr add complete varchar(36) default null comment '完成情况';
alter table tg_counseling modify column grade varchar(36);
alter table tg_counseling add complete varchar(36) default null comment '完成情况';
alter table tg_testmgr modify column grade varchar(36);
alter table tg_testmgr add complete varchar(36) default null comment '完成情况';
alter table tg_teacher_lesson add checkDate datetime default null comment '审核日期';
alter table tg_teacher_lesson add checkUser varchar(36) default null comment '审核用户';
alter table tg_teacher_train add checkDate datetime default null comment '审核日期';
alter table tg_teacher_train add checkUser varchar(36) default null comment '审核用户';
alter table tg_teacher_endtext add checkDate datetime default null comment '审核日期';
alter table tg_teacher_endtext add checkUser varchar(36) default null comment '审核用户';
alter table tg_teacher_train add attId varchar(100) default null comment '附件id';
alter table tg_teacher_lesson add attId varchar(100) default null comment '附件id';
alter table tg_teacher_endtext add attId varchar(100) default null comment '附件id';
alter table tg_teacher_lesson add complete varchar(36) default null comment '完成情况';
alter table tg_teacher_thesis modify column grade varchar(36);
alter table tg_teacher_honorarytitle modify column grade varchar(36);
alter table tg_teacher_winningrecord modify column grade varchar(36);
alter table tg_teacher_devmaterials modify column grade varchar(36);
alter table tg_teacher_lesson modify column grade varchar(36);
alter table tg_teacher_lesson add publishDate datetime default null ;
alter table tg_teacher_train add publishDate datetime default null ;
alter table tg_teacher_endtext add publishDate datetime default null ;
alter table tg_teacher_train add type varchar(36) default null comment '类型';
alter table tg_record_score_standard add teacherType varchar(10) default NULL;
alter table tg_record_score_standard add scoreMax int(5) default NULL;
alter table tg_record_score_standard add scoreMin int(5) default NULL;


---- 2017-11-16
alter table um_user add wechat varchar(50) default null comment '微信';
alter table bd_student add mobile varchar(20) default null comment '电话号码'

--- 2017-12-11
DROP TABLE IF EXISTS `ex_score_condition_config`;
CREATE TABLE `ex_score_condition_config` (
  `id` int(11) NOT NULL auto_increment ,
  `schoolId` varchar(36) character set utf8 collate utf8_bin default null comment '学校id',
  `type` int(2) DEFAULT NULL COMMENT '类型0-按分，1-按排名',
  `goodRate` int(11) DEFAULT NULL COMMENT '优秀率',
  `passRate` int(11) DEFAULT NULL COMMENT '及格率',
  `lowRate` int(11) DEFAULT NULL COMMENT '低分率',
  `singleScorePart` int(11) DEFAULT NULL COMMENT '单科区间分数段',
  `sumScorePart` int(11) DEFAULT NULL COMMENT '总分区间分数段',
  `validFlag` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `makeTime` datetime DEFAULT NULL,
  `makeUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `modifyUser` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2017-12-14 
DROP FUNCTION IF EXISTS `getAvgGradeScore`;
DELIMITER ;;
CREATE DEFINER=`test`@`%` FUNCTION `getAvgGradeScore`(`pExamId` varchar(50),`pSubjectId` varchar(50)) RETURNS double
BEGIN
    DECLARE gradeAvgScore double;
		select avg(avgDetail.gradeAvgScore) into gradeAvgScore from (
			SELECT  avg(case when sss.score is null then 0 else sss.score end)  gradeAvgScore
			FROM ex_exam_define ed 
			left join ex_student_subject_exam sse on sse.examID = ed.examID 
			left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
			where ed.examID = pExamId and sse.subjectID = 
			(select sse1.subjectID from ex_student_subject_exam sse1 where sse1.subjectExamID = pSubjectId)
			and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' and sse.`status` = 1 group by sse.subjectExamID ) avgDetail;
    RETURN gradeAvgScore ;
  END
;;
DELIMITER ;

--- 2017-12-19修改‘公告管理（管理员）’ 为 ’公告管理‘
update um_menu set  menuName = '公告管理'  where menuId = '5000'
--- 2017-12-19修改‘教学评价’ 为 ’评价查询‘
update um_menu set  menuName = '评价查询'  where menuId = '2563'
alter table um_user add head varchar(50) default null comment '用户头像';
-- 2017-12-20
INSERT INTO um_app (`appId`, `appName`, `introduce`, `appUrl`, `studentUrl`, `teacherUrl`, `icon`, `homeIcon`, `appType`, `type`, `user`, `visit`, `validFlag`) 
VALUES ('34', '新校本资源库', '', 'http://api.xbzyk.com/site/index', NULL,
 NULL, 'img/listIcon/pic_fn18_icon.png', '/img/appIcon/pic-fn17.png', '4', '2', '2,3', '1', 'Y');
-- 2017-12-22
alter table um_user add lastLogin int(2) default null comment '0最后一次登录'

-- 2017-12-25
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295422582', '2', 'LeaveType', '1', '事假', '请假类型');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295472064', '2', 'LeaveType', '2', '病假', '请假类型');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295492788', '2', 'LeaveType', '3', '其他', '请假类型');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295562559', '2', 'LeaveAuditStatus', '1', '已通过', '请假审批状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295605023', '2', 'LeaveAuditStatus', '0', '未审核', '请假审批状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295623845', '2', 'LeaveAuditStatus', '-1', '已拒绝', '请假审批状态');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295562559', '2', 'LeaveAuditStatus', '1', '已通过', '请假审批状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295605023', '2', 'LeaveAuditStatus', '0', '未审核', '请假审批状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1505295623845', '2', 'LeaveAuditStatus', '-1', '已拒绝', '请假审批状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1507536964690', '2', 'AuditStatus', '1', '待审', '审核状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1507536985154', '2', 'AuditStatus', '2', '审核通过', '审核状态');
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) VALUES ('1507537015511', '2', 'AuditStatus', '3', '审核未通过', '审核状态');
-- 2017-12-26
alter table bd_student add candidateNo varchar(50) default null comment '考生号';
-- 2017-12-27 加入candidateNo字段同时刷过该一次就行
update bd_student set candidateNo = studentNo;
-- 2018-1-3
create index code_type_index on sys_code (codeType) ;
create index student_id_index on bd_student_transfer (studentId) ;
alter table bd_school_class add index school_id_index (schoolId);
alter table bd_teacher add index school_id_index (schoolId)
alter table tg_teacher_lesson add index teacher_id_index (teacherId);
alter table tg_class_teach add index teacher_id_index (teacherId);
alter table ta_jobmgr add index teacher_id_index (teacherId);
alter table tg_counseling add index teacher_id_index (teacherId);
alter table tg_testmgr add index teacher_id_index (teacherId);
alter table tg_research_topics add index teacher_id_index (teacherId);
alter table tg_teach_essay add index teacher_id_index (teacherId);
alter table tg_public_class add index teacher_id_index (teacherId);
alter table tg_lecture add index teacher_id_index (teacherId);
alter table tg_sun_class add index teacher_id_index (teacherId);
alter table tg_teach_competition add index teacher_id_index (teacherId);
alter table tg_teacher_thesis add index teacher_id_index (teacherId);
alter table tg_teacher_honorarytitle add index teacher_id_index (teacherId);
alter table tg_teacher_winningrecord add index teacher_id_index (teacherId);
alter table tg_teacher_devmaterials add index teacher_id_index (teacherId);
alter table tg_teacher_train add index teacher_id_index (teacherId);
alter table tg_teacher_endtext add index teacher_id_index (teacherId);
alter table tg_toteacher_evaluate add index teacher_id_index (teacherId);
alter table tg_teacher_achievement add index teacher_id_index (teacherId);
alter table tg_teacher_majorguide add index teacher_id_index (teacherId);
-- 2018-1-5
alter table tg_teacher_work add index teacher_id_index (teacherId);
alter table tg_teacher_award add index teacher_id_index (teacherId);
alter table tg_performance add index teacher_id_index (teacherId);
alter table cms_comment add index teacher_id_index (teacherId);
alter table tg_teacher_grow_info add index teacher_id_index (teacherId);
-- 2018-1-6
alter table sys_message add index user_id_index (user_id);
alter table devel_number add index school_id_index (schoolId);
alter table devel_number add index class_id_index (classId);
alter table devel_class add index class_id_index (classId);

-- 2018-1-9
alter table ex_student_subject_score modify column score double default null ;
alter table eva_instance modify column period varchar(20) default null ;
-- 2018-1-10-23--
alter table ex_student_subject_exam add index school_id_index (schoolID);
alter table ex_student_subject_score add index exam_id_index (examID);
alter table ex_student_subject_score add index student_id_index (studentID);

delimiter;;
create definer=`root`@`%` function `getClassAvgScore`(pExamId varchar(36), pClassId varchar(36), pSchoolId varchar(36)) returns double
begin 
	declare classAvgScore double;
		select avg(sumCredit) into classAvgScore from (select esss1.examID,sum(esss1.score) sumCredit,esse.classID,esse.schoolID
		from ex_student_subject_score esss1
		LEFT JOIN ex_student_subject_exam esse on esss1.subjectExamID = esse.subjectExamID
		where esss1.examID = pExamId and esse.classID = pClassId and esse.schoolID = pSchoolId
		GROUP BY examID,studentID ) aa;
	return classAvgScore;
	end;;
delimiter;

-- 2018-1-12
alter table um_auth_token add index access_token_index (access_token);
alter table devel_grade_statistics add index school_id_index (schoolId);
alter table um_school_app add index school_id_index (schoolId);

-- 2018-1-15
INSERT INTO sys_code VALUES ('1483586972077', '', 'AppType', '4', 'URL同步登录', '应用类型');
-- 2018-1-16
INSERT INTO um_app (`appId`, `appName`, `introduce`, `appUrl`, `studentUrl`, `teacherUrl`, `icon`, `homeIcon`, `appType`, `type`, `user`, `visit`, `validFlag`) VALUES ('35', '数字化校园', '', '/admin/login/synchroLogin', NULL, NULL, 'img/listIcon/pic_fn18_icon.png', '/img/appIcon/pic-fn17.png', '', '4', '2,3', '1', 'Y');
-- 2018-1-17
alter table kq_roll_call_item add index call_id_index (callId);

-- 2018-1-17
alter table um_user add column mobile varchar(20) default null comment '手机号码';

-- 2018-1-22
alter table devel_paruser modify column name varchar(36) default null;
alter table devel_paruser modify column userName varchar(36) default null;
-- 学生综合素质下不必要的个人信息页面展示设置成无效
update devel_menu set validFlag = 'N' where menuId = '2692' or menuName = '个人信息'


-- 2018-1-25
alter table um_user modify column makeUser varchar(36) DEFAULT NULL;
alter table um_user modify column modifyUser varchar(36) DEFAULT NULL;
alter table devel_paruser modify column birthPlace varchar(36);
alter table devel_leav modify column type varchar(2) comment '1 初次未回复，2已回复，3 重新回复后未回复';

-- 2018-2-1 添加函数
delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getGradeNumber`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36)) RETURNS int(11)
begin 
	declare gradeNumber int;
		select count(1) into gradeNumber from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId 
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID) grade;
	return gradeNumber;
end;;
delimiter;

delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getGradeSumAvg`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36)) RETURNS double
begin 
	declare avgScore double;
		select avg(sumScore) into avgScore from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID) grade;
	return avgScore;
end;;
delimiter;

delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getGradeSumMax`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36)) RETURNS double
begin 
	declare maxScore double;
		select max(sumScore) into maxScore from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId  
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) grade ;
	return maxScore;
end;;
delimiter;

delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getGradeSumMin`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36)) RETURNS double
begin 
	declare minScore double;
		select min(sumScore) into minScore from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) grade ;
	return minScore;
end;;
delimiter;

delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getGradeSumNo`(pExamId varchar(36), pSumScore int, pPeriodId varchar(36), pGradeId varchar(36)) RETURNS int(11)
begin 
	declare gradeNo int;
		select count(1) into gradeNo from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId  and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId 
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) classScore where if(isnull(classScore.sumScore),0,classScore.sumScore) > if(isnull(pSumScore ),0,pSumScore );
	return gradeNo;
end;;
delimiter;


-- 2018-2-1 更新数据，去除学段不存在及学段无效的班级 设置为无效的 班级
update bd_school_class bsc
left join bd_period p on p.periodId = bsc.period
set bsc.validFlag = 'N'
where bsc.validFlag = 'Y' and p.periodId is null ;

update bd_school_class bsc
join bd_period p on p.periodId = bsc.period
set bsc.validFlag = 'N'
where bsc.validFlag = 'Y' and p.periodId = 'N' ;

-- 2018-2-5 更新数据，去除学段不存在或学段无效的班级科目对应的班级教师
update bd_class_teacher ct 
join bd_grade_subject gs on gs.gsId = ct.gsId 
left join bd_period p on p.periodId = gs.periodId 
set ct.validFlag = 'N'
where ct.validFlag = 'Y' and p.periodId is null;

update bd_class_teacher ct 
join bd_grade_subject gs on gs.gsId = ct.gsId 
join bd_period p on p.periodId = gs.periodId 
set ct.validFlag = 'N'
where ct.validFlag = 'Y' and p.validFlag = 'N';

-- 2018-2-26 
update um_menu set appId = '04' where menuId = '2534'

-- 2018-2-28
alter table um_app add column appUrlMT varchar(100) comment '移动端app地址' after appUrl;
alter table um_app add column teacherUrlMT varchar(100) comment '移动端教师入口地址' after teacherUrl;
alter table um_app add column studentUrlMT varchar(100) comment '移动端学生地址' after studentUrl;
alter table um_app add column homeIconMT varchar(100) comment '移动端应用首页图标地址' after homeIcon;

-- 2018-3-1
alter table kq_leave add column readFlag int default 0 comment '1代表已读' after auditUser;
alter table bd_class_teacher add index class_id_index(classId);

-- 2018-3-8
alter table um_user add index school_id_index(schoolId);


-- 2018-3-26
alter table ex_exam_class_define add index exam_id_index(examID);
alter table um_role_menu add index role_id_index(roleId);

-- 2018-4-3
alter table um_user add column qqOpenId varchar(32) default null comment "qq对应的openid";
alter table um_user add column qqNickName varchar(32) default null comment "qq昵称";

-- 2018-4-9
drop table if exists `user_third_party_info`;
CREATE TABLE `user_third_party_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(36) NOT NULL,
  `openId` varchar(36) DEFAULT NULL,
  `nickName` varchar(36) DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '关联类型，对应字典表type为relation',
  `validFlag` varchar(2) DEFAULT NULL,
  `makeTime` datetime DEFAULT NULL,
  `makeUser` varchar(36) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `modifyUser` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户第三方信息表';

insert into sys_code VALUES (UNIX_TIMESTAMP(now()), '', 'Relation', '1', 'qq', '用户关联平台');
insert into sys_code VALUES (UNIX_TIMESTAMP(now()), '', 'Relation', '2', 'wechat', '用户关联平台');


-- 2018-4-13 更新数据，去除用户不存在的学生及学生无效 的用户
update bd_student s
left join um_user u on  u.userId = s.userId 
set s.validFlag = 'N'
where s.validFlag = 'Y' and u.userId is null;

update bd_student s 
left join um_user u on u.userId = s.userId
set u.validFlag = 'N'
where s.validFlag = 'N' and u.validFlag = 'Y';


-- 2018-4-19 
drop table if exists `ex_question_type`;
create table `ex_question_type` (
	`id` int not null auto_increment,
	`name` varchar(36) default null comment '题型名',
	`score` int default 0 comment '题型分',
	`little_score` varchar(36) default null comment '小题分,多个以,分隔',
	`grade` varchar(36) default null comment '年级 2-2 初中二年级',
	`syscode_grade` int default null comment '年级 1 指一年级，12 指高三',
	`amount` int default null comment '题数量',
	`num` int default null comment '题号',
	`exam_id` varchar(36) default null comment '考试id',
	`subject_id` varchar(36) default null comment '考试科目id',
	`subject_name` varchar(36) default null comment '考试科目名',
	`valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL, 
	primary key (`id`)
) engine=innodb default charset=utf8 comment='考试题型表';

drop table if exists `ex_question_small`;
create table `ex_question_small` (
	`id` int not null auto_increment,
	`question_id` int not null ,	
	`name` varchar(36) default null comment '小题名',
	`score` int default 0 comment '分',
	`num` int default null comment '题号',
	`valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL, 
	primary key (`id`)
)engine=innodb default charset=utf8 comment='小题表';

drop table if exists `ex_question_student_score`;
create table `ex_question_student_score` (
	`id` int not null auto_increment,
	`question_id` int not null ,
	`question_small_id` int not null comment '小题id',
	`score` double not null comment '小题分',
	`student_id` varchar(36) not null comment '学生id',
	`student_name` varchar(36) not null comment '学生名',
	`class_id` varchar(36) not null comment '班级id',
	`valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL, 
	primary key (`id`)
) engine=innodb default charset=utf8 comment='学生小题分表';

-- 2018-4-20 
drop table if exists `ex_question_score_detail`;
create table `ex_question_score_detail` (
	`id` int not null auto_increment,
	`question_id` int not null ,
	`question_small_id` int not null comment '小题id',
	`class_id` varchar(36) not null,
	`class_avg` double not null comment '班级平均分',
	`grade_avg` double not null comment '年级平均分',
	`class_rate` double not null comment '班级得分率',
	`grade_rate` double not null comment '年级得分率',

	`class_full_num` int not null comment '班满分人数',
	`grade_full_num` int not null comment '年满分人数',
	`class_all_num` int not null comment '班级所有人',
	`grade_all_num` int not null comment '年级所有人',

	`class_full_rate` double not null comment '班满分率',
	`grade_full_rate` double not null comment '年满分率',
	`class_zero_num` int not null comment '班零分人数',
	`grade_zero_num` int not null comment '年零分人数',
	`class_zero_rate` double not null comment '班零分率',
	`grade_zero_rate` double not null comment '年零分率',
	`valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL, 
	primary key (`id`)
) engine=innodb default charset=utf8 comment='班级小题分详情';


-- 2018-5-14 修改函数
DROP FUNCTION IF EXISTS `getAvgGradeScore`;
DELIMITER ;;
CREATE DEFINER=`test`@`%` FUNCTION `getAvgGradeScore`(`pExamId` varchar(50),`pSubjectId` varchar(50),`pGrade` varchar(50)) RETURNS double
BEGIN
    DECLARE gradeAvgScore double;
		select avg(avgDetail.gradeAvgScore) into gradeAvgScore from (
			SELECT  avg(case when sss.score is null then 0 else sss.score end)  gradeAvgScore
			FROM ex_exam_define ed 
			left join ex_student_subject_exam sse on sse.examID = ed.examID 
			left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
			where ed.examID = pExamId and sse.subjectID = 
			(select sse1.subjectID from ex_student_subject_exam sse1 where sse1.subjectExamID = pSubjectId)
			and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' and sss.missFlag != 'Y'
			and if(isnull(pGrade),1=1 , concat(sse.periodID, '-', sse.gradeID) = pGrade) and sse.`status` = 1 group by sse.subjectExamID ) avgDetail;
    RETURN gradeAvgScore ;
  END
;;
DELIMITER ;


-- 2018-5-16 可以不用
alter table bd_class_teacher add index teacher_id_index (teacherId);


--- 2018-5-29  新增类型 单条执行
delete from sys_code where codeType = 'Diploma';
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '1', '中技', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '2', '中专', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '3', '高中', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '4', '大专', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '5', '本科', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '6', '硕士', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '7', '博士', '学历');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'Diploma', '8', 'MBA', '学历');



delete from sys_code where codeType = 'ComputerLevel';

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ComputerLevel', '1', '计算机一级', '计算机等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ComputerLevel', '2', '计算机二级', '计算机等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ComputerLevel', '3', '计算机三级', '计算机等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ComputerLevel', '4', '计算机四级', '计算机等级');



delete from sys_code where codeType = 'ChineseLevel';

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ChineseLevel', '1', '一级甲等', '普通话等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ChineseLevel', '2', '一级乙等', '普通话等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ChineseLevel', '3', '二级甲等', '普通话等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ChineseLevel', '4', '二级乙等', '普通话等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ChineseLevel', '5', '三级甲等', '普通话等级');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (unix_timestamp(), '', 'ChineseLevel', '6', '三级乙等', '普通话等级');



delete from sys_code where codeType = 'EnglishLevel';
INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1483586972706', '', 'EnglishLevel', '1', '大学英语四级', '英语水平');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1483586972706', '', 'EnglishLevel', '2', '大学英语六级', '英语水平');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1483586972706', '', 'EnglishLevel', '3', '专业英语四级', '英语水平');

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1483586972706', '', 'EnglishLevel', '4', '专业英语八级', '英语水平');



-- 2018-6-4 
alter table bd_class_teacher modify column subjectName varchar(16) not null;

-- 2018-6-6 
create table `devel_grade`(
	`id` int not null auto_increment,
	`school_id` varchar(36) not null,
	`grade_type_id` int not null comment 'devel_gradetype主键值',
	`name` varchar(36) not null comment '等级名',
	`min_score` int not null comment '最小分',
	`max_score` int not null comment '最大分',
	`year` int not null comment '学年',
	`term` int not null comment '学年',
	`valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
	primary key(`id`)
) engine=innodb default charset=utf8 comment='学生综合素质评分等级表';


--2018-6-7
alter table bd_teacher add index user_id_index(userId);


-- 2018-6-29 
create table `ribbit_unsend_msg` (
	`id` int not null auto_increment,
	`json` varchar(2000) not null comment '未发送的json格式消息',
	`queue` varchar(36) default null comment '对应发送的队列',
	`exchange` varchar(36) default null comment '对应发送的交换器',
	`type` int not null default 0 comment '类型，0 待处理数据，1 已处理数据',
	`remark` varchar(36) default null comment '备注信息',
	`valid_flag` varchar(2) default null,
	`make_time` datetime default null,
	`make_user` varchar(36) default null,
	`modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
	primary key(`id`)
) engine=innodb default charset=utf8 comment='ribbit未发送消息记录表';


-- 2018-7-2
alter table ex_score_condition_config add unique index `school_index`(schoolId);


-- 2018-7-6
create table rabbit_send_authority(
	id int not null auto_increment,
	app_id int not null comment 'appId',
	queue varchar(36) default null comment '队列',
	exchange varchar(36) default null comment '交换器',
	`valid_flag` varchar(2) default null,
	`make_time` datetime default null,
	`make_user` varchar(36) default null,
	`modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
	primary key (id)
)engine=innodb default charset=utf8 comment='rabbit发送消息权限';

-- 2018-7-9
INSERT INTO `ecampus_new`.`rabbit_send_authority` (`id`, `app_id`, `queue`, `exchange`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('1', '28', 'queue_zuoye', 'schoolClassMsg', 'Y', '2018-07-06 10:44:27', '', NULL, NULL);


-- 2018-7-16 添加函数
delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getSumRanking`(pExamId varchar(36), pSumScore int, pPeriodId varchar(36), pGradeId varchar(36), pClassId varchar(36)) RETURNS int(11)
begin 
	declare gradeNo int;
		select count(1)+1 into gradeNo from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId  and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId 
				and if(isnull(pClassId), 1=1, sse.classId = pClassId)
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) classScore where if(isnull(classScore.sumScore),0,classScore.sumScore) > if(isnull(pSumScore ),0,pSumScore );
	return gradeNo;
end;;
delimiter;

delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getSumAvg`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36), pClassId varchar(36)) RETURNS double
begin 
	declare avgScore double;
		select round(avg(if(isnull(sumScore), 0, sumScore)), 2) into avgScore from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId
				and if(isnull(pClassId), 1=1, sse.classId = pClassId)
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID) grade;
	return avgScore;
end;;
delimiter;


delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getSumMax`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36), pClassId varchar(36)) RETURNS double
begin 
	declare maxScore double;
		select max(sumScore) into maxScore from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId  
				and if(isnull(pClassId), 1=1, sse.classId = pClassId)
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) grade ;
	return maxScore;
end;;
delimiter;

DROP FUNCTION IF EXISTS `getGradeSumAvg`;
delimiter;;
CREATE DEFINER=`test`@`%` FUNCTION `getGradeSumAvg`(pExamId varchar(36), pPeriodId varchar(36), pGradeId varchar(36)) RETURNS double
begin 
	declare avgScore double;
		select round(avg(if(isnull(sumScore), 0, sumScore)), 2) into avgScore from (
				SELECT sum(sss.score) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				where ed.examID = pExamId and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID) grade;
	return avgScore;
end;;
delimiter;

-- 2018-7-26
alter table bd_school modify openMan varchar(36) DEFAULT NULL;


-- 2018-7-27
create table um_partner_user(
	id int not null auto_increment,
	uid int not null comment '合作方uid',
	user_id varchar(36) not null comment '对应数字校园用户',
	school_id int not null comment '学校id',
	remark varchar(36) default null comment '备注',
	`valid_flag` varchar(2) default null,
	`make_time` datetime default null,
	`make_user` varchar(36) default null,
	`modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
	primary key (id)
)engine=innodb default charset=utf8 comment='合作方用户';

INSERT INTO `ecampus_new`.`sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`)
 VALUES (unix_timestamp(), '', 'Subject', '99', '其他', '课程');


-- 2018-8-1-

CREATE TABLE `um_partner_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  school_id varchar(36) not null,
	`secret` varchar(100) NOT NULL COMMENT '伙伴secret',
  `valid_flag` char(1) DEFAULT NULL COMMENT '是否有效',
  `expires_in` int(11) DEFAULT NULL,
  `make_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '合作方认证';


-- 2018-8-2
alter table um_partner_user modify uid varchar(36) NOT NULL COMMENT '合作方uid';

-- 2018-8-6
CREATE TABLE `ex_report_grade` (
  `id` int NOT NULL auto_increment,
  `name` varchar(36) NOT NULL,
	`school_id` varchar(36) NOT NULL,
  `type` int(2) default 1 comment '为0 说名是默认',
  `min` int NOT NULL comment '最小的',
  `max` int NOT NULL  comment '最大的',
  `valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '报表等级设置表';


CREATE TABLE `ex_report_convent` (
  `id` int NOT NULL auto_increment,
	school_id varchar(36) not null,
  `type` int(2) default 1 comment '为0启用 1禁用',
  `valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '报表科目分数折算';

CREATE TABLE `ex_report_convent_subject` (
  `id` int NOT NULL auto_increment,
  `subject_id` varchar(36) default NULL,
	`subject_name` varchar(36) NOT NULL,
	school_id varchar(36) not null,
  `rate` int(2) not null comment '折算率',
  `valid_flag` varchar(2) DEFAULT NULL,
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '报表科目分数折算率表';


-- 2018-8-7 
CREATE TABLE `ex_report_subject_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` varchar(36) DEFAULT NULL COMMENT '考试id',
	`subject_exam_id` varchar(36) DEFAULT NULL COMMENT '考试科目id',
  `class_id` varchar(36) DEFAULT NULL COMMENT '班级id',
  `subject_id` varchar(36) DEFAULT NULL COMMENT '科目ID',
  `subject_name` varchar(36) DEFAULT NULL COMMENT '科目名',
	`exam_num` int(11) DEFAULT NULL COMMENT '实考人数',
	`miss_num` int(11) DEFAULT NULL COMMENT '缺考人数',
	`good_num` int(11) DEFAULT NULL COMMENT '优秀人数',
	`pass_num` int(11) DEFAULT NULL COMMENT '及格人数',
	`low_num` int(11) DEFAULT NULL COMMENT '低分人数',
	`good_rate` double DEFAULT NULL COMMENT '优秀率',
	`pass_rate` double DEFAULT NULL COMMENT '及格率',
	`low_rate` double DEFAULT NULL COMMENT '低分率',
	`class_avg` double DEFAULT NULL COMMENT '班级平均分',
	`grade_avg` double DEFAULT NULL COMMENT '年级平均分',
	`class_min` double DEFAULT NULL COMMENT '班级最低分',
	`class_max` double DEFAULT NULL COMMENT '班级最高分',
	`grade_min` double DEFAULT NULL COMMENT '年级最低分',
	`grade_max` double DEFAULT NULL COMMENT '年级最高分',
	`leave_avg_rate` double DEFAULT NULL COMMENT '离均差=班级平均分-年级平均分',
	`standdard_deviation` double DEFAULT NULL COMMENT '标准差',
  `valid_flag` varchar(2) DEFAULT 'Y',
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  unique key subject_exam_id_index(`subject_exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '班级科目分数报表统计';

-- 给定默认值
INSERT INTO `ecampus_new`.`ex_report_grade` (`id`, `name`, `school_id`, `type`, `min`, `max`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('1', 'A+', '0', '0', '0', '5', 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `ecampus_new`.`ex_report_grade` (`id`, `name`, `school_id`, `type`, `min`, `max`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('2', 'A', '0', '0', '5', '25', 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `ecampus_new`.`ex_report_grade` (`id`, `name`, `school_id`, `type`, `min`, `max`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('3', 'B+', '0', '0', '25', '50', 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `ecampus_new`.`ex_report_grade` (`id`, `name`, `school_id`, `type`, `min`, `max`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('4', 'B', '0', '0', '50', '75', 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `ecampus_new`.`ex_report_grade` (`id`, `name`, `school_id`, `type`, `min`, `max`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('5', 'C+', '0', '0', '75', '95', 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `ecampus_new`.`ex_report_grade` (`id`, `name`, `school_id`, `type`, `min`, `max`, `valid_flag`, `make_time`, `make_user`, `modify_time`, `modify_user`) VALUES ('6', 'C', '0', '0', '95', '100', 'Y', NULL, NULL, NULL, NULL);


-- 添加索引
alter table ex_student_subject_exam add index school_id_index(schoolID);
alter table ex_student_subject_score add index student_id_index(studentID);
alter table ex_student_subject_score add index exam_id_index(examID);
alter table ex_student_subject_exam add index exam_id_index(examId);


-- 2018-8-14
CREATE TABLE `ex_report_all_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` varchar(36) NOT NULL COMMENT '考试id',
  `class_id` varchar(36) DEFAULT NULL COMMENT '班级id',
	`class_name` varchar(36) DEFAULT NULL COMMENT '班级名',
  `exam_num` int(11) DEFAULT NULL COMMENT '实考人数',
  `miss_num` int(11) DEFAULT NULL COMMENT '缺考人数',
  `all_pass_num` int(11) DEFAULT NULL COMMENT '所有科都及格人数',
  `all_pass_rate` double DEFAULT NULL COMMENT '及格率',
  `class_avg` double DEFAULT NULL COMMENT '班级平均分',
  `grade_avg` double DEFAULT NULL COMMENT '年级平均分',
  `class_min` double DEFAULT NULL COMMENT '班级最低分',
  `class_max` double DEFAULT NULL COMMENT '班级最高分',
  `leave_avg_deviation` double DEFAULT NULL COMMENT '离均差',
  `standdard_deviation` double DEFAULT NULL COMMENT '标准差',
	ranking int default null comment '排名',
  `valid_flag` varchar(2) DEFAULT 'Y',
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `exam_class_index` (`exam_id`,`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='班级所有科目分数报表统计';


CREATE TABLE `ex_report_student_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` varchar(36) NOT NULL COMMENT '考试id',
	`student_id` varchar(36) DEFAULT NULL COMMENT '学生id',
	`score` double DEFAULT NULL COMMENT '学生总分',
	`candidate_no` varchar(36) DEFAULT NULL COMMENT '考生号',
  `class_id` varchar(36) DEFAULT NULL COMMENT '班级id',
  `class_name` varchar(36) DEFAULT NULL COMMENT '班级名',
  `class_ranking` int(11) DEFAULT NULL COMMENT '班排名',
	`grade_ranking` int(11) DEFAULT NULL COMMENT '年级排名',
  `valid_flag` varchar(2) DEFAULT 'Y',
  `make_time` datetime DEFAULT NULL,
  `make_user` varchar(36) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `exam_class_index` (`exam_id`,`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试学生总分情况';



-- 2018-8-16
DROP FUNCTION IF EXISTS `getConventSumRanking`;
delimiter;;
create definer=`test`@`%` function getConventSumRanking(pExamId varchar(36), pSumScore double, pPeriodId varchar(36), pGradeId varchar(36), pClassId varchar(36)) RETURNS int(11)
begin 
	declare gradeNo int;
		select count(1)+1 into gradeNo from (
				SELECT sum(if(isnull(rcs.rate), sss.score, sss.score*rcs.rate*0.01)) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				left join ex_report_convent_subject rcs on rcs.school_id = ed.schoolID and rcs.subject_name = sse.subjectID and rcs.valid_flag = 'Y'
				where ed.examID = pExamId  and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId 
				and if(isnull(pClassId), 1=1, sse.classId = pClassId)
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) classScore where if(isnull(classScore.sumScore),0,classScore.sumScore) > if(isnull(pSumScore ),0,pSumScore );
	return gradeNo;
end;;
delimiter;


-- 2018-8-20
alter table ex_report_subject_count add column ranking int default null comment '排名' after standdard_deviation;


-- 2018-8-21
alter table ex_report_convent add unique index school_index(school_id);
alter table um_partner_user add unique index uid_school_index(uid, school_id);



-- lixun 修改表格的创建人与修改人字段长度
alter table bd_school_class modify column makeUser varchar(36);
alter table bd_school_class modify column modifyUser varchar(36);

-- lixun 添加成绩报表页面
INSERT INTO `um_menu` VALUES ('0515', '成绩报表', '0510', '04', 'examAnalysis/teacher/classExport.html', '', '2', 'Y');


-- 2018-8-30
alter table bd_period add index school_id_index(schoolId);
alter table devel_school_stu add index school_id_index(schoolId);
alter table bd_teacher modify teachingGrade varchar(36) DEFAULT NULL COMMENT '教学年级';
alter table bd_teacher modify teachingSubject varchar(36) DEFAULT NULL COMMENT '教学课程';
alter table bd_grade_subject add index period_id_index(periodId);

-- 2018-9-5
DROP FUNCTION IF EXISTS `getConventSumRanking`;
delimiter;;
create definer=`test`@`%` function getConventSumRanking(pExamId varchar(36), pSumScore double, pPeriodId varchar(36), pGradeId varchar(36), pClassId varchar(36)) RETURNS int(11)
begin 
	declare gradeNo int;
		select count(1)+1 into gradeNo from (
				SELECT sum(if(isnull(rcs.rate) or isnull(rc.id) or rc.type = 1, sss.score, sss.score*rcs.rate*0.01)) sumScore 
				FROM ex_exam_define ed 
				left join ex_student_subject_exam sse on sse.examID = ed.examID 
				left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
				left join ex_report_convent rc on rc.school_id = ed.schoolID and rc.valid_flag = 'Y'
				left join ex_report_convent_subject rcs on rcs.school_id = ed.schoolID and rcs.subject_name = sse.subjectID and rcs.valid_flag = 'Y'
				where ed.examID = pExamId  and sse.status = '1' and sse.periodID = pPeriodId and sse.gradeID = pGradeId 
				and if(isnull(pClassId), 1=1, sse.classId = pClassId)
				and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' group by sss.studentID
			) classScore where if(isnull(classScore.sumScore),0,classScore.sumScore) > if(isnull(pSumScore ),0,pSumScore );
	return gradeNo;
end;;
delimiter;

-- 2018-9-10
alter table ex_report_student_count add convent_score double comment '折后总分' after grade_ranking;
alter table ex_report_student_count add convent_subject varchar(100) comment '折算科目（逗号分隔）' after convent_score;
alter table ex_report_student_count add convent_subject_exam_id varchar(500) comment '折算科目subjectexamId（逗号分隔）' after convent_subject;
alter table ex_report_student_count add convent_subject_score varchar(100) comment '折算科目分数（逗号分隔）' after convent_subject_exam_id;
alter table ex_report_student_count add class_subject_ranking varchar(50) comment '班级科目排名' after convent_subject_score;
alter table ex_report_student_count add grade_subject_ranking varchar(50) comment '年级科目排名' after class_subject_ranking;
alter table ex_report_student_count add convent_class_ranking int comment '折算班级排名' after grade_subject_ranking;
alter table ex_report_student_count add convent_grade_ranking int comment '折算年级排名' after convent_class_ranking;
alter table ex_report_student_count add subject_names varchar(100) comment '考试科目名（逗号分隔）' after convent_grade_ranking;
alter table ex_report_student_count add subject_scores varchar(100) comment '考试科目分数（逗号分隔）' after subject_names;



-- 2018-9-14
delete from um_auth_token where access_token in (
select access_token from (select access_token  from um_auth_token group by access_token having count(1) > 1 ) a
) and id not in (
	select id from (select min(id) id  from um_auth_token group by access_token having count(1) > 1 ) b
);
alter table um_auth_token add UNIQUE index access_token_index(access_token);

-- 2018-9-18
alter table um_app add column applyType int(2) default 0 comment '应用类型，0 内部应用， 1 外部应用' after appType;
alter table um_partner_auth add column app_id int not null comment '对应的appId' after school_id; 
alter table um_partner_auth add unique index school_app_id_index(school_id, app_id);

-- 2018-9-30
alter table ex_question_small add index question_id_index(question_id);
alter table ex_question_student_score add index question_small_id_index(question_small_id);

-- 2018-10-16
alter table ex_question_small modify score double DEFAULT '0' COMMENT '分';
alter table ex_question_type modify score double DEFAULT '0' COMMENT '题型分';

-- 2018-10-18
alter table devel_year_statistics add index student_id_index(studentId);
alter table devel_parhtml add index student_id_index(studentId);
alter table devel_parhtml add index school_id_index(schoolId);
alter table devel_year_statistics add index school_id_index(schoolId);

-- 2018-11-2
-- update `devel_parhtml` set time = null ;
alter table devel_parhtml modify time datetime default null comment '时间';
update `devel_parhtml` set time = now() ;


-- 2018-11-14 银联
create table union_product_detail(
	`id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_id` varchar(36) NOT NULL comment '商户号码',
  `order_id` varchar(40) NOT NULL COMMENT '商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	',
	`access_type` varchar(2) NOT NULL comment '接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）',
  `txn_time` varchar(36) NOT NULL COMMENT '订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效',
  `txn_amt` varchar(36) NOT NULL COMMENT '交易金额 单位为分，不能带小数点',
	`currency_code` varchar(10) NOT NULL COMMENT '境内商户固定 156 人民币',
	`sign_method` varchar(2) NOT NULL COMMENT '签名方法',
	`txn_type` varchar(2) NOT NULL COMMENT '交易类型 01:消费',
	`txn_sub_type` varchar(2) NOT NULL COMMENT '交易子类 07：申请消费二维码',
	`biz_type` varchar(10) NOT NULL COMMENT '填写000000',
	`channel_type` varchar(2) NOT NULL COMMENT '渠道类型 08手机',
	`qr_code` varchar(200) DEFAULT NULL COMMENT '二维码支付地址',
	`back_url` varchar(200) NOT NULL COMMENT '异步通知地址',
	`front_url` varchar(200) default NULL COMMENT '前端响应跳转地址',
	`reqReserved` varchar(200) default NULL COMMENT '商户自定义保留域，交易应答时会原样返回（url：如 http:www.baidu.com)',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '银联产品详情表';

create table union_pay(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`query_id` varchar(36) NOT NULL comment '消费交易的流水号，供后续查询用',
  `mer_id` varchar(36) NOT NULL comment '商户号码',
  `order_id` varchar(40) NOT NULL COMMENT '商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	',
  `trace_time` varchar(36) NOT NULL COMMENT 'traceTime，格式为YYYYMMDDhhmmss',
	`status` varchar(2) NOT NULL COMMENT '0 失败， 1 成功',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '银联支付记录';

-- 2018-11-19
create table union_merchant(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`app_id` int NOT NULL comment '应用id（暂时）',
	`number` varchar(36) NOT NULL comment '商户号',
  `make_time` datetime default null COMMENT '创建时间',
	`valid_flag` varchar(2) default 'Y' COMMENT 'Y 有效， N 无效',
	PRIMARY KEY (`id`)
)engine = innodb default charset=utf8 comment '银联商户';


-- 2018-11-16
DROP FUNCTION IF EXISTS `getAvgGradeScore`;
DELIMITER ;;
CREATE DEFINER=`test`@`%` FUNCTION `getAvgGradeScore`(`pExamId` varchar(50),`pSubjectId` varchar(50),`pGrade` varchar(50)) RETURNS double
BEGIN
    DECLARE gradeAvgScore double;
		select avg(avgDetail.gradeAvgScore) into gradeAvgScore from (
			SELECT  avg(case when sss.missFlag = 'Y' then null else if(isnull(sss.score), 0, sss.score) end)  gradeAvgScore
			FROM ex_exam_define ed 
			left join ex_student_subject_exam sse on sse.examID = ed.examID 
			left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
			where ed.examID = pExamId and sse.subjectID = 
			(select sse1.subjectID from ex_student_subject_exam sse1 where sse1.subjectExamID = pSubjectId)
			and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' and sss.missFlag != 'Y'
			and if(isnull(pGrade),1=1 , concat(sse.periodID, '-', sse.gradeID) = pGrade) and sse.`status` = 1 group by sse.subjectExamID ) avgDetail;
    RETURN gradeAvgScore ;
  END
;;
DELIMITER ;



-- 2018-11-21  农业银行二维码支付
create table abc_order(
	`id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_type_id` varchar(36) NOT NULL comment '交易类型 NATIVE：扫码支付， JSAPI：公众号（小程序）支付， APP：app支付， MICROPAY：刷卡支付，MWEB:H5支付',
  `order_date` varchar(10) NOT NULL COMMENT '订单日期 YYYY/MM/DD',
	`order_time` varchar(10) NOT NULL comment '订单时间 必须设定 ，HH:MM:SS',
  `order_timeout_date` varchar(36) default NULL COMMENT '设定订单保存时间 非必须 yyyyMMddHHmmss',
	`expired_date` varchar(10) default NULL COMMENT '设定订单保存时间 非必须 单位（天）',
	`order_no` varchar(36) NOT NULL COMMENT '订单编号 必须设定 （唯一）',
	`currency_code` varchar(10) NOT NULL COMMENT '交易币种 必须设定，156：人民币',
	`order_amount` varchar(10) NOT NULL COMMENT '交易金额 必须设定，保留小数点后两位数字',
	`fee` varchar(10) default NULL COMMENT '手续费金额 非必须 保留小数点后两位数字',
	`account_no` varchar(36) default NULL COMMENT '指定付款账户 交易类型为公众号（小程序）支付 时必填',
	`order_desc` varchar(36) default NULL COMMENT '设定订单说明 支付账户类型为"8:微信支付"时必输',
	`order_url` varchar(200) default NULL COMMENT '设定订单地址',
	`receiver_address` varchar(200) DEFAULT NULL COMMENT '收货地址 非必须',
	`installment_mark` varchar(2) NOT NULL COMMENT '必须设定，1：分期；0：不分期',
	`installment_code` varchar(2) default NULL COMMENT '分期标识为“1”时必须设定',
	`installment_num` varchar(10) default NULL COMMENT '分期标识为“1”时必须设定，0-99',
	`commodity_type` varchar(10) NOT NULL COMMENT '商品种类 必须设定，充值类 0101:支付账户充值 。。。。',
	`buy_ip` varchar(36) NOT NULL COMMENT '客户 IP 非必须',
	`valid_flag` varchar(2) default 'Y' COMMENT 'Y 有效',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '农行订单表';

create table abc_order_detail(
	`id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_mer_name` varchar(36) default NULL comment '二级商户名称 非必须',
  `sub_merId` varchar(36) NOT default COMMENT '二级商户代码 非必须',
	`sub_mer_mcc` varchar(36) default NULL comment '二级商户 MCC 码 非必须',
  `sub_merchant_remarks` varchar(36) default NULL COMMENT '二级商户备注项 非必须',
	`product_id` varchar(36) default NULL COMMENT '商品代码 非必须',
	`order_no` varchar(36) NOT NULL COMMENT '订单号',
	`product_name` varchar(36) NOT NULL COMMENT '商品名称 必须设定',
	`unit_price` varchar(10) default NULL COMMENT '商品总价 非必须',
	`qty` varchar(10) default NULL COMMENT '商品数量 非必须',
	`product_remarks` varchar(36) default NULL COMMENT '商品备注项 非必须',
	`product_type` varchar(36) default NULL COMMENT '商品类型 非必须',
	`product_discount` varchar(2) default NULL COMMENT '商品折扣 非必须',
	`product_expired_date` varchar(36) DEFAULT NULL COMMENT '商品有效期 非必须',
	`make_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '农行订单明细表';

create table abc_order_pay_request(
	`order_no` varchar(36) NOT NULL COMMENT '订单号',
  `payment_type` varchar(36) NOT NULL comment '支付方式 必须设定',
  `payment_link_type` varchar(36) NOT NULL COMMENT '交易渠道',
	`notify_type` varchar(36) NOT NULL comment '通知方式 必须',
  `result_notify_url` varchar(200) default NULL COMMENT '通知 URL 地址 必须设定',
	`merchant_remarks` varchar(200) default NULL COMMENT '附言',
	`h5_scene_type` varchar(36) NOT NULL COMMENT '场景类型',
	`h5_scene_url` varchar(200) NOT NULL COMMENT '场景 URL',
	`h5_scene_name` varchar(36) default NULL COMMENT '场景名称',
	`is_break_account` varchar(2) default '0' COMMENT '交易是否支持向二级商户入账 0 否， 1 是',
	`qr_url` varchar(200) DEFAULT NULL COMMENT '二维码支付地址',
	`make_time` datetime default NULL COMMENT '创建时间'
) engine = innodb default charset=utf8 comment '农行订单支付请求';

create table abc_order_pay(
  `order_no` varchar(36) NOT NULL comment '订单号',
  `amount` varchar(36) NOT NULL COMMENT '订单金额',
	`batch_no` varchar(36) NOT NULL comment '交易批次号',
  `voucher_no` varchar(36) default NULL COMMENT '交易凭证号（建议使用 iRspRef 作为对账依据）',
	`host_date` varchar(36) default NULL COMMENT '银行交易日期（YYYY/MM/DD）',
	`host_time` varchar(36) NOT NULL COMMENT '银行交易时间（HH:MM:SS）',
	`merchant_remarks` varchar(36) NOT NULL COMMENT '商户备注信息（商户在支付请求时所提交的信息）',
	`pay_type` varchar(36) NOT NULL COMMENT '消费者支付方式',
	`notify_type` varchar(36) default NULL COMMENT '支付结果通知方式',
	`i_rsp_ref` varchar(36) default NULL COMMENT '银行返回交易流水号',
	`is_success` varchar(2) default NULL COMMENT '支付成功标识 1 成功， 0 失败',
	`make_time` datetime default NULL COMMENT '创建时间'
) engine = innodb default charset=utf8 comment '农行订单支付';


-----------------------------------------------------------------------------









-- 2018-12-3

create table ex_report_set(
	id int(11) not null auto_increment,
	name varchar(36) not null comment '设置名称',
	school_id varchar(36) not null,
	`type` varchar(5) NOT NULL COMMENT '报表的设置类型 1-1类形式， 1代表单科下的报表， 2 代表总分下的报表',
	min int(2) not null comment '最小范围', 
	max int(2) not null comment '最大范围', 
	status int(2) default 1 comment '状态， 1 启用， 0 不启用', 
	make_time datetime default null,
	make_user varchar(36) default null,
	modify_time datetime default null,
	modify_user varchar(36) default null,
	primary key (id)
) engine=innodb default charset=utf8 comment '报表设置';

-- 默认初始化数据
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('前10%', '0', '1-4', '0', '10', '1', '2018-12-03 11:28:42', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('前20%', '0', '1-4', '0', '20', '1', '2018-12-03 11:29:20', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('前50%', '0', '1-4', '0', '50', '1', '2018-12-03 11:29:48', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('后20%', '0', '1-4', '80', '100', '1', '2018-12-03 11:30:47', NULL, NULL, NULL);

INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('前10%', '0', '2-5', '0', '10', '1', '2018-12-03 11:28:42', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('前20%', '0', '2-5', '0', '20', '1', '2018-12-03 11:29:20', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('前50%', '0', '2-5', '0', '50', '1', '2018-12-03 11:29:48', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('后20%', '0', '2-5', '80', '100', '1', '2018-12-03 11:30:47', NULL, NULL, NULL);


-- 2018-12-7
alter table um_app add column async_url varchar(255) default null comment '应用异步通知地址' after teacherUrlMT;

-- 2018-12-8
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('A+', '0', '1-1', '0', '5', '1', '2018-12-03 11:28:42', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('A', '0', '1-1', '5', '25', '1', '2018-12-03 11:29:20', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('B+', '0', '1-1', '25', '50', '1', '2018-12-03 11:29:48', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('B', '0', '1-1', '50', '75', '1', '2018-12-03 11:30:47', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('C+', '0', '1-1', '75', '95', '1', '2018-12-03 11:30:47', NULL, NULL, NULL);
INSERT INTO `ex_report_set` (`name`, `school_id`, `type`, `min`, `max`, `status`, `make_time`, `make_user`, `modify_time`, `modify_user`) 
VALUES ('C', '0', '1-1', '95', '100', '1', '2018-12-03 11:30:47', NULL, NULL, NULL);


-- 2018-12-8
alter table um_role_menu add index role_id_index(roleId);


-- 2018-12-18
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES (UNIX_TIMESTAMP(), '', 'Status', '3', '休学', '在校状态');


--2018-12-19
DROP FUNCTION IF EXISTS `getAvgGradeScore`;
DELIMITER ;;
CREATE DEFINER=`test`@`%` FUNCTION `getAvgGradeScore`(`pExamId` varchar(50),`pSubjectId` varchar(50),`pGrade` varchar(50)) RETURNS double
BEGIN
    DECLARE gradeAvgScore double;
			SELECT  avg(case when sss.score is null then 0 else sss.score end)  into gradeAvgScore
			FROM ex_exam_define ed 
			left join ex_student_subject_exam sse on sse.examID = ed.examID 
			left join  ex_student_subject_score sss on sss.subjectExamID = sse.subjectExamID 
			where ed.examID = pExamId and sse.subjectID = 
			(select sse1.subjectID from ex_student_subject_exam sse1 where sse1.subjectExamID = pSubjectId)
			and ed.validFlag = 'Y' and sse.validFlag = 'Y' and sss.validFlag = 'Y' and (sss.missFlag = 'N' or sss.missFlag is null)
			and if(isnull(pGrade),1=1 , concat(sse.periodID, '-', sse.gradeID) = pGrade) and sse.`status` = 1;
    RETURN gradeAvgScore ;
  END
;;
DELIMITER ;




-- 2018-12-24
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1545619882', '0', 'ExamType', '1', '单元考', '考试类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1545620010', '0', 'ExamType', '2', '月考', '考试类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1545620018', '0', 'ExamType', '3', '期末考', '考试类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1545620025', '0', 'ExamType', '4', '联考', '考试类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1545620031', '0', 'ExamType', '5', '模拟考', '考试类型');


alter table ex_question_type modify little_score varchar(100) DEFAULT NULL COMMENT '小题分,多个以,分隔';


-- 2018-12-25
alter table ex_exam_define add column platform int(2) default 0 comment '平台标识， 1 指代阅卷对应syscode的exam_platform' after examType;
alter table ex_exam_define add column platformId varchar(36) default null comment '平台对应的考试id' after platform;

-- 2019-1-3
alter table ex_question_type modify little_score varchar(255) DEFAULT NULL COMMENT '小题分,多个以,分隔';

--2019-1-13
update bd_student set status = '1' where status is null or status = '';
alter table bd_student modify status varchar(2) DEFAULT '1' comment '学生状态 对应 sys_code表（status）';

-- 2019-1-19
alter table ex_exam_define add column line_lock int(2) default 0 comment '0 正常，1 是加锁' after validFlag;


--2019-1-22
alter table ex_exam_define add column level int(2) default 1 comment '对应sys_code的exam_level' after examType;
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1548127032', '', 'exam_platform', '0', '自建考试', '分类类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1548127033', '', 'exam_platform', '1', '阅卷考试', '分类类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1548127034', '', 'exam_level', '1', '校级', '分类类型');
INSERT INTO `sys_code` (`codeId`, `schoolId`, `codeType`, `codeValue`, `codeName`, `codeRemark`) 
VALUES ('1548127035', '', 'exam_level', '2', '联盟', '分类类型');

-- 2019-2-21
alter table ex_schoolset_showexam add column type int(2) default 1 comment '1 考试， 2 考试类型' after schoolId;






























