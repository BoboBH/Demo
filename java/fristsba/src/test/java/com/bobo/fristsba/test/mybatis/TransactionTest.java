package com.bobo.fristsba.test.mybatis;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;

import com.bobo.fristsba.BaseTest;
import com.bobo.fristsba.domain.Transaction;
import com.bobo.fristsba.mapper.TransactionMapper;

public class TransactionTest extends BaseTest{

	@Autowired
	private TransactionMapper transactionMapper;
	
	@Test
	public void TestTransaction(){
		String id = UUID.randomUUID().toString();
		String accountId="TEST_00001";
		Transaction transaction = new Transaction();
		transaction.setId(id);
		transaction.setAccountId(accountId);
		transaction.setType("D");
		transaction.setAmount(new Double(1000));
		transaction.setRemarks("Deposit");
		transactionMapper.addTransaction(transaction);
		Transaction tran = transactionMapper.getTransactionById(id);
		Assert.assertEquals(transaction.getType(), tran.getType());
		Assert.assertEquals(transaction.getAmount(), tran.getAmount());
		Assert.assertEquals(transaction.getRemarks(), tran.getRemarks());
		Assert.assertEquals(transaction.getAccountId(), tran.getAccountId());
		transactionMapper.deleteTransaction(id);
	}
}
