package it.realttechnology.magazzino.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.realttechnology.magazzino.entity.PersonaleEntity;

@Service
public class UsersAuthenticationServiceImpl implements UsersAuthenticationService
{
	
	@Autowired
	PersonaleServiceDAOImpl usersDAOServiceImpl;

	@Override
	public PersonaleEntity loginUsername(String userName) throws UsernameNotFoundException
	{
		Optional<PersonaleEntity> usersEntity = usersDAOServiceImpl.findByUsername(userName);
		
		if (!usersEntity.isPresent())
		{
			throw new UsernameNotFoundException(userName);
		}
		
		return usersEntity.get();
	}

	@Override
	public PersonaleEntity loginToken(String token) throws UsernameNotFoundException
	{
		Optional<PersonaleEntity> usersEntity = usersDAOServiceImpl.findByToken(token);
		
		if (!usersEntity.isPresent())
		{
			throw new UsernameNotFoundException(token);
		}
		
		return usersEntity.get();
		
	}

	@Override
	public void logout(String token)  throws UsernameNotFoundException
	{
		
		Optional<PersonaleEntity> usersEntity = usersDAOServiceImpl.findByToken(token);
		
		if (!usersEntity.isPresent())
		{
			throw new UsernameNotFoundException(token);
		}
		
		usersEntity.get().setToken(null);
		
		usersDAOServiceImpl.update(usersEntity.get());
		
	}
	
	@Override
	public void setUserToken(String username, String token) throws UsernameNotFoundException
	{
		Optional<PersonaleEntity> usersEntity = usersDAOServiceImpl.findByToken(token);
			
			if (!usersEntity.isPresent())
			{
				throw new UsernameNotFoundException(username);
			}
			
			usersEntity.get().setToken(token);
	        
	        usersDAOServiceImpl.update(usersEntity.get());
	}

}
