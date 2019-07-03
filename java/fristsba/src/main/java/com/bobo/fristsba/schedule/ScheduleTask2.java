package com.bobo.fristsba.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask2 {

	private static final SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Scheduled(fixedRate = 6000)
	public void reportCurrentTime(){
		
		System.out.println("It is " + dateFormate.format(new Date()));
	}
}
