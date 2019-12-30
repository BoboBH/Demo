package com.bobo.fristsba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bobo.fristsba.domain.Transaction;

@Mapper
public interface TransactionMapper {
	List<Transaction> getTransaction();
	Transaction getTransactionById(String id);
	void addTransaction(Transaction transaction);
	void updateTransaction(Transaction transaction);
	void deleteTransaction(String id);
}
