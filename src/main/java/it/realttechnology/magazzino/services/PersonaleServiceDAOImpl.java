package it.realttechnology.magazzino.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.realttechnology.magazzino.entity.PersonaleEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.repository.PersonaleRepository;

@Service
public class PersonaleServiceDAOImpl implements PersonaleServiceDAO 
{

	@Autowired
	PersonaleRepository personaleRepository;
	@Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	    
	  
		

	@Override
	public Optional<PersonaleEntity> create(PersonaleEntity personale)
	{
	     PersonaleEntity p = null;
		
		try
		{
			personale.setPassword(bCryptPasswordEncoder.encode(personale.getPassword()));
		    p = personaleRepository.save(personale);
		}
		catch(Exception e)
		{
			return Optional.ofNullable(p);
		}
		
		return Optional.of(p);
	}

	@Override
	public Optional<PersonaleEntity> update(PersonaleEntity personale) 
	{

	     PersonaleEntity p = null;
		
		try
		{
		    p = personaleRepository.save(personale);
		}
		catch(Exception e)
		{
			return Optional.ofNullable(p);
		}
		
		return Optional.of(p);
	}

	@Override
	public Iterable<PersonaleEntity> findAll()
	{
		return personaleRepository.findAll();
	}

	@Override
	public boolean delete(PersonaleEntity personale)
	{
		// TODO Auto-generated method stub
		try
		{
			personaleRepository.delete(personale);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deleteById(int id) {
		try
		{
		  personaleRepository.deleteById(id);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}
	@Override
	public Optional<PersonaleEntity> findById(int id)
	{
		
		 return personaleRepository.findById(id);
	}
	
	@Override
	public Optional<PersonaleEntity> findByUsername(String username)
	{
		
		 return personaleRepository.findByUsername(username);
	}

	public Optional<PersonaleEntity> findByToken(String token)
	{
		 return personaleRepository.findByToken(token);
	}

}
