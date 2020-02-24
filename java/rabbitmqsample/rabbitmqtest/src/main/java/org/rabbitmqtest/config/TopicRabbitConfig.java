package org.rabbitmqtest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
	
	public final static String MESSAGE="topic.message";
	public final static String MESSAGES="topic.messages";
	

	public final static String TOP_EXCHANGE_NAME="topicExchange";

	//create queue
	@Bean
	public Queue queueMessage(){
		return new Queue(MESSAGE);
	}
	
	@Bean
	public Queue queueMessages(){
		return new Queue(MESSAGES);
	}
	
	@Bean
	public TopicExchange exchange(){
		return new TopicExchange(TOP_EXCHANGE_NAME);
	}
	
	@Bean
	public Binding bindExchangeMessage(Queue queueMessage, TopicExchange exchange)
	{		
		return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
	}
	@Bean
	public Binding bindExchangeMessages(Queue queueMessages, TopicExchange exchange){
		
		return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
	}
	
}
