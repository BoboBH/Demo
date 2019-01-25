#%matplotlib inline
import pandas
import numpy
import datetime
from matplotlib import pyplot as plt
import matplotlib.transforms as mtransforms
import time
print("load excel file....")
file = "d:\\yaml\\data\\summary of fund.xlsm"
Holdings = pandas.read_excel(file, sheet_name="Top Holdings")
Industry = pandas.read_excel(file, sheet_name="Industry")
CreditRating = pandas.read_excel(file, sheet_name="Credit Rating")
Geographic = pandas.read_excel(file, sheet_name="Geographic")
VaRMapping = pandas.read_excel(file, sheet_name="VaRMapping", skiprows=3)
GICSMapping = pandas.read_excel(file, sheet_name="GICSMapping")
RatingMapping = pandas.read_excel(file, sheet_name="RatingMapping")
historicalData = pandas.read_excel(file, sheet_name="HistoricalPrice")
postionEvents = pandas.read_excel(file, sheet_name="Position Event")
stressedScenario = pandas.read_excel(file, sheet_name="StressedScenario", index_col=0)
print("Loaded excel file successfully")
print("Format data frame....")
postionEvents['Amount'] = postionEvents['Amount'] * postionEvents['Balance Sheet Exposure']
postionEvents['Distribution'] = postionEvents['Distribution'] * postionEvents['Balance Sheet Exposure']
postionEvents['Capital Contribution'] = postionEvents['Capital Contribution'] * postionEvents['Balance Sheet Exposure']
postionEvents['Capital Commitment'] = postionEvents['Capital Commitment'] * postionEvents['Balance Sheet Exposure']
postionEvents['Key'] = postionEvents['Portfolio'] +'/'+ postionEvents['Security'] +'/' + postionEvents['Ticker']
historicalData.columns = [x.upper() for x in historicalData.columns.tolist()]
historicalData2 = historicalData.fillna(method='ffill').set_index('DATE')#['MIGWMFA LX Equity']
historicalData2.columns = [x.upper() for x in historicalData2.columns.tolist()]
historicalDataPct = historicalData2.pct_change()
print("Formated data frame successfully")

def formatToExcel(df, writer, sheetname, startrow, startcol):
    format0 = writer.book.add_format({'num_format': '#,##0.00', 'border':1, 'border':1, 'border_color':'#000000'})
    formatb = writer.book.add_format({'bg_color': '#1F4E78', 'font_color':'#FFFFFF', 'bold':True})
    formata = writer.book.add_format({'border':1, 'border_color':'#000000'})
    
    #write data
    df.to_excel(writer,sheet_name=sheetname,startrow = startrow , startcol = startcol, index=False, )  
    worksheet = writer.sheets[sheetname]
    colWidth = worksheet.col_sizes
    #write header with format b
    for i in range(len(df.columns)):
        if pandas.isnull(df.columns[i]):
            worksheet.write_blank(startrow,startcol+i,'', formatb)
        else:
            worksheet.write(startrow,startcol+i,df.columns[i], formatb)
    #write data
#    for j in range(len(df)):
#        for i in range(len(df.columns)):
#            if pandas.isnull(df.iloc[j,i]):
#                worksheet.write_blank(startrow + 1 + j,1+i,'', format0)
#            else:
#                worksheet.write(startrow + 1 + j,1+i,df.iloc[j,i], format0)
    
    #adjust column width
    for idx, col in enumerate(df):
        
        series = df[col]
        max_len = len(str(series.name)) + 1
        if len(series) > 0:
            max_len = max((
                    series.astype(str).map(len).max(),
                    len(str(series.name))
                    )) + 1
        if  startcol + idx in colWidth.keys():
            max_len = max(colWidth[startcol + idx], max_len)
        colWidth[startcol + idx] = max_len
        worksheet.set_column(startcol + idx, startcol + idx, max_len)
