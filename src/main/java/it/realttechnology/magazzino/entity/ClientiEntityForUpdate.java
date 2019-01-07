package it.realttechnology.magazzino.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientiEntityForUpdate 
{
	@NotNull
  private int id;

  private String nome;
  private String telefono;
  private String indirizzo;
  
public String getNome() {
	return nome;
}
public void setNome(String nome) 
{
	this.nome = nome;
}
public String getTelefono() 
{
	return telefono;
}
public void setTelefono(String telefono)
{
	this.telefono = telefono;
}
public String getIndirizzo() {
	return indirizzo;
}
public void setIndirizzo(String indirizzo) 
{
	this.indirizzo = indirizzo;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
}
