package it.realttechnology.magazzino.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ClientiEntityForUpdate;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.repository.ClientiRepository;
import it.realttechnology.magazzino.services.ClientiServiceDAOImpl;

@RestController
@RequestMapping("/services/clienti")
@JsonIgnoreProperties(value = "vendite")
public class ClientiController 
{

//da sostituire con il servizio!!!
	 @Autowired
     ClientiServiceDAOImpl clientiService;
	 @RequestMapping(method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<ClientiEntity> findAll()
	 {
		 List<ClientiEntity> clientiEntities = (List<ClientiEntity>) clientiService.findAll();
		
		 if(clientiEntities.size() == 0)
		   {
			   for(int i = 0; i<100; i++)
			   {
				ClientiEntity clienteEntity = new ClientiEntity();
				clienteEntity.setNome("NOME " + i);
				clienteEntity.setIndirizzo(" Via numero " + i);
				clienteEntity.setTelefono(349 + "" + (2394954 - i * 100) + "");
				clientiService.create(clienteEntity);
			  
		   }
		  }
		
	      return clientiService.findAll();
	}  
    
	 @RequestMapping(value="/c", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<ClientiEntity> findAllWithoutVendite()
	 {
		// return entityManager.createNativeQuery(clientiRepository.FIND_ALL_WITHOUT_FOREIGNS).getResultList();
		 return clientiService.findAllWithoutVendite();
	 }
	 
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 @ResponseBody
	 public Optional<ClientiEntity> findOne(@PathVariable("id") int id) 
	 {   
  	    return clientiService.findById(id)	  ;
	 }
     
     @RequestMapping(value = "/nl={name}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ClientiEntity> findByNameLike(@PathVariable("name") String name) 
   	 {   
     	    return clientiService.findByNomeContaining(name);		  
   	 }
     
     @RequestMapping(value = "/n={name}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ClientiEntity> findByName(@PathVariable("name") String name) 
   	 {   
     	    return clientiService.findByNomeContaining(name);		  
   	 }
     
     @RequestMapping(value = "/v/{id}", method = RequestMethod.GET)
     @ResponseBody
     public Iterable<ClientiEntity> findByVenditeId(@PathVariable("id") int id) 
     {   
        	    return clientiService.findByVenditeId(id);		  
     }
     
     @RequestMapping(value = "/v/pv/{id}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ClientiEntity> findByVenditeProdotto(@PathVariable("id") int id) 
   	 {   
    	 ProdottiEntity p = new ProdottiEntity();
    	 p.setId(id);
     	 return clientiService.findByVenditeProdotto(p);		  
   	 }
     
     @RequestMapping(value = "/v/pp/{id}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ClientiEntity> findByVenditeProdottoWithoutVendite(@PathVariable("id") int id) 
   	 {   
    	 ProdottiEntity p = new ProdottiEntity();
    	 p.setId(id);
     	 return clientiService.findByVenditeProdottoWithoutVendite(p);		  
   	 }
     
     @RequestMapping(method = RequestMethod.POST)
	 @ResponseStatus(HttpStatus.CREATED)
	 @ResponseBody
	  public int create(@RequestBody ClientiEntityForUpdate cliente)
	  {
    	   ClientiEntity clienteEntity = new ClientiEntity();
    	   clienteEntity.setNome(cliente.getNome());
      	   clienteEntity.setIndirizzo(cliente.getIndirizzo());
      	   clienteEntity.setTelefono(cliente.getTelefono());
	       return clientiService.create(clienteEntity).get().getId();   
      }
     
     @RequestMapping(method = RequestMethod.PUT)
	 @ResponseStatus(HttpStatus.OK)
     @ResponseBody
	 public int update(@RequestBody ClientiEntityForUpdate cliente)
	 { 
    	 ClientiEntity clienteEntity = clientiService.findById(cliente.getId()).get();
    	 if(cliente.getNome()      != null) clienteEntity.setNome(cliente.getNome());
    	 if(cliente.getIndirizzo() != null) clienteEntity.setIndirizzo(cliente.getIndirizzo());
    	 if(cliente.getTelefono()  != null) clienteEntity.setTelefono(cliente.getTelefono());
    	 return clientiService.update(clienteEntity).get().getId();
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 @ResponseStatus(HttpStatus.OK)
	 @ResponseBody
	 public boolean delete(@PathVariable("id") int id) 
	  {
		   return clientiService.deleteById(id);
	  }
	 
	
	 
	 
}
