

--Author:bobo huang
--Create Date:2019-02-18
--Description:BrokerAccount Audit Report
--PS:Replace dev02.reohk_mscrm. with empty string while load on target server;
--Sample: exec sp_BrokerAccountAuditReport '2019-02-18'
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_BrokerAccountAuditReport]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_BrokerAccountAuditReport
GO
CREATE PROCEDURE dbo.sp_BrokerAccountAuditReport(@p_EndDate DATETIME) AS
BEGIN
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_val') AND type='U')
	   DROP TABLE #tmp_val
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_col') AND type='U')
	   DROP TABLE #tmp_col
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_change') AND type='U')
	   DROP TABLE #tmp_change
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_lastest') AND type='U')
	   DROP TABLE #tmp_lastest
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_report') AND type='U')
	   DROP TABLE #tmp_report
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_STRINGMAPBASE') AND type='U')
	   DROP TABLE #tmp_STRINGMAPBASE
	create table #tmp_val(TransactionId uniqueidentifier, id int, val nvarchar(max), colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int )
	create table #tmp_col(TransactionId uniqueidentifier,id int,  val nvarchar(max), colname varchar(max))
	create table #tmp_change(id int IDENTITY(1,1), TransactionId uniqueidentifier,val nvarchar(max),colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int)
	create table #tmp_lastest(ObjectId uniqueidentifier,val nvarchar(max),colname varchar(max))
	create table #tmp_report(ObjectId uniqueidentifier,ObjectTypeCode int,oldval nvarchar(max),newval nvarchar(max),colname varchar(max))
	create table #tmp_STRINGMAPBASE(ObjectTypeCode int, AttributeName varchar(max),AttributeValue nvarchar(max),value nvarchar(max))

	
	truncate table #tmp_val
	insert into #tmp_val(TransactionId, id, val,colname,UserId,ObjectId,CreatedOn,ObjectTypeCode)
	select  a.TransactionId , v.id,v.val as val, null as colname,a.UserId,a.ObjectId, a.CreatedOn,a.ObjectTypeCode
	from dev02.reohk_mscrm.dbo.auditbase a   with(nolock) 
	inner join dev02.reohk_mscrm.dbo.new_yffbrokeraccountBase b  with(nolock) on a.ObjectId = b.new_yffbrokeraccountId
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
	 CROSS APPLY dbo.string_split(a.ChangeData,'~')v 
	 where a.CreatedOn >= @p_EndDate
	 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')

	truncate table #tmp_col
	insert into #tmp_col(TransactionId, id, val,colname)
	select a.TransactionId , v.id, null as val, v.val as colname
	from dev02.reohk_mscrm.dbo.auditbase a  with(nolock)  
	inner join dev02.reohk_mscrm.dbo.new_yffbrokeraccountBase b with(nolock)  on a.ObjectId = b.new_yffbrokeraccountId
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
	 CROSS APPLY dbo.string_split(LEFT(RIGHT(AttributeMask, (LEN(AttributeMask)-1)), (LEN(RIGHT(AttributeMask, (LEN(AttributeMask)-1)))-1)),',')v
	 where a.CreatedOn >= @p_EndDate
	 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')
	 AND a.AttributeMask  LIKE ',%,'


	 --change history data
	 truncate table #tmp_change
	 insert into #tmp_change(TransactionId,UserId,ObjectId,CreatedOn,ObjectTypeCode, val,colname)
	  select  b.TransactionId,b.UserId,b.ObjectId,b.CreatedOn,b.ObjectTypeCode,b.val,d.LogicalName
	  from #tmp_col a 
	 inner join #tmp_val b on a.TransactionId = b.TransactionId and a.id = b.id
	 inner join dev02.reohk_mscrm.dbo.new_yffbrokeraccountBase c  with(nolock) on c.new_yffbrokeraccountId = b.ObjectId
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Attribute] d  with(nolock) on d.ColumnNumber = a.colName
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Entity] e  with(nolock) on e.EntityId = d.EntityId
	 where e.LogicalName = 'new_yffbrokeraccount' and  d.LogicalName in
	 ('new_assetproperty','new_brmaxexposure','new_tradefare','new_brservicefare','new_creditlimit','new_creditratio','new_ratekind','new_expirydate','new_exposureexpirydate','new_riskdegree')
	 order by b.CreatedOn asc


	 --Lastest data
	 truncate table #tmp_lastest
	 insert into #tmp_lastest(ObjectId,colname,val)
	 select new_yffbrokeraccountId as ObjectId,CAST(colname as varchar(max)) as colname, val from( 
	 select a.new_yffbrokeraccountId, 
	CAST(new_assetproperty               AS NVARCHAR(MAX)) AS new_assetproperty          ,
	CAST(new_brmaxexposure               AS NVARCHAR(MAX)) AS new_brmaxexposure          ,
	CAST(new_tradefare                   AS NVARCHAR(MAX)) AS new_tradefare              ,
	CAST(new_brservicefare               AS NVARCHAR(MAX)) AS new_brservicefare          ,
	CAST(new_creditlimit                 AS NVARCHAR(MAX)) AS new_creditlimit            ,
	CAST(new_creditratio                 AS NVARCHAR(MAX)) AS new_creditratio            ,
	CAST(new_ratekind                    AS NVARCHAR(MAX)) AS new_ratekind               ,
	CAST(new_expirydate                  AS NVARCHAR(MAX)) AS new_expirydate             ,
	CAST(new_exposureexpirydate          AS NVARCHAR(MAX)) AS new_exposureexpirydate     ,
	CAST(new_riskdegree                  AS NVARCHAR(MAX)) AS new_riskdegree              
	  from  dev02.reohk_mscrm.dbo.new_yffbrokeraccountBase a with(nolock) 
	 where new_yffbrokeraccountId in(select ObjectId from #tmp_change)
	 ) as src  
	UNPIVOT (
			val FOR colname IN 
			(new_assetproperty,new_brmaxexposure,new_tradefare,new_brservicefare,new_creditlimit,new_creditratio,new_ratekind,new_expirydate,new_exposureexpirydate,new_riskdegree)) AS UNPVT;

	--get entity change record 
	truncate table #tmp_report
	insert into #tmp_report(ObjectId,ObjectTypeCode ,colname,oldval,newval)
	select od.ObjectId,od.ObjectTypeCode, od.colname,od.val as oldVal ,nd.val as newVal from(
	 select a.* from #tmp_change a
	 inner join
	 (select ObjectId, colname,min(id) as id from #tmp_change
	 group by ObjectId, colname) as b  on a.id = b.id) as od
	 inner join #tmp_lastest nd on od.ObjectId=nd.ObjectId and od.colname = nd.colname
	 where od.val <> nd.val;
 
	truncate table #tmp_STRINGMAPBASE
	insert into #tmp_STRINGMAPBASE(ObjectTypeCode, AttributeName,AttributeValue, value)
	select ObjectTypeCode, AttributeName,AttributeValue, value from dev02.reohk_mscrm.dbo.STRINGMAPBASE 

	--get result except for lookup values
	select  a.ObjectId,b.new_hsclientid AS HS_ClientId, a.colName,
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'')) ELSE  ISNULL(oldmap.value,a.oldval) END as oldValue, 
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), a.newval) ELSE  ISNULL(newmap.value,a.newval) END as newValue
	--ISNULL(newmap.value,a.newval) as newValue,
	--dbo.string_split_first(a.oldval,',') as lookupentity,
	--replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'') as lkid
	  from #tmp_report a
	inner join dev02.reohk_mscrm.dbo.new_yffbrokeraccountBase b  with(nolock) on a.ObjectId = b.new_yffbrokeraccountId
	left join #tmp_STRINGMAPBASE oldmap   on a.ObjectTypeCode = oldmap.ObjectTypeCode and a.colname = oldmap.AttributeName   and  a.oldval = oldmap.AttributeValue
	left join #tmp_STRINGMAPBASE newmap   on a.ObjectTypeCode = newmap.ObjectTypeCode and a.colname = newmap.AttributeName   and  a.newval = newmap.AttributeValue


END
GO
GRANT EXEC ON  dbo.sp_BrokerAccountAuditReport TO cdbdev
GO

exec dbo.sp_BrokerAccountAuditReport '2019-01-01';