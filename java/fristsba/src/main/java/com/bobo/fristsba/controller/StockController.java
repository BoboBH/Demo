package com.bobo.fristsba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bobo.fristsba.service.StockService;
import com.bobo.fristsba.stock.domain.Stock;

@RestController
@RequestMapping("/stock")
public class StockController {
	@Autowired
	private StockService stockService;
	@RequestMapping("{id}")
	public Stock getStock(@PathVariable String id){
		return stockService.getStock(id);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public boolean removeCache(@PathVariable String id){
		return stockService.removeStockFromCache(id);
	}

}
