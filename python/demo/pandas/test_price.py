import pandas as pd;
import numpy;
from numpy.random import randn;
import datetime;
from matplotlib import pyplot as plt;
import matplotlib.transforms as mtransforms;
import time;
import matplotlib.dates as mdate;
data = pd.read_csv("price.csv", index_col="Date");
print("price data is:")
print(data);
data.plot(kind='line', title='Price',grid=True);
#plt.show();
print(randn(10,5));
print(numpy.arange(0,100,10));
df = pd.DataFrame(randn(10,5),columns=['A','B','C','D','E'],index=numpy.arange(0,100,10));
df.plot(kind='line', title="Line");
#plt.show();
print("data index:")
print(data.index.tolist());
print("data columns:")
print(data.columns.tolist());
df_tmp = pd.DataFrame(columns = ['Date']);
df_tmp['Date'] = data.index.tolist();
print("df_tmp");
print(df_tmp);
for col in data.columns.tolist():
    chg=[0.0];
    print(col);
    prices = data[col].tolist();
    print(prices);
    for i in range(1,len(prices)):
        chg.append((prices[i] - prices[0])*100/prices[0]);
        print(chg);
    df_tmp[col] = chg;
df_tmp.set_index('Date');
print(df_tmp);
print("price chage is:")
print(df_tmp);
df_tmp.plot(kind='line', title='Price Change',grid=True);
plt.show();