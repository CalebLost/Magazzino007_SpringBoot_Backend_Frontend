package it.realttechnology.magazzino.services;

import java.util.List;
import java.util.Optional;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.repository.ClientiRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ClientiServiceDAOImpl implements ClientiServiceDAO
{

	@Autowired
	ClientiRepository clientiRepository;
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public Optional<ClientiEntity> findByNome(String nome) 
	{
		return clientiRepository.findByNome(nome);
	}

	@Override
	public Iterable<ClientiEntity> findByNomeContaining(String nome) 
	{
		return clientiRepository.findByNomeContaining(nome);
	}

	@Override
	public Iterable<ClientiEntity> findByVenditeId(int id) 
	{
		return clientiRepository.findByVenditeId(id);
	}

	@Override
	public Iterable<ClientiEntity> findByVenditeProdottoWithoutVendite(ProdottiEntity prodotto) 
	{
		//return the asspciated vendite each time
		//return clientiRepository.findByVenditeCliente(cliente);
		//return entityManager.createQuery(clientiRepository.FIND_ALL_BY_PRODOTTO_WITHOUT_FOREIGNS).setParameter(0,prodotto).getResultList();
	    return clientiRepository.findByVenditeProdottoWithoutVendite(prodotto);
	}
	@Override
	public Iterable<ClientiEntity> findByVenditeProdotto(ProdottiEntity prodotto) 
	{
		//return the asspciated vendite each time
		//return clientiRepository.findByVenditeCliente(cliente);
		//return entityManager.createQuery(clientiRepository.FIND_ALL_BY_PRODOTTO).setParameter(0,prodotto).getResultList();
		return clientiRepository.findByVenditeProdotto(prodotto);
	}
	@Override
	public Optional<ClientiEntity> create(ClientiEntity cliente) 
	{
	    ClientiEntity c = null;
		
		try
		{
		    c = clientiRepository.save(cliente);
		}
		catch(Exception e)
		{
			return Optional.ofNullable(c);
		}
		
		return Optional.of(c);
	}

	@Override
	public Optional<ClientiEntity> update(ClientiEntity cliente) 
	{ 
		ClientiEntity c = null;
		
		try
		{
		    c = clientiRepository.save(cliente);
		}
		catch(Exception e)
		{
			return Optional.ofNullable(c);
		}
		
		return Optional.of(c);
	}

	@Override
	public boolean delete(ClientiEntity cliente) 
	{
		// TODO Auto-generated method stub
		try
		{
	 	  clientiRepository.delete(cliente);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	public Iterable<ClientiEntity> findAll() 
	{
		// TODO Auto-generated method stub
		return clientiRepository.findAll();
	}
	public Iterable<ClientiEntity> findAllWithoutVendite() {
		// TODO Auto-generated method stub
	//	return entityManager.createNativeQuery(clientiRepository.FIND_ALL_WITHOUT_FOREIGNS).getResultList();
	//	return entityManager.createQuery(clientiRepository.FIND_ALL_WITHOUT_FOREIGNS).getResultList();
		return clientiRepository.findAllWithoutVendite();
	}

	public Optional<ClientiEntity> findById(int id) 
	{
		// TODO Auto-generated method stub
		return clientiRepository.findById(id);
	}

	public boolean deleteById(int id)
	{
		try
		{
		  clientiRepository.deleteById(id);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public Optional<ClientiEntity> find(ClientiEntity cliente) 
	{
		return clientiRepository.findById(cliente.getId());
	}
}
