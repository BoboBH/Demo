
#test loc function
import pandas as pd
import numpy as np
data = pd.read_csv("loan.csv", index_col="Id");
print("Raw data");
print(data);
print("data columns:")
print(data.columns.tolist());

print("data.loc([LP00118])");
print(data.loc[list(["LP00118","LP00126"])]);

print(data.loc["LP00118","Edu"]);
print(data.loc["LP00115","Status"]);
print("data.loc['LP00118']['Edu']")
print(data.loc["LP00118"]["Edu"]);
print("data.loc['LP00118']['Status']")
print(data.loc["LP00115"]["Status"]);
print(data);
data = data.append({'Id':'LP00128','Gender':'FEMALE', 'Edu':'Graduate','Status':'N'},ignore_index=True);
data = data.set_index('Id');
print("data:")
print(data);

temp = pd.DataFrame(columns=['Id','Gender', 'Edu','Status']);
temp = temp.append({'Id':"123",'Gender':'MALE', 'Edu':'Grantted','Status':'Y'}, ignore_index=True);
temp = temp.append({'Id':"124",'Gender':'FEMALE', 'Edu':'Grantted','Status':'N'}, ignore_index=True);
temp = temp.set_index('Id');
print("temp is :")
print(temp);