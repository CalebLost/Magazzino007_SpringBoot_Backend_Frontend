package it.realttechnology.magazzino.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.realttechnology.magazzino.entity.RolesEntity;

public interface RolesRepository extends CrudRepository<RolesEntity,Integer>
{
  
	Optional<RolesEntity> findByRole(String role);
}
