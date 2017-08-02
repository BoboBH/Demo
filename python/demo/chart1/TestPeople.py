import People as p;
import Student as s;

print("start test people");
instance = p.People("Happy Huang", 7);
instance.speak();
instance = s.Student(instance.name, instance.age, 5);
instance.speak();
print("All test cases done!");