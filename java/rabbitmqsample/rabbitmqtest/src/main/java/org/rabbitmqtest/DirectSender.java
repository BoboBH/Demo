package org.rabbitmqtest;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send(){
		String context = "hello " + new Date();
		System.out.println(context);
		this.rabbitTemplate.convertAndSend("hello", context);
	}
}
