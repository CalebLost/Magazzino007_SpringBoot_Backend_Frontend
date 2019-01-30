package it.realttechnology.magazzino.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface OAuth2Request
{
	Authentication getAuthentication(HttpServletRequest request) throws Exception;

	void setAuthentication(HttpServletRequest request) throws Exception;

}
