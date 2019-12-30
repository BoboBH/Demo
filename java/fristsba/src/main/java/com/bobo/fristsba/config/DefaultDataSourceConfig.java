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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(basePackages = DefaultDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "defaultSqlSessionFactory")
public class DefaultDataSourceConfig {
	 static final String PACKAGE = "com.bobo.fristsba.mapper";
	 static final String MAPPER_LOCATION = "classpath:mapper/*.mapper.xml";
	 
	 @Value("${spring.datasource.url}")
	 private String url;
	 @Value("${spring.datasource.username}")
	 private String username;
	 @Value("${spring.datasource.password}")
	 private String password;
	 
	 @Bean(name = "defaultDataSource")
	    @Primary
	    public DataSource defaultDataSource() {
		 return DataSourceBuilder.create().url(url).username(username).password(password).build();
	    }

	    @Bean(name = "defaultTransactionManager")
	    @Primary
	    public DataSourceTransactionManager masterTransactionManager() {
	        return new DataSourceTransactionManager(defaultDataSource());
	    }
	    

	    /**
	     * required by JPA
	     * @return
	     */
	    @Bean(name = "transactionManager")
	    public DataSourceTransactionManager transactionManager() {
	        return new DataSourceTransactionManager(defaultDataSource());
	    }

	    @Bean(name = "defaultSqlSessionFactory")
	    @Primary
	    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource)
	            throws Exception {
	        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	        sessionFactory.setDataSource(defaultDataSource);
	        sessionFactory.setMapperLocations(
	                new PathMatchingResourcePatternResolver().getResources(DefaultDataSourceConfig.MAPPER_LOCATION));
	        return sessionFactory.getObject();
	    }
}
