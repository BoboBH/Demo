from sqlalchemy import create_engine;
from sqlalchemy.orm import sessionmaker;
import Fund.FundBriefInfo as finfo;
print("start fund regarded test case...");
fundInfo = finfo.FundBriefInfo();
fundInfo.fundId="F000000001";
fundInfo.symbol = "100000";
fundInfo.fundName="Bobo Huang";

fundDao = finfo.FundBriefInfoDao();
fundDao.addFundInfo(fundInfo);

f = fundDao.getFundInfo(fundInfo.fundId);
print(f.fundName);

fundInfo = finfo.FundBriefInfo();
fundInfo.fundId = "F000000002";
fundInfo.fundName = "Bobo Test";
fundInfo.symbol = "100001";
f = fundDao.getFundInfo(fundInfo.fundId);
if f == None:
    fundDao.addFundInfo(fundInfo);
else:
    if f.fundName == "Bobo Test":
        f.fundName = "Bobo Test 1";
    else :
        f.fundName = "Bobo Test";
    fundDao.addFundInfo(f)
print("add/update fund(%s)"%fundInfo.fundId);