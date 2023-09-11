package it.realttechnology.magazzino.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import org.apache.commons.codec.binary.Base64;
//import org.apache.http.HttpStatus;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.realttechnology.magazzino.configuration.MagazzinoConfigurator;
import it.realttechnology.magazzino.configuration.Oauth2Configurator;
import it.realttechnology.magazzino.entity.ClientiEntity;
import it.realttechnology.magazzino.entity.ClientiEntityForCreate;
import it.realttechnology.magazzino.entity.ClientiEntityForUpdate;
import it.realttechnology.magazzino.entity.ProdottiEntity;
import it.realttechnology.magazzino.security.TokenUtils;
import it.realttechnology.magazzino.services.ClientiServiceDAOImpl;
import it.realttechnology.magazzino.services.ProdottiServiceDAOImpl;
import it.realttechnology.magazzino.services.UsersAuthenticationService;
import it.realttechnology.magazzino.services.VenditeServiceDAOImpl;


@Controller
class IndexController
{
    @RequestMapping("/")
    public String index() {
        return "redirect:views/login";
    }
}
@Controller
class MagazzinoErrorController implements ErrorController 
{
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,Model model) 
    {
          
    	  //  Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		   Object status = request.getAttribute("javax.servlet.error.status_code");
    	   
    	    String errorMsg = "Application Fault... ";
    	    
    	    if (status != null)
    	    {
    	    	errorMsg+="HTTP ERROR CODE: " + status.toString();
    	    }
    	    
    	    model.addAttribute("errorMsg", errorMsg);
    	    
    	    //custom error page if needed
    	    if (status != null)
    	    {
    	        Integer statusCode = Integer.valueOf(status.toString());
    	        HttpStatus httpStatusCode = HttpStatus.resolve(statusCode);
    	        
    	        if(httpStatusCode == null)
    	        {
    	          return "error";	
    	        }
    	        
    	        if(httpStatusCode == HttpStatus.NOT_FOUND) 
    	        {
    	            return "error";
    	        }
    	        
    	        if(httpStatusCode == HttpStatus.INTERNAL_SERVER_ERROR) 
    	        {
    	            return "error";
    	        }
    	    }
    	  
    	
        return "error";
    }
    
    @Override
    public String getErrorPath() {
        return "/error";
   }
}

@Controller
@RequestMapping("/views")
public class MagazzinoMVCViews
{
	enum ViewType
	{
		 VIEW_PRODOTTI_CLIENTE,
		 VIEW_PRODOTTI
	}
	@Autowired
	VenditeServiceDAOImpl venditeService;
	@Autowired
	ProdottiServiceDAOImpl prodottiService;
	@Autowired
	ClientiServiceDAOImpl clientiService;
	@Autowired
	UsersAuthenticationService userAuthenticationService;
	@Autowired
	ServletContext context; 
	
	
	
	@Autowired
	MagazzinoConfigurator configurator;
	
	@Value("${login.avatar}")
	private String loginAvatar;
	@Value("${android.apk}")
	private String androidapk;
	@Value("${android.appname}")
	private String androidappname;
	@Value("${views.datatables.language}")
	private String viewsDataTablesLanguage;
	
	
	@Autowired
	private Oauth2Configurator oauth2configurator;
	
	private static final boolean USE_CONFIG;
	private static final String TABLE_LANG;

	static
	{
		USE_CONFIG = true;
		TABLE_LANG = "it_IT";
		
	}
	
	//utile per caricare info post wiring
	@PostConstruct
	private void initInjection()
	{
		MVCUtils.setConfig(configurator, USE_CONFIG ? viewsDataTablesLanguage : TABLE_LANG);
	}
	

