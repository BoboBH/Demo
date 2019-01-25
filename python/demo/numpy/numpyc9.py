import numpy as np;
#Math function test;
a = np.array([0,30,45,60,90]);
print("test numpy.sin function");
print("original array is :")
print(a);
print("Sin array is :")
print(np.sin(a*np.pi/180));
print("Sin array is :")
print(np.cos(a*np.pi/180));

print("test numpy.round");
a = np.array([1.0,2.2,3.33,4.444,123.23345]);
print("original array is :")
print(a);
print("Round with 0 demical:")
print(np.round(a));
print("Round with 1 demical:")

print(np.round(a,1));
print("Round with 2 demical:")
print(np.round(a,2));
print("Round with -1 demical:")
print(np.round(a,-1));

print("test numpy.floor");
print("original array is :")
print(a);
print("Floor of arry is:")
print(np.floor(a));
print("Ceil of arry is:")
print(np.ceil(a));

print("Test Math operator:");
a = np.arange(9, dtype=np.float_).reshape(3,3);
print("original array is :")
print(a);
b = np.array([10,10,10]);
print("the second array is :")
print(b);
print("a + b is :")
print(np.add(a,b));
print("a - b is :")
print(np.subtract(a,b));
print("a * b is :")
print(np.multiply(a,b));
print("a / b is :")
print(np.divide(a,b));

print("a / 10 is :")
print(np.divide(a,10));

 