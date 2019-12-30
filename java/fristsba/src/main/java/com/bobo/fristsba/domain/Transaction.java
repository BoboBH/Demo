package com.bobo.fristsba.domain;

import java.util.UUID;

public class Transaction {
	private String id;
	private String accountId;
	/***
	 * D:Deposit
	 * W:Withdrawal
	 */
	private String type;
	private Double amount;
	private String remarks;
	public String getId() {
		return id;
	}
	
	public Transaction(){
		this.id = UUID.randomUUID().toString();
	}
	
	public Transaction(String accountId, String type, double amount, String remarks){
		this();
		this.accountId = accountId;
		this.type = type;
		this.amount = new Double(amount);
		this.remarks = remarks;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
