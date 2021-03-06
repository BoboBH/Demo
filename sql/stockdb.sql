﻿
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
status int,
briefname varchar(20),
createdon datetime,
 modifiedon datetime); 
 alter table stock add type int;
 

 
 select * from stock where name like '%板指' ;
 select * from stock where id = 'sz399006';
select * from stock where date is null OR date < '2019-03-29'
alter table stock drop column status;
alter table stock add status int;
update stock set status = 1 ;

select * from stock where status is null or type is null;

select 210*22;

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
 
 
 select count(1) from stockdailyperf where stockid='sz002840'
 select count(1) from stockdailyperf where continuetrend is not null;
 
 select * from stockdailyperf where stockid = 'sh000001' and changepercentage <-2 order by date asc;
 
 select date,`change`,continuetrend from stockdailyperf where stockid='sh600148' order by date;
 
 create index stockdailyperf_stockid on stockdailyperf(stockid);
 create index stockdailyperf_stockid_date on stockdailyperf(stockid,date);

insert into stock(id, name, symbol,market,date,price,status,createdon,modifiedon)
select id, name, symbol,market,date,price,status,createdon,modifiedon from stockdb.stock;

insert into stockdailyperf(id, stockid,date,open,high,low,close,volume,amount,`change`,changepercentage,lastclose,continuetrend,turnoverrate,createdon,modifiedon)
select id, stockid,date,open,high,low,close,volume,amount,`change`,changepercentage,lastclose,continuetrend,turnoverrate,createdon,modifiedon from stockdb.stockdailyperf;



select * from stockdailyperf where stockid = 'sz002125' and date
use stockdb;

select * from user;

select * from token_info
order by expiry desc;

select * from token_info where token = 'aaa'
use test;

select count(1) from stock where symbol = '000422';

insert into stock(id, name, symbol,createdon,modifiedon) values('abc','abc','abc',now(),now());

select * from stockdailyperf limit 100;

select DATEDIFF(now(),'2019-12-31');

select count(1) from stockdailyperf
order by date desc;
delete from MasterPortfolio;
insert into MasterPortfolio(name,date,baseamount,createdon,modifiedon) values('test1','2019-02-02',100000.00,now(),now());
#update MasterPortfolio set createdon = now(),modifiedon = now();

select * from MasterPortfolio;
update MasterPortfolio set status = 0;



#drop table stock;
create table stock(
id varchar(100) primary key,
name nvarchar(200),
symbol varchar(20),
createdon datetime,
 modifiedon datetime);
 
 alter table stock
 add status int null;
 
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
 create index stockdailyperf_date_changepercentage on stockdailyperf(date,changepercentage);
 
 #drop table masterportfolio;
 create table masterportfolio(
 id int auto_increment primary key,
 date datetime, 
 name nvarchar(200),
 status int comment '处理流程状态'
 baseamount decimal(11,2),
 returnday1 decimal(11,5),
 returnday2 decimal(11,5),
 returnday3 decimal(11,5),
 returnday5 decimal(11,5),
 returnday10 decimal(11,5),
 benchmark varchar(100),#stockid,例如上证指数，沪深300等
 createdon datetime,
 modifiedon datetime);
 alter table masterportfolio add status int comment '处理流程状态';
 
 
 #drop table portfolioholding;
 create table portfolioholding(
 id int  auto_increment primary key,
 masterportfolioid int,
 stockid varchar(100),
 shares decimal(19,2),
 weight decimal(9,3),
 actualweight decimal(9,3),
 createdon datetime,
 modifiedon datetime);
 alter table portfolioholding  add actualweight decimal(9,3)
