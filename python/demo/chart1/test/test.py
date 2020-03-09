# -*- coding utf-8
import math;
import time;
def sayhello(name):
    print("Hello ", name);
def isInStudentSet(stSet, stName):
    if stSet == None or stName not in stSet:
       print(stName, " is not in student set");
       return False;
    print(stName, " is in student set");
    return True;
if __name__ == "__main__":
    print("Test start..........");
    # simple line comment
    '''
    Multiple Line comments
    '''
    ival = 100;
    bval = True
    fval = 1.23
    print("interger:", ival);
    print("Boolean:", bval);
    print("Float:", fval);
    ival = 200;
    print("interger after reset:", ival);
    del ival, bval, fval;
    #will report error that ival is not defined
    #print("interger after del:", ival);
    print("Say Hello to :");
    sayhello("bobo huang");
    tuple = ("abc","bobo huang","happy");
    print("tuple(1):", tuple[1]);
    print("tuple:", tuple);
    list =["bobo huang","happy huang"];
    list.append("anna lee");
    print("list:", list);
    stuSet = {"bobo huang", "happy huang"};
    #stuSet.append("anna lee");
    print("student set:", stuSet);
    if "bobo huang" in stuSet:
        print("bobo is in student set");
    else:
        print("bobo is not in student set");
    isInStudentSet(stuSet, "bobo huang");
    isInStudentSet(stuSet, "happy huang");
    isInStudentSet(stuSet, "anna lee");
    print("test dictionary...")
    dict = {"id":100,"name":"bobo huang","age":18};
    print("dictionary: ", dict);
    print("dict['id']=:", dict["id"]);
    print("dict['name']=:", dict["name"]);
    print("dict['age']=:", dict["age"]);
    dict["email"]="bobo.huang@yff.com";
    dict["relations"]=stuSet;
    print("dict['email']=:", dict["email"]);
    print("dict keys:", dict.keys());
    print("dict values:", dict.values());
    
    print("2^2 is ",2**2);
    ival = int("256");
    print("'256' convert to integer is :", ival);
    ival = float("256.1");
    print("'256.1' convert to float is :", ival);
    x,y = math.modf(25.6);
    print("modf(25.6) is ,", x, " ,", y);
    print("abs(-10) is ", abs(-10));
    sval = "bobo huang";
    print("sval upper:", sval.upper());
    print("lenght of sval:", len(sval));    
    print("sval[0:5]", sval[0:5]);
    print("--------------Test for time--------------")
    ticks = time.time();
    print("time tikes is ", ticks)
    localtime = time.localtime(ticks);
    print("local time is ", localtime);    
    # 格式化成2016-03-20 11:45:39形式
    print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())) ;
    print(time.strftime("%Y%m%d", time.localtime())) ;
    print("Test End");