"""
Student Data access class
Author: Bobo Huang
Last Update : 2017-7-29
"""
import pymysql as ms;
import AbsBaseDao as abd;
import Student as st;
class StudentDao(abd.AbsBaseDao):
	def __init__(self, host, user, pw, db, charset):
		abd.AbsBaseDao.__init__(self,host, user, pw, db,charset);
	
	def getStudentById(self, id):
		print("Connection is:host={self.host}, user={self.user}, pw={self.password}, database={self.database}, charset={self.charset}".format(self=self));
		db = super().getDB();
		cursor = db.cursor();
		sql = "select id, name, birthday, grade, mem from student where id = {id}".format(id=id);
		print("SQL expression is :%s"%sql);
		cursor.execute(sql);
		data = cursor.fetchone();
		student = None;
		if data != None :
			student = st.Student(id);
			student.name = data[1];
			student.birthday = data[2];
			student.grade = data[3];
			student.mem = data[4];
		db.close();
		return student;
	
	def addStudent(self, student):
		print("Connection is:host={self.host}, user={self.user}, pw={self.password}, database={self.database}, charset={self.charset}".format(self=self));
		db = super().getDB();
		sql = "insert into student(id, name, birthday,grade,mem) values('%d', '%s','%s','%s','%s')"%(student.id, student.name, student.birthday,student.grade, student.mem);
		existSt = self.getStudentById(student.id);
		if existSt != None:
			sql = "update student set name='%s',birthday='%s',grade='%s',mem='%s' where id=%d"%(student.name,student.birthday,student.grade,student.mem, student.id);
		print("SQL Expression is:", sql);
		cursor = db.cursor();
		try:
			cursor.execute(sql);
			db.commit();
		except:
			db.rollback();
		db.close();
		return;
   