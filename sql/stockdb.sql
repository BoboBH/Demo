
CREATE DATABASE IF NOT EXISTS stock DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use stock;

drop table stock;
create table stock(
id varchar(100) primary key,
name nvarchar(200),
symbol varchar(20),
market varchar(20),
date datetime,
price decimal(11,3),
status varchar(20),
briefname varchar(20),
createdon datetime,
 modifiedon datetime); 
 alter table stock add type int;
 
select * from stock where symbol='000001';
update stock set type = 1 where type is null;

 #drop table stockdailyperf;
 create table stockdailyperf(
 id varchar(100) primary key,
 stockid varchar(100),
 date datetime,
 open decimal(11,3),
 high decimal(11,3),
 low decimal(11,3),
 close decimal(11,3),
 volume decimal(19,2),
 amount decimal(19,2),
 `change` decimal(11,2),
 changepercentage decimal(11,2),
 lastclose decimal(11,2),
 continuetrend int,
 turnoverrate decimal(11,2),
 createdon datetime,
 modifiedon datetime
 );
 select count(1) from stockdailyperf;


insert into stock(id, name, symbol,market,date,price,status,createdon,modifiedon)
select id, name, symbol,market,date,price,status,createdon,modifiedon from stockdb.stock;

insert into stockdailyperf(id, stockid,date,open,high,low,close,volume,amount,`change`,changepercentage,lastclose,continuetrend,turnoverrate,createdon,modifiedon)
select id, stockid,date,open,high,low,close,volume,amount,`change`,changepercentage,lastclose,continuetrend,turnoverrate,createdon,modifiedon from stockdb.stockdailyperf;


use stockdb;

select * from user;

select * from token_info
order by expiry desc;

select * from token_info where token = 'aaa'
use test;

select count(1) from stock where symbol = '000422';

insert into stock(id, name, symbol,createdon,modifiedon) values('abc','abc','abc',now(),now());

select count(1) from stockdailyperf;

select DATEDIFF(now(),'2019-12-31');

select count(1) from stockdailyperf
order by date desc;

use stockdb;

drop table stock;
create table stock(
id varchar(100) primary key,
name nvarchar(200),
symbol varchar(20),
createdon datetime,
 modifiedon datetime);
 
 alter table stock
 add status int null;
 
 drop table stockdailyperf;
 create table stockdailyperf(
 id varchar(100) primary key,
 stockid varchar(100),
 date datetime,
 open decimal(11,3),
 high decimal(11,3),
 low decimal(11,3),
 close decimal(11,3),
 volume decimal(19,2),
 amount decimal(19,2),
 `change` decimal(11,2),
 changepercentage decimal(11,2),
 lastclose decimal(11,2),
 continuetrend int,
 turnoverrate decimal(11,2),
 createdon datetime,
 modifiedon datetime
 );
