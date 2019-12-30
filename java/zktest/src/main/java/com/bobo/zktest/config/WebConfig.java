package com.bobo.zktest.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bobo.zktest.filter.MyFilter;


@Configuration
public class WebConfig {
	@Autowired
	private LeaderSelector leaderSelector;
	@Bean
	 public RemoteIpFilter remoteIpFilter(){
		 return new RemoteIpFilter();
	 }
  @SuppressWarnings("unchecked")
  @Bean
   public FilterRegistrationBean<MyFilter> testFilterRegistration() {	
		@SuppressWarnings("rawtypes")
		FilterRegistrationBean registration = new FilterRegistrationBean();
       registration.setFilter(new MyFilter(leaderSelector));
       registration.addUrlPatterns("/*");
       registration.addInitParameter("paramName", "paramValue");
       registration.setName("MyFilter");
       registration.setOrder(1);
       return registration;

   }

}
