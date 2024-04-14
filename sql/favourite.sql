DROP TABLE IF EXISTS `aii_music_favourite`;
CREATE TABLE `aii_music_favourite` (
  `fid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`uid` int(10) unsigned NOT NULL COMMENT '用户ID',
	`is_musiclist` tinyint(1) COMMENT '是否是歌单',
	`mid` int(10) unsigned NOT NULL COMMENT '音乐ID',
	`mlid` int(10) unsigned NOT NULL COMMENT '歌单ID',
	`created_time` timestamp NOT NULL COMMENT '创建时间',
	`updated_time` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
