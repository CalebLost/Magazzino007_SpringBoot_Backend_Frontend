package it.realttechnology.magazzino.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.realttechnology.magazzino.entity.PersonaleEntity;

public interface PersonaleRepository extends CrudRepository<PersonaleEntity,Integer>
{
   Optional<PersonaleEntity> findByUsername(String username);

   Optional<PersonaleEntity> findByToken(String token);
}
