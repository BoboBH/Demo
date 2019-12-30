package com.bobo.fristsba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bobo.fristsba.domain.AccountBalance;


@Mapper
public interface AccountBalanceMapper {

	List<AccountBalance> getAccountBalance();
	AccountBalance getAccountBalanceById(String id);
	void addAccountBalance(AccountBalance accountBalance);
	void updateAccountBalance(AccountBalance accountBalance);
	void deleteAccountBalance(String id);
}
