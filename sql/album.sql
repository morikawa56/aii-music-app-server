DROP TABLE IF EXISTS `aii_music_album`;
CREATE TABLE `aii_music_album` (
  `aid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `albumname` varchar(45) NOT NULL COMMENT '专辑名称',
	`creator_id` varchar(255) NOT NULL COMMENT '创作者ID',
	`introduction` varchar(255) COMMENT '专辑介绍',
	`album_avatar` varchar(255) COMMENT '专辑图片',
	`published_time` timestamp NOT NULL COMMENT '发行时间', 
	`created_time` timestamp NOT NULL COMMENT '创建时间',
	`updated_time` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`aid`) USING BTREE,
  UNIQUE KEY `albumname` (`albumname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