	@GetMapping("/login")
	//DOES FORWARD ON REQUESTS
	//when operating with jsp view is possible to interact with the datamodel passing it as parameter and populating it
	public String login(Model model, @RequestParam(value="message", required=false, defaultValue="Login") String message)
	{
	   model.addAttribute("titolo", MVCUtils.getLogin().getTitolo());
	   model.addAttribute("avatar", loginAvatar);
	   model.addAttribute("errormessage", MVCUtils.getLogin().getErrore());
	   model.addAttribute("labeluser", MVCUtils.getLogin().getNome());
	   model.addAttribute("labelpassword",  MVCUtils.getLogin().getPassword());
	   model.addAttribute("labelrememberme",  MVCUtils.getLogin().getRicordati());
	   model.addAttribute("buttonsubmit",  MVCUtils.getLogin().getEntra());
	   model.addAttribute("labelforgotpassword",  "Password dimenticata?");
	   model.addAttribute("labelmissingusername",  MVCUtils.getLogin().getNomemancante());
	   model.addAttribute("labelmissingpassword",  MVCUtils.getLogin().getPasswordmancante());
	   model.addAttribute("linkforgotpassword", "#");
	   model.addAttribute("cancel",  MVCUtils.getLogin().getCancella());	 
	   model.addAttribute("apk",androidapk);
	   model.addAttribute("appname",androidappname);
	
	   addLoggedOperationsModel(model);
	   
	  
//OAUTH STUFFS
	  model.addAttribute("googleLoginUrl", oauth2configurator.getLoginUrl());
	  model.addAttribute("googleLoginText",MVCUtils.getLogin().getGoogleSSO());
//END-OAUTHSTUFFS	
	   
	   return "login";
	}
	
	public void addLoggedOperationsModel(Model model)
	{
	
	   model.addAttribute("linklogout",oauth2configurator.getLogoutView());
	   model.addAttribute("labellogout",MVCUtils.getLogin().getEsci());
	   model.addAttribute("linkprodotti","/views/personale/prodotti/p/0/10/0/3");
	   model.addAttribute("labelprodotti",MVCUtils.getProdottiTitle());
	   model.addAttribute("linkclienti","/views/personale/clienti");
	   model.addAttribute("labelvendite",MVCUtils.getVenditeTitle());
	   model.addAttribute("linkvendite","/views/personale/vendite");
	   model.addAttribute("labelclienti",MVCUtils.getClientiTitle());
	   model.addAttribute("googlelogouturl",oauth2configurator.getLogoutUrl());
       model.addAttribute("hosturl", oauth2configurator.getHostBaseUrl());
	}
	//VENDITE BEGIN//
	@GetMapping("/personale/vendite")
	public String vendite(Principal principal,Model model)
	{
	  
		//can use model or modelandview!!! (int he view use a page
	   addVenditeModelGridLabels(principal,model);
	   model.addAttribute("venditeTitle", MVCUtils.getVenditeTitle());
	  // model.addAttribute("vendite", venditeService.findAll());
	   model.addAttribute("venditeservice","/services/vendite");
	   model.addAttribute("venditeserviceauthtokenheader","Authorization");
	   addLoggedOperationsModel(model);
	   return "venditeView";
	}

	
	@GetMapping("/personale/vendite/p/{id}")
	public String venditeByIdProdotto(Principal principal,Model model,@PathVariable("id") int id)
	{
	   
		//can use model or modelandview!!! (int he view use a page
	   ProdottiEntity prodottoEntity = new ProdottiEntity();
	   prodottoEntity.setId(id);
	   model.addAttribute("venditeTitle", MVCUtils.getVenditeProdottoTitle());
	   addVenditeModelGridLabels(principal,model);
	  // model.addAttribute("vendite", venditeService.findByProdotto(prodottoEntity));
	   model.addAttribute("venditeservice","/services/vendite/p/"+id);
	   addLoggedOperationsModel(model);
	   return "venditeView";
	}
	@GetMapping("/personale/vendite/c/{id}")
	public String venditeByIdCliente(Principal principal,Model model,@PathVariable("id") int id)
	{

	   model.addAttribute("venditeTitle", MVCUtils.getVenditeClienteTitle());
	   addVenditeModelGridLabels(principal,model);
	   model.addAttribute("venditeservice","/services/vendite/c/"+id);
	   addLoggedOperationsModel(model);

	   return "venditeView";
	}
	 @GetMapping(value = "/personale/vendite/pr/r/{priceMin}/{priceMax}")
	 public String findByPriceRange(Principal principal,Model model,@PathVariable("priceMin") double prezzoMin,@PathVariable("priceMax") double prezzoMax) 
	 {   
		addVenditeModelGridLabels(principal,model);
		  model.addAttribute("venditeTitle", MVCUtils.getVenditeTitle());
		 model.addAttribute("venditeService","/services/vendite/pr/r/"+prezzoMin+"/"+prezzoMax);
		 addLoggedOperationsModel(model);
 	    return "venditeView";
	 }
	 @GetMapping(value = "/personale/vendite/pr/l/{priceMax}")
	 public String  findByPriceMinor(Principal principal,Model model,@PathVariable("priceMax") double prezzoMax) 
	 {   
		 addVenditeModelGridLabels(principal,model);
		  model.addAttribute("venditeTitle", MVCUtils.getVenditeTitle());
		 model.addAttribute("venditeService","/services/vendite/pr/l/"+prezzoMax);
		 addLoggedOperationsModel(model);
	 	 return "venditeView";		  
	 }
	 @GetMapping(value = "/personale/vendite/pr/m/{priceMin}")
	 public String  findByPriceMajor(Principal principal,Model model,@PathVariable("priceMin") double prezzoMin) 
	 {   
		 addVenditeModelGridLabels(principal,model);
		  model.addAttribute("venditeTitle", MVCUtils.getVenditeTitle());
		// model.addAttribute("vendite", venditeService.findByPrezzoMajor(prezzoMin));
		 model.addAttribute("venditeService","/services/vendite/pr/l/"+prezzoMin);
		 addLoggedOperationsModel(model);
	 	 return "venditeView";	  
	 }
	 
