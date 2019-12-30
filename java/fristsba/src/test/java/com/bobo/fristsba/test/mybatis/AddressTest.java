package com.bobo.fristsba.test.mybatis;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.fristsba.BaseTest;
import com.bobo.fristsba.domain.Address;
import com.bobo.fristsba.domain.AddressExample;
import com.bobo.fristsba.mapper.AddressMapper;


public class AddressTest extends BaseTest{

	@Autowired
	private AddressMapper addressMapper;
	
	@Test
	public void testAddress(){
		String id = UUID.randomUUID().toString();
		Address address = new Address();
		address.setId(id);
		address.setCountry("CHN");
		address.setDetail("深圳市龙岗区阳光花园17-2403");
		addressMapper.insert(address);
		AddressExample example = new AddressExample();
		example.createCriteria().andIdEqualTo(id);
		List<Address> list = addressMapper.selectByExample(example);
		Assert.assertEquals(1, list.size());
		Address add = list.get(0);
		Assert.assertEquals(address.getDetail(), add.getDetail());
	}
}
