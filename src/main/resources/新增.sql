
---------2017-10-23----新增表 


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

------2017-10-23----新加字段

alter table tg_teacher_lesson add myGrade varchar(20) default null comment '自评等级' after id; 

---- 2017-11-1----
ALTER TABLE tg_teacher_lesson DROP submitTime;

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
-- 2017-12-20
INSERT INTO um_app (`appId`, `appName`, `introduce`, `appUrl`, `studentUrl`, `teacherUrl`, `icon`, `homeIcon`, `appType`, `type`, `user`, `visit`, `validFlag`) 
VALUES ('34', '新校本资源库', '', 'http://api.xbzyk.com/site/index', NULL,
 NULL, 'img/listIcon/pic_fn18_icon.png', '/img/appIcon/pic-fn17.png', '4', '2', '2,3', '1', 'Y');


create index code_type_index on sys_code (codeType) ;
alter table bd_school_class add index school_id_index (schoolId);


-- 2018-1-9
alter table ex_student_subject_score modify column score double default null ;

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

-- 2018-8-21
alter table ex_report_convent add unique index school_index(school_id);
alter table um_partner_user add unique index uid_school_index(uid, school_id);


-- 2018-9-14
delete from um_auth_token where access_token in (
select access_token from (select access_token  from um_auth_token group by access_token having count(1) > 1 ) a
) and id not in (
	select id from (select min(id) id  from um_auth_token group by access_token having count(1) > 1 ) b
);
alter table um_auth_token add UNIQUE index access_token_index(access_token);


-- 2019-6-3  JSON_EXTRACT 提取json中对应的值，json_contains	判断是否包含某个json值
select *
, JSON_CONTAINS(qualifications->'$[*].qualificationTwoLabel', '"甲级"', '$') jsonContains

from (
select id, qualification_info, 
replace(replace(replace(JSON_EXTRACT(qualification_info, '$.qualifications'),'\\', '') ,'"[', '[' ), ']"', ']') qualifications 
from (
select c.id, c.company_name, c.user_id, c.province_id, c.city_id, c.authenticate_status,
			q.qualification_belong, group_concat(q.qualification_type) qualificationTypes,
			(select qualification_info from lz_qualification where user_id = q.user_id and qualification_type = 1) qualification_info, 
			group_concat(if(q.qualification_type != 1 and q.qualification_type != 0, q.qualification_name, null)) qualificationNames,
			group_concat(if(q.qualification_type != 1 and q.qualification_type != 0, null, q.qualification_name)) companyAuthNames,
			group_concat(if(q.qualification_type != 1 and q.qualification_type != 0, null, q.qualification_type)) companyAuthTypes
		from lz_companys c 
			join lz_qualification q on q.user_id = c.user_id
		where c.company_status = 1 and c.authenticate_status = 2 and q.authenticate_status = 2
			and q.qualification_belong = 1 

				and c.company_name like concat('%', 'test-A的企业认证', '%')

			group by c.id
			) detail ) result  where JSON_CONTAINS(qualifications->'$[*].qualificationOne', concat('"', '1', '"'), '$')  
			and JSON_CONTAINS(qualifications->'$[*].qualificationTwo', concat('"', '1', '"'), '$') 
















