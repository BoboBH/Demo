import sys;

print("start test list");
list = ["bobo Huang", "Happy Huang"];
list.append("Yu Li");
it = iter(list);
for obj in it:
    print(obj);
print("Display all item in list");
list.clear();
print("print list after clear");
it = iter(list);
for obj in it:
    print(obj);
print("Display all item in list");

dict = {};
dict["name"]="bobo huang";
dict["id"]=30;
print(dict);


print("list:");
list = ["bobo Huang", "Happy Huang"];
print(list);
list = [x.upper() for x in list];
print(list);
for x in list:
    print(x.lower());
print("list:")
print(list);
print("list by loop:")
for i in range(len(list)):
    print("i=",i);
    print(list[i]);
    print(list[i-1]);
    print(list[i-2]);