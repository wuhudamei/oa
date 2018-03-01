DROP TABLE IF EXISTS `oa_apply_business_away`;
CREATE TABLE `oa_apply_business_away` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_user_id` int(11) NOT NULL COMMENT '申请人id',
  `apply_user_name` varchar(50) NOT NULL COMMENT '申请人名称',
  `apply_title` varchar(255) NOT NULL COMMENT '申请标题',
  `apply_code` varchar(255) NOT NULL COMMENT '申请编码',
  `set_out_address` varchar(100) NOT NULL COMMENT '出发地',
  `address` varchar(100) NOT NULL COMMENT '目的地',
  `begin_time` date NOT NULL COMMENT '开始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  `days_num` int(11) NOT NULL COMMENT '出差天数',
  `reason` varchar(255) DEFAULT NULL COMMENT '出差理由',
  `create_user` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` varchar(25) NOT NULL COMMENT '状态：审核中，通过，拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='OA出差申请';

-- ----------------------------
-- Table structure for oa_apply_purchase
-- ----------------------------
DROP TABLE IF EXISTS `oa_apply_purchase`;
CREATE TABLE `oa_apply_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_title` varchar(255) DEFAULT NULL,
  `apply_code` varchar(255) DEFAULT NULL,
  `first_type_id` int(11) DEFAULT NULL,
  `second_type_id` int(11) DEFAULT NULL,
  `apply_company` int(11) DEFAULT NULL,
  `purchase_month` varchar(10) DEFAULT NULL,
  `good_name` varchar(200) DEFAULT NULL,
  `good_num` int(11) DEFAULT NULL,
  `good_price` decimal(10,2) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `apply_user` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_apply_sequence
-- ----------------------------
DROP TABLE IF EXISTS `oa_apply_sequence`;
CREATE TABLE `oa_apply_sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_date` varchar(50) DEFAULT NULL,
  `apply_type` varchar(50) DEFAULT NULL,
  `current_sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_budget
-- ----------------------------
DROP TABLE IF EXISTS `oa_budget`;
CREATE TABLE `oa_budget` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `apply_title` varchar(100) NOT NULL COMMENT '预算申请标题',
  `apply_code` varchar(50) NOT NULL COMMENT '预算申请code',
  `company_id` int(11) DEFAULT NULL COMMENT '预算部门id',
  `type` varchar(50) NOT NULL COMMENT '科目大类',
  `budget_date` varchar(8) NOT NULL COMMENT '预算时间（yyyy-MM）',
  `total_money` double(9,2) NOT NULL COMMENT '预算总额',
  `status` varchar(30) DEFAULT NULL COMMENT '状态 审批中 审批通过 拒绝',
  `is_year` int(11) NOT NULL DEFAULT '0' COMMENT '是否年度预算 0：否 1：是',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(200) DEFAULT NULL COMMENT '预算说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='预算表';

-- ----------------------------
-- Table structure for oa_budget_details
-- ----------------------------
DROP TABLE IF EXISTS `oa_budget_details`;
CREATE TABLE `oa_budget_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `budget_id` int(11) NOT NULL COMMENT '预算id',
  `cost_item_id` int(11) NOT NULL COMMENT '费用小类id',
  `money` double(9,2) NOT NULL DEFAULT '0.00' COMMENT '预算金额',
  `remark` varchar(200) DEFAULT NULL COMMENT '预算说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=403 DEFAULT CHARSET=utf8 COMMENT='预算明细表';

