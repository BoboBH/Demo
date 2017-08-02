"""
Student Data access class
Author: Bobo Huang
Last Update : 2017-7-29
"""
import pymysql as ms;
import AbsBaseDao as abd;
import Student as st;
class StudentDao(abd.AbsBaseDao):
	def __init__(self, host, user, pw, db):
		abd.AbsBaseDao.__init__(self,host, user, pw, db);
	
	def getStudentById(self, id):
		print("Connection is:host={self.host}, user={self.user}, pw={self.password}, database={self.database}".format(self=self));
		db = ms.connect(self.host,self.user, self.password, self.database);
		cursor = db.cursor();
		sql = "select id, name, birthday, grade, mem from student where id = {id}".format(id=id);
		print("SQL expression is :%s"%sql);
		cursor.execute(sql);
		student = st.Student(123);
		return student;
	
   