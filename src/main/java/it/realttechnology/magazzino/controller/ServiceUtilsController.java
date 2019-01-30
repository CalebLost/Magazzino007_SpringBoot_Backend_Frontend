package it.realttechnology.magazzino.controller;

import java.security.Principal;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.realttechnology.magazzino.services.UsersAuthenticationService;
import it.realttechnology.magazzino.security.*;
@RestController
@RequestMapping("/")
public class ServiceUtilsController
{

    @Autowired
	UsersAuthenticationService authenticationService;
    @PostMapping("services/logout")
    @ResponseBody
    public ResponseEntity<LoginResponse> signOut(@RequestHeader HttpHeaders header) 
    {
    	authenticationService.logout(header.get(TokenUtils.HEADER_STRING).get(0).replace(TokenUtils.TOKEN_PREFIX, ""));
    	LoginResponse response = new LoginResponse(header.get(TokenUtils.HEADER_STRING).get(0),false);
    	ResponseEntity<LoginResponse> responseEntity = new ResponseEntity(response,HttpStatus.OK);
    	return responseEntity;
    
    }
    @PostMapping("/services/logincheck")
    @ResponseBody
    public void loginCheck(Principal principal) 
    {
    	Logger.getLogger(ServiceUtilsController.class).log(Level.INFO, "USER " + principal.getName());
    }
    
    @RequestMapping(value = "/user")
    public Principal user(Principal principal) {
       return principal;
    }
}
