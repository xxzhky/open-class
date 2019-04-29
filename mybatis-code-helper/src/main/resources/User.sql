-- auto Generated on 2019-04-10
-- DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
	id INT (11) NOT NULL COMMENT 'id',
	`password` VARCHAR (50) COMMENT 'password',
	gender CHAR (1) DEFAULT 1 COMMENT 'gender',
	age INT (11) COMMENT 'age',
	nation VARCHAR (50) COMMENT 'nation',
	marital_status VARCHAR (50) NOT NULL AUTO_INCREMENT COMMENT 'maritalStatus',
	birth DATETIME COMMENT 'birth',
	id_number VARCHAR (50) COMMENT 'idNumber',
	education_background VARCHAR (50) COMMENT 'educationBackground',
	university_degree VARCHAR (50) COMMENT 'universityDegree',
	PRIMARY KEY (marital_status)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'user';
