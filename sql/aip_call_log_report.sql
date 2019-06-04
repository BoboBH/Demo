

SELECT SERVERPROPERTY ('Collation')

SQL_Latin1_General_CP1_CI_AS

Latin1_General_CI_AI

select convert( varchar(20),getdate(),110); 

select count(1) from [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where createdon > convert( varchar(20),getdate(),110); 

select * from #tmp_url;

drop table #tmp_url;
create table #tmp_url(url varchar(200) COLLATE Latin1_General_CI_AI,name nvarchar(200));
insert into #tmp_url(url,name) values('onboarding/ModifyClientAccount',N'HS前置更新账号接口（中华通状态）');
insert into #tmp_url(url,name) values('onboarding/AddFundAccessApp',N'HS前置调整购买力接口');
insert into #tmp_url(url,name) values('system/admin/user/notify',N'消息中心接口');
insert into #tmp_url(url,name) values('apiSending/sendNormalSMS.do',N'Redica接口');
insert into #tmp_url(url,name) values('ross/AddOrder',N'提交ICP订单接口');
insert into #tmp_url(url,name) values('ross/AddOrderCommit',N'确认ICP订单接口');
insert into #tmp_url(url,name) values('ross/UpdateIpoOrder',N'更新ICP订单接口');
insert into #tmp_url(url,name) values('ross/AddCashProduct',N'新增ICP产品接口');
insert into #tmp_url(url,name) values('ross/realtimeexchange/UpdateOverflowStatus',N'更新实时换汇超额状态接口');
insert into #tmp_url(url,name) values('ross/GreyMarket/UpdatePhillipMart',N'更新暗盘股票状态接口');
insert into #tmp_url(url,name) values('ross/GreyMarket/AddPhillipMart',N'增加暗盘股票状态接口');
insert into #tmp_url(url,name) values('ross/UpdateIpoServiceFare',N'更新IPO Margin订单状态接口');

select b.url,b.name, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase a
inner join #tmp_url b on a.new_url like '%'+ b.url + '%'
where createdon > convert( varchar(20),getdate(),110)
group by b.url,b.name
order by  3 desc;
-----------------------HS POST 接口-------------------------------------
select new_url, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where new_url like '%onboarding%' and  createdon > convert( varchar(20),getdate(),110)
group by new_url
-----------------------消息中心接口-------------------------------------; 
union
select new_url, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where new_url like '%system/admin/user/notify%' and  createdon > convert( varchar(20),getdate(),110)
group by new_url

-----------------------Redica接口-------------------------------------; 
union
select new_url, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where new_url like '%apiSending/sendNormalSMS.do%' and  createdon > convert( varchar(20),getdate(),110)
group by new_url