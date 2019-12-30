package com.bobo.zktest.bean.elasticsearch;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(indexName="index_test", type="test_employee")
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9050742059494744787L;
	@Id
	private String id;
	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dob;
	private String address;
	private Integer age;
	@Field(type=FieldType.Object)
	private Organization organization;
	@Field(type=FieldType.Object)
	private Department department;
	
	public Employee(){
		
	}
	public Employee(String id, String name, Date dob, String address, int age, Organization organization, Department department){
		this();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.age = age;
		this.organization = organization;
		this.department = department;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
}
