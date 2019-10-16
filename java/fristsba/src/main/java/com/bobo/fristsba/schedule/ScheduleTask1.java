package com.bobo.fristsba.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class ScheduleTask1 {

	private int count = 0;
	//@Scheduled(cron="*/6 * * * * ?")
	private void process(){
		System.out.println("This is schedule task running " + (count++));
	}
}
