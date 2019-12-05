package firstspringmvc;


import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.firstspringmvc.bean.Role;
import com.bobo.firstspringmvc.mapper.RoleMapper;

import junit.framework.TestCase;

public class RoleMapperTest extends BaseUnitTest {

	@Autowired
	private RoleMapper roleMapper;
	
	@org.junit.Test
	public void TestRoleMapper(){
		String id = "TEST_123456";
		Role role = roleMapper.getRoleById(id);
		if(role != null){
			roleMapper.deleteRole(id);
			role = roleMapper.getRoleById(id);
			TestCase.assertNull(role);
		}
		role = new Role();
		role.setId(id);
		role.setName("TEST ADMIN");
		roleMapper.addRole(role);
		role = roleMapper.getRoleById(id);
		TestCase.assertNotNull(role);
		role.setName("TEST CLIENT");
		roleMapper.updateRole(role);
		role = roleMapper.getRoleById(id);
		TestCase.assertEquals("TEST CLIENT", role.getName());
		roleMapper.deleteRole(id);
		role = roleMapper.getRoleById(id);
		TestCase.assertNull(role);
	}

}
