package com.bobo.fristsba;

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

  @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FristsbaApplication.class);
    }
	public static void main(String[] args) {
		System.out.println("bobo. huang");
		//Do not stop at SilentExitException in Debug Model;
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(FristsbaApplication.class, args);
	}

}
