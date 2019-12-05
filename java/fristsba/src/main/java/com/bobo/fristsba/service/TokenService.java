package com.bobo.fristsba.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bobo.fristsba.domain.Role;
import com.bobo.fristsba.domain.User;
import com.bobo.fristsba.mapper.RoleMapper;


@Service
public class TokenService {

	public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_ROLE = "Role";
 
    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "bobo.huang";
    
    // 过期时间是3600秒，既是1个小时
    public static final long EXPIRATION_IN_SECONDS = 3600L;
 
    // 选择了记住我之后的过期时间为7天
    public static final long EXPIRATION_REMEMBER = 604800L;

    
	@Autowired
	private RoleMapper roleMapper;
	
	public String getToken(User user){
		String token = "";
		List<Role> list = roleMapper.getRolesByUserId(user.getId());
		Builder builder = JWT.create();
		builder.withIssuer(ISS);
		builder.withIssuedAt(new Date());
		builder.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_IN_SECONDS * 1000));
		builder.withSubject(user.getUsername());
		builder.withAudience(user.getId());
		String role = list.stream().map(r->r.getName()).collect(Collectors.joining(","));
		builder.withClaim(TOKEN_ROLE, role);
		token = builder.sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}
	
	public boolean VerifyToken(String token) throws Exception
	{
		if(token == null)
			return false;
		try{
			DecodedJWT tokenObj = JWT.decode(token);
		}
		catch(JWTDecodeException e){
			throw new RuntimeException("401");
		}
		return true;
	}
}
