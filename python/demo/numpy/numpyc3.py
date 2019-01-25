import numpy as np;
#NumPy - 切片和索引-Slice
a = np.arange(10);
print("a array info:")
print(a);
s = slice(2,9,2);
print("a array slince(2,9,2)info: parameters are start, stop, step")
b = a[s];
print(b);
s = slice(2,9,);#不包含stop
print("a array slince(2,9,)info:parameters are start, stop")
b = a[s];
print(b);

s = slice(2,);
print("a array slince(2,)info:parameters is start")
b = a[s];
print(b);

print("a array a[2:5] info:")
print(a[2:5]);


print("a array a[2:] info:")
print(a[2:]);
print("a array a[2:9:] info:")
print(a[2:9:]);

ma = np.array([[1,2,3],[3,4,5],[5,6,7]]);
print("ma multiple array ma info:")
print(ma);
print("ma:a[1:]:");
print(ma[1:]);


print("第二行的元素是:");
print(ma[1,...]);
print("第二列的元素是:");
print(ma[...,1]);





