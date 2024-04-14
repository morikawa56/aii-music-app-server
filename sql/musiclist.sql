DROP TABLE IF EXISTS `aii_music_musiclist`;
CREATE TABLE `aii_music_musiclist` (
  `mlid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `musiclistname` varchar(45) NOT NULL COMMENT '音乐名称',
	`uid` int(10) unsigned NOT NULL COMMENT '创建用户ID',
	`musiclist_pic` varchar(255) COMMENT '歌单图片',
	`introduction` varchar(255) COMMENT '歌单介绍',
	`style` varchar(10) COMMENT '风格',
	`created_time` timestamp NOT NULL COMMENT '创建时间',
	`updated_time` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`mlid`) USING BTREE,
  UNIQUE KEY `musiclistname` (`musiclistname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
