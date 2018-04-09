from sqlalchemy import Table, MetaData, Column, Integer, String, ForeignKey;
from sqlalchemy.orm import mapper,sessionmaker;
from sqlalchemy import create_engine;
engine = create_engine("mysql+pymysql://jeesite:123456@localhost/test?charset=utf8",
                                    encoding='utf-8', echo=True)
metaData = MetaData();
user_info_table = Table("user_info", metaData,
    Column("id", Integer, primary_key=True),
    Column("name", String(100)),
    Column("address", String(255))
    );
class UserInfo(object):
    def __init__(self, name, address):
        self.name= name;
        self.address = address;
    def __str__(self):
        return "id={id}, name={name}, address={address}".format(id=self.id, name=self.name, address = self.address);

mapper(UserInfo, user_info_table);
Session_Class = sessionmaker(bind=engine);
session = Session_Class();
userInfo = UserInfo("bobo Huang1111", "新闻路特区报业大厦13楼");
print(userInfo);
session.add(userInfo);
print(userInfo);
session.commit();
print("xxxxxx");
print();
print();
print();
print("Last Insert user is :", userInfo);
qUserInfo = session.query(UserInfo).filter_by(name="bobo Huang1111").first();
print("qUser Info is ", qUserInfo);
session.commit();
session.close();
#print("Final user info :", userInfo);

