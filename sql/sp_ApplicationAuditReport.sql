

--Author:bobo huang
--Create Date:2019-02-18
--Description:Application Audit Report
--PS:Replace dev02.reohk_mscrm. with empty string while load on target server;
USE ReoHK_MSCRM
GO

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[sp_ApplicationAuditReport]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1) 
	DROP PROCEDURE dbo.sp_ApplicationAuditReport
GO
CREATE PROCEDURE dbo.sp_ApplicationAuditReport(@p_EndDate DATETIME = NULL) AS
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
	IF EXISTS (SELECT * FROM tempdb.dbo.sysobjects WHERE id = object_id(N'tempdb..#tmp_attr') AND type='U')
	   DROP TABLE #tmp_attr
	create table #tmp_attr(entname  nvarchar(max),entdisplayname nvarchar(max), colname nvarchar(max), dispalyname nvarchar(max))
	create table #tmp_val(TransactionId uniqueidentifier, id int, val nvarchar(max), colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int )
	create table #tmp_col(TransactionId uniqueidentifier,id int,  val nvarchar(max), colname varchar(max))
	create table #tmp_change(id int IDENTITY(1,1), TransactionId uniqueidentifier,val nvarchar(max),colname varchar(max),CreatedOn datetime, UserId uniqueidentifier, ObjectId uniqueidentifier, ObjectTypeCode int)
	create table #tmp_lastest(ObjectId uniqueidentifier,val nvarchar(max),colname varchar(max))
	create table #tmp_report(ObjectId uniqueidentifier,ObjectTypeCode int,oldval nvarchar(max),newval nvarchar(max),colname varchar(max))
	create table #tmp_STRINGMAPBASE(ObjectTypeCode int, AttributeName varchar(max),AttributeValue nvarchar(max),value nvarchar(max))
	create table #tmp_finalreport(ObjectId uniqueidentifier,objectName NVARCHAR(MAX),colname varchar(max),oldval nvarchar(max),newval nvarchar(max))

	truncate table #tmp_attr
	insert into #tmp_attr(entname,entdisplayname, colname, dispalyname)
		SELECT  EntityView.Name AS EntityName, LocalizedLabelView_1.Label AS EntityDisplayName,
		   AttributeView.Name AS AttributeName, LocalizedLabelView_2.Label AS AttributeDisplayName
		FROM    dev02.reohk_mscrm.dbo.LocalizedLabelView AS LocalizedLabelView_2 INNER JOIN
			   dev02.reohk_mscrm.dbo.AttributeView ON LocalizedLabelView_2.ObjectId = AttributeView.AttributeId RIGHT OUTER JOIN
			   dev02.reohk_mscrm.dbo.EntityView INNER JOIN
			   dev02.reohk_mscrm.dbo.LocalizedLabelView AS LocalizedLabelView_1 ON EntityView.EntityId = LocalizedLabelView_1.ObjectId ON
			   AttributeView.EntityId = EntityView.EntityId
		WHERE   LocalizedLabelView_1.ObjectColumnName = 'LocalizedName'
		 AND LocalizedLabelView_2.ObjectColumnName = 'DisplayName'
		 AND LocalizedLabelView_1.LanguageId = '1033'
		 AND LocalizedLabelView_2.LanguageId = '1033'
		 AND EntityView.Name IN ('new_application')
		ORDER BY EntityName, AttributeName

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
	where a.CreatedOn >= DATEADD(hh,-8,ISNULL('2019-08-20',GETDATE()))
	AND u.FullName NOT IN('Reo Admin','spdev1 Reo') AND LEN(AttributeMask) > 0
	
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
	 ('new_chfullname','new_enfirstname','new_enlastname','new_middle_name','new_gender','new_birthdaydate','new_idtype','new_idnumber','new_issuecountrycode','new_idexpiry','new_id_effect_date','new_company','new_industrytype','new_jobstatus','new_position','new_mobilephone','emailaddress','new_v2addresscountry','new_v2addresscountrycode','new_v2addressdetail','new_countryofbirthcode','new_openhsaccount','new_openfnzaccount','new_witnesstype','new_assetproperty','new_brmaxexposure','new_tradefare','new_brservicefare','new_brorganflag','new_feecode','new_taxband',
	 'new_additionalidcountry','new_additionalidcountrycode','new_additionalidexpiry','new_additionalidnumber','new_additionalidtype','new_need_northbound','new_sign_date','new_w8expirydate','new_w8signdate',
	  'new_allowad','new_annualincome','new_income_annual','new_income_sale_of_property','new_income_savings','new_incomebusiness','new_incomeearnings','new_incomefamilygift',
	  'new_incomepension','new_incomerental','new_incomesource','new_incomesourceinvestment','new_incomesourcesalary','new_invest_target_bonds',
	  'new_invest_target_funds','new_investderivativeexperience','new_investfundexperience','new_investment_experience_bonds','new_investment_experience_funds','new_investment_objective_capital_growth',
	  'new_investment_objective_dividend','new_investment_objective_hedging','new_investment_objective_speculation','new_investnetasset','new_investstockexperience','new_investstyle',
	  'new_investtarget','new_investtargetderavitive','new_investtargetfundbondtrust','new_investtargetstock','new_seconddepositaccount','new_secondmatedepositaccount',
	  'new_wealth_earnings_from_business','new_wealth_earnings_from_work','new_wealth_gift','new_wealth_investment','new_wealth_other','new_wealth_sale_of_assets','new_wealth_savings',
	  'new_years_of_working','new_yffdisclosurechinesename','new_yffdisclosurefirstname','new_yffdisclosurelastname','new_yffdisclosurerelation','new_yffdisclosureremarks'
	 )	 order by b.CreatedOn asc	
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
	CAST(a.new_taxband                      AS NVARCHAR(MAX)) AS new_taxband                    ,
	CAST(a.new_additionalidcountry          AS NVARCHAR(MAX)) AS new_additionalidcountry        ,
	CAST(a.new_additionalidcountrycode      AS NVARCHAR(MAX)) AS new_additionalidcountrycode    ,
	CAST(a.new_additionalidexpiry           AS NVARCHAR(MAX)) AS new_additionalidexpiry         ,
	CAST(a.new_additionalidnumber           AS NVARCHAR(MAX)) AS new_additionalidnumber         ,
	CAST(a.new_additionalidtype             AS NVARCHAR(MAX)) AS new_additionalidtype           ,
	CAST(a.new_need_northbound              AS NVARCHAR(MAX)) AS new_need_northbound            ,
	CAST(a.new_sign_date                    AS NVARCHAR(MAX)) AS new_sign_date                  ,
	CAST(a.new_w8expirydate                 AS NVARCHAR(MAX)) AS new_w8expirydate               ,
	CAST(a.new_w8signdate                   AS NVARCHAR(MAX)) AS new_w8signdate                 ,
	CAST(a.new_allowad                      AS NVARCHAR(MAX)) AS new_allowad                    ,
	CAST(a.new_annualincome                 AS NVARCHAR(MAX)) AS new_annualincome               ,
	CAST(a.new_income_annual                AS NVARCHAR(MAX)) AS new_income_annual              ,
	CAST(a.new_income_sale_of_property      AS NVARCHAR(MAX)) AS new_income_sale_of_property    ,
	CAST(a.new_income_savings               AS NVARCHAR(MAX)) AS new_income_savings             ,
	CAST(a.new_incomebusiness               AS NVARCHAR(MAX)) AS new_incomebusiness             ,
	CAST(a.new_incomeearnings               AS NVARCHAR(MAX)) AS new_incomeearnings             ,
	CAST(a.new_incomefamilygift             AS NVARCHAR(MAX)) AS new_incomefamilygift           ,
	CAST(a.new_incomepension                AS NVARCHAR(MAX)) AS new_incomepension              ,
	CAST(a.new_incomerental                 AS NVARCHAR(MAX)) AS new_incomerental               ,
	CAST(a.new_incomesource                 AS NVARCHAR(MAX)) AS new_incomesource               ,
	CAST(a.new_incomesourceinvestment       AS NVARCHAR(MAX)) AS new_incomesourceinvestment     ,
	CAST(a.new_incomesourcesalary           AS NVARCHAR(MAX)) AS new_incomesourcesalary         ,
	CAST(a.new_invest_target_bonds          AS NVARCHAR(MAX)) AS new_invest_target_bonds        ,
	CAST(a.new_invest_target_funds          AS NVARCHAR(MAX)) AS new_invest_target_funds        ,
	CAST(a.new_investderivativeexperience   AS NVARCHAR(MAX)) AS new_investderivativeexperience ,
	CAST(a.new_investfundexperience         AS NVARCHAR(MAX)) AS new_investfundexperience       ,
	CAST(a.new_investment_experience_bonds  AS NVARCHAR(MAX)) AS new_investment_experience_bonds ,
	CAST(a.new_investment_experience_funds  AS NVARCHAR(MAX)) AS new_investment_experience_funds ,
	CAST(a.new_investment_objective_capital_growth AS NVARCHAR(MAX)) AS new_investment_objective_capital_growth,
	CAST(a.new_investment_objective_dividend AS NVARCHAR(MAX)) AS new_investment_objective_dividend,
	CAST(a.new_investment_objective_hedging AS NVARCHAR(MAX)) AS new_investment_objective_hedging,
	CAST(a.new_investment_objective_speculation AS NVARCHAR(MAX)) AS new_investment_objective_speculation,
	CAST(a.new_investnetasset               AS NVARCHAR(MAX)) AS new_investnetasset             ,
	CAST(a.new_investstockexperience        AS NVARCHAR(MAX)) AS new_investstockexperience      ,
	CAST(a.new_investstyle                  AS NVARCHAR(MAX)) AS new_investstyle                ,
	CAST(a.new_investtarget                 AS NVARCHAR(MAX)) AS new_investtarget               ,
	CAST(a.new_investtargetderavitive       AS NVARCHAR(MAX)) AS new_investtargetderavitive     ,
	CAST(a.new_investtargetfundbondtrust    AS NVARCHAR(MAX)) AS new_investtargetfundbondtrust  ,
	CAST(a.new_investtargetstock            AS NVARCHAR(MAX)) AS new_investtargetstock          ,
	CAST(a.new_seconddepositaccount         AS NVARCHAR(MAX)) AS new_seconddepositaccount       ,
	CAST(a.new_secondmatedepositaccount     AS NVARCHAR(MAX)) AS new_secondmatedepositaccount   ,
	CAST(a.new_wealth_earnings_from_business AS NVARCHAR(MAX)) AS new_wealth_earnings_from_business ,
	CAST(a.new_wealth_earnings_from_work    AS NVARCHAR(MAX)) AS new_wealth_earnings_from_work  ,
	CAST(a.new_wealth_gift                  AS NVARCHAR(MAX)) AS new_wealth_gift                ,
	CAST(a.new_wealth_investment            AS NVARCHAR(MAX)) AS new_wealth_investment          ,
	CAST(a.new_wealth_other                 AS NVARCHAR(MAX)) AS new_wealth_other               ,
	CAST(a.new_wealth_sale_of_assets        AS NVARCHAR(MAX)) AS new_wealth_sale_of_assets      ,
	CAST(a.new_wealth_savings               AS NVARCHAR(MAX)) AS new_wealth_savings             ,
	CAST(a.new_years_of_working             AS NVARCHAR(MAX)) AS new_years_of_working           ,               
	CAST(a.new_yffdisclosurechinesename     AS NVARCHAR(MAX)) AS new_yffdisclosurechinesename   ,
	CAST(a.new_yffdisclosurefirstname       AS NVARCHAR(MAX)) AS new_yffdisclosurefirstname     ,
	CAST(a.new_yffdisclosurelastname        AS NVARCHAR(MAX)) AS new_yffdisclosurelastname      ,
	CAST(a.new_yffdisclosurerelation        AS NVARCHAR(MAX)) AS new_yffdisclosurerelation      ,
	CAST(a.new_yffdisclosureremarks         AS NVARCHAR(MAX)) AS new_yffdisclosureremarks    
	  from  dev02.reohk_mscrm.dbo.new_applicationBase a with(nolock) 
	 where new_applicationId in(select ObjectId from #tmp_change)
	 ) as src  
	UNPIVOT (
			val FOR colname IN 
			(new_chfullname,new_enfirstname,new_enlastname,new_middle_name,new_gender,new_birthdaydate,new_idtype,new_idnumber,new_issuecountrycode,new_idexpiry,new_id_effect_date,new_company,new_industrytype,new_jobstatus,new_position,new_mobilephone,emailaddress,new_v2addresscountry,new_v2addresscountrycode,new_v2addressdetail,new_countryofbirthcode,new_openhsaccount,new_openfnzaccount,new_witnesstype,new_assetproperty,new_brmaxexposure,new_tradefare,new_brservicefare,new_brorganflag,new_feecode,new_taxband,
			 new_additionalidcountry,new_additionalidcountrycode,new_additionalidexpiry,new_additionalidnumber,new_additionalidtype,new_need_northbound,new_sign_date,new_w8expirydate,new_w8signdate,
			  new_allowad,new_annualincome,new_income_annual,new_income_sale_of_property,new_income_savings,new_incomebusiness,new_incomeearnings,new_incomefamilygift,
			  new_incomepension,new_incomerental,new_incomesource,new_incomesourceinvestment,new_incomesourcesalary,new_invest_target_bonds,
			  new_invest_target_funds,new_investderivativeexperience,new_investfundexperience,new_investment_experience_bonds,new_investment_experience_funds,new_investment_objective_capital_growth,
			  new_investment_objective_dividend,new_investment_objective_hedging,new_investment_objective_speculation,new_investnetasset,new_investstockexperience,new_investstyle,
			  new_investtarget,new_investtargetderavitive,new_investtargetfundbondtrust,new_investtargetstock,new_seconddepositaccount,new_secondmatedepositaccount,
			  new_wealth_earnings_from_business,new_wealth_earnings_from_work,new_wealth_gift,new_wealth_investment,new_wealth_other,new_wealth_sale_of_assets,new_wealth_savings,
			  new_years_of_working,new_yffdisclosurechinesename,new_yffdisclosurefirstname,new_yffdisclosurelastname,new_yffdisclosurerelation,new_yffdisclosureremarks			
			)) AS UNPVT;

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
	where colName in ('new_birthdaydate','new_id_effect_date','new_idexpiry','new_additionalidexpiry','new_sign_date','new_w8expirydate','new_w8signdate') 

	SELECT fr.ObjectId,fr.objectName,att.dispalyname as colname,fr.oldval,fr.newval, DATEADD(hh,8,bcu.createdon) as LastModifiedOn,su.FullName as LastModifiedBy FROM #tmp_finalreport fr
	INNER JOIN(
		select a.ObjectId, a.CreatedOn,a.UserId,b.colname from #tmp_change a
		INNER JOIN(
		  SELECT ObjectId, MAX(Id) AS Id,colname from #tmp_change group by ObjectId,colname
		) b ON a.ObjectId = b.ObjectId AND a.id = b.Id
	) bcu on fr.ObjectId = bcu.ObjectId and fr.colname = bcu.colname
	INNER JOIN dev02.reohk_mscrm.dbo.SystemUserBase su on su.SystemUserId = bcu.UserId
	INNER JOIN #tmp_attr att on att.colname = fr.colname
	order by fr.ObjectId


END
GO
GRANT EXEC ON  dbo.sp_ApplicationAuditReport TO cdbdev
GO

--exec sp_ApplicationAuditReport '2019-08-28'