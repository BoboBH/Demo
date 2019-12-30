package com.bobo.fristsba.test.util;

import org.junit.Assert;
import org.junit.Test;

public class StringTest {
	@Test
	public void TestFormat(){
		String actual = String.format("Name:%s", "bobo huang");
		Assert.assertEquals("Name:bobo huang", actual);
		actual = String.format("first name:%s;last name:%s", "bobo","huang");
		Assert.assertEquals("first name:bobo;last name:huang", actual);
		actual = concat("bobo", "huang");
		Assert.assertEquals("bobohuang", actual);
		actual = concat("bobo", null);
		Assert.assertEquals("bobo", actual);
	}
	
	private String concat(String... args){
		StringBuilder sb = new StringBuilder();
		for (String val : args) {
			if(val != null)
				sb.append(val);
		}
		return sb.toString();
	}

}
