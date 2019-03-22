
--Author:bobo huang
--Create Date:2019-03-20
--Description:run follower stats report but not include trading/asset data
-- sample:exec dbo.sp_follower_stats 
-- or exec dbo.sp_follower_stats '2019-03-19'
--PS:Replace dev02.reohk_mscrm. with empty string while load on target server;
--A0F765E7-B739-44EC-9EFA-30EFFC86B846:default followerid named 'Other'
/*
enum definition
Direction :
   1:Deposit;
   2.Withdrawal
Stats_Type:
   1:Current
   2:Since to Today
*/
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_follower_stats]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_follower_stats
GO
CREATE PROCEDURE dbo.sp_follower_stats(@p_EndDate DATETIME = NULL) AS
BEGIN
    IF @p_EndDate IS NULL
	   SET @p_EndDate = GETDATE()
	SET @p_EndDate = DATEADD(hh,-8,@p_EndDate)
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_client_follow_mapping') AND type='U')
		DROP TABLE #tmp_client_follow_mapping
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_user_follow_mapping') AND type='U')
		DROP TABLE #tmp_user_follow_mapping
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_register_stat') AND type='U')
		DROP TABLE #tmp_register_stat
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_account_opened_stat') AND type='U')
		DROP TABLE #tmp_account_opened_stat
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_statement_stat') AND type='U')
		DROP TABLE #tmp_statement_stat
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_client_account_opened') AND type='U')
		DROP TABLE #tmp_client_account_opened
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_client_statement') AND type='U')
		DROP TABLE #tmp_client_statement
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_rm') AND type='U')
		DROP TABLE #tmp_rm
    

	create table #tmp_client_follow_mapping(clientid uniqueidentifier,followerid uniqueidentifier)
	create table #tmp_user_follow_mapping(userid uniqueidentifier,followerid uniqueidentifier)
	create table #tmp_register_stat(followerid uniqueidentifier,cnt int,stat_type int)
	create table #tmp_account_opened_stat(followerid uniqueidentifier,cnt int,stat_type int)
	create table #tmp_statement_stat(followerid uniqueidentifier,cnt int,hkd_amount decimal(19,2),direction int,stat_type int)
	create table #tmp_client_account_opened(clientid uniqueidentifier,createdon datetime)
	create table #tmp_client_statement(clientid uniqueidentifier,direction int,business varchar(10),hkd_amount decimal(19,4),txdate datetime) 	
	create table #tmp_rm(followerid uniqueidentifier,name nvarchar(max)) 
	
	--initial 
	insert into #tmp_rm(followerid,name)
	select new_yff_relationship_managerid,new_name from dev02.reohk_mscrm.dbo.new_yff_relationship_manager
	insert into #tmp_rm(followerid,name) values('A0F765E7-B739-44EC-9EFA-30EFFC86B846','Other')
	
	--find client-follower mapping by customer
	truncate table #tmp_client_follow_mapping
	insert into #tmp_client_follow_mapping(clientid, followerid)
	select   b.new_clientinfo ,c.new_yff_relationship_managerid from dev02.reohk_mscrm.dbo.new_yffcustomer a
	inner join  dev02.reohk_mscrm.dbo.new_application b on a.new_yffcustomerid=b.new_customerinfo
	inner join dev02.reohk_mscrm.dbo.new_yff_relationship_manager c on a.new_follower = c.new_yff_relationship_managerid
	where b.new_clientinfo is not null

	--find client-follower mapping by user
	insert into #tmp_client_follow_mapping(clientid, followerid)
	select   b.new_clientinfo ,c.new_yff_relationship_managerid from dev02.reohk_mscrm.dbo.new_yffuser a
	inner join  dev02.reohk_mscrm.dbo.new_application b on a.new_yffuserid=b.new_registeruser
	inner join dev02.reohk_mscrm.dbo.new_yff_relationship_manager c on a.new_follower = c.new_yff_relationship_managerid
	left join #tmp_client_follow_mapping map on map.clientid = b.new_clientinfo and map.followerid = c.new_yff_relationship_managerid
	where b.new_clientinfo is not null and map.clientid is null
	
	--find client without follower
	insert into #tmp_client_follow_mapping(clientid, followerid)
	select new_clientinfo,'A0F765E7-B739-44EC-9EFA-30EFFC86B846' from dev02.reohk_mscrm.dbo.new_application 
	where new_clientinfo not in (select clientid from #tmp_client_follow_mapping)
	
	truncate table #tmp_user_follow_mapping
	insert into #tmp_user_follow_mapping(userid, followerid)
	select   a.new_yffuserid ,isnull(b.new_yff_relationship_managerid,'A0F765E7-B739-44EC-9EFA-30EFFC86B846') from dev02.reohk_mscrm.dbo.new_yffuser a
	left join dev02.reohk_mscrm.dbo.new_yff_relationship_manager b on a.new_follower = b.new_yff_relationship_managerid


	truncate table #tmp_register_stat
	insert into #tmp_register_stat(followerid,cnt,stat_type)
	select followerid,count(*),1 from #tmp_user_follow_mapping a
	inner join dev02.reohk_mscrm.dbo.new_yffuser b on a.userid = b.new_yffuserid
	where b.new_registertime >= @p_EndDate and b.new_registertime < DATEADD(dd,1,@p_EndDate)
	group by followerid

	insert into #tmp_register_stat(followerid,cnt,stat_type)
	select followerid,count(*),2 from #tmp_user_follow_mapping a
	inner join dev02.reohk_mscrm.dbo.new_yffuser b on a.userid = b.new_yffuserid
	where b.new_registertime < DATEADD(dd,1,@p_EndDate)
	group by followerid


	truncate table #tmp_client_account_opened
	insert into #tmp_client_account_opened(clientid,createdon)
	select b.new_clientinfo,CASE WHEN b.new_hscreatetime is null then b.new_wmcreatetime WHEN b.new_wmcreatetime is null then b.new_hscreatetime
	WHEN b.new_hscreatetime < b.new_wmcreatetime then b.new_hscreatetime ELSE b.new_wmcreatetime END
	 from dev02.reohk_mscrm.dbo.new_yffclientinfo a
	inner join dev02.reohk_mscrm.dbo.new_application b on a.new_yffclientinfoid = b.new_clientinfo 
	where b.new_hscreatetime is not null or b.new_wmcreatetime is not null

	truncate table #tmp_account_opened_stat
	insert into #tmp_account_opened_stat(followerid,cnt,stat_type)
	select b.followerid,count(1),1 as open_account_count from #tmp_client_account_opened a
	left join #tmp_client_follow_mapping b on a.clientid = b.clientid
	where a.createdon>=@p_EndDate and a.createdon < DATEADD(dd,1,@p_EndDate)
	group by b.followerid
	order by 2 desc

	insert into #tmp_account_opened_stat(followerid,cnt,stat_type)
	select b.followerid,count(1),2 as open_account_count from #tmp_client_account_opened a
	left join #tmp_client_follow_mapping b on a.clientid = b.clientid
	group by b.followerid
	order by 2 desc


	/*
	Deposit History
	*/
	insert into #tmp_client_statement(clientid,direction,hkd_amount,business,txdate)
	select new_client,1,new_amount * dbo.fn_getfxrate(new_currency,'HKD') AS HKD_AMOUNT, CASE new_business_type WHEN 100000000 THEN 'Broker'  WHEN 100000001 THEN 'WM' ELSE 'Unknown' END as business,new_tx_date
	from dev02.reohk_mscrm.dbo.new_yff_user_deposit_history
	where new_client is not null
	/*
	Withdrawal history
	*/
	--from Ins withdrawal
	insert into #tmp_client_statement(clientid,direction,hkd_amount,business,txdate)
	select ISNULL(a.new_clientinfo,b.new_clientinfo),2,new_amount * dbo.fn_getfxrate(
	CASE a.new_Currency WHEN 100000001 THEN 'USD' WHEN 100000002 THEN 'CNY' ELSE 'HKD' END,'HKD')
	,'Broker',a.new_TxDate
	 from dev02.reohk_mscrm.dbo.new_inscashwithdraw a
	left join dev02.reohk_mscrm.dbo.new_application b on b.new_Applicationid = a.new_Application
	WHERE (a.new_clientinfo is not null or b.new_clientinfo is not null) AND a.new_ProcessStatus=100000021
	and a.new_Business = 100000000


	--from CMB withdrawal
	insert into #tmp_client_statement(clientid,direction,hkd_amount,business,txdate)
	select c.new_clientinfo,2,a.new_amount *dbo.fn_getfxrate(a.new_currency,'HKD'), 'Broker',a.new_txdate
	 from dev02.reohk_mscrm.dbo.new_cmbchinawithdrawal a
	inner join dev02.reohk_mscrm.dbo.new_cmbchinaaccount b on a.new_cmbaccount = b.new_cmbchinaaccountid
	inner join dev02.reohk_mscrm.dbo.new_application c on b.new_application = c.new_applicationId
	WHERE a.new_txdate is not null and a.new_approverresult = 100000001

	-- from Minsheng Withdrawal
	insert into #tmp_client_statement(clientid,direction,hkd_amount,business,txdate)
	select c.new_clientinfo,2,a.new_amt *dbo.fn_getfxrate(a.new_ccy,'HKD'), 'Broker',CAST(a.new_tradat AS DATETIME)
	 from dev02.reohk_mscrm.dbo.new_msbwithdrawalstatement a
	inner join dev02.reohk_mscrm.dbo.new_application c on a.new_application = c.new_applicationId
	WHERE a.new_tradat is not null and a.new_processstatus = 100000006 AND new_sucflg = 1

	--from FNZ Withdrawal
	insert into #tmp_client_statement(clientid,direction,hkd_amount,business,txdate)
	select ISNULL(a.new_client_info,b.new_clientinfo),2,a.new_amount * dbo.fn_getfxrate(a.new_currency,'HKD'),'WM',new_fundingdate
	 from dev02.reohk_mscrm.dbo.new_fnzpaymentfile a
	left join dev02.reohk_mscrm.dbo.new_application b on a.new_application = b.new_applicationId
	where a.new_client_info is not null or b.new_clientinfo is not null


	--deposit stats
	truncate table #tmp_statement_stat
	insert into #tmp_statement_stat(followerid,hkd_amount,cnt,direction,stat_type)
	select a.followerid,sum(b.hkd_amount) as hkd_amount,count(distinct a.clientid) cnt,b.direction,1 as stat_type from #tmp_client_follow_mapping a
	inner join #tmp_client_statement b on a.clientid = b.clientid and b.direction =1--deposit
	where txdate>=@p_EndDate and txdate < DATEADD(dd,1,@p_EndDate)
	group by a.followerid,direction

	insert into #tmp_statement_stat(followerid,hkd_amount,cnt,direction,stat_type)
	select a.followerid,sum(b.hkd_amount) as hkd_amount,count(distinct a.clientid) cnt,b.direction,2 from #tmp_client_follow_mapping a
	inner join #tmp_client_statement b on a.clientid = b.clientid and b.direction =1--deposit
	where txdate < DATEADD(dd,1,@p_EndDate)
	group by a.followerid,b.direction

	insert into #tmp_statement_stat(followerid,hkd_amount,cnt,direction,stat_type)
	select a.followerid,sum(b.hkd_amount) as hkd_amount,count(distinct a.clientid) cnt,b.direction,1 from #tmp_client_follow_mapping a
	inner join #tmp_client_statement b on a.clientid = b.clientid and b.direction =2--deposit
	where txdate>=@p_EndDate and txdate < DATEADD(dd,1,@p_EndDate)
	group by a.followerid,direction

	insert into #tmp_statement_stat(followerid,hkd_amount,cnt,direction,stat_type)
	select a.followerid,sum(b.hkd_amount) as hkd_amount,count(distinct a.clientid) cnt,b.direction,2 from #tmp_client_follow_mapping a
	inner join #tmp_client_statement b on a.clientid = b.clientid and b.direction =2--deposit	
	where txdate < DATEADD(dd,1,@p_EndDate)
	group by a.followerid,direction


	select  CONVERT(VARCHAR(20),DATEADD(hh,8,@p_EndDate),101) as 日期,a.name as 客户经理,
	ISNULL(reg_cur_stat.cnt,0) as 注册人数, ISNULL(reg_his_stat.cnt,0) as 总注册人数,
	ISNULL(acct_cur_stat.cnt,0) as 开户数, ISNULL(acct_his_stat.cnt,0) as 总开会数,
	ISNULL(deposit_cur_stat.cnt,0) as 入金客户数,ISNULL(deposit_cur_stat.hkd_amount,0) as 入金金额,
	ISNULL(deposit_his_stat.cnt,0) as 总入金客户数,ISNULL(deposit_his_stat.hkd_amount,0) as 总入金金额,
	ISNULL(withdrawal_cur_stat.cnt,0) as 出金客户数,ISNULL(withdrawal_cur_stat.hkd_amount,0) as 出金金额,
	ISNULL(withdrawal_his_stat.cnt,0) as 总出金客户数,ISNULL(withdrawal_his_stat.hkd_amount,0) as 总出金金额
	 from #tmp_rm a
	left join #tmp_register_stat reg_cur_stat on a.followerid = reg_cur_stat.followerid and reg_cur_stat.stat_type = 1--current
	left join #tmp_register_stat reg_his_stat on a.followerid = reg_his_stat.followerid and reg_his_stat.stat_type = 2--history
	left join #tmp_account_opened_stat acct_cur_stat on a.followerid = acct_cur_stat.followerid and acct_cur_stat.stat_type = 1--current
	left join #tmp_account_opened_stat acct_his_stat on a.followerid = acct_his_stat.followerid and acct_his_stat.stat_type = 2--current
	left join #tmp_statement_stat deposit_cur_stat on a.followerid = deposit_cur_stat.followerid and deposit_cur_stat.direction=1 and deposit_cur_stat.stat_type=1
	left join #tmp_statement_stat deposit_his_stat on a.followerid = deposit_his_stat.followerid and deposit_his_stat.direction=1 and deposit_his_stat.stat_type=2
	left join #tmp_statement_stat withdrawal_cur_stat on a.followerid = withdrawal_cur_stat.followerid and withdrawal_cur_stat.direction=2 and withdrawal_cur_stat.stat_type=1
	left join #tmp_statement_stat withdrawal_his_stat on a.followerid = withdrawal_his_stat.followerid and withdrawal_his_stat.direction=2 and withdrawal_his_stat.stat_type=2
END
GO