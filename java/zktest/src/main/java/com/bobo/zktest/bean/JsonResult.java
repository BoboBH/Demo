package com.bobo.zktest.bean;


public class JsonResult<T> {
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
	
	public JsonResult(){
		this.code = 0;
		this.msg = null;
	}
	
	public JsonResult(int code, String message, T data){
		this.code = code;
		this.msg = message;
		this.data = data;
	}

}
