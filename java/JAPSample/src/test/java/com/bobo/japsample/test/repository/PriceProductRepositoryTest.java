package com.bobo.japsample.test.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.test.context.junit4.SpringRunner;

import com.bobo.jpasample.App;
import com.bobo.jpasample.domain.Product;
import com.bobo.jpasample.domain.ProductPrice;
import com.bobo.jpasample.repository.ProductPriceRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=App.class)
public class PriceProductRepositoryTest {

	@Autowired
	private ProductPriceRepository productPriceRepository;
	
	private SimpleDateFormat dateformat =  new SimpleDateFormat("yyyyMMdd");
	private Random random = new Random();
	private Calendar cd = Calendar.getInstance(); 
	
	
	@Test
	public void testFind(){
		String productId = "TEST_PROD_00001";
		String id = "TEST_PROD_00001_20190101";
		Optional<ProductPrice> result = productPriceRepository.findById(id);
		Assert.assertTrue(result.isPresent());		
		ProductPrice pp = new ProductPrice();
		pp.setProductId(productId);
		pp.setId(null);
		//pp.setCreatedon(null);
		//pp.setModifiedon(null);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("productId",ExampleMatcher.GenericPropertyMatchers.contains())
				.withIgnorePaths("createdon")
				.withIgnorePaths("modifiedon");
		Example<ProductPrice> exmpale = Example.of(pp, matcher);
		List<ProductPrice> list = productPriceRepository.findAll(exmpale);
		Assert.assertTrue(list.size() > 0);
	}
	
	//@Test
	public void InitialzieData(){
		String productId = "TEST_PROD_00001";
		for(int i = 1; i <= 1000;i++){
			productId = String.format("TEST_PROD_%05d", i);
			InitializePrice(productId, new BigDecimal(random.nextInt(10) + 5));
		}
	}
	
	private void InitializePrice(String productId, BigDecimal basePrice){
		List<ProductPrice> list = new ArrayList<ProductPrice>();
		BigDecimal price = basePrice;
		Date beginDate = new Date();
		String id = "";
		try {
			beginDate = dateformat.parse("20190101");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate = new Date();
		Optional<ProductPrice> result = null;
		while(beginDate.compareTo(endDate) < 0)
		{
			cd.setTime(beginDate);
			if(cd.get(Calendar.DAY_OF_WEEK) != 1 && cd.get(Calendar.DAY_OF_WEEK)  != 7)
			{
				id = productId + "_" + dateformat.format(beginDate);
				result = productPriceRepository.findById(id);
				if(result ==  null || !result.isPresent()){
					ProductPrice pp = new ProductPrice(productId, beginDate, price);
					list.add(pp);
				}
			}
			cd.add(Calendar.DATE, 1);
			beginDate = cd.getTime();
			int num = random.nextInt(10);
			System.out.println(String.format("num:%d",num));
			price = price.multiply(new BigDecimal(1 + (num -5)/100.0));
		}
		if(list.size() >0)
			productPriceRepository.saveAll(list);
	}
	
}
