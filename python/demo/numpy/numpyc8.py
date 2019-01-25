import numpy as np;
#测试字符串含税
print("concat 2 chars...");
print(np.char.add(['hello'],[' bobo']));
print();

print("concat 2 chars array...");
a = np.array(['hello', 'hi']);
b = np.array([' bobo',' happy']);
print("array a:")
print(a);
print("array b:")
print(b);
print("add(a,b)");
print(np.char.add(a,b));
print();

print("test multiply...");
a = np.array(['hello']);
print("array a:")
print(a);
print("multiply(a,3):")
print(np.char.multiply(a,3));
a = np.array(['hello','hi']);
print("array a:")
print(a);
print("multiply(a,3):")
print(np.char.multiply(a,3));

