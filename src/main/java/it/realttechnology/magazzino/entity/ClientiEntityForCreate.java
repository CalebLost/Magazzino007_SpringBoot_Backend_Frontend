package it.realttechnology.magazzino.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientiEntityForCreate
{
  @NotNull
  @Size(min=5,max=255)
  private String nome;
  @NotNull
  @Size(min=5,max=10)
  private String telefono;
  @NotNull
  @Size(min=5,max=255)
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

}
