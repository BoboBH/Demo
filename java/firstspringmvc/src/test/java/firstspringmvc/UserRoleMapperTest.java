package firstspringmvc;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.firstspringmvc.bean.Role;
import com.bobo.firstspringmvc.bean.User;
import com.bobo.firstspringmvc.bean.UserRole;
import com.bobo.firstspringmvc.mapper.RoleMapper;
import com.bobo.firstspringmvc.mapper.UserMapper;
import com.bobo.firstspringmvc.mapper.UserRoleMapper;

import junit.framework.TestCase;

public class UserRoleMapperTest extends BaseUnitTest {

	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserMapper userMapper;
	@Test
	public void TestUserRoleMapper() {
		String id = "TEST_123456";
		UserRole ur = userRoleMapper.getUserRoleById(id);
		if(ur != null)
			userRoleMapper.deleteUserRole(id);
		ur = new UserRole();
		ur.setId(id);
		String userId = "1234546";
		String roleId = "R_123456";
		this.CreateRole(roleId);
		this.CreateUser(Integer.parseInt(userId));
		ur.setUserId(userId);
		ur.setRoleId(roleId);
		ur.setRemarks("Initialized");
		userRoleMapper.addUserRole(ur);
		ur = userRoleMapper.getUserRoleById(id);
		TestCase.assertNotNull(ur);
		TestCase.assertEquals(roleId,ur.getRoleId());
		TestCase.assertEquals(userId,ur.getUserId());
		String remarks = "aaa";
		ur.setRemarks(remarks);
		userRoleMapper.updateUserRole(ur);
		ur = userRoleMapper.getUserRoleById(id);
		TestCase.assertEquals(remarks, ur.getRemarks());
		List<UserRole> list = userRoleMapper.getAllUserRoles();
		TestCase.assertTrue(list.size() > 0);
		List<Role> roles = roleMapper.getRolesByUserId(userId);
		TestCase.assertTrue(roles.size() > 0);
		List<Role> searchRoles = roles.stream().filter(r->r.getId().equals(roleId)).collect(Collectors.toList());
		TestCase.assertTrue(searchRoles.size() > 0);
		userRoleMapper.deleteUserRole(id);
		ur = userRoleMapper.getUserRoleById(id);
		TestCase.assertNull(ur);
		
	}
	
	private void CreateUser(Integer userId){
		User user = userMapper.getUserById(userId);
		if(user != null)
			return;
		user = new User();
		user.setId(userId);
		user.setName("UNIT_TEST_FOR_USER_ROLE");
		userMapper.addUser(user);
	}
	private void CreateRole(String roleId){
		Role role = roleMapper.getRoleById(roleId);
		if(role != null)
			return;
		role = new Role();
		role.setId(roleId);
		role.setName("UNIT_TEST_FOR_USER_ROLE");
		roleMapper.addRole(role);
	}

}
