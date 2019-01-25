#%matplotlib inline
import numpy as np;
from matplotlib import pyplot as plt;
#shape property
print("Array's shape property:")
a = np.array([[1,2,3],[4,5,6]]);
print("a shape:");
print(a.shape);
print("a ndim:")
print(a.ndim);
b = np.array([[1,2],[3,4],[5,6]]);
print("b shape:");
print(b.shape);
c = a.reshape(3,2);
print("c array info:");
print(c);
print("c shape:");
print(c.shape);

#reshape b array
b.shape = (1,6);
print("b array info:");
print(b);
print("b shape:");
print(b.shape);

print("");
print("Array's ndim property:")
a = np.arange(24);
print("a array info:")
print(a);
print("ndim info:")
print(a.ndim);
b = a.reshape(2,4,3);
print("b array info:")
print(b);
print("b's shape");
print(b.shape);
print("b's ndim:")
print(b.ndim);
print("b's itemsize:")
print(b.itemsize);
print("b's flags:")
print(b.flags);

def sum(a,b):
    return a + b;
a,b=1,2;
print(a);
print(b);
print(sum(a,b));
a = np.array([1,4,6,8]);
b = np.array([3,5,7,9]);
print("array a:b:");
print(a,b);
print("sum(a,b):");
print(sum(a,b));

x = np.arange(20);
y = x**2;
plt.plot(x,y);
plt.show();


