
import pandas as pd
import numpy as np
data = pd.read_csv("loan.csv", index_col="Id");
print("Raw data");
print(data);
print("Filter data");
print (data.loc[(data["Gender"]=="MALE") & (data["Status"]=="Y"), ["Gender","Edu","Status"]]);

print("可以通过传递一个list对象来创建一个Series，pandas会默认创建整型索引：");
series = pd.Series([1,2,3,5,8,11,19]);
print(series);
index = 0;
while index < series.size:
	print(index, series[index]);
	index = index +1;
print("Have loop all item in series");
print("通过传递一个numpy array，时间索引以及列标签来创建一个DataFrame：");
print("date range:");
dates = pd.date_range("20180101", periods=31);#http://blog.csdn.net/kancy110/article/details/69665164 details for numpy.random
df = pd.DataFrame(np.random.randn(31,4), index=dates, columns=list('ABCD'));
print(df);
print("two demession array");
twoDem = np.random.rand(4,3);
print(twoDem);
print("two demession size", twoDem.size);
rowIndex = 0;
colIndex =0;
while rowIndex < 4:
	colIndex = 0;
	while colIndex < 3:
		print("[", rowIndex, ",", colIndex,"]=",twoDem[rowIndex,colIndex]);
		colIndex = colIndex + 1;
	rowIndex = rowIndex +1;
print("Have loop all two demession's cells");
print("通过传递一个能够被转换成类似序列结构的字典对象来创建一个DataFrame：");
df2 = pd.DataFrame({
		"A":1,
		"B":pd.date_range("20180101",periods=4),
		"C":pd.Series(np.random.randint(1,100, 4)),
		"D":np.array([3]*4,dtype="int64"),
		"E":pd.Categorical(["test","train","test","train"]),
		"F":"foo"
	});
print(df2);
print("查看不同列的数据类型：");
print(df2.dtypes);
print("查看frame中头部和尾部的行：");
print("Head 2");
print(df2.head(1));
print("Tail 2");
print(df2.tail(2));
print("describe(df2)函数对于数据的快速统计汇总：");
print(df2.describe());
print("df2.sum(0)：");
print(df2.sum(0));
print("df2.mean(0)：");
print(df2.mean(0));




print("describe()函数对于数据的快速统计汇总：");
print(df.describe());
print("对数据的转置:");
print(df.T);

print("数据选择");
print("选择一个单独的列");
print(df.A);#or df["A"];