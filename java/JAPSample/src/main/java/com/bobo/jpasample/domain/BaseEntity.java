package com.bobo.jpasample.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306719316036201069L;
	@Id
	@Column(name="id")
	protected String id;
	@Column(name="remarks")
	protected String remarks;
	@Column(name="createdon")
	protected Date createdon;
	@Column(name="modifiedon")
	protected Date modifiedon;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}
	public Date getModifiedon() {
		return modifiedon;
	}
	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}
	
	public BaseEntity(){
		this.id = UUID.randomUUID().toString();
		this.createdon = new Date();
		this.modifiedon = new Date();
	}
}
