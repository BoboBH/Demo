

--Author:bobo huang
--Create Date:2019-02-18
--Description:Client Audit Report
--PS:Replace dev02.reohk_mscrm. with empty string while load on target server;
--Sample exec dbo.sp_ClientAuditReport '2019-02-18'
USE ReoHK_MSCRM
GO
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_ClientAuditReport]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_ClientAuditReport
GO
CREATE PROCEDURE dbo.sp_ClientAuditReport(@p_EndDate DATETIME) AS
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
	inner join dev02.reohk_mscrm.dbo.new_yffclientinfoBase b  with(nolock) on a.ObjectId = b.new_yffclientinfoId
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock)  on a.UserId = u.SystemUserId
	 CROSS APPLY dbo.string_split(a.ChangeData,'~')v 
	 where a.CreatedOn >= @p_EndDate
	 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')	

	truncate table #tmp_col
	insert into #tmp_col(TransactionId, id, val,colname)
	select a.TransactionId , v.id, null as val, v.val as colname
	from dev02.reohk_mscrm.dbo.auditbase a   with(nolock) 
	inner join dev02.reohk_mscrm.dbo.new_yffclientinfoBase b  with(nolock) on a.ObjectId = b.new_yffclientinfoId
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
	 inner join dev02.reohk_mscrm.dbo.new_yffclientinfoBase c  with(nolock) on c.new_yffclientinfoId = b.ObjectId
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Attribute] d  with(nolock) on d.ColumnNumber = a.colName
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Entity] e  with(nolock) on e.EntityId = d.EntityId
	 where e.LogicalName = 'new_yffclientinfo' and  d.LogicalName in
	 ('new_clientname','new_clienttype','new_clientstatus','new_expectedclosedate','new_chinesename','new_firstname','new_lastname','new_middle_name','new_idtype','new_idnumber','new_idexpiry','new_idissuecountrycode','new_birthday','new_gender','emailaddress','new_mobilephone','new_statementreportemail','new_countryofbirthcode','new_allow_ad','new_v2addresscountrycode','new_v2addressdetail','new_residentialcountry','new_residentialdetail','new_postalcountry','new_postaldetail','new_jobstatus','new_company','new_position')
	 order by b.CreatedOn asc


	 --Lastest data
	 truncate table #tmp_lastest
	 insert into #tmp_lastest(ObjectId,colname,val)
	 select new_yffclientinfoId as ObjectId,CAST(colname as varchar(max)) as colname, val from( 
	 select a.new_yffclientinfoId, 
	CAST(new_clientname                     AS NVARCHAR(MAX)) AS new_clientname           ,            
	CAST(new_clienttype                     AS NVARCHAR(MAX)) AS new_clienttype           ,
	CAST(new_clientstatus                   AS NVARCHAR(MAX)) AS new_clientstatus         ,
	CAST(new_expectedclosedate              AS NVARCHAR(MAX)) AS new_expectedclosedate    ,
	CAST(new_chinesename                    AS NVARCHAR(MAX)) AS new_chinesename          ,
	CAST(new_firstname                      AS NVARCHAR(MAX)) AS new_firstname            ,
	CAST(new_lastname                       AS NVARCHAR(MAX)) AS new_lastname             ,
	CAST(new_middle_name                    AS NVARCHAR(MAX)) AS new_middle_name          ,
	CAST(new_idtype                         AS NVARCHAR(MAX)) AS new_idtype               ,
	CAST(new_idnumber                       AS NVARCHAR(MAX)) AS new_idnumber             ,
	CAST(new_idexpiry                       AS NVARCHAR(MAX)) AS new_idexpiry             ,
	CAST(new_idissuecountrycode             AS NVARCHAR(MAX)) AS new_idissuecountrycode   ,
	CAST(new_birthday                       AS NVARCHAR(MAX)) AS new_birthday             ,
	CAST(new_gender                         AS NVARCHAR(MAX)) AS new_gender               ,
	CAST(emailaddress                       AS NVARCHAR(MAX)) AS emailaddress             ,
	CAST(new_mobilephone                    AS NVARCHAR(MAX)) AS new_mobilephone          ,
	CAST(new_statementreportemail           AS NVARCHAR(MAX)) AS new_statementreportemail ,
	CAST(new_countryofbirthcode             AS NVARCHAR(MAX)) AS new_countryofbirthcode   ,
	CAST(new_allow_ad                       AS NVARCHAR(MAX)) AS new_allow_ad             ,
	CAST(new_v2addresscountrycode           AS NVARCHAR(MAX)) AS new_v2addresscountrycode ,
	CAST(new_v2addressdetail                AS NVARCHAR(MAX)) AS new_v2addressdetail      ,
	CAST(new_residentialcountry             AS NVARCHAR(MAX)) AS new_residentialcountry   ,
	CAST(new_residentialdetail              AS NVARCHAR(MAX)) AS new_residentialdetail    ,
	CAST(new_postalcountry                  AS NVARCHAR(MAX)) AS new_postalcountry        ,
	CAST(new_postaldetail                   AS NVARCHAR(MAX)) AS new_postaldetail         ,
	CAST(new_jobstatus                      AS NVARCHAR(MAX)) AS new_jobstatus            ,
	CAST(new_company                        AS NVARCHAR(MAX)) AS new_company              ,
	CAST(new_position                       AS NVARCHAR(MAX)) AS new_position                         
	  from  dev02.reohk_mscrm.dbo.new_yffclientinfoBase a with(nolock) 
	 where new_yffclientinfoId in(select ObjectId from #tmp_change)
	 ) as src  
	UNPIVOT (
			val FOR colname IN 
			(new_clientname,new_clienttype,new_clientstatus,new_expectedclosedate,new_chinesename,new_firstname,new_lastname,new_middle_name,new_idtype,new_idnumber,new_idexpiry,new_idissuecountrycode,new_birthday,new_gender,emailaddress,new_mobilephone,new_statementreportemail,new_countryofbirthcode,new_allow_ad,new_v2addresscountrycode,new_v2addressdetail,new_residentialcountry,new_residentialdetail,new_postalcountry,new_postaldetail,new_jobstatus,new_company,new_position)) AS UNPVT;

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
	insert into #tmp_finalreport(ObjectId,objectName,colName,oldVal, newVal)
	select  a.ObjectId, ISNULL(b.new_chinesename, CONCAT(b.new_firstname,' ', b.new_enlastname)) AS ClientName, a.colName,
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'')) ELSE  ISNULL(oldmap.value,a.oldval) END as oldValue, 
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), a.newval) ELSE  ISNULL(newmap.value,a.newval) END as newValue
	--ISNULL(newmap.value,a.newval) as newValue,
	--dbo.string_split_first(a.oldval,',') as lookupentity,
	--replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'') as lkid
	  from #tmp_report a
	inner join dev02.reohk_mscrm.dbo.new_yffclientinfoBase b  with(nolock) on a.ObjectId = b.new_yffclientinfoId
	left join #tmp_STRINGMAPBASE oldmap   on a.ObjectTypeCode = oldmap.ObjectTypeCode and a.colname = oldmap.AttributeName   and  a.oldval = oldmap.AttributeValue
	left join #tmp_STRINGMAPBASE newmap   on a.ObjectTypeCode = newmap.ObjectTypeCode and a.colname = newmap.AttributeName   and  a.newval = newmap.AttributeValue

	--get client contaxt info audit report
	truncate table #tmp_val
	insert into #tmp_val(TransactionId, id, val,colname,UserId,ObjectId,CreatedOn,ObjectTypeCode)
	select  a.TransactionId , v.id,v.val as val, null as colname,a.UserId,a.ObjectId, a.CreatedOn,a.ObjectTypeCode
	from dev02.reohk_mscrm.dbo.auditbase a with(nolock)  
	inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase b with(nolock)  on a.ObjectId = b.new_yffclientcontactId
	INNER JOIN dev02.reohk_mscrm.dbo.new_yffclientinfoBase c  with(nolock) on c.new_yffclientinfoId = b.new_clientid
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
	 CROSS APPLY dbo.string_split(a.ChangeData,'~')v 
	 where a.CreatedOn >= @p_EndDate
	 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')

	truncate table #tmp_col
	insert into #tmp_col(TransactionId, id, val,colname)
	select a.TransactionId , v.id, null as val, v.val as colname
	from dev02.reohk_mscrm.dbo.auditbase a  
	inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase b  with(nolock) on a.ObjectId = b.new_yffclientcontactId
	INNER JOIN dev02.reohk_mscrm.dbo.new_yffclientinfoBase c  with(nolock) on c.new_yffclientinfoId = b.new_clientid
	INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
	 CROSS APPLY dbo.string_split(LEFT(RIGHT(AttributeMask, (LEN(AttributeMask)-1)), (LEN(RIGHT(AttributeMask, (LEN(AttributeMask)-1)))-1)),',')v
	 where a.CreatedOn >= @p_EndDate
	 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')


	 --change history data
	 truncate table #tmp_change
	 insert into #tmp_change(TransactionId,UserId,ObjectId,CreatedOn,ObjectTypeCode, val,colname)
	  select  b.TransactionId,b.UserId,b.ObjectId,b.CreatedOn,b.ObjectTypeCode,b.val,d.LogicalName
	  from #tmp_col a 
	 inner join #tmp_val b on a.TransactionId = b.TransactionId and a.id = b.id
	 inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase c  with(nolock) on c.new_yffclientcontactId = b.ObjectId
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Attribute] d  with(nolock) on d.ColumnNumber = a.colName
	 inner join dev02.reohk_mscrm.[MetadataSchema].[Entity] e  with(nolock) on e.EntityId = d.EntityId
	 where e.LogicalName = 'new_yffclientcontact' and  d.LogicalName in
	 ('new_contactstatus','new_countrycode','new_detail','new_firstname','new_lastname','new_phone')
	 order by b.CreatedOn asc


	 --Lastest data
	 truncate table #tmp_lastest
	 insert into #tmp_lastest(ObjectId,colname,val)
	 select new_yffclientcontactId as ObjectId,CAST(colname as varchar(max)) as colname, val from( 
	 select a.new_yffclientcontactId, 
		CAST(a.new_contactstatus                   AS NVARCHAR(MAX)) AS new_contactstatus                 ,               
		CAST(a.new_countrycode                  AS NVARCHAR(MAX)) AS new_countrycode                ,
		CAST(a.new_detail                   AS NVARCHAR(MAX)) AS new_detail                 ,
		CAST(a.new_firstname                  AS NVARCHAR(MAX)) AS new_firstname                ,
		CAST(a.new_lastname                       AS NVARCHAR(MAX)) AS new_lastname                     ,
		CAST(a.new_phone                      AS NVARCHAR(MAX)) AS new_phone                    
	  from  dev02.reohk_mscrm.dbo.new_yffclientcontactBase a with(nolock) 
	 where new_yffclientcontactId in(select ObjectId from #tmp_change)
	 ) as src  
	UNPIVOT (
			val FOR colname IN 
			(new_contactstatus,new_countrycode,new_detail,new_firstname,new_lastname,new_phone)) AS UNPVT;

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
 

	--get result except for lookup values
	insert into #tmp_finalreport(ObjectId,objectName,colName,oldVal, newVal)
	select  c.new_yffclientinfoId as ObjectId,ISNULL(c.new_chinesename,CONCAT(c.new_enfirstname,' ',c.new_enlastname)) as Client_Name, 
	CONCAT('Contact','_', contmap.value ,'_', a.colName) AS colName,
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'')) ELSE  ISNULL(oldmap.value,a.oldval) END as oldValue, 
	CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), a.newval) ELSE  ISNULL(newmap.value,a.newval) END as newValue
	--ISNULL(newmap.value,a.newval) as newValue,
	--dbo.string_split_first(a.oldval,',') as lookupentity,
	--replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'') as lkid
	  from #tmp_report a
	inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase b  with(nolock) on a.ObjectId = b.new_yffclientcontactId
	INNER JOIN dev02.reohk_mscrm.dbo.new_yffclientinfo c  with(nolock) on c.new_yffclientinfoId = b.new_clientId
	left join #tmp_STRINGMAPBASE oldmap   on a.ObjectTypeCode = oldmap.ObjectTypeCode and a.colname = oldmap.AttributeName   and  a.oldval = oldmap.AttributeValue
	left join #tmp_STRINGMAPBASE newmap   on a.ObjectTypeCode = newmap.ObjectTypeCode and a.colname = newmap.AttributeName   and  a.newval = newmap.AttributeValue
	LEFT JOIN #tmp_STRINGMAPBASE contmap  on a.ObjectTypeCode = contmap.ObjectTypeCode and 'new_contacttype' = contmap.AttributeName  and  b.new_contacttype = contmap.AttributeValue
	order by c.new_yffclientinfoId ASC
	
	UPDATE #tmp_finalreport
	SET oldval = CASE WHEN LEN(oldval) > 0 THEN CAST(DATEADD(hh,8, CAST(oldval as datetime)) AS NVARCHAR(MAX)) ELSE oldval END,
		 newval = CASE WHEN LEN(newval) > 0 THEN CAST(DATEADD(hh,8, CAST(newval as datetime)) AS NVARCHAR(MAX)) ELSE newval END
	where colName in ('new_birthday','new_idexpiry') 
	SELECT * FROM #tmp_finalreport
END
GO
GRANT EXEC ON  dbo.sp_ClientAuditReport TO cdbdev
GO
exec sp_ClientAuditReport '2018-02-18'