package org.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="hello")
public class DirectReceiver {

	@RabbitHandler
	public void process(String content){
		System.out.println("Direct Receiver: " + content);
	}
}
