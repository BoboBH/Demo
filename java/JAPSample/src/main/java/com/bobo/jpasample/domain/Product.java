package com.bobo.jpasample.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8817791095174326951L;

	@Column(name="name")
	private String name;
	@Column(name="price_date")
	private Date PriceDate;
	@Column(name="price")
	private BigDecimal price;
	@Column(name="balance")
	private BigDecimal balance;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getPriceDate() {
		return PriceDate;
	}
	public void setPriceDate(Date priceDate) {
		PriceDate = priceDate;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public Product(){
		super();
	}
}
