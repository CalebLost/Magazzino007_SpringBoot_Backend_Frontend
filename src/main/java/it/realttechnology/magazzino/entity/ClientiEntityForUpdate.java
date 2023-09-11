package it.realttechnology.magazzino.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientiEntityForUpdate 
{
  @NotNull
  @Min(1)
  private int id;
  @NotNull
  @Size(min=5,max=255)
  private String nome;
  @NotNull
  @Size(min=5,max=10)
  private String telefono;
  @NotNull
  @Size(min=5,max=255)
  private String indirizzo;
  private String method_;
  public String getMethod_()
  {
   return method_;
  }
  public void setMethod_(String method_)
  {
   this.method_ = method_;
  }
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
