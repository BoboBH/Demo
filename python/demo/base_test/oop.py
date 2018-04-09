class Person:
    name="";
    age = 0;
    def __init__(self, name, age):
        self.name = name;
        self.age = age;
    def __str__(self):
        return "Name={name},Age={age}".format(name=self.name, age=self.age);
class Student(Person):
    grade = 0;
    def __init__(self, name, age,grade):
        super(Student, self).__init__(name, age);
        self.grade = grade;
    def __str__(self):
        return "Name={name}, Age={age}, grade={grade}".format(name=self.name, age=self.age, grade=self.grade);
class Area:
    def Square(self):
        return 0;
class Triangle(Area):
    length = 0;
    high = 0;
    def __init__(self, length, high):
        self.length = length;
        self.high = high;
    def Square(self):
        return self.length * self.high / 2.0;
class Rectangle(Triangle):
    def __init__(self, length, high):
        super(Rectangle, self).__init__(length, high);
    def Square(self):
        return super(Rectangle,self).Square() * 2;
class Round(Area):
    radius = 0;
    def __init__(self, radius):
        self.radius = radius;
    def Square(self):
        return self.radius ** 2 *3.14;


#main
p = Person("bobo", 30);
print(p);
print("Person's Age is ", p.age);
s = Student("bobo", 30, 2);
print(s);

t = Triangle(2,3);
print("Triangle's Square is :", t.Square());
r = Rectangle(2,3);
print("Rectangle's Square is :", r.Square());
r = Round(1);
print("Round's Square is :", r.Square());