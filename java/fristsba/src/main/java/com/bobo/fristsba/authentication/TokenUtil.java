package com.bobo.fristsba.authentication;

import java.util.Date;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.netty.util.internal.StringUtil;

/**
 * 
 * @author bobo.huang
 * @create 2019-09-25
 * @desc JWT工具，为验证Springboot + shiro + jwt
 *
 */
public class TokenUtil {

	 private static final long EXPIRE_TIME = 60 * 60 * 1000; //5 mins
	 public static final String USER_NAME_CLAIM = "username";
	 public static final String ROLE_CLAIM = "role";
	 public static boolean verify(String token, String username, String secret){
		 try{
			 
			 Algorithm algorithm = Algorithm.HMAC256(secret);
			 JWTVerifier verifier = JWT.require(algorithm).withClaim(USER_NAME_CLAIM, username).build();
			 DecodedJWT jwt = verifier.verify(token);
			 return true;
		 }
		 catch(Exception ex){
			 return false;
		 }
	 }
	 
	 public static String getUsername(String token){
		 return getClaimValue(token,USER_NAME_CLAIM);
	 }
	 
	 public static String getClaimValue(String token, String claimName){
		 try{
			 DecodedJWT jwt = JWT.decode(token);
			 return jwt.getClaim(claimName).asString();
		 }
		 catch(JWTDecodeException e){
			 return null;
		 }
	 }
	 
	 public static String sign(String username, String secret,String userId, String roles){
		 Date  date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		 Algorithm algorithm = Algorithm.HMAC256(secret);
		 Builder builder = JWT.create().withClaim(USER_NAME_CLAIM, username)
				 .withExpiresAt(date);
		 if(!StringUtil.isNullOrEmpty(roles))
				 builder.withClaim(ROLE_CLAIM, roles);
		 if(!StringUtil.isNullOrEmpty(userId))
			 builder.withAudience(userId);
		 return builder.sign(algorithm);
		 
		/* return JWT.create().withClaim(USER_NAME_CLAIM, username)
				 .withArrayClaim(ROLE_CLAIM, roles)
				 .withExpiresAt(date).sign(algorithm);*/
				 
	 }
}
