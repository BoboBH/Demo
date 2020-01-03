package com.bobo.cloudmodule.bean;

import java.io.Serializable;

public abstract class JsonData <T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9047323180164080367L;
	private int code;
	private String msg;
	private T data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public JsonData() {
		this(0, null, null);
	}
	
	public JsonData(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public JsonData(T data){
		this(0,null, data);
	}
	

}
