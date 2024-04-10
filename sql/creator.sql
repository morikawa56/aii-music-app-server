DROP TABLE IF EXISTS `aii_music_creator`;
CREATE TABLE `aii_music_creator` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `creatorname` varchar(45) NOT NULL COMMENT '创作者名称',
	`gender` varchar(10) NOT NULL COMMENT '性别',
	`creator_avatar` varchar(255) NOT NULL COMMENT '歌手头像', 
	`birth` timestamp NOT NULL COMMENT '生日',
	`location` varchar(45) NOT NULL COMMENT '地区',
	`introduction` varchar(255) NOT NULL COMMENT '介绍',
  PRIMARY KEY (`cid`) USING BTREE,
  UNIQUE KEY `creatorname` (`creatorname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
