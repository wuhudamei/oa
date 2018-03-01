#2017-10-12 胡期波
ALTER TABLE `oa_payment`   
  ADD COLUMN `cc_user_status` TINYINT(4) NULL COMMENT '抄送人状态' ;
ALTER TABLE `oa_payment_signature`   
  ADD COLUMN `use_status` TINYINT(4) NULL COMMENT '签报单状态，0未使用，1使用中，2使用完' AFTER `cc_user_status`;
ALTER TABLE `oa_payment`   
  ADD COLUMN `payment_signature_id` TINYINT(4) NULL COMMENT '签报单ID' 
ALTER TABLE `oa_payment`   
  ADD COLUMN `cc_user` VARCHAR(200) NULL COMMENT '抄送人ID';
ALTER TABLE `oa_payment`   
  CHANGE `payment_signature_id` `payment_signature_id` INT(10) NULL COMMENT '签报单ID';
ALTER TABLE `oa_payment`   
  ADD COLUMN `attachment` VARCHAR(1000) NULL COMMENT '附件' ;
ALTER TABLE `oa_wf_approval_template`   
  ADD COLUMN `all_path` VARCHAR(2000) NULL COMMENT '流程全路径'


--2017/10/25 17:50
--修改人：Paul
--描  述：组织机构表 新增字段
--执行状态: 未执行
alter table oa_organization add store_flag TINYINT(1) DEFAULT 0 COMMENT '是否为门店标记  仅公司有此设置,其他都默认为否 0:否 1:是';
--2017-10-12 胡期波
ALTER TABLE `oa_payment`   
  ADD COLUMN `cc_user_status` TINYINT(4) NULL COMMENT '抄送人状态' ;
ALTER TABLE `oa_payment_signature`   
  ADD COLUMN `use_status` TINYINT(4) NULL COMMENT '签报单状态，0未使用，1使用中，2使用完' AFTER `cc_user_status`;
ALTER TABLE `oa_payment`   
  ADD COLUMN `payment_signature_id` TINYINT(4) NULL COMMENT '签报单ID' 
ALTER TABLE `oa_payment`   
  ADD COLUMN `cc_user` VARCHAR(200) NULL COMMENT '抄送人ID';
ALTER TABLE `oa_payment`   
  CHANGE `payment_signature_id` `payment_signature_id` INT(10) NULL COMMENT '签报单ID';
ALTER TABLE `oa_payment`   
  ADD COLUMN `attachment` VARCHAR(1000) NULL COMMENT '附件' ;
ALTER TABLE `oa_wf_approval_template`   
  ADD COLUMN `all_path` VARCHAR(2000) NULL COMMENT '流程全路径'
alter table `oa_payment_signature`   
  change `attachment` `attachment` varchar(10000) charset utf8 collate utf8_general_ci null comment '附件';
alter table `oa_payment`   
  change `attachment` `attachment` varchar(10000) charset utf8 collate utf8_general_ci null comment '附件';
alter table `oa_apply_common`   
  add column `last_update` timestamp default current_timestamp on update current_timestamp null;