-- ----------------------------
-- Table structure for oa_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `oa_dictionary`;
CREATE TABLE `oa_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '报销类型名称',
  `parent_code` int(11) NOT NULL COMMENT '父节点id',
  `type` int(11) NOT NULL COMMENT '节点类型 1：一级分类 2：二级分类',
  `sort` int(11) NOT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8 COMMENT='预算报销使用到的一级分类二级分类字典表表';

-- ----------------------------
-- Table structure for oa_employee
-- ----------------------------
DROP TABLE IF EXISTS `oa_employee`;
CREATE TABLE `oa_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `job_num` varchar(20) NOT NULL COMMENT '员工编号',
  `position` varchar(40) DEFAULT NULL COMMENT '职位',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名（账号）',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT '盐值',
  `gender` varchar(20) DEFAULT NULL COMMENT '性别',
  `native_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `id_num` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `census_address` varchar(100) DEFAULT NULL COMMENT '户籍所在地',
  `census_nature` varchar(20) DEFAULT NULL COMMENT '户籍性质',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `politics_status` varchar(20) DEFAULT NULL COMMENT '政治面貌',
  `marital_status` varchar(20) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(20) DEFAULT NULL COMMENT '最高学历',
  `english_level` varchar(20) DEFAULT NULL COMMENT '英语水平',
  `title` varchar(50) DEFAULT NULL COMMENT '职称',
  `family_address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `present_address` varchar(100) DEFAULT NULL COMMENT '现居地',
  `type` varchar(40) DEFAULT NULL COMMENT '用工类型',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `employee_status` varchar(255) DEFAULT NULL COMMENT '员工状态',
  `account_status` varchar(255) DEFAULT NULL COMMENT '账户状态：开启、锁定',
  `data_type` varchar(255) DEFAULT NULL COMMENT '数据类型：员工数据、虚拟账号',
  `linkman_1` varchar(255) DEFAULT NULL COMMENT '紧急联系人1姓名',
  `linkphone_1` varchar(255) DEFAULT NULL COMMENT '紧急联系人1电话',
  `linkman_2` varchar(255) DEFAULT NULL COMMENT '紧急联系人2姓名',
  `linkphone2` varchar(255) DEFAULT NULL COMMENT '紧急联系人2电话',
  `orig_prove` tinyint(1) DEFAULT '0' COMMENT '请提供同原单位解除劳动关系的证明文件',
  `retire_prove` tinyint(1) DEFAULT '0' COMMENT '如在原单位已退休，请提供退休证明文件',
  `no_prove` tinyint(1) DEFAULT '0' COMMENT '若无法提供解除劳动关系的证明文件，请在此声明：若因与原单位未解除劳动关系而产生的法律纠纷和经济补偿一概由本人承担，与本公司无关',
  `birthday` date DEFAULT NULL COMMENT '填表日期',
  `photo` varchar(255) DEFAULT NULL COMMENT '照片',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `dimission_date` date DEFAULT NULL COMMENT '离职日期',
  `create_user` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除表示位：1：已删除 0：未删除',
  PRIMARY KEY (`id`,`create_user`)
) ENGINE=InnoDB AUTO_INCREMENT=656 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_employee_bind
-- ----------------------------
DROP TABLE IF EXISTS `oa_employee_bind`;
CREATE TABLE `oa_employee_bind` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '员工id',
  `oid` varchar(50) NOT NULL COMMENT '其他平台的id',
  `platform` varchar(30) NOT NULL COMMENT '平台',
  `bind_time` datetime NOT NULL COMMENT '绑定时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_employee_contract
-- ----------------------------
DROP TABLE IF EXISTS `oa_employee_contract`;
CREATE TABLE `oa_employee_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) DEFAULT NULL COMMENT '员工id',
  `contract_no` varchar(100) DEFAULT NULL COMMENT '合同编号',
  `first_party` varchar(100) DEFAULT NULL COMMENT '甲方',
  `second_party` varchar(100) DEFAULT NULL COMMENT '乙方',
  `sign_date` date DEFAULT NULL COMMENT '签订日期',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  `try_date` date DEFAULT NULL COMMENT '试用日期',
  `end_date` date DEFAULT NULL COMMENT '终止日期',
  `base_salary` double DEFAULT NULL COMMENT '基本薪资',
  `merit_pay` double DEFAULT NULL COMMENT '绩效工资',
  `other_salary` double DEFAULT NULL COMMENT '其他薪资',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `file_url` varchar(255) DEFAULT NULL COMMENT '附件路径',
  `file_name` varchar(100) DEFAULT NULL COMMENT '附件名称',
  `create_user` int(11) DEFAULT NULL COMMENT '操作人',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `update_user` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='oa系统_员工合同信息表';

-- ----------------------------
-- Table structure for oa_employee_edu
-- ----------------------------
DROP TABLE IF EXISTS `oa_employee_edu`;
CREATE TABLE `oa_employee_edu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `school_name` varchar(50) NOT NULL COMMENT '学校名称',
  `subject` varchar(50) NOT NULL COMMENT '专业',
  `degree` varchar(50) NOT NULL COMMENT '学历',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工教育经历表';

