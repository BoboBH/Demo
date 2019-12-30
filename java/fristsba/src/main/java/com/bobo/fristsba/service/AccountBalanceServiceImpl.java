package com.bobo.fristsba.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bobo.fristsba.domain.AccountBalance;
import com.bobo.fristsba.domain.Transaction;
import com.bobo.fristsba.mapper.AccountBalanceMapper;
import com.bobo.fristsba.mapper.TransactionMapper;
/***
 * 
 * @author bobo.huang
 *
 * Description: Test Transaction
 */
@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {

	@Autowired
	private AccountBalanceMapper accountBalanceMapper;
	@Autowired
	private TransactionMapper transactionMapper;
	@Transactional
	@Override
	public void Transfer(String fromId, String toId, double amount) throws Exception{
		AccountBalance fromAb = accountBalanceMapper.getAccountBalanceById(fromId);
		AccountBalance toAb = accountBalanceMapper.getAccountBalanceById(toId);
		if(fromAb == null)
			throw new Exception(String.format("From Account({} does not exist", fromId));
		if(toAb == null)
			throw new Exception(String.format("To Account({} does not exist", toId));
		if(fromAb.getCreditAmount() == null || fromAb.getCreditAmount() < amount)
			throw new Exception(String.format("Balance is not insufficient for From Account({})", fromId));
		fromAb.setCreditAmount(fromAb.getCreditAmount()-amount);
		toAb.setCreditAmount(toAb.getCreditAmount() + amount);
		Transaction outTran = new Transaction();
		Transaction inTran = new Transaction();
		outTran.setId(UUID.randomUUID().toString());
		outTran.setAccountId(fromId);
		outTran.setType("W");
		outTran.setAmount(amount);
		outTran.setRemarks("Transfer to " + toId);
		
		inTran.setId(UUID.randomUUID().toString());
		inTran.setAccountId(toId);
		inTran.setType("D");
		inTran.setAmount(amount);
		inTran.setRemarks("Transfered from " + fromId);
		accountBalanceMapper.updateAccountBalance(fromAb);
		accountBalanceMapper.updateAccountBalance(toAb);
		transactionMapper.addTransaction(outTran);
		transactionMapper.addTransaction(inTran);
	}

	@Transactional
	@Override
	public void TransferWithRunTimeException(String fromId, String toId, double amount) throws Exception {
		// TODO Auto-generated method stub
		AccountBalance fromAb = accountBalanceMapper.getAccountBalanceById(fromId);
		AccountBalance toAb = accountBalanceMapper.getAccountBalanceById(toId);
		if(fromAb == null)
			throw new Exception(String.format("From Account({} does not exist", fromId));
		if(toAb == null)
			throw new Exception(String.format("To Account({} does not exist", toId));
		if(fromAb.getCreditAmount() == null || fromAb.getCreditAmount() < amount)
			throw new Exception(String.format("Balance is not insufficient for From Account({})", fromId));
		fromAb.setCreditAmount(fromAb.getCreditAmount()-amount);
		toAb.setCreditAmount(toAb.getCreditAmount() + amount);
		Transaction outTran = new Transaction(fromId,"W",amount,"Transfer to " + toId);
		Transaction inTran = new Transaction(toId,"D",amount,"Transfered from " + fromId);
		accountBalanceMapper.updateAccountBalance(fromAb);
		accountBalanceMapper.updateAccountBalance(toAb);
		transactionMapper.addTransaction(outTran);
		transactionMapper.addTransaction(inTran);
		throw new RuntimeException("Runtime exception");
	}

	@Transactional
	@Override
	public void TransferWithException(String fromId, String toId, double amount) throws Exception {
		// TODO Auto-generated method stub
		AccountBalance fromAb = accountBalanceMapper.getAccountBalanceById(fromId);
		AccountBalance toAb = accountBalanceMapper.getAccountBalanceById(toId);
		if(fromAb == null)
			throw new Exception(String.format("From Account({} does not exist", fromId));
		if(toAb == null)
			throw new Exception(String.format("To Account({} does not exist", toId));
		if(fromAb.getCreditAmount() == null || fromAb.getCreditAmount() < amount)
			throw new Exception(String.format("Balance is not insufficient for From Account({})", fromId));
		fromAb.setCreditAmount(fromAb.getCreditAmount()-amount);
		toAb.setCreditAmount(toAb.getCreditAmount() + amount);
		Transaction outTran = new Transaction(fromId,"W",amount,"Transfer to " + toId);
		Transaction inTran = new Transaction(toId,"D",amount,"Transfered from " + fromId);
		accountBalanceMapper.updateAccountBalance(fromAb);
		accountBalanceMapper.updateAccountBalance(toAb);
		transactionMapper.addTransaction(outTran);
		transactionMapper.addTransaction(inTran);
		throw new Exception("general exception");
		
	}

}
