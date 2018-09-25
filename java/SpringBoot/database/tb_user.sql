CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `age` int(11) NOT NULL COMMENT '年龄',
  `ctm` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  
select * from tb_user;

INSERT INTO `tb_user` (`username`, `age`, `ctm`) VALUES('黄齐仁', '18', NOW());
INSERT INTO `tb_user` (`username`, `age`, `ctm`) VALUES('李云', '16', NOW());
INSERT INTO `tb_user` (`username`, `age`, `ctm`) VALUES('黄晓乐', '16', NOW());