

SELECT SERVERPROPERTY ('Collation')

SQL_Latin1_General_CP1_CI_AS

Latin1_General_CI_AI

select convert( varchar(20),getdate(),110); 

select count(1) from [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where createdon > convert( varchar(20),getdate(),110); 

select * from #tmp_url;

drop table #tmp_url;
create table #tmp_url(url varchar(200) COLLATE Latin1_General_CI_AI,name nvarchar(200));
insert into #tmp_url(url,name) values('onboarding/ModifyClientAccount',N'HSǰ�ø����˺Žӿڣ��л�ͨ״̬��');
insert into #tmp_url(url,name) values('onboarding/AddFundAccessApp',N'HSǰ�õ����������ӿ�');
insert into #tmp_url(url,name) values('system/admin/user/notify',N'��Ϣ���Ľӿ�');
insert into #tmp_url(url,name) values('apiSending/sendNormalSMS.do',N'Redica�ӿ�');
insert into #tmp_url(url,name) values('ross/AddOrder',N'�ύICP�����ӿ�');
insert into #tmp_url(url,name) values('ross/AddOrderCommit',N'ȷ��ICP�����ӿ�');
insert into #tmp_url(url,name) values('ross/UpdateIpoOrder',N'����ICP�����ӿ�');
insert into #tmp_url(url,name) values('ross/AddCashProduct',N'����ICP��Ʒ�ӿ�');
insert into #tmp_url(url,name) values('ross/realtimeexchange/UpdateOverflowStatus',N'����ʵʱ���㳬��״̬�ӿ�');
insert into #tmp_url(url,name) values('ross/GreyMarket/UpdatePhillipMart',N'���°��̹�Ʊ״̬�ӿ�');
insert into #tmp_url(url,name) values('ross/GreyMarket/AddPhillipMart',N'���Ӱ��̹�Ʊ״̬�ӿ�');
insert into #tmp_url(url,name) values('ross/UpdateIpoServiceFare',N'����IPO Margin����״̬�ӿ�');

select b.url,b.name, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase a
inner join #tmp_url b on a.new_url like '%'+ b.url + '%'
where createdon > convert( varchar(20),getdate(),110)
group by b.url,b.name
order by  3 desc;
-----------------------HS POST �ӿ�-------------------------------------
select new_url, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where new_url like '%onboarding%' and  createdon > convert( varchar(20),getdate(),110)
group by new_url
-----------------------��Ϣ���Ľӿ�-------------------------------------; 
union
select new_url, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where new_url like '%system/admin/user/notify%' and  createdon > convert( varchar(20),getdate(),110)
group by new_url

-----------------------Redica�ӿ�-------------------------------------; 
union
select new_url, count(1) as cnt from  [ross-qa-01].reohk_mscrm.dbo.new_apicalllogbase
where new_url like '%apiSending/sendNormalSMS.do%' and  createdon > convert( varchar(20),getdate(),110)
group by new_url