package com.bobo.fristsba.util;

import io.netty.util.internal.StringUtil;

public class UrlUtil {
	public static String getUserId(String url){
		int beginIndex  = url.toLowerCase().indexOf("/api/user/");
		if(beginIndex < 0)
			return StringUtil.EMPTY_STRING;
		String subStr = url.substring(beginIndex + "/api/user/".length());
		beginIndex = subStr.indexOf("/");
		if(beginIndex < 0)
			return subStr;
		return subStr.substring(0, beginIndex);
	}

}
