package it.realttechnology.magazzino.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//TODO: conf
public class TokenUtils
{
	public static String SECRET                = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME   = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX    = "Bearer ";
    public static final String TOKEN_GOOGLE_PREFIX   = "Google ";
    public static final String HEADER_STRING   = "Authorization";
	public static final String TOKEN_AUTH_PWD  = "TOKEN_PASSWORD";
	
    public static final String TOKEN_GRANT         = "grant_type";
    public static final String TOKEN_GRANT_CODE    = "authorization_code";
    public static final String TOKEN_CLIENT_ID     = "client_id";
    public static final String TOKEN_CLIENT_SECRET = "client_secret";
    public static final String TOKEN_REDIRECT_URI  = "redirect_uri";
	public static final String TOKEN_CODE          = "code";
	public static final String TOKEN_NAME          = "access_token";
	public static final String TOKEN_TYPE          = "token_type";
	public static final String TOKEN_EXPIRES       = "expires_in";
	public static final String TOKEN_REFRESH       = "refresh_token";
	public static final String TOKEN_ID            = "id_token";
	public static final String TOKEN_SCOPE         = "scope";
	
	public static String generateToken(Authentication auth) {
		String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
		return token;
	}
	public static String generateToken(String credential) {
		String token = JWT.create()
                .withSubject(credential)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
		return token;
	}
	public static String getUserFromToken(String header) 
	{
		
		String cleanToken = header.replace(TokenUtils.TOKEN_PREFIX, "");
		
		boolean isG = false;
		
		if(cleanToken.startsWith(TOKEN_GOOGLE_PREFIX))
		{
			cleanToken = cleanToken.replace(TokenUtils.TOKEN_GOOGLE_PREFIX, "");
			isG = true;
		}
		
		//here the token should be validate by external provider.. but i've generated my token on the top ox external one
		
		DecodedJWT token = JWT.require(Algorithm.HMAC512(TokenUtils.SECRET.getBytes()))
		        .build()
		        .verify(cleanToken);
		
		LoggerFactory.getLogger(TokenUtils.class).info("DECODED TOKEN: " + token.getToken());
		
		//GET WHAT I NEED FROM THE TOKEN
		String userName = token.getSubject();
		
		return isG ? TOKEN_GOOGLE_PREFIX + userName : userName;
	}

	    	@JsonIgnoreProperties(ignoreUnknown=true)
	    	public static class GoogleUserInfo implements Serializable
	    	{
	    		    private String id;
	    		    private String email; 
	    		    private boolean verified_email;
	    		    private String name;
	    		    private String given_name;
	    		    public String getId() {
						return id;
					}
					public void setId(String id) {
						this.id = id;
					}
					public String getEmail() {
						return email;
					}
					public void setEmail(String email) {
						this.email = email;
					}
					public boolean isVerified_email() {
						return verified_email;
					}
					public void setVerified_email(boolean verified_email) {
						this.verified_email = verified_email;
					}
					public String getName() {
						return name;
					}
					public void setName(String name) {
						this.name = name;
					}
					public String getGiven_name() {
						return given_name;
					}
					public void setGiven_name(String given_name) {
						this.given_name = given_name;
					}
					public String getFamily_name() {
						return family_name;
					}
					public void setFamily_name(String family_name) {
						this.family_name = family_name;
					}
					public String getLink() {
						return link;
					}
					public void setLink(String link) {
						this.link = link;
					}
					public String getPicture() {
						return picture;
					}
					public void setPicture(String picture) {
						this.picture = picture;
					}
					public String getGender() {
						return gender;
					}
					public void setGender(String gender) {
						this.gender = gender;
					}
					public String getLocale() {
						return locale;
					}
					public void setLocale(String locale) {
						this.locale = locale;
					}
					private String family_name;
	    		    private String link;
	    		    private String picture;
	    		    private String gender;
	    		    private String locale;
	    	}
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class GoogleTokenResponse implements Serializable
	{
		private String access_token;
		private String token_type;
		
		public GoogleTokenResponse()
		{}
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public String getToken_type() {
			return token_type;
		}
		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}
		public int getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		public String getScope() {
			return scope;
		}
		public void setScope(String scope) {
			this.scope = scope;
		}
		public String getId_token() {
			return id_token;
		}
		public void setId_token(String id_token) {
			this.id_token = id_token;
		}
		private int expires_in;
		private String scope;
		private String id_token;
	}
	
	public static class GoogleTokenRequest implements Serializable
	{
		private String grant_type;
		private String code;
		private String client_id;
		
		public String getGrant_type()
		{
			return grant_type;
		}
		public void setGrant_type(String grant_type)
		{
			this.grant_type = grant_type;
		}
		public String getCode() 
		{
			return code;
		}
		public void setCode(String code)
		{
			this.code = code;
		}
		public String getClient_id()
		{
			return client_id;
		}
		public void setClient_id(String client_id) 
		{
			this.client_id = client_id;
		}
		public String getClient_secret()
		{
			return client_secret;
		}
		public void setClient_secret(String client_secret) 
		{
			this.client_secret = client_secret;
		}
		public String getRedirect_uri() 
		{
			return redirect_uri;
		}
		public void setRedirect_uri(String redirect_uri)
		{
			this.redirect_uri = redirect_uri;
		}
		private String client_secret;
		private String redirect_uri;
	}

	public static String getTokenFromHeaders(HttpHeaders header) throws Exception
	{
		return header.get(TokenUtils.HEADER_STRING).get(0).replace(TokenUtils.TOKEN_PREFIX, "");
	}
	
	public static String getFullTokenFromHeaders(HttpHeaders header) throws Exception
	{
		return header.get(TokenUtils.HEADER_STRING).get(0);
	}
	

	
}
