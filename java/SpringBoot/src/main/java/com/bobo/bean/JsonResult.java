package com.bobo.bean;

public class JsonResult {
	private int status;
	private String message;
	private Object data;
	public JsonResult(){
		this(null);
	}
	public JsonResult(int status, String msg){
		this(status, msg, null);
	}
	
	public JsonResult(int status, String msg, Object data){
		this.status = status;
		this.message = msg;
		this.data = data;
	}
	public JsonResult(Object data){
		this(0, "",data);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
