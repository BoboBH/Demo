package com.bobo.fristsba.authentication;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bobo.fristsba.domain.User;
import com.bobo.fristsba.mapper.UserMapper;
import com.bobo.fristsba.util.UrlUtil;

import io.netty.util.internal.StringUtil;

/***
 * 
 * @author bobo.huang
 * @Description:
 *    自定义注解，可以用来做权限校验，可以独立于Shiro的配置
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private UserMapper userMapper;
	
	public static final long TIME_TO_REFRESH_TOKEN = 60 * 60 * 1000; // 60 mins

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String token = request.getHeader("Token");
		if (!(object instanceof HandlerMethod))
			return true;
		HandlerMethod handlerMethod = (HandlerMethod) object;
		Method method = handlerMethod.getMethod();
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required())
				return true;
		}
		String url = request.getRequestURL().toString();
		Date expiry = new Date();
		System.out.println(url);
		if (method.isAnnotationPresent(UserLoginToken.class)) {
			UserLoginToken loginToken = method.getAnnotation(UserLoginToken.class);
			if (loginToken.required()) {
				if (token == null)
					throw new RuntimeException("No token, please login again");
			}
			String userId = StringUtil.EMPTY_STRING;
			String role = StringUtil.EMPTY_STRING;
			try {
				DecodedJWT jwt = JWT.decode(token);
				expiry = jwt.getExpiresAt();
				userId = jwt.getAudience().get(0);
				Claim roleClaim = jwt.getClaim(TokenUtil.ROLE_CLAIM);
				role = roleClaim.asString();
				String urlUserId = UrlUtil.getUserId(url);
				if (loginToken.needusermatched() && !userId.equals(urlUserId))
					throw new RuntimeException(String.format("Unauthorized for user(%s)", urlUserId));
				if (!StringUtil.isNullOrEmpty(loginToken.role())) {
					if (role == null)
						throw new RuntimeException("403:You do not have role to access resource");
					boolean hasRole = false;
					String[] requiredRoles = loginToken.role().split(",");
					String[] hasRoles = role.split(",");
					for (String srole : hasRoles) {
						for (String rrole : requiredRoles) {
							if (rrole.equals(srole)) {
								hasRole = true;
								break;
							}
						}
					}
					if (!hasRole)
						throw new RuntimeException("403:You do not have role to access resource");
				}
			} catch (JWTDecodeException ex) {
				throw new RuntimeException("401");
			}
			com.bobo.fristsba.domain.User user = userMapper.getUserById(userId);
			if (user == null)
				throw new RuntimeException("User does not exist, please login again");

			JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
			try {
				jwtVerifier.verify(token);
				if(expiry.compareTo(new Date(System.currentTimeMillis() - 60 * 60 * 1000)) > 0)
				{
					User u = userMapper.getUserById(userId);
					String newToken = TokenUtil.sign(u.getUsername(), u.getPassword(), userId, role);
					response.addHeader("token", newToken);
				}

			} catch (JWTVerificationException ex) {
				throw new RuntimeException("401");
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}

}
