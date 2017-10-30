import re;

pattern = re.compile(r"hello");
result1 = re.match(pattern, "hello");
result2 = re.match(pattern, "helloo Bobo Huang!");
result3 = re.match(pattern, "helo Bobo Huang!");
result4 = re.match(pattern, "hello Bobo");
if result1:
    print(result1.group());
else:
    print("1 match failed");
if result2:
    print(result2.group());
else:
    print("2 match failed");
if result3:
    print(result3.group());
else:
    print("3 match failed");
if result4:
    print(result4.group());
else:
    print("4 match failed");
match = re.search(pattern, "hello Bobo hUuang");
if match:
    print(match.group());
else:
    print("Cannot match");
results = re.findall(pattern, "hello Bobo hello,hUuang");
if results != None:
    print(results);
else:
    print("Cannot match");
mobile = "13632794089";
print("Match mobile:", mobile);
pattern = re.compile("^(0[1-9]{2,3}-)?1\d{10}");
result = re.match(pattern, mobile);
print("result:",result);
if result:
    print(result.group());
else:
    print(mobile, " match failed");
mobile = "086-13632794089";
print("Match mobile:", mobile);
result = re.match(pattern, mobile);
print("result:",result);
if result:
    print(result.group());
else:
    print(mobile, " match failed");
