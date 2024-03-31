DROP TABLE IF EXISTS `aii_music_user`;
CREATE TABLE `aii_music_user` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
	`nickname` varchar(20) NOT NULL COMMENT '昵称',
	`password` varchar(255) NOT NULL COMMENT '密码',
	`email_address` varchar(255) COMMENT '电子邮件地址',
	`phone_number` varchar(20) COMMENT '电话号码',
	`avatar_url` varchar(255) COMMENT '用户头像URL',
	`permission` varchar(20) NOT NULL COMMENT '用户权限组',
	`profile` varchar(255) NOT NULL COMMENT '个人简介',
	`created_time` timestamp NOT NULL COMMENT '创建时间',
	`updated_time` timestamp NOT NULL COMMENT '更新时间',
	`is_banned` tinyint(1) COMMENT '是否封禁',
	`banned_start` timestamp COMMENT '封禁开始时间',
	`banned_end` timestamp COMMENT '封禁结束时间',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `aii_music_user_tasklist`;
CREATE TABLE `aii_music_user_tasklist` (
  `task_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`permission` varchar(255) NOT NULL COMMENT '用户权限组',
	`operator` int(10) unsigned COMMENT '申请人id',
	`be_operator` int(10) unsigned COMMENT '被操作对象id',
	`detail` varchar(255) NOT NULL COMMENT '操作详情',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;