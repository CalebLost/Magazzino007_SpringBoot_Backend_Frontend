package it.realttechnology.magazzino.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.realttechnology.magazzino.entity.PersonaleEntity;


public interface UsersAuthenticationService
{
   PersonaleEntity loginUsername(String userName)  throws UsernameNotFoundException;
   PersonaleEntity loginToken(String token) throws UsernameNotFoundException;
   void logout(String token) throws UsernameNotFoundException;
   void setUserToken(String username, String token) throws UsernameNotFoundException;
   String getUserToken(String userName) throws UsernameNotFoundException;
   
}
