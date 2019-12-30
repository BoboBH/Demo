package com.bobo.zktest;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.bobo.zktest.bean.Client;
import com.bobo.zktest.bean.JsonClient;
import com.bobo.zktest.bean.JsonResult;
import com.bobo.zktest.bean.ResponseClient;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = ZktestApplication.class)
public class RestTemplateTest {

	private final static String url="https://ross-qa.youyu.cn:5432/v2/ross/clients/400014506";
	private final static String postUrl="http://localhost:9000/clients";
	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void testAPI(){
		ResponseEntity<ResponseClient> response = restTemplate.getForEntity(url, ResponseClient.class);
		ResponseClient result = response.getBody();
		Assert.assertEquals(0, result.getCode());
		Assert.assertNotNull(result.getData());
		Client cli = result.getData();
		Assert.assertEquals("1034099", cli.getAppid());
		
		ResponseEntity<JsonClient> jresponse = restTemplate.getForEntity(url, JsonClient.class);
		JsonClient jresult = jresponse.getBody();
		Assert.assertEquals(0, result.getCode());
		Assert.assertNotNull(result.getData());
		cli = jresult.getData();
		Assert.assertEquals("1034099", cli.getAppid());
	}
	
	@Test
	public void getTemplateEntity(){
		ParameterizedTypeReference<JsonResult<Client>> pTypeReference = new ParameterizedTypeReference<JsonResult<Client>>(){};
		ResponseEntity<JsonResult<Client>> response = restTemplate.exchange(url, HttpMethod.GET, null, pTypeReference);
		JsonResult<Client> result = response.getBody();
		Assert.assertEquals(0, result.getCode());
		Assert.assertNotNull(result.getData());
		Client cli = result.getData();
		Assert.assertEquals("1034099", cli.getAppid());	
		
	}
	
	@Test
	public void testGetGeneralEntity(){
		ResponseEntity<JsonResult<Client>> response = this.getForTemplateEntity(url, Client.class);
		JsonResult<Client> result = response.getBody();
		Assert.assertEquals(0, result.getCode());
		Assert.assertNotNull(result.getData());
		Client cli = result.getData();
		Assert.assertEquals("1034099", cli.getAppid());	
	}
	
	
	private <T> ResponseEntity<JsonResult<T>> getForTemplateEntity(String url, Class<T> classType){
		ParameterizedTypeReference<JsonResult<T>> pTypeReference = new ParameterizedTypeReference<JsonResult<T>>(){};
		return restTemplate.exchange(url, HttpMethod.GET, null, pTypeReference);
	}
	
	@Test
	public void testPostClient(){
		Client client = new Client();
		client.setId("1212352345");
		client.setChinesename("黄齐仁");
		client.setEnglishname("bobo Huang");
		client.setAppid("appid");
		ResponseEntity<JsonClient> response = restTemplate.postForEntity(postUrl, client, JsonClient.class);
		JsonClient jsonClient = response.getBody();
		Assert.assertNotNull(jsonClient.getData());
		Assert.assertEquals(client.getAppid(), jsonClient.getData().getAppid());
	}
}
