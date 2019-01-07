package it.realttechnology.magazzino.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;

public interface  ClientiRepository extends CrudRepository<ClientiEntity, Integer>
{
	
//	public final static String FIND_ALL_WITHOUT_FOREIGNS   = "SELECT * FROM clienti";
	//NOTA: se non uso entiti manager e create query devo per forza far partire gli indici da 1
	static final String FIND_ALL_WITHOUT_FOREIGNS             = "select NEW ClientiEntity(c.id_cliente_c,c.nome,c.telefono,c.indirizzo) FROM ClientiEntity c";
	//can select single fields or exclude vendite or distinct
	static final String FIND_ALL_BY_PRODOTTO                  = "select DISTINCT c from ClientiEntity c INNER JOIN c.vendite v WHERE v.prodotto = ?1";
	static final String FIND_ALL_BY_PRODOTTO_WITHOUT_FOREIGNS = "select DISTINCT NEW ClientiEntity(c.id_cliente_c,c.nome,c.telefono,c.indirizzo) FROM ClientiEntity c INNER JOIN c.vendite v WHERE v.prodotto = ?1";
	    //select single
	Optional<ClientiEntity> findByNome(String name);
    //select like
	Iterable<ClientiEntity> findByNomeContaining(String name);
	
	Iterable<ClientiEntity> findByVenditeId(int id);
	
	@Query(FIND_ALL_BY_PRODOTTO)
	Iterable<ClientiEntity> findByVenditeProdotto(ProdottiEntity p);
	@Query(FIND_ALL_BY_PRODOTTO_WITHOUT_FOREIGNS)
	Iterable<ClientiEntity> findByVenditeProdottoWithoutVendite(ProdottiEntity p);
	@Query(FIND_ALL_WITHOUT_FOREIGNS)
	Iterable<ClientiEntity> findAllWithoutVendite();
	
}
