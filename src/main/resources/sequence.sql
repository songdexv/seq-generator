CREATE TABLE `sg_sequence` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`seq_name` VARCHAR(45) NOT NULL COMMENT '序列名称',
	`seq_value` BIGINT(20) UNSIGNED NOT NULL COMMENT '序列值',
	`min_value` BIGINT(20) NOT NULL COMMENT '最小值',
	`max_value` BIGINT(20) NOT NULL COMMENT '最大值',
	`step` BIGINT(20) NOT NULL COMMENT '步长',
	`memo` VARCHAR(64) NULL DEFAULT NULL COMMENT '备注',
	`gmt_create` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
	`gmt_modified` TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `seq_name` (`seq_name`)
)
COLLATE='gbk_chinese_ci'
ENGINE=InnoDB;
