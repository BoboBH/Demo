import People as p;
class Student(p.People):
	grade = 1;
	
	def __init__(self, name, age, grade):
		p.People.__init__(self,name, age);
		self.grade = grade;
		
	def speak(self):
		print("%s 说: 我 %d 岁了. 我读 %d 级了!"%(self.name, self.age, self.grade));