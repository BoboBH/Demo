package com.bobo.fristsba.service;

public interface AccountBalanceService {

	void Transfer(String fromId, String toId, double amount) throws Exception;
	
	/***
	 * transaction is effective and operation will be rollbacked;
	 * @param fromId
	 * @param toId
	 * @param amount
	 * @throws Exception
	 */
	void TransferWithRunTimeException(String fromId, String toId, double amount) throws Exception;
	
	/***
	 * Trasnaction is not effective and operation will be committed.
	 * @param fromId
	 * @param toId
	 * @param amount
	 * @throws Exception
	 */
	void TransferWithException(String fromId, String toId, double amount) throws Exception;
}
