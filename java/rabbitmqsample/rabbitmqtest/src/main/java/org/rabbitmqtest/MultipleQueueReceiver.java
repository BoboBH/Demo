package org.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues={"hello","topic.message","credit.bank","credit.finance"})
public class MultipleQueueReceiver {
	
	@RabbitHandler
	public void receiveMessage(String msg){
		System.out.println("MultipleQueueReceiver receive a message:" + msg);
	}

}
