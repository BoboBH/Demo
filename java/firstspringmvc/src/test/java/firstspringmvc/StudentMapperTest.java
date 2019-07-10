package firstspringmvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.firstspringmvc.bean.Student;
import com.bobo.firstspringmvc.mapper.StudentMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:web.xml","classpath*:spring/spring-*.xml"})  
public class StudentMapperTest {

	@Autowired
	private StudentMapper studentMapper;
	@SuppressWarnings("deprecation")
	@Test
	public void TestGetAllStudent(){
		List<Student> list = studentMapper.getAllStudent();
		Assert.isTrue(list.size() > 0);
	}
}
