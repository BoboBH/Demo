#%matplotlib inline;
import pandas;
import numpy;
import datetime;
from matplotlib import pyplot as plt;
import matplotlib.transforms as mtransforms;
import time;

print("time.time():")
start_time = time.time();
print(start_time);

print("loading excel file summary of fund.xlsm...");
file = "D:\\YAML\\Data\\summary of fund.xlsm"
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
print("Load Excell Successfully");

print("fill historical price holes and format columns...")
historicalData.columns = [x.upper() for x in historicalData.columns.tolist()]
historicalData2 = historicalData.fillna(method='ffill').set_index('DATE')#['MIGWMFA LX Equity']
historicalData2.columns = [x.upper() for x in historicalData2.columns.tolist()]
historicalDataPct = historicalData2.pct_change()
print(historicalDataPct);
print("processed historical price");
print("historical price data's Date index:")
tmp = historicalData2.index[historicalData2.index >= datetime.datetime(2017,12,29,0,0,0)].tolist();
print(tmp);
print('tmp[:]');
print(tmp[:]);


