package com.bobo.fristsba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bobo.fristsba.authentication.AuthenticationInterceptor;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(authenticationInterceptor())
	                .addPathPatterns("/api/**");
	    }
	@Bean
	public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
