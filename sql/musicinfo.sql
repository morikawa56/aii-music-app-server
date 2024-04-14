DROP TABLE IF EXISTS `aii_music_musicinfo`;
CREATE TABLE `aii_music_musicinfo` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `musicname` varchar(45) NOT NULL COMMENT '音乐名称',
	`creator` varchar(255) NOT NULL COMMENT '创作者ID',
	`album` varchar(255) NOT NULL COMMENT '专辑ID',
	`introduction` varchar(255) COMMENT '音乐介绍',
	`music_avatar` varchar(255) COMMENT '音乐图片',
	`lyric` text COMMENT '歌词',
	`is_unshelved` tinyint(1) COMMENT '是否下架',
	`published_time` timestamp NOT NULL COMMENT '发行时间', 
	`res_url` varchar(255) NOT NULL COMMENT '歌曲资源地址', 
	`created_time` timestamp NOT NULL COMMENT '创建时间',
	`updated_time` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`mid`) USING BTREE,
  UNIQUE KEY `musicname` (`musicname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
