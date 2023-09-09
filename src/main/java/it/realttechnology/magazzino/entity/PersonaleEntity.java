package it.realttechnology.magazzino.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.realttechnology.magazzino.entity.RolesEntity;

@Entity
@Table(name = "personale")
public class PersonaleEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name="nome")
	String nome;
	@Column(name="cognome")
	String cognome;
	@Column(name="username")
	String username;
	@Column(name="password")
	String password;
	@Column(name="token")
	String token;
	
	@JsonIgnore
	public String getToken() {
		return token;
	}
	@JsonIgnore
	public void setToken(String token) {
		this.token = token;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "personale_roles", 
	joinColumns = @JoinColumn(name = "id_personale", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
	private Set<RolesEntity> personaleroles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Set<RolesEntity> getPersonaleroles() {
		return personaleroles;
	}

	public void setPersonaleroles(Set<RolesEntity> personaleroles) {
		this.personaleroles = personaleroles;
	}
	
	
  
}
