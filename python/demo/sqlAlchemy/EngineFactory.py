from sqlalchemy import create_engine;
def createMysqlEngine():    
    return create_engine("mysql+pymysql://jeesite:123456@localhost/test?charset=utf8",encoding='utf-8', echo=False);