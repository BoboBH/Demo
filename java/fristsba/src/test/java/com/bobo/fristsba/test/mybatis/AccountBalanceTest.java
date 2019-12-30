package com.bobo.fristsba.test.mybatis;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.fristsba.BaseTest;
import com.bobo.fristsba.domain.AccountBalance;
import com.bobo.fristsba.mapper.AccountBalanceMapper;

public class AccountBalanceTest extends BaseTest{

	@Autowired
	private AccountBalanceMapper accountBalanceMapper;
	
	@Test
	public void testBalanceAccount(){
		String id = "abcdefg";
		accountBalanceMapper.deleteAccountBalance(id);
		AccountBalance ab = new AccountBalance();
		ab.setId(id);
		ab.setCreditAmount(new Double(100000));
		ab.setDebitAmount(new Double(0));
		accountBalanceMapper.addAccountBalance(ab);
		AccountBalance accountBalance = accountBalanceMapper.getAccountBalanceById(id);
		Assert.assertEquals(new Double(100000), accountBalance.getCreditAmount());
		accountBalance.setDebitAmount(new Double(1000));
		accountBalanceMapper.updateAccountBalance(accountBalance);
		accountBalance = accountBalanceMapper.getAccountBalanceById(id);
		Assert.assertEquals(new Double(1000), accountBalance.getDebitAmount());
	}
}
