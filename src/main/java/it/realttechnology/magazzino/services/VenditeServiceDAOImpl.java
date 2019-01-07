package it.realttechnology.magazzino.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.entity.VenditeEntity;
import it.realttechnology.magazzino.repository.VenditeRepository;

@Service
public class VenditeServiceDAOImpl implements VenditeServiceDAO
{
    @Autowired
    VenditeRepository venditeRepository;
    
	@Override
	public Optional<VenditeEntity> findById(int id)
	{
		return venditeRepository.findById(id);
	}

	@Override
	public Optional<VenditeEntity> find(VenditeEntity vendita) 
	{
		// TODO Auto-generated method stub
		return venditeRepository.findById(vendita.getId());
	}

	@Override
	public Iterable<VenditeEntity> findByCliente(ClientiEntity cliente) 
	{
		// TODO Auto-generated method stub
		return venditeRepository.findByCliente(cliente);
	}

	@Override
	public Iterable<VenditeEntity> findByProdotto(ProdottiEntity prodotto)
	{
		// TODO Auto-generated method stub
		return venditeRepository.findByProdotto(prodotto);
	}

	@Override
	public Iterable<VenditeEntity> findByPrezzoRange(double prezzoMin,double prezzoMax) 
	{
		return venditeRepository.findByPrezzoRange(prezzoMin,prezzoMax);
	}
	@Override
	public Iterable<VenditeEntity> findByPrezzoMinor(double prezzoMajor) 
	{
		return venditeRepository.findByPrezzoMinor(prezzoMajor);
	}
	@Override
	public Iterable<VenditeEntity> findByPrezzoMajor(double prezzoMinor) 
	{
		return venditeRepository.findByPrezzoMajor(prezzoMinor);
	}
	@Override
	public Optional<VenditeEntity> create(VenditeEntity vendita) 
	{
		
		VenditeEntity ve = null;
		
		
		try
		{
		 ve = venditeRepository.save(vendita);
		}
		
		catch(Exception e)
		{	
		  return Optional.ofNullable(ve);
		}
		
		return Optional.of(ve);
	}

	@Override
	public Optional<VenditeEntity> update(VenditeEntity vendita) 
	{

		VenditeEntity ve = null;
		
		try
		{
	   	  ve = venditeRepository.save(vendita);
		}
		
		catch(Exception e)
		{	
		  return Optional.ofNullable(ve);
		}
		
		return Optional.of(ve);
		
	}

	@Override
	public boolean delete(VenditeEntity vendita)
	{
		
		try
		{
			venditeRepository.delete(vendita);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deleteById(int id)
	{
		try
		{
			venditeRepository.deleteById(id);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public Iterable<VenditeEntity> findAll()
	{
		// TODO Auto-generated method stub
		return venditeRepository.findAll();
	}

}
