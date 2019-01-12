package it.realttechnology.magazzino.security;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse implements Serializable
{
	private String authenticationResponse;
    private boolean status;
    


	public LoginResponse() 
	{
	
	}
	public LoginResponse(String response) 
	{
		this.authenticationResponse = response;
		this.status = true;
	}
	public LoginResponse(String response,boolean status) 
	{
		this.authenticationResponse = response;
		this.status = status;
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
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
