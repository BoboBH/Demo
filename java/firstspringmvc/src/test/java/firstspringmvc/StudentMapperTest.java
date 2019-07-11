package firstspringmvc;
import org.springframework.util.Assert;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.firstspringmvc.bean.Student;
import com.bobo.firstspringmvc.mapper.StudentMapper;


public class StudentMapperTest extends BaseUnitTest {

	@Autowired
	private StudentMapper studentMapper;
	@SuppressWarnings("deprecation")
	@Test
	public void TestGetAllStudent(){
		List<Student> list = studentMapper.getAllStudent();
		Assert.isTrue(list.size() > 0);
	}
}
