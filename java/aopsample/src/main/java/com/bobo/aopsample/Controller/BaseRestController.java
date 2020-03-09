package com.bobo.aopsample.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class BaseRestController {
	final static String AUTHORIZATION_HEAD_KEY = "Authorization";
	protected String getJwt(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(requestAttributes != null){
			HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
			String jwt = request.getHeader(AUTHORIZATION_HEAD_KEY);
			if(!StringUtils.isEmpty(jwt))
				return jwt;
			for (Cookie iCookie : request.getCookies()) {
				if(AUTHORIZATION_HEAD_KEY.equals(iCookie.getName()))
					return iCookie.getValue();
			}
		}
		return null;
	}

}
