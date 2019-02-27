

if exists (select * from tempdb.dbo.sysobjects where id = object_id(N'tempdb..#tmp_val') and type='U')
   drop table #tmp_val
if exists (select * from tempdb.dbo.sysobjects where id = object_id(N'tempdb..#tmp_col') and type='U')
   drop table #tmp_col
if exists (select * from tempdb.dbo.sysobjects where id = object_id(N'tempdb..#tmp_change') and type='U')
   drop table #tmp_change
if exists (select * from tempdb.dbo.sysobjects where id = object_id(N'tempdb..#tmp_lastest') and type='U')
   drop table #tmp_lastest
if exists (select * from tempdb.dbo.sysobjects where id = object_id(N'tempdb..#tmp_report') and type='U')
   drop table #tmp_report
if exists (select * from tempdb.dbo.sysobjects where id = object_id(N'tempdb..#tmp_STRINGMAPBASE') and type='U')
   drop table #tmp_STRINGMAPBASE


create table #tmp_val(TransactionId uniqueidentifier, id int, val nvarchar(max), colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int )
create table #tmp_col(TransactionId uniqueidentifier,id int,  val nvarchar(max), colname varchar(max))
create table #tmp_change(id int IDENTITY(1,1), TransactionId uniqueidentifier,val nvarchar(max),colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int)
create table #tmp_lastest(ObjectId uniqueidentifier,val nvarchar(max),colname varchar(max))
create table #tmp_report(ObjectId uniqueidentifier,ObjectTypeCode int,oldval nvarchar(max),newval nvarchar(max),colname varchar(max))
create table #tmp_STRINGMAPBASE(ObjectTypeCode int, AttributeName varchar(max),AttributeValue nvarchar(max),value nvarchar(max))

truncate table #tmp_val
insert into #tmp_val(TransactionId, id, val,colname,UserId,ObjectId,CreatedOn,ObjectTypeCode)
select  a.TransactionId , v.id,v.val as val, null as colname,a.UserId,a.ObjectId, a.CreatedOn,a.ObjectTypeCode
from dev02.reohk_mscrm.dbo.auditbase a with(nolock)  
inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase b with(nolock)  on a.ObjectId = b.new_yffclientcontactId
INNER JOIN dev02.reohk_mscrm.dbo.new_yffclientinfoBase c  with(nolock) on c.new_yffclientinfoId = b.new_clientid
INNER JOIN dev02.reohk_mscrm.dbo.systemuser u  with(nolock) on a.UserId = u.SystemUserId
 CROSS APPLY dbo.string_split(a.ChangeData,'~')v 
 where a.CreatedOn >= '2019-02-14' --AND  TransactionId = '6A6B9533-2530-E911-80DD-00155D084707'
 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')

truncate table #tmp_col
insert into #tmp_col(TransactionId, id, val,colname)
select a.TransactionId , v.id, null as val, v.val as colname
from dev02.reohk_mscrm.dbo.auditbase a  
inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase b on a.ObjectId = b.new_yffclientcontactId
INNER JOIN dev02.reohk_mscrm.dbo.new_yffclientinfoBase c  with(nolock) on c.new_yffclientinfoId = b.new_clientid
INNER JOIN dev02.reohk_mscrm.dbo.systemuser u on a.UserId = u.SystemUserId
 CROSS APPLY dbo.string_split(LEFT(RIGHT(AttributeMask, (LEN(AttributeMask)-1)), (LEN(RIGHT(AttributeMask, (LEN(AttributeMask)-1)))-1)),',')v
 where a.CreatedOn >= '2019-02-14'
 AND u.FullName NOT IN('Reo Admin','spdev1 Reo')


 --change history data
 truncate table #tmp_change
 insert into #tmp_change(TransactionId,UserId,ObjectId,CreatedOn,ObjectTypeCode, val,colname)
  select  b.TransactionId,b.UserId,b.ObjectId,b.CreatedOn,b.ObjectTypeCode,b.val,d.LogicalName
  from #tmp_col a 
 inner join #tmp_val b on a.TransactionId = b.TransactionId and a.id = b.id
 inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase c on c.new_yffclientcontactId = b.ObjectId
 inner join dev02.reohk_mscrm.[MetadataSchema].[Attribute] d on d.ColumnNumber = a.colName
 inner join dev02.reohk_mscrm.[MetadataSchema].[Entity] e on e.EntityId = d.EntityId
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
  from  dev02.reohk_mscrm.dbo.new_yffclientcontactBase a
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
select  c.new_yffclientinfoId as ObjectId,ISNULL(c.new_chinesename,CONCAT(c.new_enfirstname,' ',c.new_enlastname)) as Client_Name, 
CONCAT(contmap.value ,'_', a.colName) AS colName,
CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'')) ELSE  ISNULL(oldmap.value,a.oldval) END as oldValue, 
CASE WHEN dbo.string_split_first(a.oldval,',') IN('new_reoisocountry','systemuser','new_aecode_settings') THEN dbo.getLookupName(dbo.string_split_first(a.oldval,','), a.newval) ELSE  ISNULL(newmap.value,a.newval) END as newValue
--ISNULL(newmap.value,a.newval) as newValue,
--dbo.string_split_first(a.oldval,',') as lookupentity,
--replace(a.oldval,concat(dbo.string_split_first(a.oldval,','),','),'') as lkid
  from #tmp_report a
inner join dev02.reohk_mscrm.dbo.new_yffclientcontactBase b on a.ObjectId = b.new_yffclientcontactId
INNER JOIN dev02.reohk_mscrm.dbo.new_yffclientinfo c on c.new_yffclientinfoId = b.new_clientId
left join #tmp_STRINGMAPBASE oldmap   on a.ObjectTypeCode = oldmap.ObjectTypeCode and a.colname = oldmap.AttributeName   and  a.oldval = oldmap.AttributeValue
left join #tmp_STRINGMAPBASE newmap   on a.ObjectTypeCode = newmap.ObjectTypeCode and a.colname = newmap.AttributeName   and  a.newval = newmap.AttributeValue
LEFT JOIN #tmp_STRINGMAPBASE contmap  on a.ObjectTypeCode = contmap.ObjectTypeCode and 'new_contacttype' = contmap.AttributeName  and  b.new_contacttype = contmap.AttributeValue
order by c.new_yffclientinfoId ASC
