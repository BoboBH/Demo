
#insert into account(id,client_name,createdon,modifiedon) values('1212','bobo',now(),now());

#CREATE DATABASE youyuweb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use youyuweb;
drop table  if exists  account;
create table account(
   id varchar(20)  primary key comment 'UC ClientId',
	 client_name nvarchar(100)  not null,
	 hs_account_id varchar(10) comment 'UC account Id, it is required for create order,inlcude stock/icp',
	 hs_client_id varchar(10),
	 hs_fund_account varchar(10),
	 hs_icp_fund_account varchar(10),
	 hs_account_status int  comment 'enum value,1:Active;2:suspend;3:closed',
	 wm_account_id varchar(10) comment 'UC account Id, it is required for create order,inlcude wm/icp',
	 wm_client_id varchar(10),
	 wm_account_hierarchy_id varchar(16),
	 wm_subaccount_hierarchy_id varchar(16),
	 wm_user_id varchar(10),
	 wm_account_status int comment 'enum value,1:Active;2:suspend;3:closed',
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null
);
select * from account;
drop table if exists account_permission;
create table account_permission(
   id varchar(20) not null primary key comment 'compose with {account_id}_{uin}',
	 account_id varchar(20) not null,
	 uin varchar(20) not null,
   wm_hex_permission varchar(32),
	 hs_hex_permission varchar(32),
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null
);
select * from account_permission;

drop table if exists `order`;
create table `order`(
     id varchar(100) primary key comment 'uuid',
	 external_order_id varchar(20) comment 'exteral system id ,such as hs orderid,fnz order id etc',
	 account_id varchar(20),
	 business_type int not null comment 'enum vlaue,1: broker;2:wm;3:esop',
	 market varchar(10),
	 security_id varchar(20) comment 'security identity(include market info), such as symbol, isin etc',
	 security_product_id varchar(20) comment 'interal security product id',
	 security_name nvarchar(200),
	 security_type int comment 'enum vlaue,1:stock;2:fund;3:ICP',
	 direction int comment 'enum vlaue,1:buy;2:sell',
	 price decimal(19,4),
	 shares decimal(19,2),
	 amount decimal(19,4),
	 currency char(3),
	 submit_time datetime,
	 status int comment 'enum value,1:submited,2:cannceled;3:completed',
	 canncel_time datetime,
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null	 
);
select * from `order`;

drop table if exists transaction;
create table transaction(
     id varchar(10) primary key comment 'compose exteral transactionid, businesst_type ',
	 external_order_id varchar(20) comment 'exteral system id ,such as hs orderid,fnz order id etc',
	 account_id varchar(20),
	 business_type int not null comment 'enum vlaue,1: broker;2:wm;3:esop',
	 market varchar(10),
	 security_id varchar(20) comment 'security identity, such as symbol, isin etc',
	 security_product_id varchar(20) comment 'interal security product id',
	 security_name nvarchar(200),
	 security_type int comment 'enum vlaue,1:stock;2:fund;3:ICP',
	 direction int comment 'enum vlaue,1:buy;2:sell',
	 price decimal(19,4),
	 shares decimal(19,2),
	 amount decimal(19,4),
	 currency char(3),
	 tx_time datetime,
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null	 
);
select * from transaction;

drop table if exists account_balance;
create table account_balance(
     id varchar(30) primary key comment 'compose with {accountid}_{businesstype}_{date}_{currency}',
	 account_id varchar(20) not null,
	 date datetime not null, 
	 business_type int not null comment 'enum vlaue,1: broker;2:wm;3:esop',
	 currency char(3) not null,
	 balance decimal(19,4),
	 balance_for_trading decimal(19,4),
	 balance_for_withdrawal decimal(19,4),
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null	 
);
select * from account_balance;
drop table if exists account_holding;
create table account_holding(
     id varchar(30) primary key comment 'compose with {accountid}_{businesstype}_{security_type}_{security_id}_{date}', 
	 account_id varchar(20),
	 business_type int not null comment 'enum vlaue,1: broker;2:wm;3:esop',
	 market varchar(10),
	 security_id varchar(20) comment 'security identity, such as symbol, isin etc',
	 security_product_id varchar(20) comment 'interal security product id',
	 security_name nvarchar(200),
	 security_type int comment 'enum vlaue,1:stock;2:fund;3:ICP',
	 date datetime not null, 
	 price decimal(19,4),
	 shares decimal(19,2),
	 cost decimal(19,4),
	 market_value decimal(19,4),
	 currency char(3), 
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null	 
);
select * from account_holding;

#如果order，transaction,holding中不想出错冗余的security info，可以创建独立的security table
drop table if exists security;
create table security(
     id varchar(20) not null primary key,
	 market char(2) not null comment 'enum vlaue,1:hk;2:us,3:yf,internal product,sucn as ICP',
	 name nvarchar(200) not null,
	 security_product_id varchar(20) comment 'interal security product id',
	 security_type int comment 'enum vlaue,1:stock;2:fund;3:ICP',
	 symbol varchar(20),
   currency char(3),
	 isin char(12),
	 price decimal(11,4) comment 'price/nave/unit value',
	 date datetime comment 'price date',
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null	 
);
#initialize security table
insert into security(id,market,name,security_type,symbol,currency,date,price,createdon,modifiedon)
select id,market,name, 1,symbol,'CNY',date,price,now(),now() from stock.stock where id not in (select id from security);
select * from security;

drop table if exists user;
create table user(
     id varchar(20) not null primary key,
	 username varchar(100) not null,
	 password varchar(100) not null,
	 last_login datetime,
	 remarks nvarchar(1000),
	 createdon datetime not null,
	 modifiedon datetime not null	 ,
	 unique(username)
);
select *  from user;

select * from security;
