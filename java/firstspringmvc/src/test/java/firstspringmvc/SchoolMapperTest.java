package firstspringmvc;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.firstspringmvc.bean.School;
import com.bobo.firstspringmvc.mapper.SchoolMapper;

import junit.framework.TestCase;

public class SchoolMapperTest extends BaseUnitTest {

	@Autowired
	private SchoolMapper schoolMapper;
	@Test
	public void TestSchoolMapper(){
	
		School school = schoolMapper.getSchoolById("12234");
		TestCase.assertNotNull(school);
		List<School> searchResult = schoolMapper.searchSchool(school);
		TestCase.assertTrue(searchResult.size() > 0);
		school.setName( "test AAA" + school.getName());
		searchResult = schoolMapper.searchSchool(school);
		TestCase.assertTrue(searchResult.size() == 0);
		searchResult = schoolMapper.searchSchool(null);
		TestCase.assertTrue(searchResult.size() > 0);
		School sample = new School();
		sample.setName("Yangguang");
		sample.setAddress("Changlong");
		searchResult = schoolMapper.searchSchool(sample);
		TestCase.assertTrue(searchResult.size() > 0);
		
		sample = new School();
		sample.setId("123452354");
		sample.setName("Yangguang Middle School");
		sample.setAddress("test address");
		sample.setLevel(3);
		schoolMapper.addSchool(sample);
		school = schoolMapper.getSchoolById(sample.getId());
		TestCase.assertEquals(sample.getName(), school.getName());
		if("test".equals(school.getAddress()))
			school.setAddress("testaaa");
		else
			school.setAddress("test");
		schoolMapper.updateSchool(school);
		sample = schoolMapper.getSchoolById(sample.getId());
		TestCase.assertEquals(school.getAddress(), sample.getAddress());
		
	}
}
