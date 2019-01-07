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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "prodotti")
//@JsonIgnoreProperties(value = "prodotto", allowSetters=true)
public class ProdottiEntity 
{
	
	//PROPONGO STRATEGIA TIPO IDENTITI
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_prodotto_p;
	
	@Column (name = "descrizione")
	private String descrizione;
	
	@Column (name = "p_quantita")
	private int p_quantita;

	@Column (name = "p_prezzo")
	private float p_prezzo;
    
	@Column (name = "nome")
	private String nome;
	
	//MAPPED BY lavora sul nome definito in vendite
	@OneToMany(cascade = CascadeType.ALL,mappedBy="prodotto",  orphanRemoval = true,fetch = FetchType.LAZY)
	//SE MAPPED BY TOGLIERE QUESTO
	//join lavora sul campo fisico
	//@JoinColumn(name = "id_prodotto")
	@JsonIgnoreProperties(value = "id_prodotto", allowSetters=true)
	//TEST!!!!
	@JsonManagedReference
	private List<VenditeEntity> vendite;
	

	public ProdottiEntity()
	{
		
	}

	public ProdottiEntity(int id_prodotto, String nome, String descrizione, int quantita, float prezzo) {
		super();
		this.id_prodotto_p = id_prodotto;
		this.descrizione = descrizione;
		this.p_quantita = quantita;
		this.p_prezzo = prezzo;
		this.nome = nome;
		this.vendite = null;
	}

	public String getNome() {
		return nome;
	}
	//@JsonIgnore QUA DA ERRORI
	//provare a spostare dall'altra parte
	public List<VenditeEntity> getVendite() {
		return vendite;
	}
	public void setVendite(List<VenditeEntity> vendite) {
		this.vendite = vendite;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id_prodotto_p;
	}

	public void setId(int id) {
		this.id_prodotto_p = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getQuantita() {
		return p_quantita;
	}

	public void setQuantita(int quantita) {
		this.p_quantita = quantita;
	}

	public float getPrezzo() {
		return p_prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.p_prezzo = prezzo;
	}
	
	//TODO: RELAZIONI ESTERNE!!!
	
	
}
