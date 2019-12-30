package com.bobo.zktest.bean;

public class JsonClient extends JsonResult<Client>{

	public JsonClient(){
		
	}
	
	public JsonClient(int code, String msg, Client client){
		super(code, msg, client);
	}
}
