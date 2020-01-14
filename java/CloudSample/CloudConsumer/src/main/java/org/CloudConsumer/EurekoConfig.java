package org.CloudConsumer;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:application.properties",encoding = "UTF-8")
public class EurekoConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1140344034602543378L;

	@Value("${eureka.client.service-url.defaultZone}")
	private String eurekaUrl;

	@Value("${app.name}")
	private String appName;

	public String getEurekaUrl() {
		return eurekaUrl;
	}

	public void setEurekaUrl(String eurekaUrl) {
		this.eurekaUrl = eurekaUrl;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
