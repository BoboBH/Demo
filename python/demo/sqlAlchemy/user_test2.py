import sys;
sys.path.append("..");
from sqlalchemy import Column,String,Integer,DateTime, Numeric,create_engine,ForeignKey;
from sqlalchemy.orm import sessionmaker;
from sqlalchemy.ext.declarative import declarative_base;
from sqlAlchemy.JsonBase import JsonBase;
import Utility.JsonUtil as JsonUtil;
Base = declarative_base();
def convert_datetime(value):
    if value:
        return value.strftime("%Y-%m-%d %H:%M:%S");
    else:
        return "";
class UserInfo(Base):
    __tablename__ = "user_info";
    id = Column(Integer, primary_key = True);
    name = Column(String(100));
    address = Column(String(255));
    def __init__(self, name, address):
        self.name = name;
        self.address = address;
    def __str__(self):
        return "User info: id={id}, name={name}, address={address}".format(id=self.id, name=self.name,address=self.address);
    """
    def toDict(self):
        for col in self.__table__.columns:
            if isinstance(col.type, DateTime):
                value = convert_datetime(value);
            elif isinstance(col.type, Numeric):
                value = float(getattr(self, col.name));
            else:
                value = getattr(self, col.name);
            yield (col.name, value);
    """
class Book(Base):
    __tablename__ = "book";
    id = Column(Integer, primary_key = True);
    name = Column(String(200));
    user = Column(Integer, ForeignKey("user_info.id"));
    def __init__(self, name):
        self.name = name;
    def __str__(self):
        return "Book Info: id={id},name={name}, user={user}".format(id= self.id, name = self.name, user = self.user);
if __name__ == "__main__":
    engine = create_engine("mysql+pymysql://jeesite:123456@localhost/test?charset=utf8",
                                    encoding='utf-8', echo=False)
    Session_Class = sessionmaker(bind=engine) ;
    session = Session_Class();
    userInfo = UserInfo("bobo H", "address 22");
    session.add(userInfo);
    session.commit();
    userInfo.id = userInfo.id;
    print(userInfo);
    q1 = session.query(UserInfo).filter(UserInfo.name.like("%bobo%"), UserInfo.address.like("%add%")).first();
    print("query 1: ", q1);
    print("Print query Result:");
    for q in session.query(UserInfo).filter(UserInfo.name.like("%bobo%"), UserInfo.address.like("%add%")).all():
        print(q);
    print("try to update user info...");
    q1 = session.query(UserInfo).filter(UserInfo.id==userInfo.id).first();
    q1.name = "Happy Huang";
    session.commit();
    print("Final user info :");
    print(userInfo);
    session.close();