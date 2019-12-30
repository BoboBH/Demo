package com.bobo.fristsba.domain;

import java.util.List;

public class Pagination<T> {
	
	private int totalRecords;
	private int pageSize;
	private int currentPage;
	private List<T> records;
	public Pagination(){
	}
	public Pagination(int totalRecords, int pageSize, int currentPage, List<T> data){
		this.totalRecords = totalRecords;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.records = data;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalPages) {
		this.totalRecords = totalPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}

}
