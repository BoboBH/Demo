

--Author:bobo huang
--Create Date:2019-02-18
--Description:Mangaes channel but not owns the channel
--PS:Replace dev02.reohk_mscrm. with empty string while load on target server;
--sample:exec dbo.sp_own_channel_not_manage
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_own_channel_not_manage]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_own_channel_not_manage
GO
CREATE PROCEDURE dbo.sp_own_channel_not_manage(@p_EndDate DATETIME = NULL) AS
BEGIN
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_managed_channel') AND type='U')
		   DROP TABLE #tmp_managed_channel
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_channel') AND type='U')
		   DROP TABLE #tmp_channel

	create table #tmp_managed_channel(userid uniqueidentifier,channel_code varchar(20),source int, channel_name nvarchar(max))
	create table #tmp_channel(channel_code varchar(20),channel_name nvarchar(max))

	truncate table #tmp_channel
	insert into #tmp_channel(channel_code,channel_name)
	select new_channel_code,new_name from dev02.reohk_mscrm.dbo.new_smcchannel 
	where new_process_status = 100000001
	truncate table #tmp_managed_channel
	insert into #tmp_managed_channel(userid, channel_code,source)
	select  a.new_user,v.val,1 from dev02.reohk_mscrm.dbo.new_yff_relationship_manager  a
	CROSS APPLY dbo.string_split(a.new_managed_channel,',')v 
	where len(v.val) > 0
	insert into #tmp_managed_channel(userid, channel_code,source,channel_name)
	select new_internal_owner,new_channel_code ,2,new_name from dev02.reohk_mscrm.dbo.new_smcchannel 
	where new_internal_owner is not null and len(new_channel_code) > 0 and new_process_status = 100000001
	--Owns channel but not manage the channel	
	select u.FullName, a.channel_code,ch.channel_name from #tmp_managed_channel a
	inner join dev02.reohk_mscrm.dbo.systemuser u on a.userid = u.systemuserid
	inner join #tmp_channel ch on ch.channel_code = a.channel_code
	left join #tmp_managed_channel b on a.userid = b.userid and a.channel_code = b.channel_code and b.source =1
	where a.source = 2 and b.userid is null	
END
GO