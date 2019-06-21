

USE ReoHK_MSCRM
GO
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_apicalllog_report]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_apicalllog_report
GO

CREATE PROCEDURE dbo.sp_apicalllog_report(@p_EndDate DATETIME = NULL) AS
BEGIN
    IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_url') AND type='U')
	   DROP TABLE #tmp_url
	DECLARE @l_Date DATETIME;
	SET @l_Date = GETDATE();
	IF @p_EndDate IS NOT NULL
	   SET @l_Date = @p_EndDate;
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
	where createdon > convert( varchar(20),@l_Date,110)
	group by b.url,b.name
	order by  3 desc;
END
GO
--exec dbo.sp_apicalllog_report '2019-05-30';