package it.realttechnology.magazzino.services;

import java.util.Optional;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;

public interface ClientiServiceDAO 
{
	Optional<ClientiEntity> findById(int id);
	
	Optional<ClientiEntity> find(ClientiEntity cliente);
	
	Optional<ClientiEntity> findByNome(String name);
   
	Iterable<ClientiEntity> findByNomeContaining(String name);
	
	Iterable<ClientiEntity> findByVenditeId(int id);
	
	Iterable<ClientiEntity> findByVenditeProdotto(ProdottiEntity prodotto);
	
	Iterable<ClientiEntity> findByVenditeProdottoWithoutVendite(ProdottiEntity prodotto);
	
	Optional<ClientiEntity> create(ClientiEntity cliente);
	
	Optional<ClientiEntity> update(ClientiEntity cliente);
	
	Iterable<ClientiEntity> findAllWithoutVendite();
	
	boolean delete(ClientiEntity cliente);
	
	boolean deleteById(int id);

}
