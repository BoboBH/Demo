"""
test mysql function;
"""
import Student as st;
import StudentDao as std;
print("start testing");
student = st.Student(123);
student.name = "Happy Huang";
student.birthday = "2016-04-04";
student.grade = "5";
student.mem = "";
print(student.__str__());

studentDao = std.StudentDao("localhost", "root", "123456", "pytest", "utf8");
student = studentDao.getStudentById(123456);
print(student);
student = st.Student(654321);
student.name="黄齐仁";
student.birthday="2001-08-09";
student.grade="6";
student.mem="test add student";
print("try to add student:", student);
studentDao.addStudent(student);
student.name = "Bobo Huang";
student = studentDao.addStudent(student);
print("added student:", student);
student.id = 0;
student = studentDao.addStudent(student);
print("added student:", student);
