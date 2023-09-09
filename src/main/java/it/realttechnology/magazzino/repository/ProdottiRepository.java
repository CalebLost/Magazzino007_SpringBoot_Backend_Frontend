package it.realttechnology.magazzino.repository;

import java.util.Optional;

//import javax.persistence.Column;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;

public interface ProdottiRepository extends CrudRepository<ProdottiEntity,Integer> 
{
	//public final static String FIND_ALL_WITHOUT_FOREIGNS            = "SELECT * FROM prodotti";
	public final static String FIND_ALL_WITHOUT_FOREIGNS            = "select NEW ProdottiEntity(p.id_prodotto_p,p.nome,p.descrizione,p.p_quantita,p.p_prezzo) from ProdottiEntity p";
	//can select single fields or exclude vendite or distinct
	public static final String FIND_ALL_BY_CLIENTE                  = "select DISTINCT p from ProdottiEntity p INNER JOIN p.vendite v WHERE v.cliente = ?0";
	public static final String FIND_ALL_BY_CLIENTE_WITHOUT_FOREIGNS = "select DISTINCT NEW ProdottiEntity(p.id_prodotto_p,p.nome,p.descrizione,p.p_quantita,p.p_prezzo) from ProdottiEntity p INNER JOIN p.vendite v WHERE v.cliente = ?0";
	
    public static final String COUNT_BY_CLIENTE= "select COUNT(DISTINCT p) from ProdottiEntity p INNER JOIN p.vendite v WHERE v.cliente = ?1";
	
	//select single
	Optional<ProdottiEntity> findByNome(String name);
    //select like
	Iterable<ProdottiEntity> findByNomeContaining(String name);
	
	Iterable<ProdottiEntity> findByVenditeId(int id);
	
	Iterable<ProdottiEntity> findByVenditeCliente(ClientiEntity cliente);
	
	Iterable<ProdottiEntity> findByDescrizioneContaining(String descrizione);
	//TEST: will it works???
	Iterable<ProdottiEntity> findByNomeContainingOrDescrizioneContaining(String nome,String descrizione);
	
	@Query(COUNT_BY_CLIENTE)
	Long countByVenditeCliente(ClientiEntity cliente);
}
