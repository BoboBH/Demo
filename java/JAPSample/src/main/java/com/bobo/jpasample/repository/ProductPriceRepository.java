package com.bobo.jpasample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bobo.jpasample.domain.ProductPrice;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, String>{

	
}