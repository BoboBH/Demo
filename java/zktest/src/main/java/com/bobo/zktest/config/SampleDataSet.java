package com.bobo.zktest.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.mapper.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.bobo.zktest.bean.elasticsearch.Employee;
import com.bobo.zktest.repository.EmployeeRepository;

public class SampleDataSet {

	private static final Logger logger = LoggerFactory.getLogger(SampleDataSet.class);
	
	private static final String INDEX_NAME="sample";
	private static final String INDEX_TYPE="employee";
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private ElasticsearchTemplate template;
	
	@PostConstruct
	public void init(){
		for(int i = 0; i < 2;i++)
			bulk(i);
	}
	
	public void bulk(int i){
		try{
			System.out.println("to do somthing....");
		}
		catch(Exception ex){
			logger.error("Error bulk index",ex);
		}
	}
}
