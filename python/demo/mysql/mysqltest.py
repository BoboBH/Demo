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

studentDao = std.StudentDao("localhost", "root", "123456", "pytest");
student = studentDao.getStudentById(123);
print(student);