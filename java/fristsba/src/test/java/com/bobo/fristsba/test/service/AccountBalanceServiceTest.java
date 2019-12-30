package com.bobo.fristsba.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bobo.fristsba.BaseTest;
import com.bobo.fristsba.domain.AccountBalance;
import com.bobo.fristsba.mapper.AccountBalanceMapper;
import com.bobo.fristsba.service.AccountBalanceService;

public class AccountBalanceServiceTest extends BaseTest {

	@Autowired
	private AccountBalanceMapper accountBalanceMapper;
	@Autowired
	private AccountBalanceService accountBalanceService;
	
	@Test
	public void TestTransaction(){
		String fromId = "TEST_00001";
		String toId = "TEST_00002";
		AccountBalance fa = accountBalanceMapper.getAccountBalanceById(fromId);
		AccountBalance ta = accountBalanceMapper.getAccountBalanceById(toId);
		if(fa == null){
			fa = new AccountBalance();
			fa.setId(fromId);
			accountBalanceMapper.addAccountBalance(fa);
		}
		if(ta == null){
			ta = new AccountBalance();
			ta.setId(toId);
			accountBalanceMapper.addAccountBalance(ta);
		}
		fa.setCreditAmount(new Double(100000));
		ta.setCreditAmount(new Double(1000));
		accountBalanceMapper.updateAccountBalance(fa);
		accountBalanceMapper.updateAccountBalance(ta);
		try{
			accountBalanceService.Transfer(fromId, toId, 10000);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		fa = accountBalanceMapper.getAccountBalanceById(fromId);
		ta = accountBalanceMapper.getAccountBalanceById(toId);
		Assert.assertEquals(new Double(90000), fa.getCreditAmount());
		Assert.assertEquals(new Double(11000), ta.getCreditAmount());
	}
	
	@Test
	public void testTransactionRollback(){
		String fromId = "TEST_00001";
		String toId = "TEST_00002";
		AccountBalance fa = accountBalanceMapper.getAccountBalanceById(fromId);
		AccountBalance ta = accountBalanceMapper.getAccountBalanceById(toId);
		if(fa == null){
			fa = new AccountBalance();
			fa.setId(fromId);
			accountBalanceMapper.addAccountBalance(fa);
		}
		if(ta == null){
			ta = new AccountBalance();
			ta.setId(toId);
			accountBalanceMapper.addAccountBalance(ta);
		}
		fa.setCreditAmount(new Double(100000));
		ta.setCreditAmount(new Double(1000));
		accountBalanceMapper.updateAccountBalance(fa);
		accountBalanceMapper.updateAccountBalance(ta);
		try{
			accountBalanceService.TransferWithRunTimeException(fromId, toId, 10000);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		fa = accountBalanceMapper.getAccountBalanceById(fromId);
		ta = accountBalanceMapper.getAccountBalanceById(toId);
		Assert.assertEquals(new Double(100000), fa.getCreditAmount());
		Assert.assertEquals(new Double(1000), ta.getCreditAmount());
	}
	
	@Test
	public void testTransactionNotRollback(){
		String fromId = "TEST_00001";
		String toId = "TEST_00002";
		AccountBalance fa = accountBalanceMapper.getAccountBalanceById(fromId);
		AccountBalance ta = accountBalanceMapper.getAccountBalanceById(toId);
		if(fa == null){
			fa = new AccountBalance();
			fa.setId(fromId);
			accountBalanceMapper.addAccountBalance(fa);
		}
		if(ta == null){
			ta = new AccountBalance();
			ta.setId(toId);
			accountBalanceMapper.addAccountBalance(ta);
		}
		fa.setCreditAmount(new Double(100000));
		ta.setCreditAmount(new Double(1000));
		accountBalanceMapper.updateAccountBalance(fa);
		accountBalanceMapper.updateAccountBalance(ta);
		try{
			accountBalanceService.TransferWithException(fromId, toId, 10000);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		fa = accountBalanceMapper.getAccountBalanceById(fromId);
		ta = accountBalanceMapper.getAccountBalanceById(toId);
		Assert.assertEquals(new Double(90000), fa.getCreditAmount());
		Assert.assertEquals(new Double(11000), ta.getCreditAmount());
	}
}
