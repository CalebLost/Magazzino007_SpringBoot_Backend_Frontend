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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
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

import it.realttechnology.magazzino.configuration.Oauth2Configurator;

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

	@Autowired
	private Oauth2Configurator oauth2configurator;
	
	private static List<SimpleGrantedAuthority> simpleGrantedAuthorities;
	
	private java.util.Map<String, String> parameters;
	

	static 
	{
		simpleGrantedAuthorities = new ArrayList<>();
	}
	
	@PostConstruct
	public void loadRoles()
	{
		simpleGrantedAuthorities.clear();
		
		String[] vRoles =	this.oauth2configurator.getRoles();
		
		for(String role : vRoles)
		{
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
		}
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
			return this.oauth2configurator.getLoginView();
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
				 req.setClient_id(this.oauth2configurator.getoAuthClient());
				 req.setClient_secret(this.oauth2configurator.getoAuthSecret());
				 req.setGrant_type(TokenUtils.TOKEN_GRANT_CODE);
				 req.setCode(code);
				 req.setRedirect_uri((this.oauth2configurator.isSsl() || this.oauth2configurator.isRedirectssl() ? Oauth2Configurator.HTTPS : Oauth2Configurator.HTTP) +this.oauth2configurator.getHostnameConditionalPort()+this.oauth2configurator.getLoginView());
				
				 HttpEntity<TokenUtils.GoogleTokenRequest> googleTokenRequest = new HttpEntity<>(req);
				 ResponseEntity<Object> result                                = restTemplate.postForEntity(this.oauth2configurator.getoAuthTokenUri(), googleTokenRequest, Object.class);
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
			
			String[] entries = queryString.split(Oauth2Configurator.namevalsep);
				
			for(String entry : entries)
			{
				String[] nameValue = entry.split(Oauth2Configurator.datasep);
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
            
            GoogleUserInfo userName = restTemplate.getForObject(this.oauth2configurator.getoAuthUser()+Oauth2Configurator.querysep+TokenUtils.TOKEN_NAME+Oauth2Configurator.datasep+this.parameters.get(TokenUtils.TOKEN_NAME), TokenUtils.GoogleUserInfo.class);
        	
            if(userName==null)
            {
            	return null;
            }
            
            String namemail = "<DIV><SPAN>"+userName.getName() + Oauth2Configurator.usersep + userName.getEmail() + " </SPAN>" + ((userName.getPicture() != null && userName.getPicture().length() > 3) ? "<img  style='vertical-align:middle; border-radius: 50%;' src='"+userName.getPicture()+"' width='30px' heigth='30px'>" : "")+"</DIV>" ;
            
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