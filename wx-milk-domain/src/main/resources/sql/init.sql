-- 添加用户（admin）
INSERT INTO `wx_milk`.`t_user` (`id`, `account`, `name`, `status`, `password`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('1', 'faker', 'faker', '1', '1', 'admin', '2018-06-13 18:30:41', 'admin', '2018-06-13 18:30:41');
-- 角色
INSERT INTO `wx_milk`.`t_role` (`id`, `role_no`, `name`, `status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('1', 'admin', '管理员', '1', 'admin', '2018-06-13 18:31:06', 'admin', '2018-06-13 18:31:06');
-- 用户角色
INSERT INTO `wx_milk`.`t_user_role` (`id`, `account`, `role_no`, `status`) VALUES ('1', 'faker', 'admin', '1');
-- 菜单
INSERT INTO `wx_milk`.`t_menu` (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001', '设置', '0', NULL, 'ww', '1', '1', 'w', '2w', '2018-06-25', '2018-06-25');
INSERT INTO `wx_milk`.`t_menu` (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001001', '用户基本信息', '001', '/user/detail', 'w', '1', '2', '2', '2', '2018-06-11', '2018-06-25');
INSERT INTO `wx_milk`.`t_menu` (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001002', '密码修改', '001', '/user/updatePwd', 'w', '1', '3', 'w', 'w', '2018-06-25', '2018-06-25');
INSERT INTO `wx_milk`.`t_menu` (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001003', '菜单管理', '001', '/menu/index', 'e', '1', '4', 'admin', 'admin', '2018-06-29', '2018-06-29');
INSERT INTO `wx_milk`.`t_menu` (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('002', '报表', '0', NULL, '3', '1', '5', '2', '2', '2018-06-26', '2018-06-26');
INSERT INTO `wx_milk`.`t_menu` (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('002001', '日报', '002', '/report/dayReport', 'e', '1', '6', '5', '5', '2018-06-26', '2018-06-26');

-- 菜单角色
INSERT INTO ``.`` (`role_no`, `menu_id`) VALUES ('admin', '001');
INSERT INTO ``.`` (`role_no`, `menu_id`) VALUES ('admin', '002');
INSERT INTO ``.`` (`role_no`, `menu_id`) VALUES ('admin', '001001');
INSERT INTO ``.`` (`role_no`, `menu_id`) VALUES ('admin', '001002');
INSERT INTO ``.`` (`role_no`, `menu_id`) VALUES ('admin', '002001');
INSERT INTO ``.`` (`role_no`, `menu_id`) VALUES ('admin', '001003');

-- 增删改查字典
INSERT INTO `wx_milk`.`t_dictions` (`id`, `type`, `name`, `dictions_code`) VALUES ('1', '1000', '重置', 'reset');
INSERT INTO `wx_milk`.`t_dictions` (`id`, `type`, `name`, `dictions_code`) VALUES ('2', '1000', '查询', 'select');
INSERT INTO `wx_milk`.`t_dictions` (`id`, `type`, `name`, `dictions_code`) VALUES ('3', '1000', '添加', 'add');
INSERT INTO `wx_milk`.`t_dictions` (`id`, `type`, `name`, `dictions_code`) VALUES ('4', '1000', '修改', 'update');
INSERT INTO `wx_milk`.`t_dictions` (`id`, `type`, `name`, `dictions_code`) VALUES ('5', '1000', '删除', 'delete');

-- 字典权限
INSERT INTO `wx_milk`.`t_role_dictions` (`id`, `dictions_id`, `role_no`) VALUES ('1', '1', 'admin');
INSERT INTO `wx_milk`.`t_role_dictions` (`id`, `dictions_id`, `role_no`) VALUES ('3', '2', 'admin');
INSERT INTO `wx_milk`.`t_role_dictions` (`id`, `dictions_id`, `role_no`) VALUES ('4', '3', 'admin');
INSERT INTO `wx_milk`.`t_role_dictions` (`id`, `dictions_id`, `role_no`) VALUES ('5', '4', 'admin');
INSERT INTO `wx_milk`.`t_role_dictions` (`id`, `dictions_id`, `role_no`) VALUES ('6', '5', 'admin');
