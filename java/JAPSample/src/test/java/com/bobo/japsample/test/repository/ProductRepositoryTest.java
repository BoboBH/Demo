package com.bobo.japsample.test.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.bobo.jpasample.App;
import com.bobo.jpasample.domain.Product;
import com.bobo.jpasample.repository.ProductRepository;




@RunWith(SpringRunner.class)
@SpringBootTest(classes=App.class)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void testFindByName(){		
		String name = "TEST_PROD_00001";
		List<Product> list = productRepository.findByName(name);
		if(list.size() == 0){
			Product product = new Product();
			product.setName(name);
			product.setBalance(new BigDecimal(1000000));
			productRepository.save(product);
		}
		list = productRepository.findByName(name);
		Assert.assertTrue(list.size() > 0);
		String id = list.get(0).getId();
		
		Optional<Product> result = productRepository.findById(id);
		Assert.assertNotNull(result.get());
		Assert.assertEquals(name, result.get().getName());
		list = productRepository.findByNameLike(name);
		Assert.assertTrue(list.size() > 0);		
	}
	
	@Test
	public void testPagable(){
		String name = "TEST_PROD_001";
		List<Product> list = productRepository.findByNameLike(name);
		Assert.assertTrue(list.size() >0);
		Pageable p = PageRequest.of(0, 20, new Sort(Direction.ASC, "name"));
		list = productRepository.findByNameLike(name, p);
		Assert.assertEquals(20, list.size());
	}
	
	
	//@Test
	public void initializeData(){
		Product product = null;
		List<Product> list = new ArrayList<Product>();
		String name = "";
		for(int i = 1; i <= 1000; i++){
			name = this.getRandomName(i);
			if(productRepository.findByName(name).size() > 0)
				continue;
			product = new Product();
			product.setName(this.getRandomName(i));
			list.add(product);
		}
		if(list.size() > 0)
			productRepository.saveAll(list);
	}
		
	private String getRandomName(int index){
	
		return "TEST_PROD_" + String.format("%5d", index).replace(" ", "0");
	}
	
	
	
	@Test
	public void testFormat(){
		int val = 12;
		String stri = String.format("%05d", val);
		Assert.assertEquals("00012", stri);
		val = 12334662;
		stri = String.format("%05d", val);
		Assert.assertEquals("12334662", stri);
		val = -12;
		stri = String.format("%05d", val);
		Assert.assertEquals("-0012", stri);
	}
}