def getLastValidER(assetCcy, baseCcy, prices):
    if assetCcy != baseCcy:
        if assetCcy+baseCcy+' CURNCY' in prices.columns: #e.g. EURUSD
            ccyIndex = prices[assetCcy+baseCcy+' CURNCY'].last_valid_index()
            er = 1.
            if not ccyIndex is None:
                er = prices.loc[ccyIndex, assetCcy+baseCcy+' CURNCY']
            return er
        elif baseCcy+assetCcy+' CURNCY' in prices.columns: #e.g. USDCNY
            ccyIndex = prices[baseCcy+assetCcy+' CURNCY'].last_valid_index()
            er = 1.
            if not ccyIndex is None:
                er = prices.loc[ccyIndex, baseCcy+assetCcy+' CURNCY']
            return 1./er
        else:
            return 1.
    else:
        return 1.
        
def positionAugWithER(pos, prices, reportingCCY):
    #pos['ER'] = 1.
    erDict = []
    
    for i in pos.index:
        d = pos.loc[i]['Date']
        ccy = pos.loc[i]['CCY']
        if ccy != reportingCCY:
            if ccy+reportingCCY+' CURNCY' in prices.columns: #e.g. EURUSD
                ccyIndex = prices[prices['DATE']<=d][ccy+reportingCCY+' CURNCY'].last_valid_index()
                er = 1.
                if not ccyIndex is None:
                    er = prices.loc[ccyIndex, ccy+reportingCCY+' CURNCY']
                erDict.append(er)
            elif reportingCCY+ccy+' CURNCY' in prices.columns: #e.g. USDCNY
                ccyIndex = prices[prices['DATE']<=d][reportingCCY+ccy+' CURNCY'].last_valid_index()
                er = 1.
                if not ccyIndex is None:
                    er = prices.loc[ccyIndex, reportingCCY+ccy+' CURNCY']
                erDict.append(1./er)
            else:
                erDict.append(1.)
        else:
            erDict.append(1.)
    pos.loc[:,'ER'] = erDict
    return pos

