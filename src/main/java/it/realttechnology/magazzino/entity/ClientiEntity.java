package it.realttechnology.magazzino.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table(name = "clienti")
//@JsonIgnoreProperties(value = "cliente", allowSetters=true)
public class ClientiEntity
{
  
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id_cliente_c;
	
	@Column (name = "nome")
	private String nome;
	
	@Column (name = "telefono")
	private String telefono;
	
	@Column (name = "indirizzo")
	private String indirizzo;
	public ClientiEntity(int id_cliente_c, String nome, String telefono, String indirizzo) {
		super();
		this.id_cliente_c = id_cliente_c;
		this.nome = nome;
		this.telefono = telefono;
		this.indirizzo = indirizzo;
	}

	//se solo jointable con solo id conviene bidirezionale many to many
	//in questo caso ho piu info da reperire
	@OneToMany(cascade = CascadeType.ALL, mappedBy="cliente", orphanRemoval = true,fetch = FetchType.LAZY)
	//if mapped by no other relations
	//@JoinColumn(name = "id_cliente")
	
	//TEST TO BREAK THE CHAIN
	//if the vendite has the id i ignore the field, i use this class id
	@JsonIgnoreProperties(value = "id_cliente", allowSetters=true)
	@JsonManagedReference
	//!!!same stuffs as custom combination of transient and getter setter json
	//@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
	//@JsonIdentityReference(alwaysAsId=true) 
	private List<VenditeEntity> vendite;
	
	public List<VenditeEntity> getVendite() {
		return vendite;
	}

	public void setVendite(List<VenditeEntity> vendite) {
		this.vendite = vendite;
	}

	public ClientiEntity()
	{
		
		
	}
	
	
	

	public int getId() {
		return id_cliente_c;
	}

	public void setId(int id) {
		this.id_cliente_c = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	
}
