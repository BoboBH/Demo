import numpy as np;
#reshape array;

a = np.arange(0,60,3);
b = a.reshape(4,5);
print("original array is :")
print(a);
print();
print("new array with reshape(4,5) is :");
print(b);
print();
print("new array with reshape(5,4) is :");
print(b.reshape(5,4));

print("test flat on array...");
a = np.arange(8).reshape(2,4);
print("oringal array is :");
print(a);
print("flat of array is :");
print(a.flat);
print("flat[5] of array is :")
print(a.flat[5]);
b = a.flatten(order='F');
print("flattern array by array a:")
print(b);
print("test narray.ravel...");
print("the original array is :")
print(a);
b = np.ravel(a, order='F');
print("ravel(a,'F')");
print(b);

print("test np.transpose...");
print("the original array is:");
print(a);
b = np.transpose(a);
print("Transposed array is:")
print(b);
print("test narray.T...");
print("the original array is:");
print(a);
print("Transposed array is:")
print(a.T);

print("test np.rollaxis....")
a = np.arange(8).reshape(2,2,2);
print("the original array is :")
print(a);
b = np.rollaxis(a,2);
print("the array of np.rollaix(a,2) is:")
print(b);
b = np.rollaxis(a,2,1);
print("the array of np.rollaix(a,2,1) is:")
print(b);

print("test append...");
a = np.array([[1,2,3],[4,5,6]]);
print("the original array is :")
print(a);
b = np.append(a,[7,8,9]);
print("append without");
print(b);
b = np.append(a,[[7,8,9]],axis=0);
print("append with axis=0");
print(b);
b = np.append(a,[[5,5,6],[7,8,9]],axis=0);
print("append with axis=1");
print(b);

b = np.append(a,[[5,5,6],[7,8,9]]);
print("append without axis");
print(b);


