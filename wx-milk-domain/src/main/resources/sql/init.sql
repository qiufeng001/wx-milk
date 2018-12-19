-- 添加用户（admin）
drop table if exists t_user;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `account` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `is_online` tinyint(3) DEFAULT '0' COMMENT '是否在线',
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_user (`id`, `account`, `name`, `status`, `password`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('1', 'faker', 'faker', '1', '9+vrsoBB1oM=', 'admin', '2018-06-13 18:30:41', 'admin', '2018-06-13 18:30:41');

-- 角色
drop table if exists t_role;
CREATE TABLE `t_role` (
  `id` varchar(32) NOT NULL,
  `role_no` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_role (`id`, `role_no`, `name`, `status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES ('1', 'admin', '管理员', '1', 'admin', '2018-06-13 18:31:06', 'admin', '2018-06-13 18:31:06');

-- 用户角色
drop table if exists t_user_role;
CREATE TABLE `t_user_role` (
  `id` varchar(32) NOT NULL,
  `account` varchar(64) NOT NULL,
  `role_no` varchar(64) NOT NULL,
  `status` tinyint(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_user_role (`id`, `account`, `role_no`, `status`) VALUES ('1', 'faker', 'admin', '1');

-- 菜单
drop table if exists t_menu;
CREATE TABLE `t_menu` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `url` varchar(64) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `sequence` varchar(10000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_menu (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001', '设置', '0', NULL, 'ww', '1', '1', 'w', '2w', '2018-06-25', '2018-06-25');
INSERT INTO t_menu (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001001', '用户基本信息', '001', '/user/detail', 'w', '1', '2', '2', '2', '2018-06-11', '2018-06-25');
INSERT INTO t_menu (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001002', '密码修改', '001', '/user/updatePwd', 'w', '1', '3', 'w', 'w', '2018-06-25', '2018-06-25');
INSERT INTO t_menu (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('001003', '菜单管理', '001', '/menu/index', 'e', '1', '4', 'admin', 'admin', '2018-06-29', '2018-06-29');
INSERT INTO t_menu (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('002', '报表', '0', NULL, '3', '1', '5', '2', '2', '2018-06-26', '2018-06-26');
INSERT INTO t_menu (`id`, `name`, `pid`, `url`, `img`, `status`, `sequence`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('002001', '日报', '002', '/report/dayReport', 'e', '1', '6', '5', '5', '2018-06-26', '2018-06-26');

-- 菜单角色
drop table if exists t_role_menu;
CREATE TABLE `t_role_menu` (
  `menu_id` varchar(32) NOT NULL,
  `role_no` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_role_menu (`role_no`, `menu_id`) VALUES ('admin', '001');
INSERT INTO t_role_menu (`role_no`, `menu_id`) VALUES ('admin', '002');
INSERT INTO t_role_menu (`role_no`, `menu_id`) VALUES ('admin', '001001');
INSERT INTO t_role_menu (`role_no`, `menu_id`) VALUES ('admin', '001002');
INSERT INTO t_role_menu (`role_no`, `menu_id`) VALUES ('admin', '002001');
INSERT INTO t_role_menu (`role_no`, `menu_id`) VALUES ('admin', '001003');

-- 增删改查字典
drop table if exists t_dictions;
CREATE TABLE `t_dictions` (
  `id` varchar(32) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `dictions_code` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_dictions (`id`, `type`, `name`, `dictions_code`) VALUES ('1', '1000', '重置', 'reset');
INSERT INTO t_dictions (`id`, `type`, `name`, `dictions_code`) VALUES ('2', '1000', '查询', 'select');
INSERT INTO t_dictions (`id`, `type`, `name`, `dictions_code`) VALUES ('3', '1000', '添加', 'add');
INSERT INTO t_dictions (`id`, `type`, `name`, `dictions_code`) VALUES ('4', '1000', '修改', 'update');
INSERT INTO t_dictions (`id`, `type`, `name`, `dictions_code`) VALUES ('5', '1000', '删除', 'delete');

-- 字典权限
drop table if exists t_role_dictions;
CREATE TABLE `t_role_dictions` (
  `id` varchar(32) NOT NULL,
  `dictions_id` varchar(32) NOT NULL,
  `role_no` varchar(32) NOT NULL,
  PRIMARY KEY (`id`,`dictions_id`,`role_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_role_dictions (`id`, `dictions_id`, `role_no`) VALUES ('1', '1', 'admin');
INSERT INTO t_role_dictions (`id`, `dictions_id`, `role_no`) VALUES ('3', '2', 'admin');
INSERT INTO t_role_dictions (`id`, `dictions_id`, `role_no`) VALUES ('4', '3', 'admin');
INSERT INTO t_role_dictions (`id`, `dictions_id`, `role_no`) VALUES ('5', '4', 'admin');
INSERT INTO t_role_dictions (`id`, `dictions_id`, `role_no`) VALUES ('6', '5', 'admin');

-- 系统日志
drop table if exists t_sys_log;
CREATE TABLE `t_sys_log` (
  `id` varchar(32) NOT NULL,
  `operate_user` varchar(500) DEFAULT NULL COMMENT '操作人',
  `exction_method` varchar(255) DEFAULT NULL COMMENT '操作日志',
  `ip` varchar(60) DEFAULT NULL,
  `operator_type` varchar(32) DEFAULT NULL,
  `execute_date` datetime DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `exception_code` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `params` varchar(5000) DEFAULT NULL,
  `exception_detail` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 大区
drop table if exists t_zone;
CREATE TABLE `t_zone` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `number` varchar(32) DEFAULT NULL COMMENT '大区编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='大区';

INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('1', '华东', 'E');
INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('2', '华南', 'S');
INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('3', '华北', 'N');
INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('4', '华中', 'C');
INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('5', '西南', 'SW');
INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('6', '西北', 'NW');
INSERT INTO t_zone (`id`, `name`, `number`) VALUES ('7', '东北', 'EN');

-- 公司
drop table if exists t_company;
CREATE TABLE `t_company` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '公司名称',
  `zone_id` varchar(32) NOT NULL COMMENT '所属大区',
  `address` varchar(255) NOT NULL COMMENT '公司地址',
  `phone_num` varchar(32) DEFAULT NULL COMMENT '公司电话',
  `legal_person` varchar(64) NOT NULL COMMENT '公司法人',
  `legal_person_num` varchar(32) NOT NULL COMMENT '法人电话号码',
  `session_invalid_time` int(11) DEFAULT NULL COMMENT 'session 失效时间（分钟）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_company (`id`, `name`, `zone_id`, `address`, `phone_num`, `legal_person`, `legal_person_num`, `session_invalid_time`) VALUES ('1', '屌丝公司', '1', '屌丝大道1号', '222222', '屌丝', '12122222223', '30');
