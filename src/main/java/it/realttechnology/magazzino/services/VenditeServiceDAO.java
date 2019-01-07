package it.realttechnology.magazzino.services;

import java.util.Optional;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.entity.VenditeEntity;

public interface VenditeServiceDAO 
{
	Optional<VenditeEntity>  findById(int id);
	
	Optional<VenditeEntity>  find(VenditeEntity vendita);
	
	Iterable<VenditeEntity>  findAll();
	
	Iterable<VenditeEntity>  findByCliente(ClientiEntity cliente);
   
	Iterable<VenditeEntity>  findByProdotto(ProdottiEntity prodotto);
	
	Iterable<VenditeEntity>  findByPrezzoRange(double prezzoMin, double prezzoMax);
	Iterable<VenditeEntity>  findByPrezzoMajor(double prezzoMin);
	Iterable<VenditeEntity>  findByPrezzoMinor(double prezzoMax);
	
	Optional<VenditeEntity> create(VenditeEntity vendita);
	
	Optional<VenditeEntity> update(VenditeEntity vendita);
	
	boolean delete(VenditeEntity vendita);
	
	boolean deleteById(int id);

}
