package org.rabbitmqtest;

import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeaderSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	private Message getMessage(Map<String, Object> headers, String msg){
		MessageProperties messageProperties = new MessageProperties();
		for(Map.Entry<String, Object> entry:headers.entrySet()){
			messageProperties.setHeader(entry.getKey(), entry.getValue());
		}
		MessageConverter messageConverter = new SimpleMessageConverter();
		return messageConverter.toMessage(msg, messageProperties);
	}
		
	public void SendCreditBank(int index, Map<String, Object> headers, String msg){
		System.out.println("credit.bank(index=" + index + ") send message :" + msg);
		rabbitTemplate.convertAndSend("creditBankExchange","credit.bank", getMessage(headers, msg));		
	}
	
	public void SendCreditFinance(int index, Map<String, Object> headers, String msg){
		System.out.println("credit.finance(index=" + index + ") send message :" + msg);
		rabbitTemplate.convertAndSend("creditFinanceExchange","credit.finance", getMessage(headers, msg));		
	}
}
