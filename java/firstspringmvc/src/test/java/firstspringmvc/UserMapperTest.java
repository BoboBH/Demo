package firstspringmvc;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bobo.firstspringmvc.bean.User;
import com.bobo.firstspringmvc.mapper.UserMapper;

import junit.framework.TestCase;

public class UserMapperTest extends BaseUnitTest {

	@Autowired
	private UserMapper userMapper;
	@Test
	public void TestUserMapper(){		
		List<User> list = userMapper.getUserByEmail("1021@test.com");
		TestCase.assertTrue(list.size() > 0);
		User user = userMapper.getUserById(12323);
		TestCase.assertNotNull(user);		
		list = userMapper.getAllUsers();
		TestCase.assertTrue(list.size() > 0);
		Integer userId = 12345;
		user = userMapper.getUserById(userId);
		if(user != null)
			userMapper.deleteUser(userId);
		user = new User();
		user.setId(userId);
		user.setEmail("12345@test.com");
		user.setName("Bobo.huang");
		userMapper.addUser(user);
		user = userMapper.getUserById(userId);
		TestCase.assertNotNull(user);
		user.setName("Happy Huang");
		userMapper.updateUser(user);
		user = userMapper.getUserById(userId);
		TestCase.assertEquals("Happy Huang", user.getName());
	}
	
	
}