	 private void addVenditeModelGridLabels(Principal principal, Model model)
	 {
		 String userName  = principal.getName();
		 String authtoken = "";
		 
		 try
		 {
		   authtoken = userAuthenticationService.getUserToken(userName);
		 }
		 catch(Exception e)
		 {
		   authtoken = TokenUtils.generateToken(userName);
		   authtoken = TokenUtils.TOKEN_GOOGLE_PREFIX + authtoken;
		 }
		 
         model.addAttribute("venditeGridLabels", MVCUtils.getVenditeGridLabels());
		 model.addAttribute("venditeHeaders", MVCUtils.getVenditeHeaders());
		 model.addAttribute("venditeserviceauthtokenheader",TokenUtils.HEADER_STRING);
		 model.addAttribute("venditeserviceauthtokenvalue",TokenUtils.TOKEN_PREFIX + authtoken);
		 model.addAttribute("venditeservicetimeformatter",MVCUtils.getVenditeTimeFormatter());
		 model.addAttribute("venditeservicecurrency",MVCUtils.getVenditeCurrency());
		 model.addAttribute("venditeservicecurrencyprecision",MVCUtils.getVenditePrecision());
		 model.addAttribute("venditeservicecurrencyseparator1",MVCUtils.getVenditeSeparatorOne());
		 model.addAttribute("venditeservicecurrencyseparator2",MVCUtils.getVenditeSeparatorOne());
		
		 
	}
		//VENDITE END//
		//CLIENTI BEGIN//
	 @GetMapping(value = "/personale/clienti")
	 public String  findAllClientiWithoutVendite(Model model) 
	 {   
		 ClientiEntityForUpdate cliente = new ClientiEntityForUpdate(); // declareing
         addClientiModels(model, cliente);	
         addLoggedOperationsModel(model);
	 	 return "clientiView";	  
	 }


	 
	 /* 
	 @GetMapping(value = "/personale/clienti/{id}")
	 public String  findClienteWithoutVendite(Model model,@PathVariable("id") int id) 
	 {   
		 ClientiEntityForCreate clienteu = new ClientiEntityForCreate(); 
         model.addAttribute("cliente",clienteu);
         model.addAttribute("clienteFields", MVCUtils.getClassProperties("cliente")); 
         model.addAttribute("clientiGridLabels", MVCUtils.getClientiGridLabels());
		 model.addAttribute("clientiTitle", MVCUtils.getClientiTitle());
		 model.addAttribute("clientiCommands", MVCUtils.getClientiCommands());
		 model.addAttribute("clientiHeaders",  MVCUtils.getClientiHeaders());
		 List<ClientiEntity> clienti = new ArrayList<ClientiEntity>();
		 Optional<ClientiEntity> cliente = clientiService.findById(id);
		 if(cliente.isPresent())
		 {
		  clienti.add(cliente.get());
		 }
		 model.addAttribute("clienti", clienti);
		 addLoggedOperationsModel(model);
	 	 return "clientiView";	  
	 }
	 */
	
     @Secured("ROLE_ADMIN")
	 @RequestMapping(value = "/personale/clienti/{id}", method = RequestMethod.POST)
	 public String deleteClientePost(Model model,@PathVariable("id") int id,@Valid @ModelAttribute("cliente") ClientiEntityForUpdate cliente,BindingResult result) 
	 {  
		 
		 if(!cliente.getMethod_().equals("delete"))
		 {
            return "error";
		 }

	 	 return deleteCliente(model, id, cliente, result);	  
	 }

