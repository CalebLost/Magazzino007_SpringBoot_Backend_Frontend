package it.realttechnology.magazzino.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.entity.VenditeEntity;
import it.realttechnology.magazzino.repository.VenditeRepository;
import it.realttechnology.magazzino.services.VenditeServiceDAOImpl;


@RestController
@RequestMapping("/services/vendite")
public class VenditeController 
{

//da sostituire con il serivizio!!!
	 @Autowired
     VenditeServiceDAOImpl venditeService;
	 @RequestMapping(method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<VenditeEntity> findAll()
	 {
		 List<VenditeEntity> venditeEntities = (List<VenditeEntity>) venditeService.findAll();
		
		 if(venditeEntities.size() == 0)
		 {
			 ClientiEntity cliente1 = new ClientiEntity();
			 cliente1.setId(1);
			 ClientiEntity cliente2 = new ClientiEntity();
			 cliente2.setId(2);
			 
			 ProdottiEntity prodotto1 = new ProdottiEntity();
			 prodotto1.setId(1);
			
			
			 
			 for(int i=1;i<=100;i++)
			 {
				ProdottiEntity prodotto2 = new ProdottiEntity();
				ClientiEntity cliente = new ClientiEntity();
				cliente.setId(1);
				VenditeEntity ve = new VenditeEntity();
				ve.setData(new Date());
				ve.setQuantita(i * 10);
				ve.setPrezzo(34.34 * i);
				ve.setCliente(i % 2 == 0 ? cliente1 : cliente2);
				prodotto2.setId(i);
				ve.setProdotto(i % 2 == 0 ? prodotto2 : prodotto1);
				venditeService.create(ve);
			 }
			 
		 }
		
	     return venditeService.findAll();
	}  
    
	   
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 @ResponseBody
	 public Optional<VenditeEntity> findOne(@PathVariable("id") int id) 
	 {   
  	    return venditeService.findById(id);		  
	 }
     
     @RequestMapping(method = RequestMethod.POST)
	 @ResponseStatus(HttpStatus.CREATED)
	 @ResponseBody
	  public int create(@RequestBody VenditeEntity venditeEntity)
	  {
	       return venditeService.create(venditeEntity).get().getId();   
      }
     
     @RequestMapping(method = RequestMethod.PUT)
	 @ResponseStatus(HttpStatus.OK)
     @ResponseBody
	 public int update(@RequestBody VenditeEntity venditeEntity)
	 {
    	 return venditeService.update(venditeEntity).get().getId();
	 }
     
     @RequestMapping(value = "/p/{id}", method = RequestMethod.GET)
   	 @ResponseStatus(HttpStatus.OK)
   	 public Iterable<VenditeEntity> findByIdProdotto(@PathVariable( "id" ) int id)
   	 {
    	 ProdottiEntity prodotto = new ProdottiEntity();
    	 prodotto.setId(id);
       	 return venditeService.findByProdotto(prodotto);
   	 }
     @RequestMapping(value = "/c/{id}", method = RequestMethod.GET)
   	 @ResponseStatus(HttpStatus.OK)
   	 public Iterable<VenditeEntity> findByIdCliente(@PathVariable( "id" ) int id)
   	 {
    	 ClientiEntity cliente = new ClientiEntity();
    	 cliente.setId(id);
       	 return venditeService.findByCliente(cliente);
   	 }
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 @ResponseStatus(HttpStatus.OK)
	 public void delete(@PathVariable("id") int id) 
	  {
		   venditeService.deleteById(id);
	  }
	 @RequestMapping(value = "/pr/r/{priceMin}/{priceMax}", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<VenditeEntity> findByPriceRange(@PathVariable("priceMin") double prezzoMin,@PathVariable("priceMax") double prezzoMax) 
	 {   
  	    return venditeService.findByPrezzoRange(prezzoMin,prezzoMax);		  
	 }
	 @RequestMapping(value = "/pr/l/{priceMax}", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<VenditeEntity> findByPriceMinor(@PathVariable("priceMax") double prezzoMax) 
	 {   
  	    return venditeService.findByPrezzoMinor(prezzoMax);		  
	 }
	 @RequestMapping(value = "/pr/m/{priceMin}", method = RequestMethod.GET)
	 @ResponseBody
	 public Iterable<VenditeEntity> findByPriceMajor(@PathVariable("priceMin") double prezzoMin) 
	 {   
  	    return venditeService.findByPrezzoMajor(prezzoMin);		  
	 }
}
