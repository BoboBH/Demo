package org.rabbitmqtest;

import java.util.Date;

import org.rabbitmqtest.config.TopicRabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

	@Autowired
	private AmqpTemplate rabbitmqTemplate;
	
	public void send2TopicMessage(int i){
		String content =  "Message No=" + i + "Topic Message 1 with round key = (topic.message): " + new Date();
		System.out.println("Send Content: " + content);
		rabbitmqTemplate.convertAndSend("topicExchange",TopicRabbitConfig.MESSAGE, content);
	}
	public void send2TopicMessages(int i){
		String content = "Message No=" + i + " Topic Messages 2 with round key = (topic.#):" + new Date();
		System.out.println("Send Content: " + content);
		rabbitmqTemplate.convertAndSend("topicExchange","topic.messages", content);
	}
}
