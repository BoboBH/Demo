package com.bobo.fristsba.service;

import com.bobo.fristsba.stock.domain.Stock;

public interface StockService {
	public Stock getStock(String id);
	public boolean removeStockFromCache(String id);
	public Stock updateStock(String id, Stock stock);

}
