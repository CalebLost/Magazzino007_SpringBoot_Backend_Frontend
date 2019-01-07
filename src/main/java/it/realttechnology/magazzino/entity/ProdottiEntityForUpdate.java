package it.realttechnology.magazzino.entity;

public class ProdottiEntityForUpdate 
{
  private int id;
  private String nome;
  private String descrizione;
  private Integer quantita;
  private Float prezzo;
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
public Integer getQuantita() {
	return quantita;
}
public void setQuantita(Integer quantita) {
	this.quantita = quantita;
}
public Float getPrezzo() {
	return prezzo;
}
public void setPrezzo(Float prezzo) {
	this.prezzo = prezzo;
}
public Integer getId() {
	return id;
}
  
}
