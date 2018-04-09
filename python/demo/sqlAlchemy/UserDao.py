from sqlalchemy import Column,String,Integer,create_engine,ForeignKey;
from sqlalchemy.orm import sessionmaker;
from sqlalchemy.ext.declarative import declarative_base;
from user_test2 import UserInfo;
import EngineFactory as ef;
class UserDao:
    def getUserInfoById(self, userId):
        engine =  ef.createMysqlEngine();
        Session_Class = sessionmaker(bind=engine);
        session = Session_Class();
        userInfo = session.query(UserInfo).filter(UserInfo.id==userId).first();
        session.close();
        return userInfo;
    def updateUserInfo(self, userInfo):
        engine =  ef.createMysqlEngine();
        Session_Class = sessionmaker(bind=engine);
        session = Session_Class();
        q = session.query(UserInfo).filter(UserInfo.id==userInfo.id).first();
        if q != None:
            q.name = userInfo.name;
            q.address = userInfo.address;
        else:
            print("User does not exist");
            userid = 0;
            session.add(userInfo);
        session.commit();
        userid = userInfo.id;
        session.close();
        return userid;