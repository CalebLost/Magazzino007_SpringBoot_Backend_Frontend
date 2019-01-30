package it.realttechnology.magazzino.security;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.services.oauth2.Oauth2;
//import com.google.api.services.oauth2.model.Userinfoplus;

import it.realttechnology.magazzino.security.OAuth2Request;
import it.realttechnology.magazzino.security.TokenUtils;
import it.realttechnology.magazzino.security.TokenUtils.GoogleUserInfo;

@Component
public class GoogleOAuth2Request implements OAuth2Request
	{
	@Value("${security.oauth2.client.accessTokenUri}")
	private String oAuthUri;
	@Value("${security.oauth2.resource.userInfoUri}")
	private String oAuthUser;
	@Value("${spring.security.oauth2.client.registration.google.clientSecret}")
	private String oAuthSecret;
	@Value("${spring.security.oauth2.client.registration.google.clientId}")
	private String oAuthClient;
	
	@Value("${security.oauth2.client.clientAuthenticationScheme}")
	private String oAuthClienthScheme;
	@Value("${security.oauth2.client.authenticationScheme}")
	private String oAuthScheme;
	@Value("${security.oauth2.client.scope}")
	private String oAuthScope;
	@Value("${server.ssl.enabled}")
	private boolean isSsl;
	@Value("${login.view}")
	private String loginView;
	@Value("${app.hostname}")
	private String hostname;
	
	final static String querysep     =  "?";
	final static String namevalsep   =  "&";
	final static String datasep      =  "=";
	
	private static List<SimpleGrantedAuthority> simpleGrantedAuthorities;
	
	private java.util.Map<String, String> parameters;

	static 
	{
		simpleGrantedAuthorities = new ArrayList<>();
		simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_GOOGLE_USER"));
		
	}
	
	public GoogleOAuth2Request()
	{
		
		parameters = new HashMap<String,String>();
	}
		public GoogleOAuth2Request(  HttpServletRequest request) throws Exception
		{
			parameters = new HashMap<String,String>();
			update(request);		
		}
		
		public String getLoginView()
		{
			return loginView;
		}
		private void update(HttpServletRequest request)
		{
			parameters.clear();
			
			getQS(request);
			
			String code = parameters.get(TokenUtils.TOKEN_CODE);
			
			if(code!=null)
			{
				 RestTemplate restTemplate         = new RestTemplate();
				 TokenUtils.GoogleTokenRequest req = new TokenUtils.GoogleTokenRequest();
				 req.setClient_id(oAuthClient);
				 req.setClient_secret(oAuthSecret);
				 req.setGrant_type(TokenUtils.TOKEN_GRANT_CODE);
				 req.setCode(code);
				 req.setRedirect_uri((isSsl ? "https" : "http") + "://"+hostname+loginView);
				
				 HttpEntity<TokenUtils.GoogleTokenRequest> googleTokenRequest = new HttpEntity<>(req);
				 ResponseEntity<Object> result                                = restTemplate.postForEntity( oAuthUri, googleTokenRequest, Object.class);
				 Map<String,Object> resp                                      = (Map<String,Object>)(result.getBody());
				 
				 parameters.clear();
				 
				 parameters.put(TokenUtils.TOKEN_NAME,(String)resp.get(TokenUtils.TOKEN_NAME));
				 parameters.put(TokenUtils.TOKEN_TYPE,(String)resp.get(TokenUtils.TOKEN_TYPE));
				 parameters.put(TokenUtils.TOKEN_EXPIRES,Integer.toString((Integer)resp.get(TokenUtils.TOKEN_EXPIRES)));
				 parameters.put(TokenUtils.TOKEN_ID,(String)resp.get(TokenUtils.TOKEN_ID));
				 parameters.put(TokenUtils.TOKEN_SCOPE,(String)resp.get(TokenUtils.TOKEN_SCOPE));
			}
		}

	
		
		private void getQS(HttpServletRequest request) 
		{

			String queryString = request.getQueryString();
			
			try 
			{
				queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8.toString());
			}
			catch (UnsupportedEncodingException e) 
			{
				 Logger.getLogger(GoogleOAuth2Request.class).log(Level.DEBUG, "URL DECODE EXCEPTION: " + e.getMessage());
			}
			
			String[] entries = queryString.split(namevalsep);
			
			
			for(String entry : entries)
			{
				String[] nameValue = entry.split(datasep);
                parameters.put(nameValue[0],nameValue[1]);
			}
		
		}
         @Override
		public Authentication getAuthentication(HttpServletRequest request) throws Exception 
		{
        	update(request);
        	
        	if(parameters.isEmpty())
        	{
        		return null;
        	}
        	    
            RestTemplate restTemplate = new RestTemplate();
            
            GoogleUserInfo userName = restTemplate.getForObject(this.oAuthUser+this.querysep+TokenUtils.TOKEN_NAME+this.datasep+this.parameters.get(TokenUtils.TOKEN_NAME), TokenUtils.GoogleUserInfo.class);
        	
            if(userName==null)
            {
            	return null;
            }
            
            String namemail = userName.getName() +":"+ userName.getEmail();
            
	        Authentication authentication = getAuthentication(namemail);
	
        	return authentication;
		}
     
		public static Authentication getAuthentication(String username)
		{
			List<GrantedAuthority> authorities = new ArrayList<>();
        	
        	for(SimpleGrantedAuthority sga : simpleGrantedAuthorities)
        	{
        	 authorities.add(sga);
        	 authorities.add(sga);
        	}
        	
            User user = new User(username,TokenUtils.TOKEN_GOOGLE_PREFIX, authorities);
            
        	Authentication authentication = new UsernamePasswordAuthenticationToken(user, TokenUtils.TOKEN_GOOGLE_PREFIX,authorities);
			return authentication;
		}
		
        @Override
		  public void setAuthentication(HttpServletRequest request) throws Exception
		  {
			Authentication auth = this.getAuthentication(request);
			 
			if(auth!=null)
			 {
					SecurityContextHolder.getContext().setAuthentication(auth);
		     }
			
		}
	
	}