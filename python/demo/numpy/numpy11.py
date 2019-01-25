import numpy.matlib;
import numpy as np;
#test matlib
m = np.matlib.empty((2,2));
print("empty matrix:");
print(m);
m = np.matlib.zeros((2,3));
print("zeros matrix:");
print(m);
m = np.matlib.ones((2,3));
print("ones matrix:");
print(m);
m = np.matlib.eye(n=3,M=4,k=0,dtype=float);
print("eye matrix:这个函数返回一个矩阵，对角线元素为 1，其他位置为零");
print(m);

m = np.matlib.eye(n=3,M=4,k=1,dtype=float);
print("eye matrix:这个函数返回一个矩阵，对角线元素为 1，其他位置为零");
print(m);

m = np.matlib.identity(5, dtype=float);
print("identity matrix:函数返回给定大小的单位矩阵。单位矩阵是主对角线元素都为 1 的方阵");
print(m);

m = np.matlib.rand(3,3)
print("rand matrix:函数返回给定大小的填充随机值的矩阵");
print(m);

m = np.matrix('1,2;3,4');
print("matrix:")
print(m);

a = np.array([[1,2],[3,4]]);
b = np.array([[11,12],[13,14]]);
print("array a:");
print(a);
print("array b:");
print(b);
print("array np.dot(a,b):")
print(np.dot(a,b));

