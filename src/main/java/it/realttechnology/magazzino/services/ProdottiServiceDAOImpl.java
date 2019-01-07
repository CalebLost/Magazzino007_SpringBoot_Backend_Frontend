package it.realttechnology.magazzino.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.repository.ProdottiRepository;

@Service
public class ProdottiServiceDAOImpl implements ProdottiServiceDAO
{

	@Autowired
	ProdottiRepository prodottiRepository;
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public Optional<ProdottiEntity> findByNome(String nome) 
	{
		return prodottiRepository.findByNome(nome);
	}

	@Override
	public Iterable<ProdottiEntity> findByNomeContaining(String nome) 
	{
		return prodottiRepository.findByNomeContaining(nome);
	}

	@Override
	public Iterable<ProdottiEntity> findByVenditeId(int id) 
	{
		return prodottiRepository.findByVenditeId(id);
	}

	@Override
	public Iterable<ProdottiEntity> findByVenditeClienteWithoutVendite(ClientiEntity cliente) 
	{
		//return the asspciated vendite each time
		//return prodottiRepository.findByVenditeCliente(cliente);
		return entityManager.createQuery(prodottiRepository.FIND_ALL_BY_CLIENTE_WITHOUT_FOREIGNS).setParameter(0,cliente).getResultList();
	
	}
	
	@Override
	public Iterable<ProdottiEntity> findByVenditeCliente(ClientiEntity cliente) 
	{
		//return the asspciated vendite each time
		//return prodottiRepository.findByVenditeCliente(cliente);
		return entityManager.createQuery(prodottiRepository.FIND_ALL_BY_CLIENTE).setParameter(0,cliente).getResultList();
	}
	
	@Override
	public Iterable<ProdottiEntity> findByVenditeClienteWithoutVendite(ClientiEntity cliente,int row,int columns) 
	{
		int firstResult = row * columns;
		int maxResults  = columns;
		return entityManager.createQuery(prodottiRepository.FIND_ALL_BY_CLIENTE).setParameter(0,cliente).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}
	
	
	
	
	@Override
	public Optional<ProdottiEntity> create(ProdottiEntity prodotto) 
	{
	ProdottiEntity p = null;
		
		try
		{
		    p = prodottiRepository.save(prodotto);
		}
		catch(Exception e)
		{
			return Optional.ofNullable(p);
		}
		
		return Optional.of(p);
	}

	@Override
	public Optional<ProdottiEntity> update(ProdottiEntity prodotto) 
	{ 
		ProdottiEntity p = null;
		
		try
		{
		    p = prodottiRepository.save(prodotto);
		}
		catch(Exception e)
		{
			return Optional.ofNullable(p);
		}
		
		return Optional.of(p);
	}

	@Override
	public boolean delete(ProdottiEntity prodotto) 
	{
		// TODO Auto-generated method stub
		try
		{
		prodottiRepository.delete(prodotto);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}
    @Override
	public Iterable<ProdottiEntity> findAll() 
	{
		// TODO Auto-generated method stub
		return prodottiRepository.findAll();
	}
	@Override
	public Iterable<ProdottiEntity> findAllWithoutForeigns() {
		// TODO Auto-generated method stub
	//	return entityManager.createNativeQuery(prodottiRepository.FIND_ALL_WITHOUT_FOREIGNS).getResultList();
		return entityManager.createQuery(prodottiRepository.FIND_ALL_WITHOUT_FOREIGNS).getResultList();
	}
	public Iterable<ProdottiEntity> findAllWithoutForeigns(int row,int columns) 
	{
		int firstResult = row * columns;
		int maxResults  = columns;
		// TODO Auto-generated method stub
	    //return entityManager.createNativeQuery(prodottiRepository.FIND_ALL_WITHOUT_FOREIGNS).getResultList();
		return entityManager
			    .createQuery(prodottiRepository.FIND_ALL_WITHOUT_FOREIGNS)
				 .setFirstResult(firstResult)
				  .setMaxResults(maxResults).getResultList();
	}
	public Optional<ProdottiEntity> findById(int id) 
	{
		// TODO Auto-generated method stub
		return prodottiRepository.findById(id);
	}

	public boolean deleteById(int id)
	{
		try
		{
		  prodottiRepository.deleteById(id);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public Optional<ProdottiEntity> find(ProdottiEntity prodotto) 
	{
		return prodottiRepository.findById(prodotto.getId());
	}

	@Override
	public Iterable<ProdottiEntity> findByDescrizioneContaining(String descrizione) 
	{
	    return prodottiRepository.findByDescrizioneContaining(descrizione);
	}

	@Override
	public Iterable<ProdottiEntity> findByNomeContainingOrDescrizioneContaining(String nome, String descrizione) 
	{
		 return prodottiRepository.findByNomeContainingOrDescrizioneContaining(nome,descrizione);
	}
	@Override
	public Long count() 
	{
		return prodottiRepository.count();
	}
	@Override
	public Long countByVenditeCliente(ClientiEntity cliente)
	{
		return prodottiRepository.countByVenditeCliente(cliente);
	}
	
	
}
