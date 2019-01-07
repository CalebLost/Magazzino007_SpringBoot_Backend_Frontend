package it.realttechnology.magazzino.security;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse implements Serializable
{
	private String authenticationResponse;

	public LoginResponse() 
	{
	
	}

	public LoginResponse(String response) 
	{
		this.authenticationResponse = response;
	}

	@JsonProperty("id_token")
	public String getIdToken() 
	{
		return authenticationResponse;
	}

	public void setIdToken(String idToken) 
	{
		this.authenticationResponse = idToken;
	}
}