-- ----------------------------
-- Table structure for oa_employee_org
-- ----------------------------
DROP TABLE IF EXISTS `oa_employee_org`;
CREATE TABLE `oa_employee_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `org_department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `org_company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `org_id` int(11) DEFAULT NULL COMMENT '上级节点',
  `type` varchar(20) DEFAULT NULL COMMENT '类型：主要职位、兼职',
  `department_principal` tinyint(1) DEFAULT NULL COMMENT '部门负责人 1-是,0-否',
  `company_principal` tinyint(1) DEFAULT '0' COMMENT '公司负责人1-是,0-否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_employee_work
-- ----------------------------
DROP TABLE IF EXISTS `oa_employee_work`;
CREATE TABLE `oa_employee_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `start_date` date NOT NULL COMMENT '开始时间',
  `end_date` date NOT NULL COMMENT '结束时间',
  `company_name` varchar(50) NOT NULL COMMENT '公司名称',
  `position` varchar(50) NOT NULL COMMENT '职位',
  `duty` varchar(255) NOT NULL COMMENT '主要工作',
  `certifier_name` varchar(20) NOT NULL COMMENT '证明人姓名',
  `certifier_phone` varchar(20) NOT NULL COMMENT '证明人联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工工作经历表';

-- ----------------------------
-- Table structure for oa_inside_message
-- ----------------------------
DROP TABLE IF EXISTS `oa_inside_message`;
CREATE TABLE `oa_inside_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_title` varchar(50) DEFAULT NULL COMMENT '消息标题',
  `message_content` text COMMENT '消息内容',
  `message_type` varchar(50) DEFAULT NULL COMMENT '消息类型',
  `creater` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `expiration_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '过期时间',
  `remind_mode` varchar(50) DEFAULT NULL COMMENT '提醒方式',
  `message_level` tinyint(1) NOT NULL COMMENT '消息重要程度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COMMENT='oa系统_站内信信息';

-- ----------------------------
-- Table structure for oa_inside_message_target
-- ----------------------------
DROP TABLE IF EXISTS `oa_inside_message_target`;
CREATE TABLE `oa_inside_message_target` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL COMMENT '消息id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户账号',
  `status` int(1) DEFAULT NULL COMMENT '状态(0:未读，1:已读)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8 COMMENT='OA系统_站内信_目标用户';

-- ----------------------------
-- Table structure for oa_noticeboard
-- ----------------------------
DROP TABLE IF EXISTS `oa_noticeboard`;
CREATE TABLE `oa_noticeboard` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `org_familyCode` varchar(500) DEFAULT NULL,
  `org_id` varchar(500) DEFAULT NULL COMMENT '接收者(orgId及其下属)',
  `noticestatus` varchar(1) DEFAULT NULL,
  `create_nameId` bigint(20) DEFAULT NULL,
  `create_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=239 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_organization
