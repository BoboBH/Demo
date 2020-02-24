package org.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="topic.message")
public class TopicReceiverQ1 {

	@RabbitHandler
	public void process(String message){
		System.out.println("topic Receiver 1  receive a message :" + message);
	}
}
