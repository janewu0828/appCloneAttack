--
-- Table structure for table `tracing_log`
--
CREATE TABLE IF NOT EXISTS `tracing_log` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '追蹤編號',
  `app_id2` varchar(40) NOT NULL COMMENT 'app編號',
  `deviceid` varchar(36) NOT NULL COMMENT 'UUID',
  `load_file_name` varchar(255) NULL COMMENT '載入的Jar檔',
  `personal_key` varchar(255) NULL COMMENT '個人金鑰',
  `post_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '發佈時間',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='追蹤記錄' AUTO_INCREMENT=1 ;