package com.bobo.fristsba.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = StockDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "stockSqlSessionFactory")
public class StockDataSourceConfig {

	static final String PACKAGE = "com.bobo.fristsba.stock.mapper";
	static final String MAPPER_LOCATION = "classpath:mapper/stock/*.mapper.xml";

	@Value("${stock.datasource.url}")
	private String url;
	@Value("${stock.datasource.username}")
	private String username;
	@Value("${stock.datasource.password}")
	private String password;

	@Bean(name = "stockDataSource")
	public DataSource stockDataSource() {
		return DataSourceBuilder.create().url(url).username(username).password(password).build();
	}

	@Bean(name = "stockTransactionManager")
	public DataSourceTransactionManager masterTransactionManager() {
		return new DataSourceTransactionManager(stockDataSource());
	}

	@Bean(name = "stockSqlSessionFactory")
	public SqlSessionFactory stockSqlSessionFactory(@Qualifier("stockDataSource") DataSource stockDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(stockDataSource);
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(StockDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}

}
