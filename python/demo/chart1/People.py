
class People:
	name = "";
	age = 18;
	__weight = 100
	
	def __init__(self, n, a):
		self.name = n;
		self.age = a;
	
	def speak(self):
		print("%s 说:我 %d 岁了"%(self.name, self.age));