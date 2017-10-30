import sys;
from sqlalchemy import create_engine,Column,String;
from sqlalchemy.orm import sessionmaker;
from sqlalchemy.ext.declarative import declarative_base;
Base = declarative_base();
class FundBriefInfo(Base):
    __tablename__="fund_brief_info";
    fundId = Column(String(10),primary_key=True);
    symbol = Column(String(10));
    fundName = Column(String(200));
class FundBriefInfoDao:
    engine = None;
    def __init__(self):
        self.engine = create_engine("mysql://jeesite:123456@localhost:3306/pytest");
    def getSession(self):
        DBSession = sessionmaker(bind=self.engine);
        return DBSession();
    def addFundInfo(self,fundInfo):
        session = self.getSession();
        query = session.query(FundBriefInfo);
        f = query.get(fundInfo.fundId);
        msg = "";
        if f == None:
            session.add(fundInfo);            
            msg = "Add Fund Info(%s) successfully"%fundInfo.fundId;
        else:
            session.merge(fundInfo);
            msg = "Update Fund Info(%s) successfully"%fundInfo.fundId;
        session.commit();
        print(msg);
    def getFundInfo(self, fundId):
        session = self.getSession();
        query = session.query(FundBriefInfo);
        return query.get(fundId);