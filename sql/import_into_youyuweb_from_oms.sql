
USE CDB;
GO
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[initialize_youyuweb_data]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.initialize_youyuweb_data
GO
CREATE PROCEDURE dbo.initialize_youyuweb_data AS
BEGIN
	--test insert a record into selecurity'--
	--DELETE FROM OPENQUERY(dachenguat, 'select * from security where id = ''test123''')
	--insert into openquery(dachenguat,'select id,market,name,symbol,currency,price,date,createdon,modifiedon from security')
	--select 'test123','yf','test123','test123','usd',1,GETDATE(),GETDATE(),GETDATE()


	-----------import cash balance(to account_holding table)-------------
	INSERT INTO OPENQUERY(dachenguat,'select id,account_id,business_type,market,security_id,security_type,date,price,shares,cost,market_value,currency,remarks,createdon,modifiedon from account_holding') 
	--cash
	select CONCAT(b.id,'_',1,'_',4,'_CASH',currency,'_',INTEGRAL_UPDATE) as id,   b.id, 1 as business_type,
	'YF' as market,'CASH' + currency as security_id,4 as security_type,cast(cast(INTEGRAL_UPDATE as varchar(100)) as datetime) as date,
	1 as price,cashbalance as shares,cashbalance as cost,cashbalance as market_value,currency,'import by sql' as remarks,GETDATE() as createdon,GETDATE() as modifiedon
	from openquery([OMS], 'select a.client_id,a.INTEGRAL_UPDATE,c.dict_prompt currency,
	a.current_balance-(a.uncome_sell_balance_t1-a.uncome_buy_balance_t1)-(a.uncome_sell_balance_t2-a.uncome_buy_balance_t2)-(a.uncome_sell_balance_tn-a.uncome_buy_balance_tn) cashbalance
	from hs_fund.fund a,hs_user.sysdictionary c where a.money_type=c.subentry and c.dict_entry=1086
	and client_id in(''5012683'',''5012681'') and a.FUND_ACCOUNT NOT LIKE ''%13''
	') a inner join openquery(dachenguat,'select * from account') b on a.client_id = b.hs_client_id
	where  CONCAT(b.id,'_',1,'_',4,'_CASH',currency,'_',INTEGRAL_UPDATE) not in (select id from OPENQUERY(dachenguat,'select id from account_holding where id like ''%CASH%'''));


	---------------------------------------STOCK HOLDING-------------------------
	----------if stock_code is isin, then the security is fund ,or else the security is fund, then reset business type and security type,
	INSERT INTO OPENQUERY(dachenguat,'select id,account_id,business_type,market,security_id,security_type,date,price,shares,cost,market_value,currency,remarks,createdon,modifiedon from account_holding') 
	select a.*, CASE WHEN c.id IS NULL THEN　CONCAT(b.id,'_',1,'_',1,'_',CASE a.currency WHEN 'HKD' THEN 'hk' ELSE 'us' END,CASE a.currency WHEN 'USD' THEN stock_code ELSE SUBSTRING('00000',1,5-len(stock_code)) + stock_code END,convert(varchar(8),getdate(),112)) 
	ELSE CONCAT(b.id,'_',1,'_',1,'_',CASE a.currency WHEN 'USD' THEN stock_code ELSE SUBSTRING('00000',1,5-len(stock_code)) + stock_code END,convert(varchar(8),getdate(),112))  END
	as id,
	b.id as account_id,CASE WHEN c.id IS NULL THEN 1 ELSE 2 END as business_type,CASE a.currency WHEN 'HKD' THEN 'hk' ELSE 'us' END as market,
	CASE WHEN c.id IS NULL THEN CASE a.currency WHEN 'USD' THEN 'us' + stock_code ELSE  'hk' + SUBSTRING('00000',1,5-len(stock_code)) + stock_code END
	ELSE c.id END	 as security_id,
	CASE WHEN c.id IS NULL THEN 1 ELSE 2 END as security_type,CAST(convert(varchar(8),getdate(),112) as datetime) as date,a.CLOSE_PRICE,a.CURRENT_AMOUNT as shares,'0' as cost,a.MARKET_VALUE_CUR as market_value,
	a.currency,'imported by sql',getdate(),getdate()

	 from openquery([OMS], 'select a.*,c.dict_prompt currency
					from hs_his.totalstkvalue a,hs_user.sysdictionary c 
					where a.money_type=c.subentry and c.dict_entry=1086
					and client_id in(''5012683'',''5012681'')
					and a.init_Date = (select max(init_Date) from hs_his.totalfund)'
	)
	a inner join openquery(dachenguat,'select * from account') b on a.client_id = b.hs_client_id
	left join openquery(dachenguat,'select * from security') c on a.STOCK_CODE = c.ISIN
	where   CASE WHEN c.id IS NULL THEN　CONCAT(b.id,'_',1,'_',1,'_',CASE a.currency WHEN 'HKD' THEN 'hk' ELSE 'us' END,CASE a.currency WHEN 'USD' THEN stock_code ELSE SUBSTRING('00000',1,5-len(stock_code)) + stock_code END,convert(varchar(8),getdate(),112)) 
	ELSE CONCAT(b.id,'_',1,'_',1,'_',CASE a.currency WHEN 'USD' THEN stock_code ELSE SUBSTRING('00000',1,5-len(stock_code)) + stock_code END,convert(varchar(8),getdate(),112))  END
	 not in (select id from OPENQUERY(dachenguat,'select id from account_holding'));

	

	----------- ICP HOLDING---------------------------------
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_icp_holding') AND type='U')
		   DROP TABLE #tmp_icp_holding;
	create table #tmp_icp_holding(
	id varchar(100),account_id varchar(20),business_type int,market varchar(2),security_id varchar(20),
	security_type int,date datetime,price decimal(19,4),shares decimal(19,2),cost decimal(19,4),market_value decimal(19,4),
	currency varchar(10),remarks varchar(100),createdon datetime,modifiedon datetime
	)
	insert into #tmp_icp_holding(id, account_id,business_type,market, security_id,
	security_type,date,price,shares,cost,market_value,
	currency,remarks,createdon,modifiedon)
	select  CONCAT(b.new_name,'_', a.new_businesstype+1-100000000,'_3_', 'ICP' + CASE a.new_currency WHEN 100000000 THEN 'HKD' ELSE 'USD' END + CASE a.new_duration WHEN 100000000 THEN '07' WHEN 100000001 THEN '30' ELSE '90' END,'_',convert(varchar(8),getdate(),112)) as id, 
	 b.new_name as account_id,a.new_businesstype+1-100000000 as business_type,'yf' as market,'ICP' + CASE a.new_currency WHEN 100000000 THEN 'HKD' ELSE 'USD' END + CASE a.new_duration WHEN 100000000 THEN '07' WHEN 100000001 THEN '30' ELSE '90' END as security_id,
	3 as security_type,CAST(convert(varchar(8),getdate(),112) as datetime) as date, 1 as price,a.new_amount as shares, a.new_amount as cost, a.new_amount as market_value, 
	CASE a.new_currency WHEN 100000000 THEN 'HKD' ELSE 'USD' END as currency,'imported by sql ' as remarks,getdate() as createdon, getdate() as modifiedon
	from   [ross-qa-01].reohk_mscrm.dbo.new_yffcashorderbase a
	inner join  [ross-qa-01].reohk_mscrm.dbo.new_yffclientinfobase b on a.new_clientinfo = b.new_yffclientinfoid
	where a.new_orderstatus = 100000000 and a.new_processstatus = 100000005

	INSERT INTO OPENQUERY(dachenguat,'select id,account_id,business_type,market,security_id,security_type,date,price,shares,cost,market_value,currency,remarks,createdon,modifiedon from account_holding') 
	select id, account_id,business_type, market,security_id,
	security_type,CAST(convert(varchar(8),getdate(),112) as datetime) as date,1 as price, shares,cost,market_value,
	currency,'imported by sql',getdate() as createdon, getdate() as modifiedon
	 from (
	select id,account_id,business_type,market,security_id,currency,security_type,sum(shares) as shares,sum(cost) as cost,sum(market_value) as market_value
	 from #tmp_icp_holding group by id,account_id,business_type,market,security_id,currency,security_type,currency 
	 ) as t where id not in( select id from OPENQUERY(dachenguat,'select id from account_holding')); 

	-------------------import transactions ------------------------
	insert into openquery(dachenguat,'select id,external_transaction_id,account_id,business_type,security_id,security_type,direction,price,shares,amount,currency,tx_time,remarks,createdon,modifiedon from transaction')
	select CAST(NEWID() as varchar(100)),CONCAT(a.trade_date,'_',a.refno) as external_id,b.id,	
	CASE WHEN c.id IS NULL THEN 1 ELSE 2 END as business_type,
	CASE WHEN c.id IS NULL THEN CASE ccy WHEN 'USD' THEN 'us' + stock_code ELSE  'hk' + SUBSTRING('00000',1,5-len(stock_code)) + stock_code END ELSE c.id END as security_id,
	CASE WHEN c.id IS NULL THEN 1 ELSE 2 END  as security_type,CASE a.ENTRUST_BS WHEN 'B' THEN 1 ELSE 2 END as direction,a.average_price as price,a.shares,a.total_amount,ccy,cast(cast(a.trade_date as varchar(10)) as datetime) as tx_date,'imported by sql' as remarks, getdate() as createdon, getdate() as modifiedon
	from openquery(OMS, '
		   select a.client_id,a.trade_date,a.init_date,a.sequence_no,a.sequence_no||a.allocation_id refno,a.exchange_type,a.stock_code,a.stock_name, j.name_english ccy,a.average_price,a.total_amount as shares, a.gloss_balance total_amount, a.fare0 commission_my ,decode(a.entrust_bs,''1'',''B'',''2'',''S'','''') as entrust_bs
		   from hs_his.hisbargaininfo a,hs_user.dictionarytranslate j
		   where  a.money_type     = j.subentry
		and  j.dict_entry     =''1101''
		   AND a.bargain_source = ''C''
		and  a.bargain_status = ''7''
		   and  not exists(select 1 from hs_his.hisdeliver e where a.trade_date=e.entrust_date and a.sequence_no=e.sequence_no and a.allocation_id=e.allocation_id and e.deliver_status=''2'' )
		   and a.CANCEL_FLAG = 0   
		   and client_id in(''5012683'',''5012681'')     
		   ')a
		   inner join openquery(dachenguat,'select * from account where hs_client_id in (''5012683'',''5012681'') ') b	  on a.client_id = b.hs_client_id	   
		   left join openquery(dachenguat,'select * from security') c on a.STOCK_CODE = c.ISIN
	where CONCAT(a.trade_date,'_',a.refno) not in (select external_transaction_id from OPENQUERY(dachenguat,'select external_transaction_id from transaction')); 
END
GO

--exec initialize_youyuweb_data


--删除作业
IF  EXISTS (SELECT JOB_ID FROM MSDB.DBO.SYSJOBS_VIEW WHERE NAME =N'initialize_youyuweb_data_job') 
EXECUTE MSDB.DBO.SP_DELETE_JOB @JOB_NAME=N'initialize_youyuweb_data_job' 

--定义创建作业
DECLARE @jobid uniqueidentifier
EXEC msdb.dbo.sp_add_job
        @job_name = N'initialize_youyuweb_data_job',
        @job_id = @jobid OUTPUT

--定义作业步骤
DECLARE @sql nvarchar(400),@dbname sysname
SELECT    @dbname=DB_NAME(), --执行的数据库（当前）
        @sql=N'exec initialize_youyuweb_data' --一般定义的是使用TSQL处理的作业,这里定义要执行的Transact-SQL语句
EXEC msdb.dbo.sp_add_jobstep
        @job_id = @jobid,
        @step_name = N'initialize_youyuweb_data_step',
        @subsystem = 'TSQL', --步骤的类型,一般为TSQL
        @database_name=@dbname,
        @command = @sql

--创建调度(使用后面专门定义的几种作业调度模板)
EXEC msdb..sp_add_jobschedule
        @job_id = @jobid,
        @name = N'initialize_youyuweb_data_schedule',
        @freq_type=4,                --每天
        @freq_interval=1,            --指定每多少天发生一次,这里是1天.
        @freq_subday_type=0x8,       --重复方式,0x1=在指定的时间,0x4=多少分钟,0x8=多少小时执行一次
        @freq_subday_interval=1,     --重复周期数,这里每小时执行一次
        @active_start_date = NULL,   --作业执行的开始日期,为NULL时表示当前日期,格式为YYYYMMDD
        @active_end_date = 99991231, --作业执行的停止日期,默认为99991231,格式为YYYYMMDD
        @active_start_time = 080000, --作业执行的开始时间,格式为HHMMSS
        @active_end_time = 093000    --作业执行的停止时间,格式为HHMMSS