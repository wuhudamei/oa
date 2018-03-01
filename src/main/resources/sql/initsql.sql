-- 初始化字典表的预算、报销、采购的科目大类
INSERT INTO oa_dictionary(id,NAME,parent_code,TYPE,sort) VALUES
        (1,'人事类',0,1,1),
        (2,'营销类',0,1,2),
        (3,'行政类',0,1,3),
        (4,'财务类',0,1,4),
        (5,'客管类',0,1,5),
        (6,'其他类',0,1,6);


-- 初始化管理员用户
INSERT INTO oa_employee (ID,NAME,job_num,POSITION,username,PASSWORD,salt,employee_status,account_status) VALUES
(1,'超级管理员','admin','超级管理员','admin','9502be17e6ab9e09c01b2a9a6afaffe7d0c672fc','144c2b5765f6e248','ON_JOB','OPEN');


-- 初始化超级管理员角色
Insert into oa_role (ID,NAME,DESCRIPTION) values (1,'超级管理员','超级管理员');

-- 超级管理员账户和角色关联
Insert into oa_user_role (USER_ID,ROLE_ID) values (1,1);

delete from oa_permission;
-- 所有权限表 id命名为模块编码+权限编码，如：第十一个模块的第十一个权限id为：1111
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(1,'所有权限','embed',0,'*');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(20,'主页','主页',1,'index:list');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(31,'系统管理菜单','系统管理',2,'system:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(32,'组织架构-列表','系统管理',2,'organization:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(33,'组织架构-添加','系统管理',2,'organization:add');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(34,'组织架构-编辑','系统管理',2,'organization:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(35,'组织架构-删除','系统管理',2,'organization:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(40,'数据字典-列表','系统管理',2,'dictionary:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(41,'数据字典-添加','系统管理',2,'dictionary:add');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(42,'数据字典-编辑','系统管理',2,'dictionary:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values(43,'数据字典-删除','系统管理',2,'dictionary:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (50,'角色管理-列表','系统管理',2,'role:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (51,'角色管理-添加','系统管理',2,'role:add');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (52,'角色管理-编辑','系统管理',2,'role:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (53,'角色管理-授权','系统管理',2,'role:auth');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (54,'角色管理-删除','系统管理',2,'role:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (60,'规章制度-菜单','规章制度管理',3,'regulation:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (61,'规章制度-列表','规章制度管理',3,'regulation:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (62,'创建规章制度','规章制度管理',3,'regulation:add');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (64,'规章制度-编辑','规章制度管理',3,'regulation:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (65,'规章制度-删除','规章制度管理',3,'regulation:delete');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (66,'附件下载','规章制度管理',3,'regulation:att:download');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (70,'员工管理-菜单','员工管理',4,'emp:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (71,'员工列表','员工管理',4,'emp:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (72,'员工管理','员工管理',4,'emp:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (73,'员工管理-编辑','员工管理',4,'emp:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (74,'员工管理-兼职','员工管理',4,'emp:part');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (75,'员工管理-设置角色','员工管理',4,'emp:role');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (76,'员工管理-合同','员工管理',4,'emp:contract');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (77,'员工管理-部门负责人','员工管理',4,'emp:department');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (78,'员工管理-公司负责人','员工管理',4,'emp:company');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (79,'员工管理-重置密码','员工管理',4,'emp:resetPw');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (710,'员工管理-离职','员工管理',4,'emp:dismission');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (711,'新增员工','员工管理',4,'emp:new');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (80,'我的审批-菜单','我的审批',5,'approve:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (81,'我的审批-列表','我的审批',5,'approve:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (82,'我的审批-通过','我的审批',5,'approve:access');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (83,'我的审批-拒绝','我的审批',5,'approve:reject');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (90,'我的申请-菜单','我的申请',6,'leave:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (91,'请假申请','我的申请',6,'leave:apply');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (92,'出差申请','我的申请',6,'business:apply');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (93,'我的事务','我的申请',6,'business:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (94,'我的事务-提交','我的申请',6,'business:submit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (95,'我的事务-编辑','我的申请',6,'business:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (96,'我的事务-删除','我的申请',6,'business:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (100,'年度预算管理-菜单','年度预算管理',7,'yearBudget:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (101,'行政类申请','年度预算管理',7,'yearBudget:administrator');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (102,'人事类申请','年度预算管理',7,'yearBudget:matters');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (103,'营销类申请','年度预算管理',7,'yearBudget:marketing');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (104,'客管类申请','年度预算管理',7,'yearBudget:customer');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (105,'其他类申请','年度预算管理',7,'yearBudget:others');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (106,'我的事务','年度预算管理',7,'yearBudget:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (107,'我的事务-提交','年度预算管理',7,'yearBudget:submit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (108,'我的事务-编辑','年度预算管理',7,'yearBudget:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (109,'我的事务-删除','年度预算管理',7,'yearBudget:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (110,'月度预算管理-菜单','月度预算管理',8,'monthBudget:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (111,'行政类申请','月度预算管理',8,'monthBudget:administrator');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (112,'人事类申请','月度预算管理',8,'monthBudget:matters');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (113,'营销类申请','月度预算管理',8,'monthBudget:marketing');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (114,'客管类申请','月度预算管理',8,'monthBudget:customer');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (115,'其他类申请','月度预算管理',8,'monthBudget:others');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (116,'我的事务','月度预算管理',8,'monthBudget:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (117,'我的事务-提交','月度预算管理',8,'monthBudget:submit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (118,'我的事务-编辑','月度预算管理',8,'monthBudget:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (119,'我的事务-删除','月度预算管理',8,'monthBudget:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (120,'月度预算管理-菜单','采购申请管理',9,'purchase:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (121,'行政类申请','采购申请管理',9,'purchase:administrator');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (122,'人事类申请','采购申请管理',9,'purchase:matters');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (123,'营销类申请','采购申请管理',9,'purchase:marketing');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (124,'客管类申请','采购申请管理',9,'purchase:customer');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (125,'其他类申请','采购申请管理',9,'purchase:others');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (126,'我的事务','采购申请管理',9,'purchase:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (127,'我的事务-提交','采购申请管理',9,'purchase:submit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (128,'我的事务-编辑','采购申请管理',9,'purchase:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (129,'我的事务-删除','采购申请管理',9,'purchase:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (130,'费用报销管理-菜单','费用报销管理',10,'payment:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (131,'行政类申请','费用报销管理',10,'payment:administrator');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (132,'人事类申请','费用报销管理',10,'payment:matters');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (133,'营销类申请','费用报销管理',10,'payment:marketing');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (134,'客管类申请','费用报销管理',10,'payment:customer');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (135,'其他类申请','费用报销管理',10,'payment:others');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (136,'我的事务','费用报销管理',10,'payment:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (137,'我的事务-提交','费用报销管理',10,'payment:submit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (138,'我的事务-编辑','费用报销管理',10,'payment:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (139,'我的事务-删除','费用报销管理',10,'payment:delete');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (140,'内容管理-菜单','内容管理',11,'content:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (141,'发布公告通知','内容管理',11,'content:notice:public');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (142,'董事长公告','内容管理',11,'content:manager:notice');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (143,'公告通知列表','内容管理',11,'content:notice:list');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (144,'公告通知-编辑','内容管理',11,'content:notice:edit');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (145,'公告通知-删除','内容管理',11,'content:notice:delete');
-- Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (141,'发微信消息','内容管理',11,'content:wechatMs:message');
-- Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (142,'微信消息列表','内容管理',11,'content:wechatMs:list');

INSERT INTO oa_permission (`id`, `NAME`, `module`, `seq`, `permission`) VALUES (1201, '职场设置', '考勤管理', 12, 'work:signsite');
INSERT INTO oa_permission (`id`, `NAME`, `module`, `seq`, `permission`) VALUES (1202, '考勤统计', '考勤管理', 12, 'work:workstatistics');


Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (150,'极客报表','报表管理',11,'report:geekReport');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (161,'修改密码','修改密码',13,'user:resetPw');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (730,'微信管理','微信菜单管理',14,'wechat:menu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (731,'微信-标签管理','微信菜单管理',14,'wechat:tagmanager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (732,'微信-自定义菜单','微信菜单管理',14,'wechat:custommenu');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (733,'微信-个性化菜单','微信菜单管理',14,'wechat:conditionalmenu');


-- 为管理员角色设置权限
Insert into oa_role_permission (ROLE_ID,PERMISSION_ID) values (1,1);

--2017-05-05
--修改人：邹志财(Andy)
--描  述： 初始化流程审批人
--脚  本：
delete from `oa_wf_approve`;
INSERT INTO `oa_wf_approve` VALUES ('', 'APPROVEMATTERS', '140');
INSERT INTO `oa_wf_approve` VALUES ('', 'APPROVEMATTERS', '1');
INSERT INTO `oa_wf_approve` VALUES ('', 'MARKETING', '1');
INSERT INTO `oa_wf_approve` VALUES ('', 'FINANCE', '1');
INSERT INTO `oa_wf_approve` VALUES ('', 'ADMINISTRATOR', '1');
INSERT INTO `oa_wf_approve` VALUES ('', 'MATTERS', '1');
INSERT INTO `oa_wf_approve` VALUES ('', 'VICEPRESIDENT', '1');
INSERT INTO `oa_wf_approve` VALUES ('', 'CUSTOMER', '1');

--2017-05-05
--修改人：邹志财(Andy)
--描  述：初始化流程模板
--脚  本：
delete from `oa_wf_template`;
INSERT INTO `oa_wf_template` VALUES ('1', 'BUSINESS', 'DEPARTHEAD', '1');
INSERT INTO `oa_wf_template` VALUES ('2', 'BUSINESS', 'APPROVEMATTERS', '2');
INSERT INTO `oa_wf_template` VALUES ('3', 'BUSINESS', 'MANAGER', '3');
INSERT INTO `oa_wf_template` VALUES ('10', 'LEAVE', 'DEPARTHEAD', '1');
INSERT INTO `oa_wf_template` VALUES ('11', 'LEAVE', 'APPROVEMATTERS', '2');
INSERT INTO `oa_wf_template` VALUES ('12', 'LEAVE', 'MANAGER', '3');
INSERT INTO `oa_wf_template` VALUES ('20', 'BUDGETMATTERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('21', 'BUDGETMATTERS', 'MATTERS', '2');
INSERT INTO `oa_wf_template` VALUES ('22', 'BUDGETMATTERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('30', 'BUDGETADMINISTRATOR', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('31', 'BUDGETADMINISTRATOR', 'ADMINISTRATOR', '2');
INSERT INTO `oa_wf_template` VALUES ('32', 'BUDGETADMINISTRATOR', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('40', 'BUDGETMARKETING', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('41', 'BUDGETMARKETING', 'MARKETING', '2');
INSERT INTO `oa_wf_template` VALUES ('42', 'BUDGETMARKETING', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('50', 'BUDGETCUSTOMER', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('51', 'BUDGETCUSTOMER', 'CUSTOMER', '2');
INSERT INTO `oa_wf_template` VALUES ('52', 'BUDGETCUSTOMER', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('60', 'BUDGETOTHERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('61', 'BUDGETOTHERS', 'VICEPRESIDENT', '2');
INSERT INTO `oa_wf_template` VALUES ('62', 'BUDGETOTHERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('70', 'EXPENSEMATTERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('71', 'EXPENSEMATTERS', 'MATTERS', '2');
INSERT INTO `oa_wf_template` VALUES ('72', 'EXPENSEMATTERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('80', 'EXPENSEADMINISTRATOR', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('81', 'EXPENSEADMINISTRATOR', 'ADMINISTRATOR', '2');
INSERT INTO `oa_wf_template` VALUES ('82', 'EXPENSEADMINISTRATOR', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('90', 'EXPENSEMARKETING', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('91', 'EXPENSEMARKETING', 'MARKETING', '2');
INSERT INTO `oa_wf_template` VALUES ('92', 'EXPENSEMARKETING', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('100', 'EXPENSECUSTOMER', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('101', 'EXPENSECUSTOMER', 'CUSTOMER', '2');
INSERT INTO `oa_wf_template` VALUES ('102', 'EXPENSECUSTOMER', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('110', 'EXPENSEOTHERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('111', 'EXPENSEOTHERS', 'VICEPRESIDENT', '2');
INSERT INTO `oa_wf_template` VALUES ('112', 'EXPENSEOTHERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('130', 'YEARBUDGETMATTERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('131', 'YEARBUDGETMATTERS', 'MATTERS', '2');
INSERT INTO `oa_wf_template` VALUES ('132', 'YEARBUDGETMATTERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('140', 'YEARBUDGETADMINISTRATOR', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('141', 'YEARBUDGETADMINISTRATOR', 'ADMINISTRATOR', '2');
INSERT INTO `oa_wf_template` VALUES ('142', 'YEARBUDGETADMINISTRATOR', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('150', 'YEARBUDGETMARKETING', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('151', 'YEARBUDGETMARKETING', 'MARKETING', '2');
INSERT INTO `oa_wf_template` VALUES ('152', 'YEARBUDGETMARKETING', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('160', 'YEARBUDGETCUSTOMER', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('161', 'YEARBUDGETCUSTOMER', 'CUSTOMER', '2');
INSERT INTO `oa_wf_template` VALUES ('162', 'YEARBUDGETCUSTOMER', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('170', 'YEARBUDGETOTHERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('171', 'YEARBUDGETOTHERS', 'VICEPRESIDENT', '2');
INSERT INTO `oa_wf_template` VALUES ('172', 'YEARBUDGETOTHERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('180', 'PURCHASEADMINISTRATOR', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('181', 'PURCHASEADMINISTRATOR', 'ADMINISTRATOR', '2');
INSERT INTO `oa_wf_template` VALUES ('182', 'PURCHASEADMINISTRATOR', 'VICEPRESIDENT', '3');
INSERT INTO `oa_wf_template` VALUES ('183', 'PURCHASEMATTERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('184', 'PURCHASEMATTERS', 'MATTERS', '2');
INSERT INTO `oa_wf_template` VALUES ('185', 'PURCHASEMATTERS', 'VICEPRESIDENT', '3');
INSERT INTO `oa_wf_template` VALUES ('186', 'PURCHASEMARKETING', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('187', 'PURCHASEMARKETING', 'MARKETING', '2');
INSERT INTO `oa_wf_template` VALUES ('188', 'PURCHASEMARKETING', 'VICEPRESIDENT', '3');
INSERT INTO `oa_wf_template` VALUES ('189', 'PURCHASECUSTOMER', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('190', 'PURCHASECUSTOMER', 'CUSTOMER', '2');
INSERT INTO `oa_wf_template` VALUES ('191', 'PURCHASECUSTOMER', 'VICEPRESIDENT', '3');
INSERT INTO `oa_wf_template` VALUES ('192', 'PURCHASEOTHERS', 'MANAGER', '1');
INSERT INTO `oa_wf_template` VALUES ('193', 'PURCHASEOTHERS', 'VICEPRESIDENT', '2');
INSERT INTO `oa_wf_template` VALUES ('194', 'PURCHASEOTHERS', 'FINANCE', '3');
INSERT INTO `oa_wf_template` VALUES ('195', 'SPECIALLEAVE', 'DEPARTHEAD', '1');
INSERT INTO `oa_wf_template` VALUES ('196', 'SPECIALLEAVE', 'VICEPRESIDENT', '2');
INSERT INTO `oa_wf_template` VALUES ('197', 'SPECIALLEAVE', 'APPROVEMATTERS', '3');
INSERT INTO `oa_wf_template` VALUES ('198', 'SPECIALBUSINESS', 'DEPARTHEAD', '1');
INSERT INTO `oa_wf_template` VALUES ('199', 'SPECIALBUSINESS', 'VICEPRESIDENT', '2');
INSERT INTO `oa_wf_template` VALUES ('200', 'SPECIALBUSINESS', 'APPROVEMATTERS', '3');

--2017-05-18
--修改人：郭康龙(Kong)
--描  述： 初始化设计师相关权限 Id规范 模块编号（两位）+ 该模块权限编号（两位） 如：第17个模块 的第一个权限id为：1700
--脚  本：
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1700,'设计师提成管理','设计师提成管理',15,'stylist:menu');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1701,'规则设置','设计师提成管理',15,'stylist:rules');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1702,'设计师管理','设计师提成管理',15,'stylist:manager');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1703,'设计师管理-新增','设计师提成管理',15,'stylist:manager-new');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1704,'设计师管理-删除','设计师提成管理',15,'stylist:manager-del');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1705,'考核管理','设计师提成管理',15,'stylist:assessment');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1706,'考核管理-添加','设计师提成管理',15,'stylist:assessment-new');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1707,'考核管理-模板下载','设计师提成管理',15,'stylist:assessment-template');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1708,'考核管理-批量导入','设计师提成管理',15,'stylist:assessment-batchAdd');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1709,'考核管理-删除','设计师提成管理',15,'stylist:assessment-del');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1710,'合同确认','设计师提成管理',15,'stylist:contract');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1711,'合同确认-同步','设计师提成管理',15,'stylist:contract-sync');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1712,'合同确认-编辑','设计师提成管理',15,'stylist:contract-edit');

Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1713,'提成账单','设计师提成管理',15,'stylist:bill');
Insert into oa_permission(ID,NAME,MODULE,SEQ,PERMISSION) values (1714,'提成账单-生成账单','设计师提成管理',15,'stylist:bill-generate');

