package com.bobo.fristsba.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.bobo.fristsba.domain.Address;
import com.bobo.fristsba.domain.Pagination;

public class PaginationTest {

	@Test
	public void testPagination(){
		Pagination<Address> pageAddress = new Pagination<Address>();
		List<Address> list = new ArrayList<Address>();
		pageAddress.setCurrentPage(1);
		
		pageAddress.setPageSize(10);
		pageAddress.setTotalRecords(2);
		Address a1 = new Address();
		a1.setId(UUID.randomUUID().toString());
		a1.setCountry("CHN");
		a1.setDetail("D1");
		Address a2 = new Address();
		a2.setId(UUID.randomUUID().toString());
		a2.setCountry("CHN");
		a2.setDetail("D2");
		list.add(a1);
		list.add(a2);
		pageAddress.setRecords(list);
		Assert.assertEquals(2, pageAddress.getRecords().size());
		Pagination<Address> pageAddress2 = new Pagination<Address>(2,10,1,list);
		Assert.assertEquals(2, pageAddress2.getRecords().size());
	}
}
