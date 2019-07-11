package firstspringmvc;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bobo.firstspringmvc.bean.User;
import com.bobo.firstspringmvc.mapper.UserMapper;

public class UserMapperTest extends BaseUnitTest {

	@Autowired
	private UserMapper userMapper;
	@Test
	public void TestUserMapper(){
		
		List<User> list = userMapper.getUserByEmail("1021@test.com");
		Assert.isTrue(list.size() > 0);
		User user = userMapper.getUserById(12323);
		Assert.notNull(user);
		
	}
	
	
}
