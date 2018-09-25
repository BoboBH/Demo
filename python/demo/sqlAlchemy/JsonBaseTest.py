from JsonBase import JsonBase as JsonBase;
from sqlalchemy import Column,String,Integer,DateTime, Numeric,create_engine,ForeignKey;
from flask import json;
"""
class User(JsonBase):
    __tablename__ = "user_info";
    name = Column(Integer, primary_key=True);
    def __init__(self):
        pass;
"""
if __name__ == "__main__":
    print("run Jsonbase test case");
    baseObj = JsonBase();
    baseObj.name = "bobo huang";
    baseObj.id = 100;
    print(baseObj);
    print("Base Object Dict:");
    print(baseObj.toDict());
