import demjson;
from flask import json;
class JsonTestObj:
    name = "";
    age = 10;
    def __init__(self, name, age):
        self.name = name;
        self.age = age;
    def toJsonString(self):
        return "";

if __name__ == "__main__":
    print("test demson...");
    jstr = "{\"name\":\"bobo huang\"}";
    jobj = demjson.decode(jstr);
    print("object's name is :{name}".format(name=jobj["name"]));
    print("object:");
    print(jobj);
    jtObj = JsonTestObj("bobo", 10);
    print(jtObj.__dict__);
    jtString = json.dumps(jtObj.__dict__);
    print("JsonTestObject is :");
    print(jtString);
    