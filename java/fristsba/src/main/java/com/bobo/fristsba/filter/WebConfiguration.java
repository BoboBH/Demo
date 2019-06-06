package com.bobo.fristsba.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
	 @Bean
	 public RemoteIpFilter remoteIpFilter(){
		 return new RemoteIpFilter();
	 }
   @SuppressWarnings("unchecked")
@Bean
    public FilterRegistrationBean<MyFilter> testFilterRegistration() {	
		@SuppressWarnings("rawtypes")
		FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;

    }
	
}
