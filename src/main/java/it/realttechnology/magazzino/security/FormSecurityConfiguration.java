package it.realttechnology.magazzino.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.realttechnology.magazzino.services.UserDetailsAuthenticationServiceImpl;
import it.realttechnology.magazzino.services.UsersAuthenticationService;


@Configuration
@Order(2)
@EnableWebSecurity
//for method secure annotation
@EnableGlobalMethodSecurity(securedEnabled = true)
public class FormSecurityConfiguration extends WebSecurityConfigurerAdapter
{
      
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UsersAuthenticationService userAuthenticationService;
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.antMatcher("/views/**");
		//https://www.thomasvitale.com/https-spring-boot-ssl-certificate/
	    //enable access on services or disAble
		http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/views/**"))
	   //http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/views/login"))
	  .and()
	  .authorizeRequests()
	  //the secured annotation on methods specify differents auth stuffs
	  .antMatchers("/views/personale/**").hasRole("USER")
	  .anyRequest().authenticated()
	  .and()
	  .formLogin()
	  .successHandler(tokenGeneratorSuccessHandler("/views/login"))
	  .loginPage("/views/login")
	  .permitAll()
	  .failureUrl("/views/login?error=true")
      .and()
      .rememberMe().key("uniqueAndSecret")
      .and() 
      .logout().logoutRequestMatcher(new AntPathRequestMatcher("/views/logout"))
      .addLogoutHandler(tokenDeleterLogoutHandler()).logoutSuccessUrl("/views/login");
		//.invalidateHttpSession(true).deleteCookies("JSESSIONID").
	   //multiple form urls for different paths
	  // .exceptionHandling()
      // .defaultAuthenticationEntryPointFor(
      //   loginUrlauthenticationEntryPointWithWarning(),
     //    new AntPathRequestMatcher("..."))
     //  .defaultAuthenticationEntryPointFor(
     //    loginUrlauthenticationEntryPoint(), 
     //    new AntPathRequestMatcher("..."));
	  
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
	     auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	@Bean
	AuthenticationSuccessHandler tokenGeneratorSuccessHandler(String redirectUrl)
	{
		return new TokenGeneratorSuccessHandler(redirectUrl,this.userAuthenticationService);
	}
	@Bean
	LogoutHandler tokenDeleterLogoutHandler()
	{
		return new TokenDeleterLogoutHandler(this.userAuthenticationService);
	}

}
