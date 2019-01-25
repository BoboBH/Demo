#计算最大回撤
import pandas as pd
import numpy as np
data = pd.read_csv("nav.csv", index_col="Date");
data = data.sort_values(by='Date',axis=0,ascending=True);
#data["drawdown"] = 0.0;
row_count = data.shape[0];
index = 0;
#print(data);
minArray = np.zeros(row_count);
maxDrawdownArray = np.zeros(row_count);
minDateArray = np.zeros(row_count, dtype=str, order="C");
for row_index,row in data.iterrows():
    sl = data.loc[row_index:,"NAV"];
    min = sl.min();
    nav = row["NAV"]; 
    minArray[index] = min;
    maxDrawdownArray[index] = 100*(min-nav)/nav;
    print("mask_data");
    index = index + 1;
print("drawdown array:", maxDrawdownArray);
data["minNav"],data["maxDrawdown"],data["minDate"] =minArray, maxDrawdownArray,minDateArray;
print(data);
sl = data[:"maxDrawdown"].min();
print("Max Drawdwon: ",sl["maxDrawdown"]);
mask_data = data[data["maxDrawdown"]==sl["maxDrawdown"]];
print("The Max Drawdown dates are :")
print(mask_data);
    
    