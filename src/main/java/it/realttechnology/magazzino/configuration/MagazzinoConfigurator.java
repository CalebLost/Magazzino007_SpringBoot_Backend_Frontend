package it.realttechnology.magazzino.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import it.realttechnology.magazzino.controller.PagerPage;

@Component
@PropertySource("classpath:languages.properties")
@ConfigurationProperties("magazzino")
public class MagazzinoConfigurator 
{
 private String titolo;

public String getTitolo() {
	return titolo;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}

private it_IT it_it = new it_IT();

public void setIt_it(it_IT it_it) 
{
	this.it_it = it_it;
}
public it_IT getIt_it() 
{
	return it_it;
}


public static class ProdottiHeaders
{
	private String id;
	private String nome;
	private String descrizione;
	private String quantita;
	private String prezzo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
}

//default
public static class Prodotti
{
	private String titolo;
	private String titolo_cliente;
	//construct the class here to be filled after
    private ProdottiHeaders headers = new ProdottiHeaders();
    
	public String getTitolo() 
	{
		return titolo;
	}

	public void setClienteTitolo(String titolo)
	{
		this.titolo_cliente = titolo;
	}
	public String getClienteTitolo() 
	{
		return titolo_cliente;
	}

	public void setTitolo(String titolo)
	{
		this.titolo = titolo;
	}
	public ProdottiHeaders getHeaders() 
	{
		return headers;
	}

	
	
}

public static class it_IT
{
	private Prodotti prodotti = new Prodotti();
    public void setProdotti(Prodotti prodotti) {
		this.prodotti = prodotti;
	}
	private String titolo;
    
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Prodotti getProdotti() {
		return prodotti;
	}
	
}

}
