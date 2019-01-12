package it.realttechnology.magazzino.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.realttechnology.magazzino.services.UsersAuthenticationService;


@Configuration
@Order(1)
@EnableWebSecurity( debug = true )
public class ServiceSecurityConfiguration extends WebSecurityConfigurerAdapter
{
@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
private UserDetailsService userDetailsService;
@Autowired
private UsersAuthenticationService userAuthenticationService;

	    @Override
	    protected void configure(HttpSecurity http) throws Exception
	    { 

	    	 http.antMatcher("/services/**")
	    	 
	    	 .cors().and().csrf().disable().authorizeRequests()
	    	
            .antMatchers("/services/prodotti/**").hasRole("USER")
	    	.antMatchers("/services/vendite/**").hasRole("ADMIN")
            .antMatchers("/services/personale/**").hasRole("ADMIN")
            
            .anyRequest().authenticated()
	
	        .and()
	        .addFilterBefore
	        (
	            new JWTFilterForCredentials("/services/login",authenticationManager(),this.userAuthenticationService),
	        	UsernamePasswordAuthenticationFilter.class
	        )
	        .addFilterBefore
	        (
	        	new JWTFilterForTokens(authenticationManager()),
	        	UsernamePasswordAuthenticationFilter.class
	        )
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	    	.exceptionHandling().authenticationEntryPoint
	    	(
	    		(req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)
	    	);
	    	 
	    }


	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception
	    {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    }

	 
}