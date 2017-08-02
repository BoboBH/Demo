

import pymysql as ms;
class AbsBaseDao:
	host = "";
	user = "";
	password="";
	database= "";
	def __init__(self, host, user, pw, db):
		self.host = host;
		self.user = user;
		self.password = pw;
		self.database = db;
		
	
	