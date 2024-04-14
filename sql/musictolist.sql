DROP TABLE IF EXISTS `aii_music_musictolist`;
CREATE TABLE `aii_music_musictolist` (
  `mapid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`mid` int(10) unsigned NOT NULL COMMENT '歌曲ID',
	`mlid` int(10) unsigned NOT NULL COMMENT '歌单ID',
  PRIMARY KEY (`mapid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
