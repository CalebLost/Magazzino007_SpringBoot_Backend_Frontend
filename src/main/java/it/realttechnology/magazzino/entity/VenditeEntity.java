package it.realttechnology.magazzino.entity;

import java.util.Date;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;


@Entity
@Table(name = "vendite")
public class VenditeEntity

{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_prodotto_p", nullable = false)
	//TEST
	@JsonBackReference
	private ProdottiEntity prodotto;
	
    //name è il fisico sulla chiave esterna
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_cliente_c", nullable = false)
	//TEST
	@JsonBackReference
	private ClientiEntity cliente;
	
	//Transient properties are shown in json but not use persist
	@Transient
	private int id_cliente_v;
	@Transient
	private int id_prodotto_v;
	
	//for json only
	public int getId_cliente() {
		return cliente.getId();
	}
	//for json only
	public void setId_cliente(int id_cliente) 
	{
		this.cliente.setId(id_cliente_v);
	}
	//for json only
	public int getId_prodotto() {
		return prodotto.getId();
	}
	//for json only
	public void setId_prodotto(int id_prodotto)
	{
		this.prodotto.setId(id_prodotto_v);
	}

	
	//DATA
	@Column (name = "data")
	Date data;


	//PREZZO TOTALE
	@Column (name = "v_prezzo")
	double v_prezzo;
	
	@Column (name = "v_quantita")
	int v_quantita;

	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	//TEST TO BREAK THE CHAIN SERIALIZATION
	//@JsonIgnore
	public ClientiEntity getCliente() 
	{
		return cliente;
	}

	//LE VENDITE NON DEVONO PERSISTERE LE ENTITY
	public void setCliente(ClientiEntity cliente)
	{
		this.cliente = cliente;
	}

	public void setProdotto(ProdottiEntity prodotto) 
	{
		this.prodotto = prodotto;
	}
	public ProdottiEntity getProdotto() {
		return prodotto;
	}

	
	public Date getData()
	{
		return data;
	}

	public void setData(Date data) 
	{
		this.data = data;
	}

	public double getPrezzo() 
	{
		return v_prezzo;
	}

	public void setPrezzo(double prezzo) 
	{
		this.v_prezzo = prezzo;
	}

	public int getQuantita()
	{
		return v_quantita;
	}

	public void setQuantita(int quantita) 
	{
		this.v_quantita = quantita;
	}
    @Override
	public String toString()
	{
		return "{id:"+getId()+",cliente:"+getId_cliente()+",prodotto:"+getId_prodotto()+",data:"+getData()+",prezzo:"+getPrezzo()+",quantita:"+getQuantita()+"}";

	}
}
