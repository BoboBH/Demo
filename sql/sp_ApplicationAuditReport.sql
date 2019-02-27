

--Author:bobo huang
--Create Date:2019-02-18
--Description:Application Audit Report
--PS:Replace dev02.reohk_mscrm. with empty string while load on target server;
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_ApplicationAuditReport]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_ApplicationAuditReport
GO
CREATE PROCEDURE dbo.sp_ApplicationAuditReport(@p_EndDate DATETIME) AS
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
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_finalreport') AND type='U')
	   DROP TABLE #tmp_finalreport
	create table #tmp_val(TransactionId uniqueidentifier, id int, val nvarchar(max), colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int )
	create table #tmp_col(TransactionId uniqueidentifier,id int,  val nvarchar(max), colname varchar(max))
	create table #tmp_change(id int IDENTITY(1,1), TransactionId uniqueidentifier,val nvarchar(max),colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int)
	create table #tmp_lastest(ObjectId uniqueidentifier,val nvarchar(max),colname varchar(max))
	create table #tmp_report(ObjectId uniqueidentifier,ObjectTypeCode int,oldval nvarchar(max),newval nvarchar(max),colname varchar(max))
	create table #tmp_STRINGMAPBASE(ObjectTypeCode int, AttributeName varchar(max),AttributeValue nvarchar(max),value nvarchar(max))
	create table #tmp_finalreport(ObjectId uniqueidentifier,objectName NVARCHAR(MAX),colname varchar(max),oldval nvarchar(max),newval nvarchar(max))
	truncate table #tmp_val
	insert into #tmp_val(TransactionId, id, val,colname,UserId,ObjectId,CreatedOn,ObjectTypeCode)
	select  a.TransactionId , v.id,v.val as val, null as colname,a.UserId,a.ObjectId, a.CreatedOn,a.ObjectTypeCode
	from dev02.reohk_mscrm.dbo.auditbase a   with(nolock) 
	inner join dev02.reohk_mscrm.dbo.new_applicationBase b  with(nolock) on a.ObjectId = b.new_applicationId
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
		CROSS APPLY dbo.string_split(a.ChangeData,'~')v 
	where a.CreatedOn >= DATEADD(hh,-8,ISNULL(@p_EndDate,GETDATE()))
	AND u.FullName NOT IN('Reo Admin','spdev1 Reo')
	AND a.AttributeMask  LIKE ',%,'

	truncate table #tmp_col
	insert into #tmp_col(TransactionId, id, val,colname)
	select a.TransactionId , v.id, null as val, v.val as colname
	from dev02.reohk_mscrm.dbo.auditbase a  with(nolock)  
	inner join dev02.reohk_mscrm.dbo.new_applicationBase b  with(nolock) on a.ObjectId = b.new_applicationId
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
		CROSS APPLY dbo.string_split(LEFT(RIGHT(AttributeMask, (LEN(AttributeMask)-1)), (LEN(RIGHT(AttributeMask, (LEN(AttributeMask)-1)))-1)),',')v
	where a.CreatedOn >= DATEADD(hh,-8,ISNULL(@p_EndDate,GETDATE()))
	AND u.FullName NOT IN('Reo Admin','spdev1 Reo')

	 --change history data
	 truncate table #tmp_change
	 insert into #tmp_change(TransactionId,UserId,ObjectId,CreatedOn,ObjectTypeCode, val,colname)
	  select  b.TransactionId,b.UserId,b.ObjectId,b.CreatedOn,b.ObjectTypeCode,b.val,d.LogicalName
	  from #tmp_col a 
	 inner join #tmp_val b on a.TransactionId = b.TransactionId and a.id = b.id
	 inner join dev02.reohk_mscrm.dbo.new_applicationBase c  with(nolock) on c.new_applicationId = b.ObjectId
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Attribute] d  with(nolock) on d.ColumnNumber = a.colName
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Entity] e  with(nolock) on e.EntityId = d.EntityId
	 where e.LogicalName = 'new_application' and  d.LogicalName in
	 ('new_chfullname','new_enfirstname','new_enlastname','new_middle_name','new_gender','new_birthdaydate','new_idtype','new_idnumber','new_issuecountrycode','new_idexpiry','new_id_effect_date','new_company','new_industrytype','new_jobstatus','new_position','new_mobilephone','emailaddress','new_v2addresscountry','new_v2addresscountrycode','new_v2addressdetail','new_countryofbirthcode','new_openhsaccount','new_openfnzaccount','new_witnesstype','new_assetproperty','new_brmaxexposure','new_tradefare','new_brservicefare','new_brorganflag','new_feecode','new_taxband')
	 order by b.CreatedOn asc
	 --Lastest data
	truncate table #tmp_lastest
	insert into #tmp_lastest(ObjectId,colname,val)
	select new_applicationId as ObjectId,CAST(colname as varchar(max)) as colname, val from( 
	select a.new_applicationId, 
	CAST(a.new_chfullname                   AS NVARCHAR(MAX)) AS new_chfullname                 ,               
	CAST(a.new_enfirstname                  AS NVARCHAR(MAX)) AS new_enfirstname                ,
	CAST(a.new_enlastname                   AS NVARCHAR(MAX)) AS new_enlastname                 ,
	CAST(a.new_middle_name                  AS NVARCHAR(MAX)) AS new_middle_name                ,
	CAST(a.new_gender                       AS NVARCHAR(MAX)) AS new_gender                     ,
	CAST(a.new_birthdaydate                 AS NVARCHAR(MAX)) AS new_birthdaydate               ,
	CAST(a.new_idtype                       AS NVARCHAR(MAX)) AS new_idtype                     ,
	CAST(a.new_idnumber                     AS NVARCHAR(MAX)) AS new_idnumber                   ,
	CAST(a.new_issuecountrycode             AS NVARCHAR(MAX)) AS new_issuecountrycode           ,
	CAST(a.new_idexpiry             　　　　AS NVARCHAR(MAX)) AS new_idexpiry          　　　　 ,
	CAST(a.new_id_effect_date               AS NVARCHAR(MAX)) AS new_id_effect_date             ,
	CAST(a.new_company                      AS NVARCHAR(MAX)) AS new_company                    ,
	CAST(a.new_industrytype                 AS NVARCHAR(MAX)) AS new_industrytype               ,
	CAST(a.new_jobstatus                    AS NVARCHAR(MAX)) AS new_jobstatus                  ,
	CAST(a.new_position                     AS NVARCHAR(MAX)) AS new_position                   ,
	CAST(a.new_mobilephone                  AS NVARCHAR(MAX)) AS new_mobilephone                ,
	CAST(a.emailaddress                     AS NVARCHAR(MAX)) AS emailaddress                   ,
	CAST(a.new_v2addresscountry             AS NVARCHAR(MAX)) AS new_v2addresscountry           ,
	CAST(a.new_v2addresscountrycode         AS NVARCHAR(MAX)) AS new_v2addresscountrycode       ,
	CAST(a.new_v2addressdetail              AS NVARCHAR(MAX)) AS new_v2addressdetail            ,
	CAST(a.new_countryofbirthcode           AS NVARCHAR(MAX)) AS new_countryofbirthcode         ,
	CAST(a.new_openhsaccount                AS NVARCHAR(MAX)) AS new_openhsaccount              ,
	CAST(a.new_openfnzaccount               AS NVARCHAR(MAX)) AS new_openfnzaccount             ,
	CAST(a.new_witnesstype                  AS NVARCHAR(MAX)) AS new_witnesstype                ,
	CAST(a.new_assetproperty                AS NVARCHAR(MAX)) AS new_assetproperty              ,
	CAST(a.new_brmaxexposure                AS NVARCHAR(MAX)) AS new_brmaxexposure              ,
	CAST(a.new_tradefare                    AS NVARCHAR(MAX)) AS new_tradefare                  ,
	CAST(a.new_brservicefare                AS NVARCHAR(MAX)) AS new_brservicefare              ,
	CAST(a.new_brorganflag                  AS NVARCHAR(MAX)) AS new_brorganflag                ,
	CAST(a.new_feecode                      AS NVARCHAR(MAX)) AS new_feecode                    ,
	CAST(a.new_taxband                      AS NVARCHAR(MAX)) AS new_taxband                    
	  from  dev02.reohk_mscrm.dbo.new_applicationBase a with(nolock) 
	 where new_applicationId in(select ObjectId from #tmp_change)
	 ) as src  
	UNPIVOT (
			val FOR colname IN 
			(new_chfullname,new_enfirstname,new_enlastname,new_middle_name,new_gender,new_birthdaydate,new_idtype,new_idnumber,new_issuecountrycode,new_idexpiry,new_id_effect_date,new_company,new_industrytype,new_jobstatus,new_position,new_mobilephone,emailaddress,new_v2addresscountry,new_v2addresscountrycode,new_v2addressdetail,new_countryofbirthcode,new_openhsaccount,new_openfnzaccount,new_witnesstype,new_assetproperty,new_brmaxexposure,new_tradefare,new_brservicefare,new_brorganflag,new_feecode,new_taxband)) AS UNPVT;

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
	
	truncate table #tmp_finalreport
	insert into #tmp_finalreport(ObjectId,objectName,colName,oldVal, newVal)
	select  a.ObjectId,b.new_clientname as objectName, a.colName,
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'')) ELSE  ISNULL(oldmap.value,a.oldval) END as oldValue, 
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), a.newval) ELSE  ISNULL(newmap.value,a.newval) END as newValue
	--ISNULL(newmap.value,a.newval) as newValue,
	--dbo.string_split_first(a.oldval,',') as lookupentity,
	--replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'') as lkid
	  from #tmp_report a
	inner join dev02.reohk_mscrm.dbo.new_applicationBase b with(nolock)  on a.ObjectId = b.new_applicationId
	left join #tmp_STRINGMAPBASE oldmap   on a.ObjectTypeCode = oldmap.ObjectTypeCode and a.colname = oldmap.AttributeName   and  a.oldval = oldmap.AttributeValue
	left join #tmp_STRINGMAPBASE newmap   on a.ObjectTypeCode = newmap.ObjectTypeCode and a.colname = newmap.AttributeName   and  a.newval = newmap.AttributeValue

	UPDATE #tmp_finalreport
	SET oldval = CASE WHEN LEN(oldval) > 0 THEN CAST(DATEADD(hh,8, CAST(oldval as datetime)) AS NVARCHAR(MAX)) ELSE oldval END,
		 newval = CASE WHEN LEN(newval) > 0 THEN CAST(DATEADD(hh,8, CAST(newval as datetime)) AS NVARCHAR(MAX)) ELSE newval END
	where colName in ('new_birthdaydate','new_id_effect_date','new_idexpiry') 
	SELECT * FROM #tmp_finalreport
END
GO
GRANT EXEC ON  dbo.sp_ApplicationAuditReport TO cdbdev
GO