package com.bobo.zktest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate getRestTemplate(ClientHttpRequestFactory factory){
		RestTemplate restTemplate = new RestTemplate(factory);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter map = new MappingJackson2HttpMessageConverter();
		messageConverters.add(map);
		messageConverters.add(new FormHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
	@Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory(){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);
        return requestFactory;

    }

}
