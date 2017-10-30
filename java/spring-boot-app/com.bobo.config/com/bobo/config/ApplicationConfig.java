package com.bobo.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	private String applicationName;

	public String getApplicationName() {
		return applicationName;
	}
	public String setApplicationName(String applicationName) {
		return this.applicationName = applicationName;
	}

}
