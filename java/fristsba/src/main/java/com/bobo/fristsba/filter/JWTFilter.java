package com.bobo.fristsba.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bobo.fristsba.authentication.JWTToken;

import io.netty.util.internal.StringUtil;

/**
 * 
 * @author bobo.huang
 * @desc 验证shiro + jwt
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
		
		try{
			
			return executeLogin(request, response);
		}
		catch(Exception ex){
			return false;
		}
	}
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception
	{
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		String token = httpServletRequest.getHeader("token");
		if(StringUtil.isNullOrEmpty(token))
		{
			Cookie[] cookies = httpServletRequest.getCookies();
			for (Cookie cookie : cookies) {
				if("token".equals(cookie.getName()))
				{
					token = cookie.getValue();
					break;
				}
			}
		}
		if(StringUtil.isNullOrEmpty(token))
			return false;
		JWTToken jwtToken = new JWTToken(token);
		getSubject(request, response).login(jwtToken);		
		httpServletResponse.addHeader("testheader", "Hello response header");
		return true;
	}
	
	/**
	 * Support跨域（CORS)支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception{
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
        	httpServletResponse.setStatus(HttpStatus.OK.value());
        	return false;
        }        
		return super.preHandle(request, response);
	}

}
