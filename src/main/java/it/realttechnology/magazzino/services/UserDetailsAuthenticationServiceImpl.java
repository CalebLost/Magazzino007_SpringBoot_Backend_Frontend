package it.realttechnology.magazzino.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.realttechnology.magazzino.entity.PersonaleEntity;
import it.realttechnology.magazzino.entity.RolesEntity;
import it.realttechnology.magazzino.security.TokenProperties;




//Implements the userdetails service
@Service
public class UserDetailsAuthenticationServiceImpl implements UserDetailsService
{



    @Autowired
    UsersAuthenticationServiceImpl personaleAuthentication;
  
    private final String TOKEN_PWD;
    public UserDetailsAuthenticationServiceImpl
    (
    		BCryptPasswordEncoder bCryptPasswordEncoder
	)
    {
        TOKEN_PWD = bCryptPasswordEncoder.encode(TokenProperties.TOKEN_AUTH_PWD);
    }
    //GET A USER DETAILS SPRING SEC OBJ FROM DB USER
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException 
    {

        PersonaleEntity user = userName.startsWith(TokenProperties.TOKEN_PREFIX)
                ? personaleAuthentication.loginToken(userName.replace(TokenProperties.TOKEN_PREFIX, ""))
                : personaleAuthentication.loginUsername(userName);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        
        //eager fetch
        Set<RolesEntity> grantedAuthoritiesR = user.getPersonaleroles();
        
        if(grantedAuthoritiesR!=null)
        {
          for(RolesEntity role : grantedAuthoritiesR)
          {
        	
            LoggerFactory.getLogger(UserDetailsAuthenticationServiceImpl.class).info("Role " + role.getRole());
          	grantedAuthorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s",role.getRole())));
          }
        }
        
        //TOKEN VALIDATOR
        if (userName.startsWith(TokenProperties.TOKEN_PREFIX))
        {
            return new User
        		  (
        			  userName,
        			  TOKEN_PWD,
              		  grantedAuthorities
              		);
        		
          }
        //USER PASSWORD VALIDATOR
        return new User
        		(
        		  user.getUsername(),
        		  user.getPassword(),
        		  grantedAuthorities
        		);
    }
}