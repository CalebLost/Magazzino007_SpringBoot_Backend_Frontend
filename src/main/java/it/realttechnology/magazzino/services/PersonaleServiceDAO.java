package it.realttechnology.magazzino.services;

import java.util.Optional;

import it.realttechnology.magazzino.entity.PersonaleEntity;

public interface PersonaleServiceDAO 
{
	Optional<PersonaleEntity> create(PersonaleEntity personale);
	
	Optional<PersonaleEntity> update(PersonaleEntity personale);
	
	Iterable<PersonaleEntity> findAll();

	boolean delete(PersonaleEntity personale);
	
	boolean deleteById(int id);

	Optional<PersonaleEntity> findById(int id);

	Optional<PersonaleEntity> findByUsername(String username);
}
