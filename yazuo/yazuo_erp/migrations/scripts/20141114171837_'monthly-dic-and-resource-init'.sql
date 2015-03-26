-- // 'monthly-dic-and-resource-init'
-- Migration SQL that makes the change goes here.


-- resource
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (133, 74, '006004', '006', '1', '我的主页', '', 4, '1', 'my_home_page_133', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (134, 74, '006005', '006', '1', '业务分配', '', 5, '1', 'b_assign_134', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (135, 74, '006006', '006', '1', '话术管理', '', 6, '1', 'tel_skills_mag_135', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (136, 74, '006007', '006', '1', '业务处理', '', 7, '1', 'b_handle_136', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (137, 133, '006004001', '006004', '2', '未指派', '', 1, '1', 'unassigned_137', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (138, 133, '006004002', '006004', '2', '未完成', '', 2, '1', 'incomplete_138', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (139, 133, '006004003', '006004', '2', '已完成', '', 3, '1', 'complete_139', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (140, 133, '006004004', '006004', '2', '创建需求', '', 4, '1', 'create_reqs_140', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (141, 137, '006004001001', '006004001', '2', '指派', '', 1, '1', 'assign_141', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (142, 137, '006004001001', '006004001', '2', '批量指派', '', 2, '1', 'batch_assign_142', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (143, 134, '006005001', '006005', '2', '修改', '', 1, '1', 'modify_143', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (144, 134, '006005002', '006005', '2', '批量修改', '', 2, '1', 'batch_modify_144', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (145, 135, '006006001', '006006', '2', '添加话术', '', 1, '1', 'add_tel_skills_145', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (146, 135, '006006002', '006006', '2', '修改', '', 2, '1', 'modify_146', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (147, 135, '006006003', '006006', '2', '删除', '', 3, '1', 'delete_147', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (148, 136, '006007001', '006007', '2', '营销活动', '', 1, '1', 'marketing_act_148', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (149, 136, '006007002', '006007', '2', '微信', '', 2, '1', 'weixin_149', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (150, 136, '006007003', '006007', '2', '规则', '', 3, '1', 'rule_150', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (151, 136, '006007004', '006007', '2', '回访', '', 4, '1', 're_visite_151', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (152, 136, '006007005', '006007', '2', '续卡', '', 5, '1', 'lay_over_card_152', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (153, 136, '006007006', '006007', '2', '续短信', '', 6, '1', 'lay_over_SMS_153', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (154, 136, '006007007', '006007', '2', '新需求', '', 7, '1', 'new_reqs_154', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (155, 136, '006007008', '006007', '2', '数据提取', '', 8, '1', 'data_extraction_155', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (156, 136, '006007009', '006007', '2', '投诉', '', 9, '1', 'compliant_156', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (157, 136, '006007010', '006007', '2', '培训', '', 10, '1', 'train_157', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (158, 136, '006007011', '006007', '2', '设备配送', '', 11, '1', 'delivery_158', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (159, 148, '006007001001', '006007001', '2', '微信', '', 1, '1', 'weixin_159', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (160, 148, '006007001002', '006007001', '2', '其他', '', 2, '1', 'others_160', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (161, 151, '006007004001', '006007004', '2', '月报回访', '', 1, '1', 'monthly_rpt_161', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (162, 151, '006007004002', '006007004', '2', '培训回访', '', 2, '1', 'train_reviste_162', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (163, 151, '006007004003', '006007004', '2', '上线回访', '', 3, '1', 'onlin_reviste_163', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (164, 74, '006008', '006', '1', '管理统计', '', 4, '1', 'mag_statistics_164', 1, now(), 1, now());
INSERT INTO sys.sys_resource (id, parent_id, tree_code, parent_tree_code, resource_type, resource_name, resource_url, sort, is_visible, remark, insert_by, insert_time, update_by, update_time) VALUES (165, 164, '006008001', '006008', '2', '月报讲解统计', '', 1, '1', 'rpt_teach_statics_165', 1, now(), 1, now());


--dictionary

INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000102','后端需求联系人类型','1','商户','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000102','后端需求联系人类型','2','前端','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000103','后端需求来源分类','1','商户','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000103','后端需求来源分类','2','前端','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000103','后端需求来源分类','3','销售','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000103','后端需求来源分类','4','代理','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000104','后端需求来源','1','400','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000104','后端需求来源','2','QQ','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000104','后端需求来源','3','ERP','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000104','后端需求来源','4','微信','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000104','后端需求来源','5','邮件','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000104','后端需求来源','6','传真','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000105','处理人','1','无','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000105','处理人','2','自己','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000105','处理人','3','指派给','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','1','占线','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','2','对方挂机','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','3','无人接听','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','4','不在服务器','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','5','关机','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','6','已停机','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000106','回访电话接听状态','7','号码错误','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000107','评估要素','1','满意度','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000107','评估要素','2','回访','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000107','评估要素','3','发送','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000107','评估要素','4','讲解','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000107','评估要素','5','合格','',1,'2014-5-27',1,'2014-5-27');

INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000108','评估要素满意度','1','非常满意（80-100分）','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000108','评估要素满意度','2','满意（60-80分）','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000108','评估要素满意度','3','不满意（0-60分）','',1,'2014-5-27',1,'2014-5-27');


INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000109','评估要素回访','1','已回访','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000109','评估要素回访','2','未回访','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000109','评估要素回访','3','放弃回访','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000110','评估要素发送','1','正常','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000110','评估要素发送','2','延迟','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000110','评估要素发送','3','未发','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000111','评估要素讲解','1','已讲解','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000111','评估要素讲解','2','延迟讲解','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000111','评估要素讲解','3','未讲解','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000112','评估要素合格','1','合格','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000112','评估要素合格','2','不合格','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000113','后端服务需求状态','1','未指派','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000113','后端服务需求状态','2','未完成','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000113','后端服务需求状态','3','已完成','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000077','操作日志类型','17','月报[工作计划]','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000077','操作日志类型','18','月报[需求指派]','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000077','操作日志类型','19','月报[回访]','',1,'2014-5-27',1,'2014-5-27');
INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000077','操作日志类型','20','月报[完成]','',1,'2014-5-27',1,'2014-5-27');

INSERT into sys.sys_dictionary(dictionary_type,dictionary_name,dictionary_key,dictionary_value,remark,insert_by,insert_time,update_by,update_time) VALUES ('00000057','提醒事项类型','07','回访反馈','',1,'2014-5-27',1,'2014-5-27');

-- //@UNDO
-- SQL to undo the change goes here.


delete from sys.sys_dictionary where dictionary_type in ('00000102','00000103','00000104','00000105','00000106','00000107','00000108','00000109','00000110','00000111','00000112','00000113');

delete from  sys.sys_resource where  id>=  133  and id <=165;

