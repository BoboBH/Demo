package com.bobo.fristsba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.domain.Address;
import com.bobo.fristsba.domain.AddressExample;
import com.bobo.fristsba.mapper.AddressMapper;


@RestController
@RequestMapping("/addresses")
public class AddressController {
	
	@Autowired
	private AddressMapper addressMapper;
	

	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public Address getAddress(@PathVariable String id){
		AddressExample example = new AddressExample();
		example.createCriteria().andIdEqualTo(id);
		List<Address> list = addressMapper.selectByExample(example);
		if(list.size() > 0)
			return list.get(0);
		return null;
		
	}
	@RequestMapping(value="",method=RequestMethod.POST)
	public Address addAddress(@RequestBody Address addess){
		AddressExample example = new AddressExample();
		example.createCriteria().andIdEqualTo(addess.getId());
		List<Address> list = addressMapper.selectByExample(example);
		if(list.size() > 0){
			addressMapper.updateByExample(addess, example);
		}
		else
			addressMapper.insert(addess);
		return addess;
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	public Address updateAddress(@PathVariable String id, @RequestBody Address addess) throws Exception{
		AddressExample example = new AddressExample();
		example.createCriteria().andIdEqualTo(id);
		List<Address> list = addressMapper.selectByExample(example);
		if(list.size() == 0){
			throw new Exception(String.format("Address(%s) does not exist", id));
		}
		else
			addressMapper.updateByExample(addess, example);
		return addess;
	}
	@RequestMapping(value="{id}/delta",method=RequestMethod.PUT)
	public Address delUpdateAddress(@PathVariable String id, @RequestBody Address addess) throws Exception{
		AddressExample example = new AddressExample();
		example.createCriteria().andIdEqualTo(id);
		List<Address> list = addressMapper.selectByExample(example);
		if(list.size() == 0){
			throw new Exception(String.format("Address(%s) does not exist", id));
		}
		else
			addressMapper.updateByExampleSelective(addess, example);
		list = addressMapper.selectByExample(example);
		return list.size() > 0? list.get(0): addess;
	}

}
