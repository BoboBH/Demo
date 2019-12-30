package com.bobo.zktest.mapper;

import com.bobo.zktest.domain.Stock;
import com.bobo.zktest.domain.StockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	long countByExample(StockExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	int deleteByExample(StockExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	int insert(Stock record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	int insertSelective(Stock record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	List<Stock> selectByExample(StockExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table stock
	 * @mbg.generated  Tue Dec 24 14:16:56 CST 2019
	 */
	int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);
	
	List<Stock> selectAllByPagination(@Param("currentIndex") int currentIndex, @Param("pageSize") int pageSize);
}