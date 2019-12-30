package com.bobo.fristsba.domain;

import java.util.UUID;

public class AccountBalance {

	private String id;
	private Double creditAmount;
	private Double debitAmount;
	
	public AccountBalance(){
		this.id = UUID.randomUUID().toString();
	}
	public AccountBalance(Double credit, Double debit){
		this();
		this.creditAmount = credit;
		this.debitAmount = debit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}
}
