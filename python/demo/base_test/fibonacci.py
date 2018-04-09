

#defince fibonacci function;
def fibonacci(n):
    if(n == 0):
        return 0;
    if(n == 1):
        return 1;
    a, b = 0,1;
    index = 2;
    while index <= n:
        temp = b;
        b = a + b;
        a = temp;
        index = index + 1;
    return b;
#define recursive fibonacci function:
def fibonacci_recursive(n):
    if(n == 0):
        return 0;
    if(n == 1):
        return 1;
    return fibonacci_recursive(n-2) + fibonacci_recursive(n-1);
#main program entrance:
a = 0;
b=1;
series = [];
series.append(a);
series.append(b);
print(series);
index = 2;
#print(a);
while index < 20:
    print("a=",a, "b=",b, "a+b=", a+b);
    series.append(a+b);  
    temp = b;  
    b = a + b;
    a = temp;
    index += 1;
print(series);
print("ouput series one by one...");
for temp in series:
    print(temp);
print("output element in rang(5,9)...");
for temp in range(5,9):
    if temp == 8:
        continue;
    print(temp);
for temp in range(0,9):
    print("fibonacci(", temp, ") =", fibonacci(temp));
    print("fibonacci_recursive(", temp, ") =", fibonacci_recursive(temp));
