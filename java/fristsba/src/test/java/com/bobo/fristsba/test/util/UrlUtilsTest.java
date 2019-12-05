package com.bobo.fristsba.test.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bobo.fristsba.FristsbaApplication;
import com.bobo.fristsba.util.UrlUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes=FristsbaApplication.class)
public class UrlUtilsTest {
	
	@Test
	public void TestGetUserId(){
		
		String url = "http://localhost:8080/api/user/12345/profile";
		String userId = UrlUtil.getUserId(url);

        Assert.assertEquals("12345", userId);
        url = "http://localhost:8080/api/user/12345";
        userId = UrlUtil.getUserId(url);
        Assert.assertEquals("12345", userId);
        url = "http://localhost:8080/api/user/12345/";
        userId = UrlUtil.getUserId(url);
        Assert.assertEquals("12345", userId);
        url = "/api/user/12345";
        userId = UrlUtil.getUserId(url);
        Assert.assertEquals("12345", userId);
        url = "/api/user/12345/";
        userId = UrlUtil.getUserId(url);
        Assert.assertEquals("12345", userId);
	}
	

}
