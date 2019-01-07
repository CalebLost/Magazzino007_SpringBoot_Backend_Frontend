package it.realttechnology.magazzino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.entity.VenditeEntity;

public interface VenditeRepository extends CrudRepository<VenditeEntity, Integer> 
{

		Iterable<VenditeEntity> findByCliente(ClientiEntity clienteEntity);
		
		Iterable<VenditeEntity> findByProdotto(ProdottiEntity prodottiEntity);
		
		@Query("select v from VenditeEntity v WHERE v.v_prezzo >= ?1 AND v.v_prezzo <=?2")
		Iterable<VenditeEntity> findByPrezzoRange(double prezzoMin,double prezzoMax);
		@Query("select v from VenditeEntity v WHERE v.v_prezzo <= ?1")
		Iterable<VenditeEntity> findByPrezzoMinor(double prezzoMax);
		@Query("select v from VenditeEntity v WHERE v.v_prezzo >= ?1")
		Iterable<VenditeEntity> findByPrezzoMajor(double prezzoMin);

		
}
