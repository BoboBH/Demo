package com.bobo.fristsba.stock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bobo.fristsba.stock.domain.Stock;

@Mapper
public interface StockMapper {

	List<Stock> getAllStocks();
	Stock getStockById(String id);
	void deleteStock(String id);
}
