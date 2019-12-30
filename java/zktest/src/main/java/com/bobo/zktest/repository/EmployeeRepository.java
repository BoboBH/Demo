package com.bobo.zktest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bobo.zktest.bean.elasticsearch.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{
	List<Employee> findByOrganizationName(String name);
	List<Employee> findByName(String name);
}