-- ----------------------------
DROP TABLE IF EXISTS `oa_organization`;
CREATE TABLE `oa_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(20) DEFAULT NULL COMMENT '如:\r\n            北京 bj\r\n            上海:sh\r\n            广州:gz 等',
  `org_name` varchar(100) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `family_code` varchar(100) DEFAULT NULL COMMENT '例: 1-2-3 ',
  `status` int(1) DEFAULT NULL COMMENT '0:无效 \r\n            1:正常',
  `create_date` varchar(20) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_payment
-- ----------------------------
DROP TABLE IF EXISTS `oa_payment`;
CREATE TABLE `oa_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `apply_title` varchar(100) NOT NULL COMMENT '报销申请标题',
  `apply_code` varchar(50) NOT NULL COMMENT '报销申请code',
  `company_id` int(11) NOT NULL COMMENT '公司id',
  `type` varchar(50) NOT NULL COMMENT '科目大类 type:',
  `payment_date` varchar(8) NOT NULL COMMENT '报销时间',
  `total_money` double(9,2) NOT NULL COMMENT '报销金额',
  `invoice_num` int(11) NOT NULL COMMENT '发票总张数',
  `reason` varchar(200) DEFAULT NULL COMMENT '报销事由',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` varchar(30) NOT NULL COMMENT '审批状态： 审批中 审批通过 拒绝',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(11) DEFAULT NULL COMMENT '最后操作人',
  `create_user` int(11) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='分公司报销表';

-- ----------------------------
-- Table structure for oa_payment_details
-- ----------------------------
DROP TABLE IF EXISTS `oa_payment_details`;
CREATE TABLE `oa_payment_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `payment_id` int(11) NOT NULL COMMENT '报销id',
  `cost_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开支日期',
  `cost_item_id` int(11) NOT NULL COMMENT '费用科目id',
  `cost_detail_id` int(11) NOT NULL COMMENT '费用明细id',
  `money` double(9,2) NOT NULL COMMENT '报销金额',
  `invoice_num` int(11) NOT NULL COMMENT '发票张数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='报销明细表';

-- ----------------------------
-- Table structure for oa_permission
-- ----------------------------
DROP TABLE IF EXISTS `oa_permission`;
CREATE TABLE `oa_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `module` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `seq` int(11) NOT NULL COMMENT '排序字段',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for oa_regulation
-- ----------------------------
DROP TABLE IF EXISTS `oa_regulation`;
CREATE TABLE `oa_regulation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `type` int(11) NOT NULL COMMENT '类型',
  `status` varchar(20) NOT NULL COMMENT '状态',
  `file_url` varchar(500) DEFAULT NULL,
  `file_name` varchar(500) DEFAULT NULL,
  `sort` int(11) NOT NULL COMMENT '排序字段',
  `create_user` int(11) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='规章制度';

-- ----------------------------
-- Table structure for oa_regulation_att
-- ----------------------------
DROP TABLE IF EXISTS `oa_regulation_att`;
CREATE TABLE `oa_regulation_att` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `regulation_id` int(11) NOT NULL COMMENT '规章制度id',
  `path` varchar(200) NOT NULL COMMENT '附件地址',
  `name` varchar(200) NOT NULL COMMENT '附件名称',
  `size` varchar(20) NOT NULL COMMENT '附件大小（k）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规章制度附件表';

-- ----------------------------
-- Table structure for oa_role
-- ----------------------------
DROP TABLE IF EXISTS `oa_role`;
CREATE TABLE `oa_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(80) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for oa_role_org
-- ----------------------------
DROP TABLE IF EXISTS `oa_role_org`;
CREATE TABLE `oa_role_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `org_id` int(11) NOT NULL COMMENT '机构id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `oa_role_permission`;
CREATE TABLE `oa_role_permission` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

-- ----------------------------
-- Table structure for oa_user_role
-- ----------------------------
DROP TABLE IF EXISTS `oa_user_role`;
CREATE TABLE `oa_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for oa_vacation
-- ----------------------------
DROP TABLE IF EXISTS `oa_vacation`;
CREATE TABLE `oa_vacation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_title` varchar(100) DEFAULT NULL COMMENT '申请标题',
  `apply_code` varchar(20) NOT NULL COMMENT '申请编号',
  `emp_id` int(11) NOT NULL COMMENT '申请人',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `days` double(11,4) NOT NULL COMMENT '请假天数',
  `reason` varchar(255) NOT NULL COMMENT '请假事由',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片url',
  `status` varchar(255) NOT NULL COMMENT '当前申请状态',
  `create_time` datetime NOT NULL COMMENT '创建时间（申请时间）',
  `apply_type` int(11) NOT NULL COMMENT '请假类型，对应字典表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_wf_approve
-- ----------------------------
DROP TABLE IF EXISTS `oa_wf_approve`;
CREATE TABLE `oa_wf_approve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `approver_id` int(11) DEFAULT NULL COMMENT '审批人ID',
  `type` varchar(50) DEFAULT NULL COMMENT '分公司人事,集团财务,集团人事,集团行政',
  `company_id` int(11) DEFAULT NULL COMMENT '公司',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_wf_process
-- ----------------------------
DROP TABLE IF EXISTS `oa_wf_process`;
CREATE TABLE `oa_wf_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` varchar(20) DEFAULT NULL COMMENT '当前节点ID',
  `node_type` varchar(50) DEFAULT NULL COMMENT '当前节点类型(请假,出差,预算等)',
  `is_sign` int(1) DEFAULT NULL COMMENT '是否需要会签;0否,1是',
  `status` varchar(20) NOT NULL COMMENT '节点状态：未开始、待审核、已审核、无效',
  `type` varchar(20) DEFAULT NULL COMMENT '出差,请假,预算,报销,采购',
  `approver_id` int(11) DEFAULT NULL COMMENT '审批人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_result` varchar(20) DEFAULT NULL COMMENT '审批结果：同意、拒绝',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `form_id` int(11) DEFAULT NULL COMMENT '表单ID',
  `apply_code` varchar(50) DEFAULT NULL,
  `apply_title` varchar(255) DEFAULT NULL,
  `apply_user_id` int(11) DEFAULT NULL,
  `super_node_id` varchar(20) DEFAULT NULL COMMENT '节点ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1096 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_wf_process_distribute
