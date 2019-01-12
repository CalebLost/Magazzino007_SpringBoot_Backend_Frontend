package it.realttechnology.magazzino.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;

import it.realttechnology.magazzino.services.UsersAuthenticationService;

public class TokenDeleterLogoutHandler implements LogoutHandler{
   
	private final UsersAuthenticationService authenticationService;
	
	public TokenDeleterLogoutHandler (UsersAuthenticationService authenticationService)
	{ 
	 	this.authenticationService = authenticationService;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		
		if (authentication == null)
		{	
			return;
		}
		
		String username = "";
	   
		
		try
	    {
			  username = authentication.getName();
	          String token = authenticationService.getUserToken(username);
	          
	          if(token == null || token.isEmpty())
	          {
	        	  LoggerFactory.getLogger(TokenDeleterLogoutHandler.class).info("User [" + username+ "] token canno't be deleted beacuse does not exists");
	          }
	          else
	          {
	        	  authenticationService.logout(token);
	          }
	    }
		catch(NullPointerException n)
	    {
			 LoggerFactory.getLogger(TokenDeleterLogoutHandler.class).info("User [" + username+ "] token canno't be deleted " +  n.toString());
	    }
		catch(UsernameNotFoundException u)
	    {
	          LoggerFactory.getLogger(TokenDeleterLogoutHandler.class).info("User ["+ username+ "] not found, token canno't be deleted " +  u.toString());
	        
	    }
		
		SecurityContextLogoutHandler sec = new SecurityContextLogoutHandler();
		sec.setClearAuthentication(true);
		sec.setInvalidateHttpSession(true);
		
		final String sessioncookie  = "JSESSIONID";
		final String remembercookie = "remember-me";
		removeCookie(request, response, sessioncookie);
		removeCookie(request, response, remembercookie);
		
		sec.logout(request, response, authentication);
		
	}

	private static void removeCookie(HttpServletRequest request, HttpServletResponse response, final String sessioncookie) {
		Cookie cookie = new Cookie(sessioncookie,null);   
	    cookie.setMaxAge(0);
	    cookie.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
	    response.addCookie(cookie);
	}
}