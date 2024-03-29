package org.rabbitmqtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleReceiverTest {

	@Autowired
	private DirectSender directSender;
	
	@Autowired
	private TopicSender topicSender;
	
	@Test
	public void test(){
		
		for(int i = 0; i < 1; i++){
			directSender.send();
			topicSender.send2TopicMessage(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