	 @Secured("ROLE_ADMIN")
	 @RequestMapping(value = "/personale/clienti/{id}", method = RequestMethod.DELETE)
	 public String deleteCliente(Model model,@PathVariable("id") int id,@Valid @ModelAttribute("cliente") ClientiEntityForUpdate cliente,BindingResult result) 
	 {  
		 
		 //check for errors, i can put validators also
		 if(result.hasErrors())
		 {
			 return "error";
		 }
		 
		 if(cliente.getId() != id)
		 {
			 return "error";
		 }
		 
		 if(!clientiService.deleteById(id))
		 {
			 return "error";
		 }
		 
	 	 return "redirect:/views/personale/clienti";	  
	 }
	 @Secured("ROLE_ADMIN")
	 @RequestMapping(value = "/personale/clienti", method = RequestMethod.POST)
	 public String postCliente(Model model,@Valid @ModelAttribute("cliente") ClientiEntityForCreate cliente,BindingResult result) 
	 {  	 
	    
		 if(cliente.getMethod_().equals("put"))
		 {
			//Logger.getLogger("postClienti").log(Level.INFO, "validation - failed create - should be  update");
		    ClientiEntityForUpdate up = new ClientiEntityForUpdate();
			up.setId(cliente.getId());
			up.setIndirizzo(cliente.getIndirizzo());
			up.setNome(cliente.getNome());
			up.setTelefono(cliente.getTelefono());
			return putCliente(model, up, result);
		}


         //check for errors, i can put validators also
		 if(result.hasErrors())
		 {
			 model.addAttribute("cliente", cliente);
			 return "error";
		 }
		 //create a new real entity based on the dto one
		 ClientiEntity clienteEntity = new ClientiEntity();
		 clienteEntity.setIndirizzo(cliente.getIndirizzo());
		 clienteEntity.setNome(cliente.getNome());
		 clienteEntity.setTelefono(cliente.getTelefono());
		 
		 if(!clientiService.create(clienteEntity).isPresent())
		 { 
			 model.addAttribute("cliente",cliente);
			 return "error";
		 }
		 
	 	 return "redirect:/views/personale/clienti";	  
	 }
	 @Secured("ROLE_ADMIN")
	 @RequestMapping(value = "/personale/clienti", method = RequestMethod.PUT)
	 public String putCliente(Model model,@Valid @ModelAttribute("cliente") ClientiEntityForUpdate cliente,BindingResult result) 
	 {  	 
	
		 //check for errors, i can put validators also
		 if(result.hasErrors())
		 {
			 model.addAttribute("cliente", cliente);
			 return "error";
		 }
		 Logger.getLogger("putClienti").log(Level.INFO, " "+cliente.getId());
		 Logger.getLogger("putClienti").log(Level.INFO, " "+cliente.getIndirizzo());
		 Logger.getLogger("putClienti").log(Level.INFO, " "+cliente.getNome());
		 Logger.getLogger("putClienti").log(Level.INFO, " "+cliente.getTelefono());
		 
		 Optional<ClientiEntity> clienteEntityOp = clientiService.findById(cliente.getId());
		 if(!clienteEntityOp.isPresent())
		 {
			
			 model.addAttribute("cliente", cliente);
			 return "error";
		 }
		 
		 ClientiEntity clienteEntity = clienteEntityOp.get();
		 
    	 if(cliente.getNome()      != null) clienteEntity.setNome(cliente.getNome());
    	 if(cliente.getIndirizzo() != null) clienteEntity.setIndirizzo(cliente.getIndirizzo());
    	 if(cliente.getTelefono()  != null) clienteEntity.setTelefono(cliente.getTelefono());
		 
		 if(!clientiService.update(clienteEntity).isPresent())
		 {
			 model.addAttribute("cliente", cliente);
			 return "error";
		 }
		 
	 	 return "redirect:/views/personale/clienti";	  
	 }
	 
	 private void addClientiModels(Model model, ClientiEntityForUpdate cliente) {
		 model.addAttribute("cliente", cliente); // adding in model to bind get-post object
         model.addAttribute("clienteFields", MVCUtils.getClassProperties("cliente")); 
         model.addAttribute("clientiGridLabels", MVCUtils.getClientiGridLabels());
		 model.addAttribute("clientiTitle", MVCUtils.getClientiTitle());
		 model.addAttribute("clientiCommands", MVCUtils.getClientiCommands());
		 model.addAttribute("clientiHeaders",  MVCUtils.getClientiHeaders());
		 model.addAttribute("clienti", clientiService.findAllWithoutVendite());
	}
	 
