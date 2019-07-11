package firstspringmvc;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.junit.Test;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:web.xml","classpath*:spring/spring-*.xml"})  
public class BaseUnitTest {

	@Test
	public void Test(){
		TestCase.assertTrue(true);
	}
}
