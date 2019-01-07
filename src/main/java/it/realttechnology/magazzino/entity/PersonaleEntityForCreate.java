package it.realttechnology.magazzino.entity;

import javax.validation.constraints.NotNull;

public class PersonaleEntityForCreate
{
	  @NotNull
	String nome;
	  @NotNull
	String cognome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	  @NotNull
	String username;
	  @NotNull
	String password;
	
}
