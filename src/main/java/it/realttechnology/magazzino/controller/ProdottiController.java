package it.realttechnology.magazzino.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntityForUpdate;
import it.realttechnology.magazzino.repository.ProdottiRepository;
import it.realttechnology.magazzino.services.ProdottiServiceDAOImpl;


@RestController
@RequestMapping("/services/prodotti")
public class ProdottiController 
{

//da sostituire con il serivizio!!!
	
	@Autowired
    ProdottiServiceDAOImpl prodottiService;
	 @RequestMapping(method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<ProdottiEntity> findAll()
	 {
		 List<ProdottiEntity> prodottiEntities = (List<ProdottiEntity>) prodottiService.findAll();
		
		 if(prodottiEntities.size() == 0)
		   {
			   for(int i = 0; i<100; i++)
			   {
				ProdottiEntity prodottoEntity = new ProdottiEntity();
			    prodottoEntity.setDescrizione("DESCRIZIONE " + i);
			    prodottoEntity.setPrezzo(3 * i * 100.34f);
			    prodottoEntity.setQuantita(i + 10);
			    prodottoEntity.setNome("NOME " + i);
				prodottiService.create(prodottoEntity);
			  
		   }
		   }
		   else
		   {
			   
			   for(int i = 0; i<prodottiEntities.size(); i++)
			   {
				ProdottiEntity prodottoEntity = prodottiEntities.get(i);

			    prodottoEntity.setNome("NOME UPDATE " + i);
				prodottiService.update(prodottoEntity);
			   }
		   }
	      return prodottiService.findAll();
	}  
    
	 @RequestMapping(value="/p", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<ProdottiEntity> findAllWithoutVendite()
	 {
		// return new ArrayList<ProdottiEntity>();
		return prodottiService.findAllWithoutForeigns();
	 }
	
     @RequestMapping(value = "/n={name}", method = RequestMethod.GET)
	 @ResponseBody
	 public Optional<ProdottiEntity> findByName(@PathVariable("name") String name) 
	 {   
  	    return prodottiService.findByNome(name);		  
	 }
     @RequestMapping(value = "/dl={descrizione}", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<ProdottiEntity> findByDescrizioneLike(@PathVariable("descrizione") String descrizione) 
	 {   
  	    return prodottiService.findByDescrizioneContaining(descrizione);		  
	 }
     @RequestMapping(value = "or/nl={nome}&dl={descrizione}", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<ProdottiEntity> findByNameOrDescrizioneLike(@PathVariable("nome") String nome, @PathVariable("descrizione") String descrizione) 
	 {   
  	    return prodottiService.findByNomeContainingOrDescrizioneContaining(nome,descrizione);		  
	 }
     @RequestMapping(value = "/nl={name}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ProdottiEntity> findByNameLike(@PathVariable("name") String name) 
   	 {   
     	    return prodottiService.findByNomeContaining(name);		  
   	 }
     @RequestMapping(value = "/v/{id}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ProdottiEntity> findByVenditeId(@PathVariable("id") int id) 
   	 {   
     	    return prodottiService.findByVenditeId(id);		  
   	 }
     @RequestMapping(value = "/v/cv/{id}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ProdottiEntity> findByVenditeCliente(@PathVariable("id") int id) 
   	 {   
    	 ClientiEntity c = new ClientiEntity();
    	 c.setId(id);
     	 return prodottiService.findByVenditeCliente(c);		  
   	 }
     @RequestMapping(value = "/v/cc/{id}", method = RequestMethod.GET)
   	 @ResponseBody
   	 public Iterable<ProdottiEntity> findByVenditeClienteWithoutVendite(@PathVariable("id") int id) 
   	 {   
    	 ClientiEntity c = new ClientiEntity();
    	 c.setId(id);
     	 return prodottiService.findByVenditeClienteWithoutVendite(c);		  
   	 }
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 @ResponseBody
	 public Optional<ProdottiEntity> findById(@PathVariable("id") int id) 
	 {   
  	    return prodottiService.findById(id);		  
	 }
     
     @RequestMapping(method = RequestMethod.POST)
	 @ResponseStatus(HttpStatus.CREATED)
	 @ResponseBody
	  public int create(@RequestBody ProdottiEntityForUpdate prodotto)
	  {
    	   ProdottiEntity prodottoEntity = new ProdottiEntity();
    	   prodottoEntity.setDescrizione(prodotto.getDescrizione());	 
    	   prodottoEntity.setNome(prodotto.getNome());	 
    	   prodottoEntity.setPrezzo(prodotto.getPrezzo());	 
    	   prodottoEntity.setQuantita(prodotto.getQuantita()); 
    	   
	       return prodottiService.create(prodottoEntity).get().getId();   
      }
     
     @RequestMapping(method = RequestMethod.PUT)
	 @ResponseStatus(HttpStatus.OK)
     @ResponseBody
	 public int update(@RequestBody ProdottiEntityForUpdate prodotto)
	 {
    	 //NEED TO WORK ONN THE ORM REFERENCE!!!
    	 ProdottiEntity prodottoEntity = prodottiService.findById(prodotto.getId()).get();
    	 
    	 if(prodotto.getNome()      != null) prodottoEntity.setNome(prodotto.getNome());
    	 if(prodotto.getDescrizione() != null)  prodottoEntity.setDescrizione(prodotto.getDescrizione());
    	 if(prodotto.getQuantita()  != null)  prodottoEntity.setQuantita(prodotto.getQuantita());
    	 if(prodotto.getPrezzo()  != null)  prodottoEntity.setPrezzo(prodotto.getPrezzo());
    	 
    	 return prodottiService.update(prodottoEntity).get().getId();
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 @ResponseStatus(HttpStatus.OK)
	 @ResponseBody
	 public boolean delete(@PathVariable("id") int id) 
	  {
		   return prodottiService.deleteById(id);
	  }
	 
	 
}
