package it.realttechnology.magazzino.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.PersonaleEntity;
import it.realttechnology.magazzino.entity.PersonaleEntityForCreate;
import it.realttechnology.magazzino.entity.PersonaleEntityForUpdate;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntityForUpdate;
import it.realttechnology.magazzino.entity.RolesEntity;
import it.realttechnology.magazzino.repository.ProdottiRepository;
import it.realttechnology.magazzino.repository.RolesRepository;
import it.realttechnology.magazzino.services.PersonaleServiceDAOImpl;
import it.realttechnology.magazzino.services.ProdottiServiceDAOImpl;



@RestController
@RequestMapping("/services/personale")
public class PersonaleController 
{

	 @Autowired
     PersonaleServiceDAOImpl personaleService;
	 @Autowired
     RolesRepository rolesRepository;
	 
	 
	 @RequestMapping(method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<PersonaleEntity> findAll()
	 {
		 List<PersonaleEntity> personaleEntities = (List<PersonaleEntity>) personaleService.findAll();
		
		 
		 if(personaleEntities.size() == 0)
		   {
			 Optional<RolesEntity> roleU = rolesRepository.findByRole("USER");
			 Optional<RolesEntity> roleA = rolesRepository.findByRole("ADMIN");
			 
			 RolesEntity roleUser = null;
			 RolesEntity roleAdmin = null;
			 
			 if(!roleU.isPresent())
			 {
				 roleUser = new RolesEntity();
				 roleUser.setRole("USER");
			     roleUser = rolesRepository.save(roleUser);
			    
			 }
			 else
			 {
				 roleUser = roleU.get();
				
			 }
			 if(!roleA.isPresent())
			 {
				 roleAdmin = new RolesEntity();
				 roleAdmin.setRole("ADMIN");
			     roleAdmin = rolesRepository.save(roleAdmin);
			    
			 }
			 else
			 {
				 roleAdmin = roleA.get();
				
			 }
			
			 
			 Set<RolesEntity> rolesALL = new HashSet<RolesEntity>();
			 Set<RolesEntity> rolesLIMITED = new HashSet<RolesEntity>();
			 rolesALL.add(roleUser);
			 rolesALL.add(roleAdmin);
			 rolesLIMITED.add(roleUser);
			   for(int i = 0; i<100; i++)
			   {
				
				PersonaleEntity personaleEntity = new PersonaleEntity();
			    personaleEntity.setNome("N " + i);
			    personaleEntity.setPassword("P " + i);
			    personaleEntity.setUsername("U " + i);
			    personaleEntity.setCognome("C " + i);
			    personaleEntity.setPersonaleroles(i < 5 ? rolesALL : rolesLIMITED);
				personaleService.create(personaleEntity);
			  
		   }
		   }
		  
	      return personaleService.findAll();
	}  
    
	
     @RequestMapping(method = RequestMethod.POST)
	 @ResponseBody
	  public  ResponseEntity<Integer>  create(@RequestBody PersonaleEntityForCreate personale)
	  {
    	    if(personaleService.findByUsername(personale.getUsername()).isPresent()) 	
    	    {
    	    	
    	    	return new ResponseEntity<Integer>(HttpStatus.FOUND);
    	    }
    	 
    	    PersonaleEntity personaleEntity = new PersonaleEntity();
    	    personaleEntity.setNome(personale.getNome());
		    personaleEntity.setPassword(personale.getPassword());
		    personaleEntity.setUsername(personale.getUsername());
		    personaleEntity.setCognome(personale.getCognome());
				   
	        return new ResponseEntity<Integer>(personaleService.create(personaleEntity).get().getId(),HttpStatus.CREATED);  
      }
     
     @RequestMapping(method = RequestMethod.PUT)
	 @ResponseStatus(HttpStatus.OK)
     @ResponseBody
	 public int update(@RequestBody PersonaleEntityForUpdate personale)
	 {
    	 //NEED TO WORK ONN THE ORM REFERENCE!!!
    	 PersonaleEntity personaleEntity = personaleService.findById(personale.getId()).get();
    	 
    	 if(personale.getNome()      != null) personaleEntity.setNome(personale.getNome());
    	 if(personale.getPassword() != null)  personaleEntity.setPassword(personale.getPassword());
    	 if(personale.getCognome()  != null)  personaleEntity.setCognome(personale.getCognome());
    	 if(personale.getUsername()  != null)  personaleEntity.setUsername(personale.getUsername());
    	 
    	 return personaleService.update(personaleEntity).get().getId();
	 }
     
     @RequestMapping(value = "/{id}/roles", method = RequestMethod.PUT)
	 @ResponseStatus(HttpStatus.OK)
     @ResponseBody
	 public ResponseEntity<Integer> updateRoles(@PathVariable("id") int id, @RequestBody Set<RolesEntity> roles)
	 {
    	 //NEED TO WORK ONN THE ORM REFERENCE!!!
    	 Optional<PersonaleEntity> personaleEntity = null;
    	 try
   	     { 
    		 personaleEntity = personaleService.findById(id);
    	
    	 if(!personaleEntity.isPresent())
    	 {
    	    return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
    	 }
    	 
    	    personaleEntity.get().setPersonaleroles(roles);
    	    personaleEntity = personaleService.update(personaleEntity.get());
    		
    	  }
    	 catch(Exception e)
    	 {
    		 return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR); 
    	 }
    	 
    	 return new ResponseEntity<Integer>(personaleEntity.get().getId(),HttpStatus.OK);
	 }
     
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 @ResponseStatus(HttpStatus.OK)
	 @ResponseBody
	 public boolean delete(@PathVariable("id") int id) 
	  {
		   return personaleService.deleteById(id);
	  }
	 
	 
}