def postionsByDate(positions_orgin, startDate, endDate, prices, selectMask, baseCCY): # dataframe , timestamp, series
    start_time = time.time()
    #print('Start Run----------------------------------------------------')
    reportingCCY = baseCCY
    positions_tmp = positions_orgin.copy()
    #total_mtm = 0.
    #total_cost = 0.
    data = pandas.DataFrame(columns=['Portfolio','Security','Ticker','Market Value', 'Amount','Market Price','Market Price Date', 'Total Cost', 'LILO Cost', 'Dist&Realized', 'Total Distribution','Currency','Exchange Rate'])
    cash0 = dict()
    # I distinguish cash account by security name
    #pre analysis of cash account
    mask = (positions_tmp['Date'] <= endDate) &(positions_tmp['Date'] >= startDate) & selectMask
    mask2 = (positions_tmp['Date'] <= endDate) & selectMask
    positions = positions_tmp[mask]
    positions2 = positions_tmp[mask2]
    maskC = (positions['Portfolio']=='Cash')
    cashAccounts = positions[maskC][['Date', 'Security', 'Ticker', 'CCY', 'Amount']]
    #print(cashAccounts)
    positions = positions.drop(positions[maskC].index)
    
    positionAugWithER(positions, prices, reportingCCY)
    positionAugWithER(positions2, prices, reportingCCY)
    #loop over each ticker 'P/S/T'
    for k in positions['Key'].unique():     
        singleData = dict()
        pos = positions[positions['Key'] == k].sort_values('Date')
        pos2 = positions2[positions2['Key'] == k].sort_values('Date')
        if len(pos) == 0:
            continue
        
        ticker = pos['Ticker'].unique()[-1]
        
        #assign port/sec/tick to return
        singleData['Ticker'] = ticker
        singleData['Security'] = pos['Security'].unique()[-1]
        singleData['Portfolio'] = pos['Portfolio'].iloc[-1]
        #augment exchange rate
        start_AugER = time.time()
        #pos = positionAugWithER(pos, prices, reportingCCY)
        
        #positionAugWithER(pos, prices, reportingCCY)
        
        #print('Aug_ER', time.time() - start_AugER)
        #total purchase share amount is expected, FX is NOT adjusted for Amount
        tmpAmount = pos['Amount'].sum() #(pos['Amount'] * pos['ER']).sum()#current amount
        singleData['Amount'] = tmpAmount
        tmpCcy = reportingCCY
        #--#singleData['Currency'] = tmpCcy
        
        
        #total contribution/Distribution/Commitment is expected, FX is adjusted
        #--#tmpContr = (pos['Capital Contribution'] * pos['ER']).sum()#current contribution
        #--#tmpDist = (pos['Distribution'] * pos['ER']).sum()#current distribution
        #--#tmpCommi = (pos['Capital Commitment'] * pos['ER']).sum()#current committment
        tmpContr = (pos['Capital Contribution']).sum()#current contribution # original currency
        tmpDist = (pos['Distribution']).sum()#current distribution # original currency
        tmpDist2 = (pos2['Distribution']).sum()#current distribution # original currency
        tmpCommi = (pos['Capital Commitment']).sum()#current committment # original currency
        
        #cost will be based on Share*price*ER, total contribution will be included in cost
        #Note: ER will be based on different event time point, such that the cost will be constant for each valuation date
        #--#cost = (pos["Amount"] * pos["Cost"] * pos['ER']).sum() - tmpContr
        cost = (pos["Amount"] * pos["Cost"]).sum() - tmpContr # original currency
        
        ##add here the current cost and realized amount
        ##last in last out
        tmpLILOAmount = 0.
        tmpLILOCost = 0
        tmpPos = pos[pos['Amount']>=0]
        for i in range(1, len(tmpPos) + 1):   
            if tmpLILOAmount < tmpAmount:
                added = min(tmpAmount - tmpLILOAmount, tmpPos.loc[tmpPos.index[-i],'Amount'])
                tmpLILOAmount += added
                tmpLILOCost += added * tmpPos.loc[tmpPos.index[-i],'Cost'] - tmpPos.loc[tmpPos.index[-i],'Capital Contribution']
        tmpLILOPrice = 0.
        if tmpLILOAmount != 0.:
            tmpLILOPrice = tmpLILOCost/tmpLILOAmount     
            
        
        #print(k, cost)
        distribution = tmpDist
        totalDistribution = tmpDist2
        mtm = 0.
        #mtm will be calculated based on price df, if no price is in df, historical cost will be used, mtm should also be in Base currency
        if ticker.upper() in prices.columns:
            #print(sec)
            priceIndex = prices[ticker.upper()].last_valid_index()
            if priceIndex is None:
                # no price is found
                mtm = cost
                #++ cfs
            else:
                # price exisits, the last valid price will be used
                thisPrice = prices.loc[priceIndex, ticker.upper()]
                thisPriceDate = prices.loc[priceIndex, 'DATE']
                #if historical price is used, the contribution between price date and current date will be included in MTM,the FX will be adjusted
                tmpContr2 = pos[(pos['Date']>thisPriceDate)]
                #--#tmpContr2 = (tmpContr2['Capital Contribution'] * tmpContr2['ER']).sum()#
                #print(ticker.upper(), thisPriceDate, tmpContr2)
                tmpContr2 = (tmpContr2['Capital Contribution']).sum()# Original currency
                #distribution in between, I will NOT adjust it to MTM for the moment
                tmpDist3 = pos[(pos['Date']>thisPriceDate)]
                #--#tmpDist3 = (tmpDist3['Distribution']* tmpDist3['ER']).sum()#mtm estimation with contribution
                tmpDist3 = (tmpDist3['Distribution']).sum()#mtm estimation with contribution # Original currency
                #tmpAmount is not used directly, the dealing of ER is different from that with cost, as the existing position should be based on current ER
                #--#ccyPos = pos.groupby('CCY').sum()
                #--#ccyPos['Latest ER'] = [getLastValidER(i,reportingCCY, prices) for i in ccyPos.index]
                #--#mtm = thisPrice * (ccyPos['Amount'] * ccyPos['Latest ER']).sum() - tmpContr2# - tmpDist3
                mtm = thisPrice * (pos['Amount']).sum() - tmpContr2# - tmpDist3 # Original Currency
                singleData['Market Price'] = thisPrice
                singleData['Market Price Date'] = thisPriceDate
                #++ cfs
        else:#no price given
            mtm = cost
        #Leave ER as it is for the moment, optimization may be further considered  
        ccyPos = pos.groupby('CCY').sum()
        #ccyPos['Latest ER'] = [getLastValidER(i,reportingCCY, prices) for i in ccyPos.index]
        singleData['Exchange Rate'] = getLastValidER(ccyPos.index[-1],reportingCCY, prices)
        singleData['Currency'] = ccyPos.index[-1]

        singleData['Market Value'] = mtm
        singleData['Total Cost'] = cost ### change here
        singleData['LILO Cost'] = tmpLILOCost
        singleData['Dist&Realized'] = distribution + tmpLILOCost - cost
        singleData['Total Distribution'] = totalDistribution
        #Assign cash movement to each cash account
        #print(endDate,"Start")
        for i in pos.index:
            currentDis = pos.loc[i, 'Distribution']
            currentCost = pos.loc[i, "Amount"] *  pos.loc[i, "Cost"] - pos.loc[i, 'Capital Contribution']
            if currentDis != 0.:
                cashAccounts = cashAccounts.append({'Date':pos.loc[i, 'Date'], 'Security':pos.loc[i, 'Portfolio'], 'Ticker':pos.loc[i, 'Ticker'], 'CCY':pos.loc[i, 'CCY'], 'Amount':currentDis},ignore_index = True)
            if currentCost != 0.:
                cashAccounts = cashAccounts.append({'Date':pos.loc[i, 'Date'], 'Security':pos.loc[i, 'Portfolio'], 'Ticker':pos.loc[i, 'Ticker'], 'CCY':pos.loc[i, 'CCY'], 'Amount':-currentCost}, ignore_index = True)
        data = data.append(singleData, ignore_index=True)
    
    #loop done
    
    #aggregate cash to each existing account

    if len(cashAccounts) > 0:
        cashAgg = cashAccounts.groupby(['Security','CCY']).sum().reset_index()
        cashAgg['Exchange Rate'] = [getLastValidER(i,reportingCCY, prices) for i in cashAgg['CCY']]
        #--#cashAgg['Market Value'] = cashAgg['Exchange Rate'] * cashAgg['Amount']
        cashAgg['Market Value'] = cashAgg['Amount']
        cashAgg['Total Cost'] = cashAgg['Market Value']
        cashAgg['Market Price'] = 1.
        cashAgg['Portfolio'] = 'Cash'
        cashAgg['Ticker'] = 'Cash'
        cashAgg['Market Price Date'] = numpy.nan
        cashAgg['Total Distribution'] = 0.
        cashAgg['Dist&Realized'] = 0.
        cashAgg['Currency'] = cashAgg['CCY']
        data = data.append(cashAgg[['Portfolio','Security','Ticker','Market Value', 'Amount','Market Price','Market Price Date', 'Total Cost', 'Dist&Realized', 'Total Distribution','Currency','Exchange Rate']], ignore_index=True)

    data['Date'] = endDate
    del positions
    data['(UR)PnL'] = data['Market Value'] - data['LILO Cost']
    data['Market Value(' + reportingCCY + ')'] = data['Market Value'] * data['Exchange Rate']
    data['Dist&Realized(' + reportingCCY + ')'] = data['Dist&Realized'] * data['Exchange Rate']
    data['Total Distribution(' + reportingCCY + ')'] = data['Total Distribution'] * data['Exchange Rate']
    data['(UR)PnL(' + reportingCCY + ')'] = data['(UR)PnL'] * data['Exchange Rate']
    return data
