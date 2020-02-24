package org.rabbitmqtest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeaderTest {

	@Autowired
	private HeaderSender sender;
	
	@Test
	public void testSendCreditBankWithType(){		
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("type", "cash");
		sender.SendCreditBank(1, headers, "credit bank(partial match)");
	}
	@Test
	public void testSendCreditBank(){		
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("type", "cash");
		headers.put("aging", "fast");
		sender.SendCreditBank(1, headers, "credit bank(full match)");
	}
	@Test
	public void testSendCreditFinanceWithType(){		
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("type", "cash");
		sender.SendCreditFinance(1, headers, "credit finance(partial match)");
	}
	@Test
	public void testSendCreditFinance(){		
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("type", "cash");
		headers.put("aging", "fast");
		sender.SendCreditFinance(1, headers, "credit finance(full match)");
	}
}
