package com.bobo.fristsba;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;



@MapperScan("com.bobo.fristsba.mapper")
@SpringBootApplication
@EnableScheduling
public class FristsbaApplication  extends SpringBootServletInitializer{

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FristsbaApplication.class);
   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FristsbaApplication.class);
    }
	public static void main(String[] args) {
		//Do not stop at SilentExitException in Debug Model;
		System.out.println("bobo. huang");
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(FristsbaApplication.class, args);
		log.trace("Here is log Trance");
		log.debug("Here is log Debug");
		log.info("Here is log info");
		log.warn("Here is log Warn");
		log.error("Here is log Error");
	}

}
