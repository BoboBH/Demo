package org.rabbitmqtest.config;


import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderRabbitConfig {

	@Bean
	public Queue creditBankQueue(){
		
		return new Queue("credit.bank");
	}
	@Bean
	public Queue creditFinanceQueue(){
		
		return new Queue("credit.finance");
	}
	
	@Bean
	public HeadersExchange creditBankExchange(){		
		return new HeadersExchange("creditBankExchange");
	}
	
	@Bean
	public HeadersExchange creditFinanceExchange(){		
		return new HeadersExchange("creditFinanceExchange");
	}
	
	@Bean
	public Binding bindingCreditAExchange(Queue creditBankQueue, HeadersExchange creditBankExchange){
		Map<String, Object> headerValues = new HashMap<String, Object>();
		headerValues.put("type", "cash");
		headerValues.put("aging", "fast");
		return BindingBuilder.bind(creditBankQueue).to(creditBankExchange).whereAny(headerValues).match();
	}

	@Bean
	public Binding bindingFinanceAExchange(Queue creditFinanceQueue, HeadersExchange creditFinanceExchange){
		Map<String, Object> headerValues = new HashMap<String, Object>();
		headerValues.put("type", "cash");
		headerValues.put("aging", "fast");
		return BindingBuilder.bind(creditFinanceQueue).to(creditFinanceExchange).whereAll(headerValues).match();
	}
}
