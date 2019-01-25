#计算夏普比率
import pandas as pd
import numpy as np
data = pd.read_csv("nav.csv", index_col="Date");
data = data.sort_values(by='Date',axis=0,ascending=True);
data["daily_ret"] = data["NAV"].pct_change();
#平均日回报率：
totalReturn = (data["NAV"].iloc[-1] - data["NAV"].iloc[0])/data["NAV"].iloc[0];
mean = data["daily_ret"].mean();
std = data["daily_ret"].std();
sharpratio = np.sqrt(252)*totalReturn/std;
print("totalReturn",totalReturn);
print("mean:",mean);
print("std:",std);
print("sharpe ratio:", sharpratio);
print(data.head(10));