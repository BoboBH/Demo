package com.bobo.zktest.bean;

public class Client {
	
	private String id;
	private String chinesename;
	private String firstname;
	private String lastname;
	private String appid;
	private String countrybirthcode;
	private String version;
	private String englishName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCountrybirthcode() {
		return countrybirthcode;
	}
	public void setCountrybirthcode(String countrybirthcode) {
		this.countrybirthcode = countrybirthcode;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEnglishname() {
		return englishName;
	}
	public void setEnglishname(String englishName) {
		this.englishName = englishName;
	}
	
	@Override
	public String toString(){
		return String.format("Client Info:id={%s};chinse name={%s};english name={%s}", id, chinesename, englishName);
	}

}
