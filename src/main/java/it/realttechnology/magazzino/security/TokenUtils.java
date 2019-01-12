package it.realttechnology.magazzino.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

//TODO: conf
public class TokenUtils
{
	public static String SECRET                = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME   = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX    = "Bearer ";
    public static final String HEADER_STRING   = "Authorization";
	public static final String TOKEN_AUTH_PWD  = "TOKEN_AUTH_PWD";
	
	static String generateToken(Authentication auth) {
		String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
		return token;
	}
	static String generateToken(String credential) {
		String token = JWT.create()
                .withSubject(credential)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
		return token;
	}
	static String getUserFromToken(String header) {
		DecodedJWT token = JWT.require(Algorithm.HMAC512(TokenUtils.SECRET.getBytes()))
		        .build()
		        .verify(header.replace(TokenUtils.TOKEN_PREFIX, ""));
		
		LoggerFactory.getLogger(TokenUtils.class).info("DECODED TOKEN: " + token.getToken());
		
		//GET WHAT I NEED FROM THE TOKEN
		String userName = token.getSubject();
		return userName;
	}
	
}
