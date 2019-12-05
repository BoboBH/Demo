package com.bobo.fristsba.stock.domain;

public class Stock {
	
	private String id;
	private String name;
	private String symbol;
	private String market;
	private String briefName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getBriefName() {
		return briefName;
	}
	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}

}
