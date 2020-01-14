package com.bobo.jpasample.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bobo.jpasample.domain.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, String>{	
	
	@Query(value="select u from Product u where name=:name")
	public List<Product> findByName(@Param("name") String name);
	
	@Query(value="select u from Product u where name =:name")
	public List<Product> findByName(@Param("name") String name, Pageable pageable);
	

	@Query(value="select u from Product u where name like %:name%")
	public List<Product> findByNameLike(@Param("name") String name);
	

	@Query(value="select u from Product u where name like %:name%")
	public List<Product> findByNameLike(@Param("name") String name, Pageable pageable);
}
