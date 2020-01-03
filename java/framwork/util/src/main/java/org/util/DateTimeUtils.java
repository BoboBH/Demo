package org.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	
	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date getDateBySimpleFormat(String date){
		try{
			return simpleDateFormat.parse(date);
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
	}
	public static Date getDate(String format, String date){
		try
		{
			return new SimpleDateFormat(format).parse(date);
		}catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
		
	}

}
