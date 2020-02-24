package org.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="topic.messages")
public class TopicReceiverQ2 {

	@RabbitHandler
	public void process(String message){
		System.out.println("topic Receiver 2  receive a message :" + message);
	}
}
