package it.realttechnology.magazzino.security;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;



public class JWTFilterForTokens extends  OncePerRequestFilter 
{

	 private final AuthenticationManager authenticationManager;
	 

	 
	 public JWTFilterForTokens(AuthenticationManager authenticationManager)
	 {
		 this.authenticationManager = authenticationManager;
		 
	 }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException 
    {
    	
    	//1) GET THE HEADER
    	
        String header = request.getHeader(TokenUtils.HEADER_STRING);
        
        LoggerFactory.getLogger(JWTFilterForTokens.class).info("PROCESSING TOKEN,HEADER :" + header);
        
        //2)IF THE HEADER IS NOT RECOGNIZED AS A VALID TOKEN HEADER GO ON PROCESSING THE FILTERS CHIAN
        if(header == null || !header.startsWith(TokenUtils.TOKEN_PREFIX))
        {
            LoggerFactory.getLogger(JWTFilterForTokens.class).info("WRONG HEADER , GO ON");
            
			chain.doFilter(request, response);  		
			return;
		}
        
        //3 PROCESS THE HEADER ACQUIRING THE AUTHENTICATION STUFFS
        processSecurityHeader(header);

        chain.doFilter(request, response);
    }

    //PROCESS SECURITY
    private void processSecurityHeader(String header)
    {
 
    	  LoggerFactory.getLogger(JWTFilterForTokens.class).info("PROCESSING TOKEN");
    	  
          try
          {
        	//DECODE THE TOKEN
            //TODO: check for guests, add to utils const, use email insteead of name
            String userName = TokenUtils.getUserFromToken(header);
            
            LoggerFactory.getLogger(JWTFilterForTokens.class).info(userName);
            //EXTRACT DETAILED INFO FOR THE USER WITH THE GIVEN TOKEN
            if (userName != null)
            {
            	LoggerFactory.getLogger(JWTFilterForTokens.class).info("AUTHENTICATING TOKEN");
            	
            	Authentication auth = null;
            	
            	if(userName.startsWith(TokenUtils.TOKEN_GOOGLE_PREFIX))
            	{
            		auth = GoogleOAuth2Request.getAuthentication(userName.replace(TokenUtils.TOKEN_GOOGLE_PREFIX, ""));
            	}
            	else
            	{
            	auth = authenticationManager.authenticate
            	(
                        new UsernamePasswordAuthenticationToken
                        (
                        		header,
                        		TokenUtils.TOKEN_AUTH_PWD,
                                Collections.emptyList()
                        )
                        
                );
            	}
                //SET THE AUTH 
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            
           }
           catch(Exception e)
           {
        	   LoggerFactory.getLogger(JWTFilterForTokens.class).info(e.toString());
        	   //CLEAN THE SEC CONTEXT ON FAILURES
        	   SecurityContextHolder.clearContext();
           }
          
    
    }
	
}