	//CLEINTI END//
	 
	//PRODOTTI BEGIN//
	 @GetMapping(value = "/personale/prodotti")
	 public String  findAllProductsWithoutVendite(Model model) 
	 {   
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findAllWithoutForeigns();
		
		 int num = prodotti.size();
    	 int pagine = 1;	
    	 int row = 0;
    	 int columns = 0;
		 addProdottiModelGridLabels(model, row, columns, num, pagine,null);
		 model.addAttribute("prodotti", prodotti);	
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }
	
	 @GetMapping(value = "/personale/prodotti/nl={nome}")
	 public String  findAllProductsWithoutVendite(Model model,@PathVariable("nome") String nome) 
	 {   
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByNomeContaining(nome);
		 int num = prodotti.size();
    	 int pagine = 1;	
    	 int row = 0;
    	 int columns = 0;
		 addProdottiModelGridLabels(model, row, columns, num, pagine,null);
		 model.addAttribute("prodotti", prodotti);	
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }
	 
     @GetMapping(value = "/personale/prodotti/dl={descrizione}")
	 public String findByDescrizioneLike(Model model, @PathVariable("descrizione") String descrizione) 
	 {   
    	 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByDescrizioneContaining(descrizione);			
    	 int num = prodotti.size();
    	 int pagine = 1;	
    	 int row = 0;
    	 int columns = 0;
		 addProdottiModelGridLabels(model, row, columns, num, pagine,null);
    	 model.addAttribute("prodotti", prodotti);
    	 addLoggedOperationsModel(model);
    	 return "prodottiView";
	 }
     
     @GetMapping(value = "/personale/prodotti/or/nl={nome}&dl={descrizione}")
	 public String findByNameOrDescrizioneLike(Model model, @PathVariable("nome") String nome, @PathVariable("descrizione") String descrizione) 
	 {   
    	 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByNomeContainingOrDescrizioneContaining(nome,descrizione);
    	 int num = prodotti.size();
    	 int pagine = 1;	
    	 int row = 0;
    	 int columns = 0;
		 addProdottiModelGridLabels(model, row, columns, num, pagine,null);
    	 model.addAttribute("prodotti", prodotti); addLoggedOperationsModel(model);
    	 return "prodottiView";		  
	 }
     
	 @GetMapping(value = "/personale/prodotti/p/{row}/{columns}")
	 public String  findAllProductsWithoutVenditePaging(Model model,@PathVariable("row") int row,@PathVariable("columns") int columns) 
	 {   	
		 int num = (int)((long)prodottiService.count());
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findAllWithoutForeigns(row,columns);
		 int pagine = (int) Math.ceil(((double)num / columns));
		 
		 addProdottiModelGridLabels(model, row, columns, num, pagine,"/views/personale/prodotti/p/{row}/"+columns);
		 
		 model.addAttribute("prodotti", prodotti);
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }

	 @GetMapping(value = "/personale/prodotti/p/{row}/{columns}/{win}/{wins}")
	 public String  findAllProductsWithoutVenditePaging(Model model,@PathVariable("row") int row,@PathVariable("win") int win,@PathVariable("wins") int wins,@PathVariable("columns") int columns) 
	 {   	
		 int num = (int)((long)prodottiService.count());
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findAllWithoutForeigns(row,columns);
		 int pagine = (int) Math.ceil(((double)num / columns));

		 
		 addProdottiModelGridLabels(model, row, columns, num, pagine, win,wins,ViewType.VIEW_PRODOTTI,"/views/personale/prodotti/p/{row}/"+columns+"/{win}/"+wins);
		 addLoggedOperationsModel(model);
		 model.addAttribute("prodotti", prodotti);	
	 	 return "prodottiView";	  
	 }

	
	 
