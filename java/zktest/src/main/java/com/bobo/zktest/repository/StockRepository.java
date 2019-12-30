package com.bobo.zktest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bobo.zktest.bean.elasticsearch.Stock;

public interface StockRepository  extends CrudRepository<Stock, String>{

	List<Stock> findBySymbol(String symbol);
	List<Stock> findByName(String name);
	List<Stock> findByBriefname(String briefname);
}
