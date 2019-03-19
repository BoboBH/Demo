

select a.*,b.username as ownername,c.name as managername from client a 
left join aspnetusers b on a.owner = b.id
left join clientmanager c on c.id = a.clientmanagerId


select cli.*,usr.username as ownername,cm.name as managername,'ClientInfo' as Discriminator from client cli left join aspnetusers usr on cli.owner = usr.id left join clientmanager cm on cm.id = cli.clientmanagerId where cli.id=@id

use WebPage;

select * from smc_promotion

select * from aspnetuserroles;


select * from aspnetusertokens;

client_ibfk_1
create table client(id varchar(100) primary key,
 name nvarchar(100), 
 owner varchar(100),
 foreign key (owner) REFERENCES aspnetusers(id));
 
 alter table client
   add ClientManagerId varchar(100);
	 
	 update client 
	 set ClientManagerId = '4ad9756f-4d9a-4752-b67c-372c42b8f05d';
 
  alter table client add foreign key(ClientManagerId) references clientManager(id) on delete cascade on update cascade;
	
 select * from client;
 
 create table clientManager(id varchar(100) primary key,
 name nvarchar(100),mobile varchar(100),email varchar(200));
 
 select * from clientManager
 
 insert into client values(uuid(),'黄齐仁','ed41f437-8a17-4d73-8fab-ccdcc64bbaa2');

select c.*,a.* from aspnetusers a 
inner join aspnetuserroles b on a.id = b.userid
inner join aspnetroles c on b.roleId = c.id


select * from contact;

select * from aspnetusers;

select userid, roleid from aspnetuserroles;

insert into aspnetuserroles(userid, roleid)
values('ed41f437-8a17-4d73-8fab-ccdcc64bbaa2','0d6e50dc-4633-11e9-88bf-4ccc6a538d83');


select * from aspnetroles


insert into aspnetroles(id, ConcurrencyStamp,name,NormalizedName)
values(uuid(),uuid(),'Adminnistor','ADMINNISTOR')


insert into aspnetroles(id, ConcurrencyStamp,name,NormalizedName)
values(uuid(),uuid(),'ContactManagers','CONTACTMANAGERS')


select * from smc_activity;



# Host: localhost  (Version 5.6.27)
# Date: 2019-03-05 15:05:04
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "contact"
#

DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ownerid` varchar(100) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "contact"
#

INSERT INTO `contact` VALUES (1,'黄齐仁','阳光花园17-2403','深圳','广东','518000','bobo.huang@yff.com',NULL,NULL);

create table t1(id int primary key, bod timestamp default now());

select * from t1;
drop table t1;

select * from smc_activity;

DROP TABLE IF EXISTS `smc_activity`;
CREATE TABLE `smc_activity` (
  `id` varchar(100) not null,
  `name` varchar(200) not NULL,
  `ownerid` varchar(100) DEFAULT NULL,
  `description` nvarchar(1000) DEFAULT NULL,
  `key` varchar(100) DEFAULT NULL,
  `parent_activity_id` varchar(100) DEFAULT NULL,
  `type` int DEFAULT 1,
  `status` int(4) DEFAULT 0,
	createdon datetime default now(),
  updatedon datetime default now(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS smc_channel;
create table smc_channel(
  id varchar(100),
	name nvarchar(100),
  status int default 0,
	channel_code char(6),
	description nvarchar(500),
	ownerid varchar(100),
 `key`  varchar(100),	
	createdon datetime default now(),
  updatedon datetime default now(),
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS smc_promotion
CREATE TABLE smc_promotion(
  id varchar(100) not null,
	name nvarchar(200),
	ownerid varchar(100),
	code char(8),
	status int default 0,
	`key`  varchar(100),	
	activity_id varchar(100),
	channel_id varchar(100),
	start_date date,
	end_date date,
	url varchar(255),
	createdon datetime default now(),
  updatedon datetime default now(),	
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

#
# Data for table "contact"
#
