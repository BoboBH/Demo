package com.bobo.fristsba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bobo.fristsba.stock.domain.Stock;
import com.bobo.fristsba.stock.mapper.StockMapper;
/***
 * 
 * @author bobo.huang
 * Support Cacheable for Stock
 *
 */
@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	private StockMapper stockMapper;
	@Cacheable(value="stockcache", key="#p0")
	@Override
	public Stock getStock(String id){
		return stockMapper.getStockById(id);
	}
	
	@CacheEvict(value="stockcache", key="#p0")
	@Override
	public boolean removeStockFromCache(String id){
		return true;		
	}
	

	@CachePut(value="stockcache", key="#p0")
	@Override
	public Stock updateStock(String id,Stock stock){
		stockMapper.updateStock(stock);
		return stock;
	}

}
