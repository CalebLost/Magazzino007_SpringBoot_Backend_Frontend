package it.realttechnology.magazzino.entity;

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

@Entity
@Table(name = "fornitori")
public class FornitoriEntity
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;  
   @Column(name = "piva")
   String piva;
   @Column(name = "indirizzo")
   String indirizzo;
   @Column(name = "codicefiscale")
   String codicefiscale;
   @Column(name = "telefono")
   String telefono;
   
   public FornitoriEntity()
   {
	   
   }
   
   public int getId() 
   {
	return id;
   }

    public void setId(int id) 
    {
	 this.id = id;
    }

public String getPiva() {
	return piva;
}

public void setPiva(String piva) {
	this.piva = piva;
}

public String getIndirizzo() {
	return indirizzo;
}

public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}

public String getCodicefiscale() {
	return codicefiscale;
}

public void setCodicefiscale(String codicefiscale) {
	this.codicefiscale = codicefiscale;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}


}
