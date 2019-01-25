import pandas
import numpy
import datetime
from matplotlib import pyplot as plt
import matplotlib.transforms as mtransforms
import time
import matplotlib.dates as mdate
data = pandas.DataFrame(columns=['Date','Volume']);
data = data.append({'Date':datetime.datetime(2018,1,1),'Volume':100},ignore_index=True);
data = data.append({'Date':datetime.datetime(2018,1,10),'Volume':200},ignore_index=True);
data = data.append({'Date':datetime.datetime(2018,1,20),'Volume':300},ignore_index=True);
data = data.append({'Date':datetime.datetime(2018,1,30),'Volume':200},ignore_index=True);
data = data.append({'Date':datetime.datetime(2018,2,10),'Volume':100},ignore_index=True);
data = data.append({'Date':datetime.datetime(2018,2,20),'Volume':-100},ignore_index=True);
data = data.append({'Date':datetime.datetime(2018,3,1),'Volume':-200},ignore_index=True);
data = data.set_index("Date").plot(kind="bar", title="Volume");
print(data);
plt.xlabel("xxxx");
plt.ylabel("yyy");
data.plot();
#plt.show();

data = pandas.DataFrame(columns = ['name','age','gender','state','num_children','num_pets']);
data = data.append({"name":'Bobo Huang','age':40,'gender':'M','state':'GD', 'num_children':1,'num_pets':2},ignore_index=True);
data = data.append({"name":'Li Yun','age':35,'gender':'F','state':'GD', 'num_children':1,'num_pets':3},ignore_index=True);
data = data.append({"name":'Mary','age':20,'gender':'M','state':'GD', 'num_children':0,'num_pets':2},ignore_index=True);
data = data.append({"name":'Peter','age':25,'gender':'F','state':'JX', 'num_children':2,'num_pets':5},ignore_index=True);
data = data.append({"name":'Jeff','age':45,'gender':'M','state':'JX', 'num_children':3,'num_pets':1},ignore_index=True);
data = data.append({"name":'Bil','age':55,'gender':'F','state':'JX', 'num_children':1,'num_pets':3},ignore_index=True);
data = data.append({"name":'Lisa','age':32,'gender':'M','state':'JX', 'num_children':1,'num_pets':4},ignore_index=True);
print(data);
data.plot(kind='bar', x= 'name', y = 'age');
#plt.show();
plt.savefig("d:\\temp\\age.png");
data.groupby('state')['num_children'].sum().plot(kind='bar');
#plt.show();
plt.savefig("d:\\temp\\state_num_children.png");
data.groupby('state')['num_pets'].sum().plot(kind='bar');
#plt.show();
plt.savefig("d:\\temp\\state_num_pets.png");


data = pandas.DataFrame(columns=['currency','fx','weight']);
data = data.append({'currency':'USD','fx':1.0,'weight':1.0},ignore_index=True);
data = data.append({'currency':'CNY','fx':0.15,'weight':2.0},ignore_index=True);
data = data.append({'currency':'HKD','fx':0.14,'weight':3.0},ignore_index=True);
print(data);
data['er'] = data['fx']*data['weight'];
data['new_col'] = numpy.array([2.0,2.1,2.2]);
print(data);
print("new_col:")
print(data['new_col']);
print("new_col:")
tmp = data.pop('new_col');
print(tmp);
print("tmp[new_col]")
print(tmp.tolist());
data['new_col'] = tmp.tolist();
print(data);
l = {1,2,3};
print("list...")
print(l);
data["new_col2"] = l;
print("data with new_col2");
print(data);
list1 = [4,5,6];
data["new_col3"] = list1;
print("data with new_col3");
print(data);
array = numpy.array([7,8,9]);
print("array:")
print(array);
data["new_col4"] = array;
print("data with new_col4");
print(data);
data["new_col5"] = data['fx'];
print("data with new_col5");
print(data);
print('data[fx]');
print(data['fx']);
print('data[fx] array');
print(data['fx'].tolist());
df_fx_only = data['fx'].to_frame();
print('df_fx_only:');
print(df_fx_only);
df_fx_only['currency'] = data['currency'];
print('df_fx_only with currency:');
df_fx_only.set_index("currency");
print(df_fx_only);