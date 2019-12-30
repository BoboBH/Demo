package com.bobo.zktest.test.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bobo.zktest.ZktestApplication;
import com.bobo.zktest.domain.Stock;
import com.bobo.zktest.domain.StockExample;
import com.bobo.zktest.mapper.StockMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@SpringBootTest(classes = ZktestApplication.class)
public class StockMapperTest {

	@Autowired 
	private StockMapper stockMapper;
	 @Test
	 public void testGet(){
		 String id = "cn000422";
		 StockExample example = new StockExample();
		 example.createCriteria().andIdEqualTo(id);
		 List<Stock> stocks = stockMapper.selectByExample(example);
		 Assert.assertEquals(1, stocks.size());
		 Assert.assertEquals(id, stocks.get(0).getId());
	 }
	 
	 @Test
	 public void testPagination(){
		 List<Stock> stocks = stockMapper.selectAllByPagination(100, 20);
		 Assert.assertEquals(20, stocks.size());
	 }
}
