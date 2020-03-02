package com.bobo.shirosample.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bobo.shirosample.auth.JwtRealm;
import com.bobo.shirosample.service.UserService;

@Configuration
public class JwtShiroConfig {
	
	@Bean
	public JwtRealm jwtRealm(UserService userService){
		return new JwtRealm(userService);
	}
	@Bean
    public SecurityManager securityManager(JwtRealm JwtRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(JwtRealm);
        return securityManager;
    }
	

}
