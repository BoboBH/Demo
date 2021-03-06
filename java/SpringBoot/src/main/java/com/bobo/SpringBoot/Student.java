
package com.bobo.SpringBoot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Student {
	public String name;
	public Date birthday;
	
	public Student(String name, Date birthday){
		this.name = name;
		this.birthday = birthday;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		this.birthday = cal.getTime();
		DateFormat df=new SimpleDateFormat("yyyyMMdd");  
		//this.name = df.format(this.birthday);
	}

}