-- ----------------------------
DROP TABLE IF EXISTS `oa_wf_process_distribute`;
CREATE TABLE `oa_wf_process_distribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `distribute_emp_id` int(11) DEFAULT NULL COMMENT '分配人ID',
  `emp_id` int(11) DEFAULT NULL COMMENT '人员ID',
  `form_id` int(11) DEFAULT NULL COMMENT '流程ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_wf_template
-- ----------------------------
DROP TABLE IF EXISTS `oa_wf_template`;
CREATE TABLE `oa_wf_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(50) NOT NULL COMMENT '出差,请假,预算,报销,采购',
  `approve_node` varchar(50) DEFAULT NULL COMMENT '部门负责人,分公司人事,总经理,集团财务,集团行政,集团人事,副总裁,总裁',
  `sort` int(2) DEFAULT NULL COMMENT '审批顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COMMENT='工作流模板表';

-- ----------------------------
-- Table structure for oa_year_budget
-- ----------------------------
DROP TABLE IF EXISTS `oa_year_budget`;
CREATE TABLE `oa_year_budget` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_code` varchar(100) DEFAULT NULL,
  `apply_title` varchar(255) DEFAULT NULL,
  `apply_company` int(11) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `budget_year` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_year_budget_detail
-- ----------------------------
DROP TABLE IF EXISTS `oa_year_budget_detail`;
CREATE TABLE `oa_year_budget_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year_budget_id` int(11) DEFAULT NULL,
  `subject_code` int(11) DEFAULT NULL,
  `january` int(11) DEFAULT NULL,
  `february` int(11) DEFAULT NULL,
  `march` int(11) DEFAULT NULL,
  `april` int(11) DEFAULT NULL,
  `may` int(11) DEFAULT NULL,
  `june` int(11) DEFAULT NULL,
  `july` int(11) DEFAULT NULL,
  `august` int(11) DEFAULT NULL,
  `september` int(11) DEFAULT NULL,
  `october` int(11) DEFAULT NULL,
  `november` int(11) DEFAULT NULL,
  `december` int(11) DEFAULT NULL,
  `budget_year` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=339 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tab` varchar(30) NOT NULL,
  `start_val` int(6) NOT NULL,
  `incr_by` int(6) NOT NULL,
  `cur_val` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uqk_seq_table` (`tab`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_qrcode
-- ----------------------------
DROP TABLE IF EXISTS `wechat_qrcode`;
CREATE TABLE `wechat_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scene_id` int(32) DEFAULT NULL COMMENT '场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）',
  `scene_str` varchar(255) DEFAULT NULL COMMENT '场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段  ',
  `type` varchar(255) DEFAULT NULL COMMENT '二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值',
  `expire_seconds` int(20) DEFAULT NULL COMMENT '该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。',
  `generated` tinyint(1) DEFAULT NULL COMMENT '是否已经生成',
  `ticket` varchar(255) DEFAULT NULL COMMENT '获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。',
  `url` varchar(255) DEFAULT NULL COMMENT '二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `wechat_user`;
CREATE TABLE `wechat_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `subscribe` int(1) DEFAULT NULL COMMENT '是否订阅0-为订阅,1-订阅',
  `openid` varchar(50) NOT NULL COMMENT 'openid',
  `nickname` mediumblob COMMENT '昵称',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `country` varchar(50) DEFAULT NULL COMMENT '国家',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `language` varchar(10) DEFAULT NULL COMMENT '语言',
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '头像',
  `subscribe_time` varchar(30) DEFAULT NULL COMMENT '关注时间戳',
  `unionid` varchar(100) DEFAULT NULL COMMENT '平台编码',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `groupid` int(11) DEFAULT NULL COMMENT '所在组id',
  `type` varchar(20) DEFAULT NULL COMMENT '用户类型：内部用户、外部用户',
  `user_id` int(11) DEFAULT NULL COMMENT '本地系统的用户id',
  `tags` varchar(50) DEFAULT NULL COMMENT '用户标签，以逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='关注的微信用户';

