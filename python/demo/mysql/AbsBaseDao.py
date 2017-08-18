

import pymysql as ms;
class AbsBaseDao:
	host = "";
	user = "";
	password="";
	database= "";
	charset = "";
	def __init__(self, host, user, pw, db, charset):
		self.host = host;
		self.user = user;
		self.password = pw;
		self.database = db;
		self.charset=charset;
	def getDB(self):
		return 	ms.connect(host=self.host,user=self.user, passwd=self.password, db=self.database,charset=self.charset);
	
	