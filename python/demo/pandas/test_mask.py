#test mask function
import pandas as pd
import numpy as np
data = pd.read_csv("loan.csv", index_col="Id");
print("Raw data");
print(data);
print("data columns:")
print(data.columns.tolist());
tempdata = data.copy();
print("Copy data:")
print(tempdata);
mask = tempdata["Gender"] == "MALE";
mask_data = tempdata[mask];
print(mask_data);
exc_mask_data = tempdata.drop(mask_data.index);
print("exclude mask data is :")
print(exc_mask_data);

print("sort data by ID:")
print(data.sort_values("Id", ascending=False));


print("Test Unique Gender:")
print(data["Gender"].unique());
print("Test Unique[-1]:")
print(data["Gender"].unique()[-1]);
print("Test Unique[0]:")
print(data["Gender"].unique()[0]);
print("Test Unique[1]:")
print(data["Gender"].unique()[1]);

print("test loc:");
print(data.loc['LP00122'])
print("test iloc[0]:");
print(data.iloc[0])
print("test iloc[-1]:");
print(data.iloc[-1])

print("data['Edu']");
print(data['Edu']);
print("data['Edu'][-1]");
print(data['Edu'].iloc[-1]);
print("data['Edu'][0]");
print(data['Edu'].iloc[0]);
print("data['Edu'][1]");
print(data['Edu'].iloc[1]);
