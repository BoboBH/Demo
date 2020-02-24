package org.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HeaderReceiver {

	@RabbitHandler
	@RabbitListener(queues="credit.bank")
	public void creceiveCreditBank(String msg){
		System.out.println("Receive Credit Bank message: " + msg);
	}
	
	@RabbitHandler
	@RabbitListener(queues="credit.finance")
	public void creceiveCreditFinance(String msg){
		System.out.println("Receive Credit Finance message: " + msg);
	}
}
