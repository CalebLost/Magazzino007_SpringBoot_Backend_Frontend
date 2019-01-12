package it.realttechnology.magazzino.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import it.realttechnology.magazzino.services.UsersAuthenticationService;

public class TokenGeneratorSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {
	 
	private final UsersAuthenticationService authenticationService;
	
	public TokenGeneratorSuccessHandler(String defaultTargetUrl,UsersAuthenticationService authenticationService)
	{ 
		super();
		super.setDefaultTargetUrl(defaultTargetUrl);
	 	this.authenticationService = authenticationService;
	}
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException 
	{
		
		super.onAuthenticationSuccess(request, response, authentication);
		
		String username = authentication.getName();
		String token = TokenUtils.generateToken(authentication);
		
		try
	    {
	          authenticationService.setUserToken(username,token);
	       
	    }
		catch(UsernameNotFoundException u)
	    {
	        	   SecurityContextHolder.clearContext();
	        	   LoggerFactory.getLogger(TokenGeneratorSuccessHandler.class).info("User " + username + " canno't be further autherized " +  u.toString());
	        
	    }
		
		
	}

}
