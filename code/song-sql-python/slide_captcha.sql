drop database if exists slide_captcha;

create database slide_captcha;

use slide_captcha;

grant select,insert,update,delete on slide_captcha.* to 'slidecaptcha' identified by '123456';

create table origpic(
	`id` varchar(50) not null,
	`storage_location` varchar(50) not null,
	`add_time` real not null,
	`file_suffixes` varchar(50) not null,
	`file_size` varchar(50) not null,
	`file_long` varchar(50) not null,
	`file_wide` varchar(50) not null,
	key `idx_add_time` (`add_time`),
	primary key (`id`)
) engine=innodb default charset=utf8;

create table processedpic(
	`id` varchar(50) not null,
	`og_id` varchar(50) not null,
	`sub_id` varchar(50) not null,
	`storage_location` varchar(50) not null,
	`add_time` real not null,
	`file_suffixes` varchar(50) not null,
	`file_size` varchar(50) not null,
	`file_long` varchar(50) not null,
	`file_wide` varchar(50) not null,
	key `idx_add_time` (`add_time`),
	primary key (`id`)
) engine=innodb default charset=utf8;

create table subpic(
	`id` varchar(50) not null,
	`processed_id` varchar(50) not null,
	`border_struct` varchar(50) not null,
	`file_suffixes` varchar(50) not null,
	`file_size` varchar(50) not null,
	primary key (`id`)
) engine=innodb default charset=utf8;

create table combpic(
	`id` varchar(50) not null,
	`processed_id` varchar(50) not null,
	`sub_id` varchar(50) not null,
	`add_time` real not null,
	key `idx_add_time` (`add_time`),
	primary key (`id`)
) engine=innodb default charset=utf8;

create table unlockpic(
	`id` varchar(50) not null,
	`processed_id` varchar(50) not null,
	`try_times` int(50) not null,
	`success_times` int(50) not null,
	`avg_time` real not null,
	primary key (`id`)
) engine=innodb default charset=utf8;

create table userunlock(
	`id` varchar(50) not null,
	`user_id` varchar(50) not null,
	`combpic_id` varchar(50) not null,
	`start_time` real not null,
	`end_time` real not null,
	`result` bool not null,
	primary key (`id`)
) engine=innodb default charset=utf8;

create table mousestates(
	`id` varchar(50) not null,
	`userunlock` varchar(50) not null,
	`clock_times` int(50) not null,
	`mouse_press` real not null,
	`mouse_free` real not null,
	`mouse_continue` real not null,
	`mouse_path` varchar(50) not null,
	primary key (`id`)
) engine=innodb default charset=utf8;
