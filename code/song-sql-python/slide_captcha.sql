drop database if exists slide_captcha;

create database slide_captcha;

use slide_captcha;

grant select,insert,update,delete on slide_captcha.* to 'slidecaptcha' identified by '123456';

create table origpic(
	`id` integer not null AUTO_INCREMENT,
	`name` varchar(50) not null,
	`storage_location` varchar(50) not null,
	`add_time` real not null,
	`file_suffixes` varchar(50) not null,
	`file_size` varchar(50) not null,
	`file_pixel` varchar(50) not null,
	key `idx_add_time` (`add_time`),
	primary key (`id`)
) engine=innodb default charset=utf8;

create table processedpic(
	`id` integer not null AUTO_INCREMENT,
	`name` varchar(50) not null,
	`og_id` integer not null,
	`sub_id` integer ,
	`storage_location` varchar(50) not null,
	`add_time` real not null,
	`file_suffixes` varchar(50) not null,
	`file_size` varchar(50) not null,
	`file_pixel` varchar(50) not null,
	key `idx_add_time` (`add_time`),
	primary key (`id`)
) engine=innodb default charset=utf8;

create table subpic(
	`id` integer not null AUTO_INCREMENT,
	`name` varchar(50) not null,
	`processed_id` integer not null,
	`border_struct` varchar(50),
	`file_suffixes` varchar(50) not null,
	`file_size` varchar(50) not null,
	primary key (`id`)
) engine=innodb default charset=utf8;

create table combpic(
	`id` integer not null AUTO_INCREMENT,
	`processed_id` integer not null,
	`sub_id` integer not null,
	`add_time` real not null,
	key `idx_add_time` (`add_time`),
	primary key (`id`)
) engine=innodb default charset=utf8;

create table unlockpic(
	`id` integer not null AUTO_INCREMENT,
	`processed_id` integer not null,
	`try_times` integer ,
	`success_times` integer 
	`avg_time` real ,
	primary key (`id`)
) engine=innodb default charset=utf8;

create table userunlock(
	`id` integer not null AUTO_INCREMENT,
	`user_id` varchar(50) not null,
	`processed_id` integer not null,
	`start_time` real ,
	`end_time` real ,
	`result` bool ,
	primary key (`id`)
) engine=innodb default charset=utf8;

create table mousestates(
	`id` integer not null AUTO_INCREMENT,
	`userunlock` integer not null,
	`clock_times` integer ,
	`mouse_press` real ,
	`mouse_free` real ,
	`mouse_continue` real ,
	`mouse_path` varchar(50) ,
	primary key (`id`)
) engine=innodb default charset=utf8;
