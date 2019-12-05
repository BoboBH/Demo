package com.bobo.sb_no_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
	@Bean
	public UCConfig getUCConfig(){
		 UCConfig config = new UCConfig();
		 config.setBaseUrl("http://www.baidu.com");
		 config.setTerminalKey("bobohuang");
		 return config;
	}

}
