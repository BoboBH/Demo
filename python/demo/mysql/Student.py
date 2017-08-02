"""
Define student calss, which will be used to fill data through by data row;
Authorizer :  Bobo Huang
Last Update : 2017-07-29
"""

class Student:
	id = 0;
	name = "";
	birthday = "";
	grade = "";
	mem = "";
	def __init__(self, id):
		self.id = id;
		
	def __str__(self):
		return "id={self.id},name={self.name}, birthday={self.birthday},grade={self.grade},mem={self.mem}".format(self=self);
	

