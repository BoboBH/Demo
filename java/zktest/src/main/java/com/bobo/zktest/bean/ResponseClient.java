package com.bobo.zktest.bean;

public class ResponseClient {
	private int code;
	private String msg;
	private Client data;
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
	public Client getData() {
		return data;
	}
	public void setData(Client data) {
		this.data = data;
	}
	
	public ResponseClient(){
		this.code = 0;
		this.msg = null;
	}
	
	public ResponseClient(int code, String message, Client data){
		this.code = code;
		this.msg = message;
		this.data = data;
	}

}
