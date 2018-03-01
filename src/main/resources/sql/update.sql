--大美装饰管理平台变更记录
--说明：
--（1）每次只要涉及数据库更新，都需要编写更新脚本，并记录在变更记录中
--（2）标明修改的内容和修改人、时间、脚本等信息

--2017-05-03
--修改人：邹志财(Andy)
--描  述： 请假申请表修改调整
--1:estimated_cost 	    新增字段-预估费用
--执行状态：已执行
ALTER TABLE oa_apply_business_away ADD COLUMN  estimated_cost1  decimal(10,2) DEFAULT 0 COMMENT '预估费用' after days_num;

-- 员工删除权限
-- 执行状态:已执行
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (717,'员工管理-删除','员工管理',4,'emp:delete');

--2017-05-05
--修改人：邹志财(Andy)
--描  述：初始化部门分管副总裁
-- 目前配置的是 4个部门归属陈顺虎管理
-- 执行状态:已执行
INSERT INTO `oa_wf_department_vicepresident` VALUES ('1', '139', '1148');
INSERT INTO `oa_wf_department_vicepresident` VALUES ('2', '128', '1148');
INSERT INTO `oa_wf_department_vicepresident` VALUES ('3', '160', '1148');
INSERT INTO `oa_wf_department_vicepresident` VALUES ('4', '162', '1148');

