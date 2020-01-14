package com.bobo.jpasample.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

@Entity
@Table(name="product_price")
public class ProductPrice extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8215610776921078344L;
	@Column(name="product_id")
	private String productId;
	@Column(name="date")
	private Date date;
	@Column(name="price")
	private BigDecimal price;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public ProductPrice(){
		super();
	}
	
	public ProductPrice(String productId, Date date, BigDecimal price){
		super();
		this.productId = productId;
		this.price = price;
		this.date = date;
		this.setId(this.productId + "_" + new SimpleDateFormat("yyyyMMdd").format(date));
	}
}
