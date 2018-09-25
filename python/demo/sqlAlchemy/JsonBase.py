import sys;
sys.path.append("..");
import Utility.JsonUtil as JsonUtil;
##JsonBase的对象，实现toDict()方法，将Sqlalchemy对象转换成dict-json
from sqlalchemy import Column,String,Integer,DateTime, Numeric,create_engine,ForeignKey;
from sqlalchemy.orm import sessionmaker;
from sqlalchemy.ext.declarative import declarative_base;
Base = declarative_base();
def test(self):
    print("test self");
    return "test self";
def toDict(self):
    print("try to convert object to dict...");
    return "xxx";
    """
    for col in self.__table__.columns:
        if isinstance(col.type, DateTime):
            value = convert_datetime(value);
        elif isinstance(col.type, Numeric):
            value = float(getattr(self, col.name));
        else:
            value = getattr(self, col.name);
        yield (col.name, value);
    """
def to_dict(self):
    print("xxxxxxx");
    return {c.name: getattr(self, c.name, None) for c in self.__table__.columns};
Base.toDict = JsonUtil.gen_dict;
def convert_datetime(value):
    if value:
        return value.strftime("%Y-%m-%d %H:%M:%S");
    else:
        return "";
class JsonBase(Base):
    __tablename__ = "jsbase";#just for base
    id = Column(Integer, primary_key=True);
    name = Column(String(200));
    def __init__(self):
        #__tablename__ = "jsbase";
        pass;