--2017-05-05
--修改人：邹志财(Andy)
--描  述： 部门分管副总裁对应关系(用于工作流)
--1:oa_wf_department_vicepresident 	    新增表
--脚  本：
CREATE TABLE `oa_wf_department_vicepresident` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `org_department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `emp_id` int(11) DEFAULT NULL COMMENT '副总裁id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;



--2017-05-15
--修改人：邹志财(Andy)
--描  述： 微信自定义菜单
--1:wechat_conditional_menu 	    新增表
--脚  本：
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
--脚  本：
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
--脚  本：
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
--脚  本：
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
--脚  本：
CREATE TABLE `wechat_tag_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_id` int(11) NOT NULL COMMENT '标签本地id',
  `tag_oid` int(11) NOT NULL COMMENT '标签微信端id',
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `openid` varchar(50) NOT NULL COMMENT '员工的openid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='微信标签和员工关联关系表';

--2017-08-01
--修改人：郭涛(Paul)
--描  述： 董事长信箱表
--1:oa_chairmanmail 	    新增表
--状态: 生产库未执行!
--脚  本：
DROP TABLE IF EXISTS `oa_chairman_mailbox`;
CREATE TABLE `oa_chairman_mailbox` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(500) NOT NULL COMMENT '信件标题',
  `content` varchar(10000) DEFAULT NULL COMMENT '信件内容',
  `read_status` tinyint(1) DEFAULT NULL COMMENT '阅读状态  0未阅读  1已阅读',
  `anonymous` tinyint(1) DEFAULT NULL COMMENT '是否匿名 0实名 1匿名',
  `important_degree` int(2) DEFAULT NULL COMMENT '重要程度 列表排序',
  `comment` varchar(1000) DEFAULT NULL COMMENT '领导批注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) DEFAULT NULL COMMENT '创建者id',
  `department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;



--2017-10-20
--修改人：刘铎
--描述：订单合同信息表
--order_contract_info    新增表
--脚本
DROP TABLE IF EXISTS `order_contract_info`;
CREATE TABLE `order_contract_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_code` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户姓名',
  `customer_phone` varchar(20) DEFAULT NULL COMMENT '客户手机号',
  `project_address` varchar(500) DEFAULT NULL COMMENT '工程地址',
  `designer_name` varchar(50) DEFAULT NULL COMMENT '设计师',
  `designer_phone` varchar(20) DEFAULT NULL COMMENT '设计师电话',
  `manager_name` varchar(50) DEFAULT NULL COMMENT '项目经理',
  `manager_phone` varchar(20) DEFAULT NULL COMMENT '项目经理电话',
  `inspector_name` varchar(50) DEFAULT NULL COMMENT '质检人员',
  `inspector_phone` varchar(20) DEFAULT NULL COMMENT '质检人员电话',
  `contract_start_date` date DEFAULT NULL COMMENT '合同签订时间',
  `contract_finish_date` date DEFAULT NULL COMMENT '合同计划开工时间',
  `actual_start_date` date DEFAULT NULL COMMENT '实际开工时间',
  `actual_finish_date` date DEFAULT NULL COMMENT '实际竣工时间',
  `contract_total_amount` decimal(18,2) DEFAULT NULL COMMENT '合同总金额',
  `alteration_total_amount` decimal(18,2) DEFAULT NULL COMMENT '拆改总金额',
  `change_total_amount` decimal(18,2) DEFAULT NULL COMMENT '变更总金额',
  `paid_total_amount` decimal(18,2) DEFAULT NULL COMMENT '实收总金额',
  `store_name` varchar(50) DEFAULT NULL COMMENT '门店名称',
  `project_mode` varchar(50) DEFAULT NULL COMMENT '工程模式',
  `data_sources` varchar(50) DEFAULT NULL COMMENT '数据来源',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--2017-10-23
