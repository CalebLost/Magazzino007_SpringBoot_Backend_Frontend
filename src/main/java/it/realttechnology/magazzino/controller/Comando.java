package it.realttechnology.magazzino.controller;

public class Comando
{
	private String nome;
	private String annulla;
	private String conferma;
	private String azione;
	String type;
	
	public Comando(String nome,String conferma, String annulla, String azione, String type) 
	{
		super();
		this.nome = nome;
		this.azione = azione;
		this.type = type;
		this.conferma = conferma;
		this.annulla = annulla;
	}
	public String getConferma() {
		return conferma;
	}
	public void setConferma(String confirm) {
		this.conferma = confirm;
	}
	public String getNome() 
	{
		return nome;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public String getAnnulla() {
		return annulla;
	}
	public void setAnnulla(String abort) {
		this.annulla = abort;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione)
	{
		this.azione = azione;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	 
	
}