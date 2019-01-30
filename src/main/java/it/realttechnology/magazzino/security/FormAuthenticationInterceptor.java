package it.realttechnology.magazzino.security;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import antlr.StringUtils;

@Component
public class FormAuthenticationInterceptor implements HandlerInterceptor  {
	
	
	@Autowired
	private GoogleOAuth2Request oRequest;
	
	public FormAuthenticationInterceptor()
	{
	//	oRequest = new GoogleOAuth2Request();
	}
	
	
	 @Override
	   public boolean preHandle
	      (HttpServletRequest request, HttpServletResponse response, Object handler) 
	      throws Exception {
		 
		 try
		 {
		   oAuthHandshake(request);
		 }
		 catch(Exception e)
		 {
			 Logger.getLogger(FormAuthenticationInterceptor.class).log(Level.DEBUG, " OAUTH NOT RECOGNIZED : " + e.getMessage());
	
		 }
	
	  //    System.out.println("Pre Handle method is Calling");
	      
	      return true;
	   }

	   @Override
	   public void postHandle(HttpServletRequest request, HttpServletResponse response, 
	      Object handler, ModelAndView modelAndView) throws Exception {
		//   getAuthInfo(request);
	   }
	   
	   @Override
	   public void afterCompletion
	      (HttpServletRequest request, HttpServletResponse response, Object 
	      handler, Exception exception) throws Exception {

	   }
	   
	   private void oAuthHandshake(HttpServletRequest request) throws Exception
		{
			if(!request.getServletPath().contains(oRequest.getLoginView()))
			 {
				 return;
			 }
			 	
			Authentication auth = oRequest.getAuthentication(request);
					 
			if(auth!=null)
			 {
			 	  //  auth.setAuthenticated(true);
					SecurityContextHolder.getContext().setAuthentication(auth);
		     }

		}
	   
}