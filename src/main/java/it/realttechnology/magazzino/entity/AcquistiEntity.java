package it.realttechnology.magazzino.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "acquisti")
public class AcquistiEntity 
{
	//DEFAULT EMPTY CONST
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId_fornitore()
	{
		return id_fornitore;
	}
	
	public void setId_fornitore(int id_fornitore)
	{
		this.id_fornitore = id_fornitore;
	}
	
	public int getId_articolo()
	{
		return id_articolo;
	}
	
	public void setId_articolo(int id_articolo)
	{
		this.id_articolo = id_articolo;
	}
	public String getCodicefattura()
	{
		return codicefattura;
	}
	
	public void setCodicefattura(String codicefattura) 
	{
		this.codicefattura = codicefattura;
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
		return prezzo;
	}
	public void setPrezzo(double prezzo)
    {
		this.prezzo = prezzo;
	}
	
	public int getQuantita() 
	{
		return quantita;
	}
	
	public void setQuantita(int quantita) 
	{
		this.quantita = quantita;
	}
	
	@Column (name = "id_fornitore")
	int id_fornitore;
	@Column (name = "id_articolo")
	int id_articolo;
	@Column (name = "codicefattura")
	String codicefattura;
	@Column (name = "data")
	Date data;
	@Column (name = "prezzo")
	double prezzo;
	@Column (name = "quantita")
	int quantita;
}
