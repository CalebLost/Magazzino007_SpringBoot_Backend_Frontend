package it.realttechnology.magazzino.configuration;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import it.realttechnology.magazzino.controller.PagerPage;

@Component
@PropertySource("classpath:languages.properties")
@ConfigurationProperties("magazzino")
public class MagazzinoConfigurator 
{
	
 @Autowired
private MessageSource messageSource;
	    
 private String titolo;
 
 @PostConstruct
 private void init() 
 {
	 it_IT.set(messageSource);
 }
public String getTitolo() {
	return titolo;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}

private it_IT it_IT = new it_IT();

public void setit_IT(it_IT it_IT) 
{
	this.it_IT = it_IT;
	
}
public it_IT getit_IT() 
{
	return it_IT;
}
public static class Login
{
	private String titolo;
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	private String nome;
	private String password;
	private String nomemancante;
	private String passwordmancante;
	private String cancella;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNomemancante() {
		return nomemancante;
	}
	public void setNomemancante(String nomemancante) {
		this.nomemancante = nomemancante;
	}
	public String getPasswordmancante() {
		return passwordmancante;
	}
	public void setPasswordmancante(String passwordmancante) {
		this.passwordmancante = passwordmancante;
	}
	public String getCancella() {
		return cancella;
	}
	public void setCancella(String cancella) {
		this.cancella = cancella;
	}
	public String getEntra() {
		return entra;
	}
	public void setEntra(String entra) {
		this.entra = entra;
	}
	public String getEsci() {
		return esci;
	}
	public void setEsci(String esci) {
		this.esci = esci;
	}
	public String getRicordati() {
		return ricordati;
	}
	public void setRicordati(String ricordati) {
		this.ricordati = ricordati;
	}
	private String entra;
	private String esci;
	private String ricordati;
	private String errore;
	private String googlesso;
	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	public String getGoogleSSO()
	{
		return googlesso;
	}
	public void setGoogleSSO(String googlesso)
	{
		this.googlesso = googlesso;
	}


}
public static class Vendite
{
	private String titolo;
	private String titolo_cliente;
	private String titolo_prodotto;
	//construct the class here to be filled after
    private VenditeHeaders headers = new VenditeHeaders();
    private String timeformatter;
	private int precision;
	private String currency;
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	private String separatorone;
	public String getSeparatorOne() {
		return separatorone;
	}

	public void setSeparatorOne(String separatorOne) {
		this.separatorone = separatorOne;
	}

	public String getSeparatorTwo() {
		return separatortwo;
	}

	public void setSeparatorTwo(String separatorTwo) {
		this.separatortwo = separatorTwo;
	}
	private String separatortwo;
	public String getTimeformatter() {
		return timeformatter;
	}

	public void setTimeformatter(String timeformatter) {
		this.timeformatter = timeformatter;
	}

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
	public VenditeHeaders getHeaders() 
	{
		return headers;
	}
	public void setProdottoTitolo(String titolo)
	{
		this.titolo_prodotto = titolo;
	}
	public String getProdottoTitolo()
	{
		return this.titolo_prodotto;
	}

	public int getPrecision() 
	{
		return this.precision;
	}
	public void setPrecision(int precision) 
	{
		this.precision = precision;
	}

	
	
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
public static class VenditeHeaders
{
	private String id;
	private String prodotto;
	private String cliente;
	private String prezzo;
	private String quantita;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProdotto() {
		return prodotto;
	}
	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	private String data;
	
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
	private Vendite vendite = new Vendite();
	private Login login = new Login();
	private MessageSourceAccessor accessor;
	private String[] clientiHeaders;
	
	//test
	public String[] getClientiHeaders()
	{
		if(clientiHeaders == null)
		{
			clientiHeaders = new String[]
				{
				accessor.getMessage("CLIENTI_ID"),
				accessor.getMessage("CLIENTI_NOME"),
				accessor.getMessage("CLIENTI_INDIRIZZO"),
				accessor.getMessage("CLIENTI_TELEFONO"),
				accessor.getMessage("CLIENTI_OPERAZIONI")
				};
		}
		return clientiHeaders;
	}
	
    public Login getLogin() 
    {
		return login;
	}
    
    void set(MessageSource source)
    {
    	accessor = new MessageSourceAccessor(source, Locale.ITALIAN);
    }
    
    public it_IT()
    {
    
    }
    
    
	public void setLogin(Login login) {
		this.login = login;
	}
	public void setProdotti(Prodotti prodotti)
    {
		this.prodotti = prodotti;
	}
    public void setVendite(Vendite vendite)
    {
  		this.vendite = vendite;
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
	public Vendite getVendite() {
		return vendite;
	}
}

}
