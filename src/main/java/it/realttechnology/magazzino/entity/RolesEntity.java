package it.realttechnology.magazzino.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.realttechnology.magazzino.entity.PersonaleEntity;

@Entity
@Table(name="roles")
public class RolesEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(name="role")
    String role;
   
	
	@ManyToMany
	@JoinTable(name = "personale_roles", 
	joinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "id_personale", referencedColumnName = "id"))
	private Set<PersonaleEntity> rolepersonale;


	public int getId()
	{
		return id;
	}


	public void setId(int id) 
	{
		this.id = id;
	}


	public String getRole()
	{
		return role;
	}


	public void setRole(String role) 
	{
		this.role = role;
	}

	@JsonIgnore
	public Set<PersonaleEntity> getRolepersonale()
	{
		return rolepersonale;
	}


	public void setRoleusers(Set<PersonaleEntity> rolepersonale) 
	{
		this.rolepersonale = rolepersonale;
	}
	
	
}
