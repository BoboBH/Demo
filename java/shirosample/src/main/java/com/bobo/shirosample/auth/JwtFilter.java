package com.bobo.shirosample.auth;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtFilter extends AccessControlFilter{

	final static Logger log = LoggerFactory.getLogger(AccessControlFilter.class);
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		log.warn("isAccessAllowed is called");
		return false;
	}
	
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception
	{
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		String token = httpServletRequest.getHeader("Authorization");
		if(StringUtils.isNotEmpty(token))
		{
			Cookie[] cookies = httpServletRequest.getCookies();
			for (Cookie cookie : cookies) {
				if("Authorization".equals(cookie.getName()))
				{
					token = cookie.getValue();
					break;
				}
			}
		}
		if(StringUtils.isEmpty(token))
			return false;
		JwtToken jwtToken = new JwtToken(token);
		getSubject(request, response).login(jwtToken);		
		httpServletResponse.addHeader("testheader", "Hello response header");
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		log.info("isAccessAllowed is called");
		HttpServletRequest hsr = (HttpServletRequest)request;
		String jwt = hsr.getHeader("Authorization");
		if(StringUtils.isEmpty(jwt))
		{
			Cookie[] cookies = hsr.getCookies();
			for (Cookie cookie : cookies) {
				if("Authorization".equals(cookie.getName()))
				{
					jwt = cookie.getValue();
					break;
				}
			}
		}
		log.info("Authorization is {} ", jwt);
		JwtToken jwtToken = new JwtToken(jwt);
		try{
			getSubject(request, response).login(jwtToken);
		}
		catch(Exception ex){
			ex.printStackTrace();
			onLoginFail(response);
			return false;
		}
		return true;
	}
	
	private void onLoginFail(ServletResponse response) throws IOException{
		HttpServletResponse hsr = (HttpServletResponse)response;
		hsr.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		hsr.getWriter().write("Login Failed");		
	}

}
