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