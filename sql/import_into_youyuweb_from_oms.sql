
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


--holding
INSERT INTO OPENQUERY(dachenguat,'select id,account_id,business_type,market,security_id,security_type,date,price,shares,cost,market_value,currency,remarks,createdon,modifiedon from account_holding') 
select CONCAT(b.id,'_',1,'_',1,'_',CASE currency WHEN 'HKD' THEN 'hk' ELSE 'us' END,CASE currency WHEN 'USD' THEN stock_code ELSE SUBSTRING('00000',1,5-len(stock_code)) + stock_code END,convert(varchar(8),getdate(),112)) as id,
b.id as account_id,1 as business_type,CASE currency WHEN 'HKD' THEN 'hk' ELSE 'us' END as market,CASE currency WHEN 'USD' THEN 'us' + stock_code ELSE  'hk' + SUBSTRING('00000',1,5-len(stock_code)) + stock_code END as security_id,
1 as security_type,CAST(convert(varchar(8),getdate(),112) as datetime),a.CLOSE_PRICE,a.CURRENT_AMOUNT as shares,'0' as cost,a.MARKET_VALUE_CUR as market_value,
a.currency,'imported by sql',getdate(),getdate()

 from openquery([OMS], 'select a.*,c.dict_prompt currency
				from hs_his.totalstkvalue a,hs_user.sysdictionary c 
				where a.money_type=c.subentry and c.dict_entry=1086
				and client_id in(''5012683'',''5012681'')
				and a.init_Date = (select max(init_Date) from hs_his.totalfund)'
)
a inner join openquery(dachenguat,'select * from account') b on a.client_id = b.hs_client_id
where  CONCAT(b.id,'_',1,'_',1,'_',CASE currency WHEN 'HKD' THEN 'hk' ELSE 'us' END,CASE currency WHEN 'USD' THEN stock_code ELSE SUBSTRING('00000',1,5-len(stock_code)) + stock_code END,convert(varchar(8),getdate(),112)) not in (select id from OPENQUERY(dachenguat,'select id from account_holding'));



-------------------import transactions ------------------------
insert into openquery(dachenguat,'select id,external_transaction_id,account_id,business_type,security_id,security_type,direction,price,shares,amount,currency,tx_time,remarks,createdon,modifiedon from transaction')
select CAST(NEWID() as varchar(100)),CONCAT(a.trade_date,'_',a.refno) as external_id,b.id,1 as business_type,CASE ccy WHEN 'USD' THEN 'us' + stock_code ELSE  'hk' + SUBSTRING('00000',1,5-len(stock_code)) + stock_code END as security_id,
1 as security_type,CASE a.ENTRUST_BS WHEN 'B' THEN 1 ELSE 2 END as direction,a.average_price as price,a.shares,a.total_amount,ccy,cast(cast(a.trade_date as varchar(10)) as datetime) as tx_date,'imported by sql' as remarks, getdate() as createdon, getdate() as modifiedon
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
	   inner join openquery(dachenguat,'select * from account where hs_client_id in (''5012683'',''5012681'') ') b
	on a.client_id = b.hs_client_id
where CONCAT(a.trade_date,'_',a.refno) not in (select external_transaction_id from OPENQUERY(dachenguat,'select external_transaction_id from transaction')); 