--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信菜单权限数据
--执行状态：已执行
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (730,'微信管理','微信菜单管理',14,'wechat:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (731,'微信-标签管理','微信菜单管理',14,'wechat:tagmanager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (732,'微信-自定义菜单','微信菜单管理',14,'wechat:custommenu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (733,'微信-个性化菜单','微信菜单管理',14,'wechat:conditionalmenu');

--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信自定义菜单
--1:wechat_conditional_menu 	    新增表
-- 执行状态:已执行
CREATE TABLE `wechat_conditional_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `oid` varchar(20) DEFAULT NULL COMMENT '微信端的id，删除用',
  `tag_id` int(11) DEFAULT NULL COMMENT '关联的标签id,此id为本地系统的id',
  `tag_oid` int(11) DEFAULT NULL COMMENT '关联的标签oid, oid为标签微信端的id',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别：男（1）女（2），不填则不做匹配',
  `country` varchar(40) DEFAULT NULL COMMENT '国家信息，是用户在微信中设置的地区',
  `province` varchar(40) DEFAULT NULL COMMENT '省份信息，是用户在微信中设置的地区，',
  `city` varchar(40) DEFAULT NULL COMMENT '城市信息，是用户在微信中设置的地区',
  `client_platform_type` varchar(1) DEFAULT NULL COMMENT '客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配',
  `language` varchar(20) DEFAULT NULL COMMENT '语言信息，是用户在微信中设置的语言',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='微信个性化菜单';


--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信自定义菜单详情
--1:wechat_conditional_menu_detail 	    新增表
-- 执行状态:已执行
CREATE TABLE `wechat_conditional_menu_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `name` varchar(20) NOT NULL COMMENT '菜单名称',
  `type` varchar(20) NOT NULL COMMENT '菜单类型',
  `level` varchar(20) NOT NULL COMMENT '菜单级别，如果是二级菜单，pid不能为空',
  `click_key` varchar(100) DEFAULT NULL COMMENT 'click等点击类型必须，菜单KEY值，用于消息接口推送，不超过128字节',
  `url` varchar(500) DEFAULT NULL COMMENT 'view、miniprogram类型必须，网页链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。',
  `media_id` int(11) DEFAULT NULL COMMENT 'media_id类型和view_limited类型必须，调用新增永久素材接口返回的合法media_id',
  `pid` int(11) DEFAULT NULL COMMENT '二级菜单所属的一级菜单的id',
  `sort` int(11) DEFAULT '0' COMMENT '排序字段',
  `c_id` int(11) NOT NULL COMMENT '自定义菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='微信自定义菜单';


--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信菜单
--1:wechat_menu 	    新增表
-- 执行状态:已执行
CREATE TABLE `wechat_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `name` varchar(20) NOT NULL COMMENT '菜单名称',
  `type` varchar(20) NOT NULL COMMENT '菜单类型',
  `level` varchar(20) NOT NULL COMMENT '菜单级别，如果是二级菜单，pid不能为空',
  `click_key` varchar(100) DEFAULT NULL COMMENT 'click等点击类型必须，菜单KEY值，用于消息接口推送，不超过128字节',
  `url` varchar(500) DEFAULT NULL COMMENT 'view、miniprogram类型必须，网页链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。',
  `media_id` int(11) DEFAULT NULL COMMENT 'media_id类型和view_limited类型必须，调用新增永久素材接口返回的合法media_id',
  `pid` int(11) DEFAULT NULL COMMENT '二级菜单所属的一级菜单的id',
  `sort` int(11) DEFAULT '0' COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='微信自定义菜单';

--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信标签
--1:wechat_tag 	    新增表
-- 执行状态:已执行
CREATE TABLE `wechat_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '本地系统主键',
  `name` varchar(30) NOT NULL COMMENT '标签名',
  `oid` int(11) NOT NULL COMMENT '微信端标签id',
  `fans_count` int(11) DEFAULT '0' COMMENT '此标签下粉丝数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='微信标签管理表';


--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信标签员工关系
--1:wechat_tag_employee 	    新增表
-- 执行状态:已执行
CREATE TABLE `wechat_tag_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_id` int(11) NOT NULL COMMENT '标签本地id',
  `tag_oid` int(11) NOT NULL COMMENT '标签微信端id',
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `openid` varchar(50) NOT NULL COMMENT '员工的openid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='微信标签和员工关联关系表';


--2017-05-17
--修改人：邹志财(Andy)
--描  述： 生成带参数二维码
--执行状态：执行
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (734,'微信-生成二维码','微信菜单管理',14,'wechat:qrcodemenu');


--2017-05-18
--修改人：郭康龙(Kong)
--描  述： 设计师相关数据表
--执行状态：执行
/*Table structure for table `oa_stylist` */

DROP TABLE IF EXISTS `oa_stylist`;

CREATE TABLE `oa_stylist` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '设计师id',
  `job_no` varchar(50) NOT NULL COMMENT '工号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `minister` varchar(11) DEFAULT NULL COMMENT '工长工号',
  `minister_name` varchar(50) DEFAULT NULL COMMENT '工长姓名',
  `minister_mobile` varchar(15) DEFAULT NULL COMMENT '工长手机号',
  `type` varchar(50) NOT NULL COMMENT '类型： 设计师  部长',
  `department` int(11) DEFAULT NULL COMMENT '创建人所在部门id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_ID` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设计师表';

/*Table structure for table `oa_stylist_bill` */

DROP TABLE IF EXISTS `oa_stylist_bill`;

CREATE TABLE `oa_stylist_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `month_bill_id` int(11) NOT NULL COMMENT '月度提成账单id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `job_no` varchar(50) DEFAULT NULL COMMENT '工号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `bill_month` varchar(7) NOT NULL COMMENT '提成账单月份',
  `total_money` double(7,2) NOT NULL COMMENT '提成总额',
  `status` varchar(20) NOT NULL COMMENT '账单状态 draft:草稿 normal:正常',
  `formula` varchar(400) DEFAULT NULL COMMENT '计算公式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设计师提成账单主表';

/*Table structure for table `oa_stylist_bill_details` */

DROP TABLE IF EXISTS `oa_stylist_bill_details`;

CREATE TABLE `oa_stylist_bill_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bill_id` int(11) NOT NULL COMMENT '账单id',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `contract_name` varchar(200) DEFAULT NULL COMMENT '合同名称',
  `project_money` double(9,2) NOT NULL COMMENT '工程总价',
  `contract_status` varchar(20) NOT NULL COMMENT '合同状态',
  `taxes_money` double(9,2) DEFAULT NULL COMMENT '税金',
  `manager_money` double(9,2) DEFAULT NULL COMMENT '管理费',
  `design_money` double(9,2) DEFAULT NULL COMMENT '设计费',
  `remote_money` double(9,2) DEFAULT NULL COMMENT '远程费',
  `others_money` double(9,2) DEFAULT NULL COMMENT '其他费',
  `privilege_money` double(9,2) DEFAULT NULL COMMENT '费用优惠',
  `bill_money` double(9,2) NOT NULL COMMENT '提成总额',
  `formula` varchar(400) DEFAULT NULL COMMENT '计算公式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设计师提成账单详情表';

/*Table structure for table `oa_stylist_contract` */

DROP TABLE IF EXISTS `oa_stylist_contract`;

CREATE TABLE `oa_stylist_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `emp_id` int(11) NOT NULL COMMENT '设计师id',
  `order_id` varchar(100) NOT NULL COMMENT '大美装饰管理平台订单id',
  `order_no` varchar(100) DEFAULT NULL COMMENT '大美装饰管理平台订单No',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `contract_no` varchar(50) NOT NULL COMMENT '合同编号',
  `money` double(9,2) NOT NULL COMMENT '合同金额',
  `taxes_money` double(9,2) DEFAULT NULL COMMENT '税金',
  `manager_money` double(9,2) DEFAULT NULL COMMENT '管理费',
  `design_money` double(9,2) DEFAULT NULL COMMENT '设计费',
  `remote_money` double(9,2) DEFAULT NULL COMMENT '远程费',
  `others_money` double(9,2) DEFAULT NULL COMMENT '其他费用',
  `privilege_money` double(9,2) DEFAULT NULL COMMENT '优惠费',
  `modify_money` double(9,2) DEFAULT NULL COMMENT '变更后金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设计师提成-合同表';

/*Table structure for table `oa_stylist_contract_status` */

DROP TABLE IF EXISTS `oa_stylist_contract_status`;

CREATE TABLE `oa_stylist_contract_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contract_id` int(11) NOT NULL COMMENT '合同id',
  `status` varchar(50) NOT NULL COMMENT '状态',
  `status_time` datetime DEFAULT NULL COMMENT '合同时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='合同状态子表';

/*Table structure for table `oa_stylist_evaluate` */

DROP TABLE IF EXISTS `oa_stylist_evaluate`;

CREATE TABLE `oa_stylist_evaluate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_no` varchar(50) NOT NULL COMMENT '设计师工号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `evaluate_month` varchar(7) NOT NULL COMMENT '评价月份',
  `score` double(5,2) NOT NULL COMMENT '分数',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设计师评价表';

/*Table structure for table `oa_stylist_monthbill` */

DROP TABLE IF EXISTS `oa_stylist_monthbill`;

CREATE TABLE `oa_stylist_monthbill` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(200) NOT NULL COMMENT '账单标题 设计部4月份提成账单',
  `month` varchar(7) NOT NULL COMMENT '账单月份',
  `total_money` double(7,2) NOT NULL COMMENT '账单总额',
  `status` varchar(20) NOT NULL COMMENT '账单状态',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `oa_stylist_rule` */

DROP TABLE IF EXISTS `oa_stylist_rule`;

CREATE TABLE `oa_stylist_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(30) NOT NULL COMMENT '规则code，对应具体规则枚举的name',
  `name` varchar(100) NOT NULL COMMENT '规则名称',
  `ratio1` double(4,2) NOT NULL COMMENT '比例1（保存的是百分比）',
  `ratio2` double(4,2) DEFAULT NULL COMMENT '比例2（保存的是百分比%）',
  `type` varchar(50) NOT NULL COMMENT '类型（等级比例、间接费用比例、发放比例设置）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `CODE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设计师提成奖励规则设置';



--2017-05-19
--修改人：邹志财(Andy)
--描  述： 考勤管理 - 签到/签退按钮权限
--执行状态：
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1203,'上班/下班','考勤管理',12,'work:sign');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (149,'报表管理','报表管理',11,'report:Menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (151,'报表中心','报表管理',11,'report:reportCenter');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (152,'整体报表-按钮','报表管理',11,'report:reportCenter-wholeReport');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (153,'线索报表-按钮','报表管理',11,'report:reportCenter-clueReport');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (154,'进店报表-按钮','报表管理',11,'report:reportCenter-intoReport');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (155,'小定报表-按钮','报表管理',11,'report:reportCenter-smallReport');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (156,'大定报表-按钮','报表管理',11,'report:reportCenter-bigReport');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (157,'现金收入流水报表-按钮','报表管理',11,'report:reportCenter-cashReport');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (158,'应收报表-按钮','报表管理',11,'report:reportCenter-receivableReport');

--2017-05-23
--修改人：郭康龙（Kong）
--描  述： 考勤管理 - 菜单权限、职场设置编辑、删除按钮权限
--执行状态：
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1200,'考勤管理菜单','考勤管理',12,'work:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1204,'职场设置-编辑','考勤管理',12,'signsite:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1205,'职场设置-删除','考勤管理',12,'signsite:delete');

--2017-05-23
--修改人：刘铎(ark)
--描 述：公告表 - 修改相关字段长度
--执行状态：
ALTER TABLE oa_noticeboard MODIFY COLUMN org_familyCode VARCHAR(3000);
ALTER TABLE oa_noticeboard MODIFY COLUMN org_id VARCHAR(2000);



--2017-06-20
--修改人：邹志财(Andy)
--描  述： 微信用户表新增字段
--1:job_num  新增字段-员工编号
--2:执行更新语句,将员工表的jobNum更新到微信用户的jobNum字段
--执行状态:执行
ALTER TABLE wechat_user ADD COLUMN  job_num VARCHAR(50) COMMENT '员工编号' after user_id;

update wechat_user wu,oa_employee oe set wu.job_num=oe.job_num
where wu.user_id=oe.id;



--2017-07-05
--修改人：邹志财(Andy)
--描  述： 修改员工表字段
--1:data_type 修改为 account_type 
--2:account_source 新增字段
--执行状态: 已执行
alter table oa_employee change data_type account_type VARCHAR(50);
ALTER TABLE oa_employee ADD COLUMN  account_source VARCHAR(50) COMMENT '账号来源' after account_type;


--2017-07-21
--修改人：邹志财(Andy)
--描  述： 修改员工表字段
--1:修改员工号长度 为50
--2:dep_no 新增字段 部门码
--3:org_no 新增字段 集团码
--4:修改微信用户表,修改job_num长度为50
--执行状态: 已执行
ALTER TABLE oa_employee MODIFY COLUMN job_num VARCHAR(50);
ALTER TABLE oa_employee ADD COLUMN  dep_code VARCHAR(10) COMMENT '3位部门码,与集团码联合组成8位员工号码(频繁变动)' after job_num;
ALTER TABLE oa_employee ADD COLUMN  org_code VARCHAR(15) COMMENT '5位集团码,以便以后扩展给予8位长度' after dep_code;
ALTER TABLE wechat_user MODIFY COLUMN job_num VARCHAR(50);

















--2017-08-3
--修改人：邹志财(Andy)
--描  述：
-- 1:删除原流程相关表结构
-- 2:新增流程模板表: oa_wf_approval_template
-- 3:修改流程表: oa_wf_process ,新增"流程性质"字段
-- 4:新增董事长授权表:oa_financail_payment
-- 5:新增系统参数表:oa_system_param
-- 6:新增董事长授权开关参数
--执行状态:
drop TABLE oa_wf_template;
drop table oa_wf_process_distribute;
drop table oa_wf_department_vicepresident;
drop table oa_wf_approve;

CREATE TABLE oa_wf_approval_template (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`node_title`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点标题' ,
`wf_type`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程类型(行政类,人事类等,枚举)' ,
`wf_nature`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程性质(审批,执行)' ,
`approval_type`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批类型(会签,一般)' ,
`approver`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批人' ,
`approval_amount`  int(11) NULL DEFAULT 0 COMMENT '审批金额' ,
`approval_day_num`  int(11) NULL DEFAULT 0 COMMENT '审批天数' ,
`apply_org`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请机构(存放组织机构ID)' ,
`seq`  int(11) NULL DEFAULT 0 COMMENT '权限排序值 按从大到小的倒叙排列' ,
`pid`  int(11) NULL DEFAULT NULL COMMENT '上级节点' ,
`status`  int(1) NULL DEFAULT 1 COMMENT '是否删除 1 未删除  0 已删除 ' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='流程模板表'
AUTO_INCREMENT=351
ROW_FORMAT=COMPACT;

ALTER TABLE oa_wf_process ADD COLUMN  wf_nature VARCHAR(50) COMMENT '流程性质' after node_type;

CREATE TABLE oa_financail_payment (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`total_price`  decimal(10,2) NULL DEFAULT NULL COMMENT '报销总金额' ,
`apply_user`  int(11) NULL DEFAULT NULL COMMENT '申请人' ,
`apply_company`  int(11) NULL DEFAULT NULL COMMENT '申请公司' ,
`apply_type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`budget_month`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预算月份' ,
`apply_date`  datetime NULL DEFAULT NULL COMMENT '申请时间' ,
`invoice_total`  int(11) NULL DEFAULT NULL COMMENT '发票总数' ,
`wf_process_id`  int(11) NULL DEFAULT NULL COMMENT '工作流ID' ,
`form_id`  int(11) NULL DEFAULT NULL COMMENT '表单ID' ,
`wf_process_tittle`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作流标题' ,
`note`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明' ,
`status`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态(董事长授权,待报销,已报销)' ,
`payment_handler`  int(11) NULL DEFAULT NULL COMMENT '报销操作人' ,
`payment_handle_date`  datetime NULL DEFAULT NULL COMMENT '报销操作时间' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='财务报销表'
AUTO_INCREMENT=8
ROW_FORMAT=COMPACT;

CREATE TABLE oa_system_param (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`param_key`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数key值' ,
`param_key_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数key值名称' ,
`param_flag`  tinyint(2) NOT NULL COMMENT '是否为自定义参数（‘1，是‘，’0，否‘）' ,
`param_value`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统参数' ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='系统参数表'
AUTO_INCREMENT=3
CHECKSUM=0
ROW_FORMAT=DYNAMIC
DELAY_KEY_WRITE=0;

INSERT INTO oa_system_param (`id`, `param_key`, `param_key_name`, `param_flag`, `param_value`) VALUES ('1', 'CEOAUTH', '董事长授权开关', '0', 'Y');

--2017-08-4
--修改人：郭康龙（Kong）
--描  述：
-- 1:年度预算、月度预算、报销 添加附件字段
-- 2:报销添加签报单id和是否超预算字段
-- 3:新增签报单模块，添加签报单主表和字表
--执行状态: 

-- 1.年度预算、月度预算、报销 添加附件字段
ALTER TABLE oa_year_budget ADD attachment VARCHAR(100) COMMENT '附件';
ALTER TABLE oa_budget ADD attachment VARCHAR(100) COMMENT '附件';
ALTER TABLE oa_payment ADD attachment VARCHAR(100) COMMENT '附件';

-- 2.报销添加签报单id和是否超预算字段
ALTER TABLE oa_payment ADD signature_id INT COMMENT '签报单id';
ALTER TABLE oa_payment ADD surpass_budget TINYINT COMMENT '是否超预算 0:未超出 1：超出';

-- 3:新增签报单模块，添加签报单主表和字表
CREATE TABLE `oa_payment_signature` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_title` varchar(100) NOT NULL COMMENT '申请标题',
  `apply_code` varchar(50) NOT NULL COMMENT '申请编码',
  `company_id` int(11) NOT NULL COMMENT '申请人公司id',
  `type` varchar(50) NOT NULL COMMENT '科目大类',
  `signature_date` varchar(8) NOT NULL COMMENT '申请月份',
  `total_money` double(9,2) NOT NULL COMMENT '申请总金额',
  `reason` varchar(500) NOT NULL COMMENT '签报事由',
  `status` varchar(50) NOT NULL COMMENT '审批状态:审批中 审批通过 拒绝',
  `surpass_budget` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未超出预算 1：超出预算',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(11) DEFAULT NULL COMMENT '最后更新人',
  `attachment` varchar(100) DEFAULT '' COMMENT '附件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='签报单表';

CREATE TABLE `oa_payment_signature_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `signature_id` int(11) NOT NULL COMMENT '签报单id',
  `cost_item_id` int(11) NOT NULL COMMENT '费用科目id',
  `cost_detail_id` int(11) NOT NULL COMMENT '费用明细id',
  `money` double(9,2) NOT NULL COMMENT '报销金额',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='签报单明细表';

--2017-08-11
--修改人：郭康龙（Kong）
--描  述：
--修改月度预算、年度预算、签报单、报销、采购金额相关的字段为decimal(18,2)类型
--执行状态:
alter table oa_payment modify total_money decimal(18,2) COMMENT '报销总额';
alter table oa_payment_details modify money decimal(18,2) comment '报销金额';
ALTER TABLE oa_payment_signature MODIFY total_money DECIMAL(18,2) COMMENT '签报总额';
ALTER TABLE oa_payment_signature_details MODIFY money DECIMAL(18,2) COMMENT '签报金额';
ALTER TABLE oa_budget MODIFY total_money DECIMAL(18,2) COMMENT '预算总额';
ALTER TABLE oa_budget_details MODIFY money DECIMAL(18,2) COMMENT '预算金额';
ALTER TABLE oa_year_budget MODIFY total_money DECIMAL(18,2) COMMENT '预算总额';
ALTER TABLE oa_year_budget_details MODIFY money DECIMAL(18,2) COMMENT '预算金额';
ALTER TABLE oa_apply_purchase MODIFY good_price DECIMAL(18,2) COMMENT '单价';
ALTER TABLE oa_apply_purchase MODIFY total_price DECIMAL(18,2) COMMENT '总额';


--2017-08-22
--修改人：Tony
--描  述：添加抄送人
--执行状态:
ALTER TABLE oa_wf_approval_template ADD COLUMN `cc_user` varchar(2000) COMMENT '抄送人' AFTER `status`;

--2017-08-22
--修改人：Tony
--描  述：科目流程管理
--执行状态:
CREATE TABLE `oa_subject_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `wf_type` varchar(100) DEFAULT NULL COMMENT '流程类型(行政类,人事类等,枚举)',
  `wf_id` int(11) NOT NULL COMMENT '流程ID',
  `process_type_id` int(11) NOT NULL COMMENT '流程类型',
  `subject_id` int(11) NOT NULL COMMENT '费用科目',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `status` int(11) DEFAULT NULL COMMENT '是否删除 1 未删除  0 已删除 ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

ALTER TABLE oa_subject_process CHANGE COLUMN `status` `status` int(11) DEFAULT 1 COMMENT '是否删除 1 未删除  0 已删除 ';


--2017-08-23
--修改人：Kong
--描  述：签报、报销、请假表添加抄送人字段、抄送人是否可见字段，签报、报销表添加费用小项字段
--执行状态:
alter table oa_payment add cost_item int COMMENT '费用科目';
alter table oa_payment add cc_user varchar(500) COMMENT '抄送人';
alter table oa_payment add cc_user_status tinyint COMMENT '抄送人是否可见：0：不可见 1：可见';
alter table oa_payment_signature add cost_item int COMMENT '费用科目';
alter table oa_payment_signature add cc_user varchar(500) COMMENT '抄送人';
alter table oa_payment_signature add cc_user_status tinyint COMMENT '抄送人是否可见：0：不可见 1：可见';
alter table oa_vacation add cc_user varchar(500) COMMENT '抄送人';
alter table oa_vacation add cc_user_status tinyint COMMENT '抄送人是否可见：0：不可见 1：可见';

-- 流程模板初始化根节点
INSERT INTO oa_wf_approval_template (`id`, `node_title`, `wf_type`, `wf_nature`, `approval_type`, `approver`, `approval_amount`, `approval_day_num`, `apply_org`, `seq`, `pid`, `status`, `cc_user`) VALUES ('1', '流程模板', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '0', '1', NULL);
INSERT INTO oa_wf_approval_template (`id`, `node_title`, `wf_type`, `wf_nature`, `approval_type`, `approver`, `approval_amount`, `approval_day_num`, `apply_org`, `seq`, `pid`, `status`, `cc_user`) VALUES ('2', '请假流程', 'LEAVE', NULL, NULL, NULL, NULL, NULL, NULL, '1', '1', '1', NULL);
INSERT INTO oa_wf_approval_template (`id`, `node_title`, `wf_type`, `wf_nature`, `approval_type`, `approver`, `approval_amount`, `approval_day_num`, `apply_org`, `seq`, `pid`, `status`, `cc_user`) VALUES ('3', '签报流程', 'SIGNATURE', NULL, NULL, NULL, NULL, NULL, NULL, '2', '1', '1', NULL);
INSERT INTO oa_wf_approval_template (`id`, `node_title`, `wf_type`, `wf_nature`, `approval_type`, `approver`, `approval_amount`, `approval_day_num`, `apply_org`, `seq`, `pid`, `status`, `cc_user`) VALUES ('4', '报销流程', 'EXPENSE', NULL, NULL, NULL, '0', '0', NULL, '0', '1', '1', NULL);


-- 新增签到表字段
ALTER table oa_sign add COLUMN out_address VARCHAR(500); '签退地址'
ALTER table oa_sign add COLUMN out_latitude VARCHAR(100); '签退纬度'
ALTER table oa_sign add COLUMN out_longitude VARCHAR(100);'签退经度'
ALTER table oa_sign add COLUMN punch_card_type INT(1);'签到类型'
ALTER table oa_sign add COLUMN out_punch_card_type INT(1);'签退类型'
ALTER table oa_organization add COLUMN sign_time datetime;'部门设置的签到时间'
ALTER table oa_organization add COLUMN signout_time datetime;'部门设置的签退时间'
ALTER table oa_sign add COLUMN sign_type enum('NORMAL','BELATE','LEAVEEARLY','ABSENTEEISM','BELATEANDLEAVEEARLY');'正常，迟到，早退，旷工，迟到并早退'
ALTER TABLE oa_sign add COLUMN creat_time datetime;'创建时间'
ALTER TABLE oa_sign add COLUMN creator VARCHAR(50);'创建人'
ALTER TABLE oa_sign add COLUMN insert_signtype_time datetime;'插入类型时间'

--2017-09-19
--修改人：Paul
--描  述：修改字段为text
--执行状态: 生产未执行
alter table oa_noticeboard modify content text;
