import numpy as np;

#numpy-数组创建实例

print("test empty function:")
a = np.empty((3,2), dtype="i4");
print("a array info:")
print(a);
print("a shape:");
print(a.shape);
print("a itemsize:")
print(a.itemsize);

print("test zeros function:")
a = np.zeros(5,dtype="i4");
print("a array info:")
print(a);
print("a shape:");
print(a.shape);
print("a itemsize:")
print(a.itemsize);

print("test arange:")
a = np.arange(5);
print("a array info:")
print(a);
a = np.arange(start=1, stop=100,step=3,dtype="i4");
print("a array info:")
print(a);
 
print("test linspace:")
a = np.arange(start=2,stop = 10, num = 10,endpoint=True);
print("a array info:")
print(a);