--修改人：刘铎
--描  述： 智装工资
--1:oa_salary_detail 	    新增表
--脚  本：
DROP TABLE IF EXISTS `oa_salary_detail`;
CREATE TABLE `oa_salary_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `oa_employee_org_id` int(11) DEFAULT NULL COMMENT '员工与部门关联表id',
  `credit_card_numbers` varchar(30) DEFAULT NULL COMMENT '银行卡号',
  `bank` varchar(20) DEFAULT NULL COMMENT '银行',
  `bank_of_deposit` varchar(30) DEFAULT NULL COMMENT '开户行',
  `salary_basic` varchar(50) DEFAULT NULL COMMENT '基本工资',
  `salary_performance` varchar(50) DEFAULT NULL COMMENT '绩效工资',
  `salary_basic_total` varchar(50) DEFAULT NULL COMMENT '基本工资合计',
  `deduction_wage` varchar(50) DEFAULT NULL COMMENT '提成金额',
  `salary_total` varchar(50) DEFAULT NULL COMMENT '工资合计',
  `should_work_days` double DEFAULT NULL COMMENT '应出勤天数',
  `practical_work_days` double DEFAULT NULL COMMENT '实际出勤天数',
  `work_coefficient` double DEFAULT NULL COMMENT '出勤系数',
  `sick_leave_salary` varchar(50) DEFAULT NULL COMMENT '病假工资',
  `meal_subsidy` varchar(50) DEFAULT NULL COMMENT '餐补',
  `traffic_subsidy` varchar(50) DEFAULT NULL COMMENT '交通补助',
  `should_basic_salary` varchar(50) DEFAULT NULL COMMENT '应发基本工资',
  `salary_practical_total` varchar(50) DEFAULT NULL COMMENT '实发工资',
  `should_salary_total` varchar(50) DEFAULT NULL COMMENT '应发工资总额',
  `salary_taxable` varchar(50) DEFAULT NULL COMMENT '应税工资',
  `compensation` varchar(50) DEFAULT NULL COMMENT '补偿金',
  `endowment_insurance` varchar(50) DEFAULT NULL COMMENT '养老保险',
  `employment_injury_insurance` varchar(50) DEFAULT NULL COMMENT '工伤保险',
  `medicare` varchar(50) DEFAULT NULL COMMENT '医疗保险',
  `birth_insurance` varchar(50) DEFAULT NULL COMMENT '生育保险',
  `unemployment_insurance` varchar(50) DEFAULT NULL COMMENT '失业保险',
  `housing_fund` varchar(50) DEFAULT NULL COMMENT '住房公积金',
  `social_security_personal` varchar(50) DEFAULT NULL COMMENT '社保个人扣款',
  `deduct_money` varchar(50) DEFAULT NULL COMMENT '扣款',
  `individual_income_tax` varchar(50) DEFAULT NULL COMMENT '个税',
  `social_security_company` varchar(50) DEFAULT NULL COMMENT '社保公司扣款合计',
  `effective_date` date DEFAULT NULL COMMENT '工资生效日期',
  `expiry_date` date DEFAULT NULL COMMENT '工资终止日期',
  `other_subsidy` varchar(50) DEFAULT NULL COMMENT '其他补助',
  `month_salary` date DEFAULT NULL COMMENT '按月份查询工资',
  `update_user` int(11) DEFAULT NULL COMMENT '更改人',
  `update_date` datetime DEFAULT NULL COMMENT '更改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--2018-01-03
--修改人：刘铎
--描  述： 考勤打卡记录
--1:oa_sign_record 	    新增表
--脚  本：
create table oa_sign_record
(
   id                   int(11) not null AUTO_INCREMENT COMMENT 'id',
   employee_id          int(11) DEFAULT NULL COMMENT '员工id',
   sign_date            datetime DEFAULT NULL COMMENT '打卡时间',
   longitude            float(10,6) DEFAULT NULL COMMENT '经度',
   latitude             float(10,6) DEFAULT NULL COMMENT '纬度',
   address              varchar(200) DEFAULT NULL COMMENT '打卡地址',
   punch_card_type      varchar(10) DEFAULT NULL COMMENT '考勤类型',
   sign_type            varchar(50) DEFAULT NULL COMMENT '打卡类型（上班、下班）',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考勤打卡记录';
