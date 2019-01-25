import numpy as np;
#test function

a = np.array([[3,5,2],[7,4,6],[9,8,5]]);
print("original array is:");
print(a);
print("np.amin(a,1)");
print(np.amin(a,1));
print("np.amin(a,0)");
print(np.amin(a,0));
print("np.amin(a)");
print(np.amin(a));
print("numpy.ptp():函数返回沿轴的值的范围(最大值 - 最小值)。")
print("np.ptp(a):");
print(np.ptp(a));
print("np.ptp(a, 0):");
print(np.ptp(a,0));
print("np.ptp(a, 1):");
print(np.ptp(a,1));

print("np.percentile():百分位数是统计中使用的度量，表示小于这个值得观察值占某个百分比。 函数numpy.percentile()接受以下参数。");
a = np.array([[30,80,20],[80,20,70],[60,90,60]]);
print("oringal array:");
print(a);
print("np.percentile(a,99.9):");
print(np.percentile(a,99.9));
print("np.percentile(a,99):");
print(np.percentile(a,99));
print("np.percentile(a,50):");
print(np.percentile(a,50));
print("np.percentile(a,50,0):");
print(np.percentile(a,50,0));
print("np.percentile(a,50,1):");
print(np.percentile(a,50,1));

b = np.array([30,60,80]);
print("original array:")
print(b);
print("np.percentile(b, 50)");
print(np.percentile(b,50));


b = np.array([4.56,12.98,65.80]);
print("original array:")
print(b);
print("np.percentile(b, 75)");
print(np.percentile(b,75));

a = np.arange(9).reshape(3,3);
print("original array:")
print(a);
y = np.where(a > 3);
print("np.where(a>3):");
print(y);
print("array after where(a>3):")
print(a[y]);