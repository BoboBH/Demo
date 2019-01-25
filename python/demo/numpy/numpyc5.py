import numpy as np;
#test broadcast(广播);

a = np.array([1,2,3,4]);
b = np.array([10,40,50,60]);
c = a*b;
print("array a:");
print(a);
print("array b:");
print(b);
print("a* b is :");
print(c);

a = np.array([[0,0,0],[10,10,10],[20,20,20],[30,30,30]]);
b = np.array([1,2,3]);
c = np.array([[1,2,3]]);
print();
print("array a is :")
print(a);
print("array b is :");
print(b);
print("array a+b is :")
print(a + b);

print("array a*b is :")
print(a * b);

print("array a*b is :")
print(a / b);

print("array c is :")
print(c);
print("array a+c is :")
print(a + c);