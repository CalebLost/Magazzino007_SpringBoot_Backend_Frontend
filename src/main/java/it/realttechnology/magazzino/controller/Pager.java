package it.realttechnology.magazzino.controller;

import java.util.List;

public class Pager
{
   private int colonne;
   private String precedente;
   private String sucessivo;
   private String pathPrecedente;
   
   public String getPathPrecedente()
{
	return pathPrecedente;
}
public void setPathPrecedente(String pathPrecedente) {
	this.pathPrecedente = pathPrecedente;
}
public String getPathSucessivo() {
	return pathSucessivo;
}
public void setPathSucessivo(String pathSucessivo) {
	this.pathSucessivo = pathSucessivo;
}
private String pathSucessivo;
   
   public int getColonne() {
	return colonne;
}
public void setColonne(int colonne) {
	this.colonne = colonne;
}
public String getPrecedente() {
	return precedente;
}
public void setPrecedente(String precedente) {
	this.precedente = precedente;
}
public String getSucessivo() {
	return sucessivo;
}
public void setSucessivo(String sucessivo) {
	this.sucessivo = sucessivo;
}
public List<PagerPage> getPagine() {
	return pagine;
}
public void setPagine(List<PagerPage> pagine) {
	this.pagine = pagine;
}
List<PagerPage> pagine;
}
