import numpy as np;
# test iterate on array
a = np.arange(0,60,5);
b = a.reshape(3,4);
print("original array is :")
print(a);
print("reshape(3,4) array is:")
print(b);
c = a.reshape(2,6);
print("reshape(2,6) array is:")
print(c);

d = c.T;
print("Transpose的matrix of c:");
print(d);

print("updated array is with order=F:");
for x in np.nditer(d):
    print(x,end=' ');
print();


print("updated array is with order=C:");
e = d.copy(order='C');
for x in np.nditer(e):
    print(x, end = ' ');
print();
print("Sepcfiy order=C while iterate C");
for x in np.nditer(d, order='C'):
    print(x, end = ' ');
print();

print("Sepcfiy order=C while iterate F");
for x in np.nditer(d, order='F'):
    print(x, end = ' ');
print();

index = 1;
print("iterate array and update its value by op_flags=['readwrite]");
for x in np.nditer(d, op_flags=['readwrite']):
    x[...]= 2 * x + index;
    index += 1;
print("Update array is :");
print(d);

print("broadcast for interate");
n = np.array([1,2]);
print("array n is:")
print(n);
index = 1;
for x,y in np.nditer([d,n]):
    print("%d:%d:%d"%(x,y,index),end=' ');
    index += 1;


