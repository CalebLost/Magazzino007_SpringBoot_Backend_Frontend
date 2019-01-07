package it.realttechnology.magazzino.services;

import java.util.Optional;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;

public interface ProdottiServiceDAO 
{
	Optional<ProdottiEntity> findById(int id);
	
	Optional<ProdottiEntity> find(ProdottiEntity prodotto);
	
	Optional<ProdottiEntity> findByNome(String name);
   
	Iterable<ProdottiEntity> findAll();
		
	Iterable<ProdottiEntity> findByNomeContaining(String nome);
	//TODO: implements:
	Iterable<ProdottiEntity> findByDescrizioneContaining(String descrizione);
	
	Iterable<ProdottiEntity> findByNomeContainingOrDescrizioneContaining(String name, String descrizione);
	//--------------------------------------------------------
	Iterable<ProdottiEntity> findByVenditeId(int id);
	
	Iterable<ProdottiEntity> findByVenditeCliente(ClientiEntity cliente);
	
	Iterable<ProdottiEntity> findByVenditeClienteWithoutVendite(ClientiEntity cliente,int row,int columns);
	
	Iterable<ProdottiEntity> findByVenditeClienteWithoutVendite(ClientiEntity cliente);
	
	Optional<ProdottiEntity> create(ProdottiEntity prodotto);
	
	Optional<ProdottiEntity> update(ProdottiEntity prodotto);
	
	Iterable<ProdottiEntity> findAllWithoutForeigns();
	
	Iterable<ProdottiEntity> findAllWithoutForeigns(int row,int columns);
	
	boolean delete(ProdottiEntity prodotto);
	
	boolean deleteById(int id);

	Long count();

	Long countByVenditeCliente(ClientiEntity cliente);

}