	 @GetMapping(value = "/personale/prodotti/v/cc/{id}/{row}/{columns}")
	 public String  findByVenditeClienteWithoutVenditePaging(Model model,@PathVariable("id") int id,@PathVariable("row") int row,@PathVariable("columns") int columns) 
	 {   
		 addProdottiByIdClienteModelNoVPaging(model, id, row, columns);
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }
	 @GetMapping(value = "/personale/prodotti/v/cc/{id}/{row}/{columns}/{win}/{wins}")
	 public String  findByVenditeClienteWithoutVenditePaging(Model model,@PathVariable("id") int id,@PathVariable("row") int row,@PathVariable("win") int win,@PathVariable("wins") int wins,@PathVariable("columns") int columns) 
	 {   
		 addProdottiByIdClienteModelNoVPaging(model, id, row, columns,win, wins);
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }

	 
	 @GetMapping(value = "/personale/prodotti/v/cc/{id}")
	 public String  findByVenditeClienteWithoutVendite(Model model,@PathVariable("id") int id) 
	 {   
		 addProdottiByIdClienteModelNoV(model, id);	
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }
	 @GetMapping(value = "/personale/prodotti/v/cv/{id}")
	 public String  findByVenditeCliente(Model model,@PathVariable("id") int id) 
	 {   
		 addProdottiByIdClienteModel(model, id);
		 addLoggedOperationsModel(model);
	 	 return "prodottiView";	  
	 }
	
	private void addProdottiByIdClienteModelNoV(Model model, int id) 
	{
		 ClientiEntity cliente = new ClientiEntity();
		 cliente.setId(id);
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByVenditeClienteWithoutVendite(cliente);
		 int num = prodotti.size();
		 int pagine = 1;
		 int row = 0;
		 int columns = 0;
		 addProdottiModelGridLabels(model, row, columns, num, pagine,null);
		 model.addAttribute("prodotti", prodottiService.findByVenditeClienteWithoutVendite(cliente));
	}

	private void addProdottiByIdClienteModel(Model model, int id)
	{
		 ClientiEntity cliente = new ClientiEntity();
		 cliente.setId(id);	 
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByVenditeCliente(cliente);
		 int num = prodotti.size();
		 int pagine = 1;
		 int row = 0;  
		 int columns = 0;	 
		 addProdottiModelGridLabels(model, row, columns, num, pagine,null); 
		 model.addAttribute("prodotti", prodotti);
	}
	
	private void addProdottiByIdClienteModelNoVPaging(Model model, int id, int row, int columns) 
	{
		 ClientiEntity cliente = new ClientiEntity();
		 cliente.setId(id);
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByVenditeClienteWithoutVendite(cliente,row,columns);
		 int num = (int)(long)prodottiService.countByVenditeCliente(cliente);
		 int pagine = (int) Math.ceil(((double)num / columns));
		 addProdottiModelGridLabels(model, row, columns, num, pagine, "/views/personale/prodotti/v/cc/"+id+"/{row}/"+columns);
		 model.addAttribute("prodotti", prodotti);
	}
	
	private void addProdottiByIdClienteModelNoVPaging(Model model, int id, int row, int columns,int win, int wins) 
	{
		 ClientiEntity cliente = new ClientiEntity();
		 cliente.setId(id);
		 List<ProdottiEntity> prodotti = (List<ProdottiEntity>) prodottiService.findByVenditeClienteWithoutVendite(cliente,row,columns);
		 int num = (int)(long)prodottiService.countByVenditeCliente(cliente);
		 int pagine = (int) Math.ceil(((double)num / columns));
		 addProdottiModelGridLabels(model, row, columns, num, pagine, win,wins,ViewType.VIEW_PRODOTTI_CLIENTE,"/views/personale/prodotti/v/cc/"+id+"/{row}/"+columns+"/{win}/"+wins);
		 model.addAttribute("prodotti", prodotti);
	 }
	
	 private void addProdottiModelGridLabels(Model model, int row, int columns, int num, int pagine,String path)
	 {
			model.addAttribute("prodottiPager"  , MVCUtils.getProdottiPager(num,row,pagine,-1,-1,path));
			model.addAttribute("prodottiTitle"  , MVCUtils.getProdottiTitle());
			model.addAttribute("prodottiHeaders",  MVCUtils.getProdottiHeaders());
	 }
	 
	 private void addProdottiModelGridLabels(Model model, int row, int columns, int num, int pagine,int finestra,int finestre,ViewType viewType,String path)
	 {
			model.addAttribute("prodottiPager"  , MVCUtils.getProdottiPager(num,row,pagine,finestra,finestre,path));
		
			model.addAttribute("prodottiHeaders",  MVCUtils.getProdottiHeaders());
			switch(viewType)
			{
			  case VIEW_PRODOTTI_CLIENTE: model.addAttribute("prodottiTitle"  , MVCUtils.getProdottiClienteTitle());
			  break;
			  case VIEW_PRODOTTI: model.addAttribute("prodottiTitle"  , MVCUtils.getProdottiTitle());
			  break;
			  default:
			  break;
			}
	 }
	 
	
}
