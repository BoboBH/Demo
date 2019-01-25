import numpy as np;
#numpy的高级索引

x = np.array([[1,2],[3,4],[5,6]]);
print("original arrya 3X2, 3 rows and 2 columns:");
print(x);
y = x[[0,1,2],[0,1,0]];
print(y);

x = np.array([[  0,  1,  2],[  3,  4,  5],[  6,  7,  8],[  9,  10,  11]]);
print('我们的数组是：');
print(x);
print();
rows = np.array([[0,0],[3,3]]);
cols = np.array([[0,2],[0,2]]);
print("rows is :");
print(rows);
print("cols is :");
print(cols);
y = x[rows,cols];
print('x[rows,cols]：');
print(y);
print("x[[0,0,3,3],[0,2,0,2]]");
print(x[[0,0,3,3],[0,2,0,2]]);
print();
print("Slice:x[1:4,1:3]:");
y = x[1:4,1:3];
print(y);
print();
print("Slice:x[1:4,[1,2]]:")
y = x[1:4,[0,1]];
print(y);
print();
print("Slice:x[1:3,[1,2]]:")
y = x[1:2,[1,2]];
print(y);
print();

print("Slice:x[2:4,[1,2]]: right-bottom 2X2")
y = x[2:4,[1,2]];
print(y);
print();

print("Slice:x[0:2,[0,1]]: right-top 2X2")
y = x[0:2,[0,1]];
print(y);
print();

print("Slice:x[0:3,[2,1,0]]: reverse: right->left")
y = x[0:4,[2,1,0]];
print(y);

print("Slice:x[0:3,[2,1,0]]: reverse: right->left")
y = x[[3,2,1,0],0:3];
print(y);


print("Slice:y[0:4,[2,1,0]]: totally reverse: right->left, top->bottom")
z = y[0:4,[2,1,0]];
print(z);
print("original array is :")
print(x);

print();
print("Test boolean index:");
print("filter out all values which is bigger than '5'");
print(x[x > 5]);


print();
print("filter out all values which is not '5'");
print(x[x != 5]);


print();
print("filter out all values which is even");
y = x[x % 2 == 0];
print(y[y>0]);



print();
print("Array with nan");
print("the original array is:");
a = np.array([np.nan,1,2,3,np.nan,5,9]);
print(a);
print("filter out nan values:");
print(a[~np.isnan(a)]);