#navDF.to_csv("8Stratigic.csv")
def runNAV(thisPostionEvents, thisHistoricalData, datelist):
    acc = 1.
    navDF = pandas.DataFrame(columns=['Date', 'Market Value(USD)', 'Cash Injection', 'Acc Factor', 'Acc Adj Market Value(USD)'])
    for i in range(len(datelist)):
        d = datelist[i]
        d0 = datelist[i-1]
        mask = True#(postionEvents['Portfolio'] == 'Hangzhou Majik') | (postionEvents['Security'] == 'Hangzhou Majik')
        a=postionsByDate(thisPostionEvents, datelist[0], d, thisHistoricalData[thisHistoricalData['DATE']<=d], mask,'USD')
        a0=postionsByDate(thisPostionEvents, datelist[0], d0, thisHistoricalData[thisHistoricalData['DATE']<=d], mask,'USD')
        cf = 0.
        cf2g = 0.
        
        if len(thisPostionEvents[(thisPostionEvents['Ticker'] == 'Cash') & (thisPostionEvents['Date'] <= d) & (thisPostionEvents['Date'] > d0) & mask])> 0:
            cf = thisPostionEvents[(thisPostionEvents['Ticker'] == 'Cash') & (thisPostionEvents['Date'] <= d) & (thisPostionEvents['Date'] > d0) & mask]#['Amount'].sum()
            cf2 = cf[cf['Deduction'] == 1.]
            
            #--#cf = positionAugWithER(cf, historicalData[historicalData['DATE']<=d], 'USD')
            cfg = cf.groupby('CCY').sum()['Amount'].to_frame()
            cf2g = cf2.groupby('CCY').sum()['Amount'].to_frame()
            cfg['ER'] = [getLastValidER(i,'USD', thisHistoricalData[thisHistoricalData['DATE']<=d]) for i in cfg.index]
            cf2g['ER'] = [getLastValidER(i,'USD', thisHistoricalData[thisHistoricalData['DATE']<=d]) for i in cf2g.index]
            #print(cf2g)
            cf = (cfg['Amount']* cfg['ER']).sum() - (cf2g['Amount']* cf2g['ER']).sum() # update here
            
        dist = 0.
        
        if len(thisPostionEvents[(thisPostionEvents['Date'] <= d) & (thisPostionEvents['Date'] > d0)& mask])> 0:
            dist = thisPostionEvents[(thisPostionEvents['Date'] <= d) & (thisPostionEvents['Date'] > d0)& mask][['Distribution','CCY']]
            distg = dist.groupby('CCY').sum()['Distribution'].to_frame()
            distg['ER'] = [getLastValidER(i,'USD', thisHistoricalData[thisHistoricalData['DATE']<=d]) for i in distg.index]
            dist = (distg['Distribution']* distg['ER']).sum()
            if type(cf2g) == pandas.DataFrame:
                dist = dist + (cf2g['Amount']* cf2g['ER']).sum() # update here
        v0 = a0['Market Value(USD)'].sum() + dist
        v = a['Market Value(USD)'].sum()
        #print(d)
        print(a['Market Value(USD)'])
        if v0 != 0 and v !=0:
            acc = v/v0*acc
        #print(d, v,cf, dist,acc, v/acc)
        navDF = navDF.append({'Date':d, 'Market Value(USD)':v, 'Cash Injection':cf, 'Acc Factor':acc, 'Acc Adj Market Value(USD)':v/acc}, ignore_index=True)
    navDF['PnL'] = navDF['Market Value(USD)'].diff()-navDF['Cash Injection']
    navDF['Cum PnL'] = navDF['PnL'].cumsum()
    navDF['Acc Adj Market Value(USD)'] = navDF['Acc Adj Market Value(USD)']/navDF['Acc Adj Market Value(USD)'].iloc[0]
    navDF.fillna(0.,inplace=True)
    return navDF
print("show tmp date list...")
tmp = historicalData2.index[historicalData2.index >= datetime.datetime(2017,12,29,0,0,0)].tolist()
print(tmp)
navDF = runNAV(postionEvents, historicalData, tmp[:])
print("press any key  to show navDF....")
print(navDF)
for pname in postionEvents['Portfolio'].unique():
    if pname == "Cash":
        continue
    poEv = postionEvents[(postionEvents['Portfolio'] == pname) | (postionEvents['Security'] == pname)].copy()
    print(pname,"->")
    tmpNav = runNAV(poEv, historicalData, tmp)
    navDF['Cum_' + pname] = tmpNav['Cum PnL']