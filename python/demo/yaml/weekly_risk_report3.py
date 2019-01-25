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
print("run credit rating report")
latestDate = CreditRating.groupby('Security').max()['Date']
mask = []
for i in CreditRating[['Security','Date']].values:
    if((i[0] in latestDate.index) and (latestDate[i[0]] == i[1])):
        mask.append(True)
    else:
        mask.append(False)
tmpCreditRating = CreditRating[mask]
tmpCreditRating = tmpCreditRating.merge(currentPortfolio[['Security','Market Value(USD)']], how='left',left_on='Security', right_on='Security')
tmpCreditRating['Equity'] = tmpCreditRating['Asset %'] * tmpCreditRating['Leverage'] * tmpCreditRating['Market Value(USD)']
tmpCreditRating = tmpCreditRating.groupby('SPRating').sum()['Equity'].to_frame()
tmpCreditRating['% Total Investment'] = tmpCreditRating['Equity']/currentPortfolio['Market Value(USD)'].sum()
tmpCreditRating = tmpCreditRating.merge(RatingMapping[['Standard & Poor','Rank']], left_index=True,right_on='Standard & Poor').sort_values('Rank').set_index('Rank')[['Standard & Poor','Equity','% Total Investment']]
tmpCreditRating = tmpCreditRating.drop_duplicates()
print(tmpCreditRating)