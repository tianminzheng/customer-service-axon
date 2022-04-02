
DROP TABLE IF EXISTS `customer_ticket_summary`;

CREATE TABLE `customer_ticket_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket_id` varchar(100) not null,
  `order_number` varchar(100) not null,
  `account` varchar(50) not null,
  `staff` varchar(100) DEFAULT NULL,  
  `status` varchar(20) not null,  
  `score` int(10) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `token_entry`;

create table token_entry (
	processor_name varchar(255) not null, 
	segment integer not null, 
	owner varchar(255), 
	timestamp varchar(255) not null, 
	token longblob, 
	token_type varchar(255), 
	primary key (processor_name, segment)
)


