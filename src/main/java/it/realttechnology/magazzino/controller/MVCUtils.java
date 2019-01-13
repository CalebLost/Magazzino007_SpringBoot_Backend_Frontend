package it.realttechnology.magazzino.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import it.realttechnology.magazzino.configuration.MagazzinoConfigurator;



public class MVCUtils 
{
	
	final static List<String> gridCommonLabelsIT          = new ArrayList();
	final static List<String> gridProdottiHeadersIT        = new ArrayList();
	final static Pager gridProdottiPagerIT                 = new Pager();
	final static List<ClassProperty> clientePropertiesIT   = new ArrayList();
	final static List<Comando> comandiClientiIT            = new ArrayList<Comando>();
	final static List<String> gridVenditeHeadersIT             = new ArrayList();
	final static List<String> headersClientiIT             = new ArrayList();
	
	static MagazzinoConfigurator config;
	static String lang;

	public static void setConfig(MagazzinoConfigurator configurator,String language) 
	{
	  
	
		lang = language; 
		setConfig(configurator);
	}

	public static void setConfig(MagazzinoConfigurator config) 
	{
		MVCUtils.config = config;
		
		switch(lang)
		{
		case "it_IT":
			
		gridVenditeHeadersIT.clear();
		gridVenditeHeadersIT.add(config.getIt_it().getVendite().getHeaders().getId());
		gridVenditeHeadersIT.add(config.getIt_it().getVendite().getHeaders().getProdotto());
		gridVenditeHeadersIT.add(config.getIt_it().getVendite().getHeaders().getCliente());
		gridVenditeHeadersIT.add(config.getIt_it().getVendite().getHeaders().getPrezzo());
		gridVenditeHeadersIT.add(config.getIt_it().getVendite().getHeaders().getQuantita());
		gridVenditeHeadersIT.add(config.getIt_it().getVendite().getHeaders().getData());
		gridProdottiHeadersIT.clear();
		gridProdottiHeadersIT .add(config.getIt_it().getProdotti().getHeaders().getId());
		gridProdottiHeadersIT .add(config.getIt_it().getProdotti().getHeaders().getNome());
		gridProdottiHeadersIT .add(config.getIt_it().getProdotti().getHeaders().getDescrizione());
		gridProdottiHeadersIT .add(config.getIt_it().getProdotti().getHeaders().getQuantita());
		gridProdottiHeadersIT .add(config.getIt_it().getProdotti().getHeaders().getPrezzo());
		break;
		default:
			break;
		}
		
	}

	static
	{

		gridCommonLabelsIT.add("Cerca");
		gridCommonLabelsIT.add("Mostra _MENU_ records per pagina");
		gridCommonLabelsIT.add("Nessun record trovato");
		gridCommonLabelsIT.add("Nessun record disponibile");
		gridCommonLabelsIT.add("Pagina _PAGE_ di _PAGES_");
		gridCommonLabelsIT.add("Risultato filtrato da _MAX_ records totali");
		gridCommonLabelsIT.add("Precedente");
		gridCommonLabelsIT.add("Sucessiva");
		
		
		
		clientePropertiesIT.add(new ClassProperty("id","Id:"));
		clientePropertiesIT.add(new ClassProperty("nome","Nome:"));
		clientePropertiesIT.add(new ClassProperty("telefono","Telefono:"));
		clientePropertiesIT.add(new ClassProperty("indirizzo","Indirizzo:"));
		
		 comandiClientiIT.add(new Comando("VENDITE",null,null,"/views/personale/vendite/c/","get"));
		 comandiClientiIT.add(new Comando("PRODOTTI",null,null,"/views/personale/prodotti/v/cc/","get"));
		 comandiClientiIT.add(new Comando("AGGIUNGI" , "CONFERMA" , "ANNULLA","/views/personale/clienti","post"));
		 comandiClientiIT.add(new Comando("AGGIORNA" , "CONFERMA" , "ANNULLA","/views/personale/clienti","put"));
		 comandiClientiIT.add(new Comando("ELIMINA"  , "CONFERMA" , "ANNULLA","/views/personale/clienti/","delete"));
		 
		  headersClientiIT.add("ID");
		  headersClientiIT.add("NOME");
		  headersClientiIT.add("INDIRIZZO");
		  headersClientiIT.add("TELEFONO");
		  headersClientiIT.add("OPERAZIONI");
		  
		  gridProdottiPagerIT.setColonne(5);
		  gridProdottiPagerIT.setPrecedente("Precedente");
		  gridProdottiPagerIT.setSucessivo("Sucessivo");
		
	}
	
	
	 static List<String> getProdottiHeaders()
	 {
		 List<String> headers = null;
		 switch(lang)
		 {
		 case "it_IT":  
		  headers = gridProdottiHeadersIT;
		  break;
		 }
		 
		 return headers;
	}
	
	static List<String> getVenditeHeaders()
	 {
		 List<String> headers = null;
		 switch(lang)
		 {
		 case "it_IT":  
		   headers = gridVenditeHeadersIT;
		  break;
		 }
		 
		 return headers;
	}
	 
	static List<String> getClientiHeaders()
	 {
		 List<String> headers = null;
		 switch(lang)
		 {
		 case "it_IT":  
		     headers = headersClientiIT;
		  break;
		 }
		 
		 return headers;
	}
	  static List<Comando> getClientiCommands()
	 {
		 List<Comando> comandi = null;
		 switch(lang)
		 {
		 case "it_IT":  
			 comandi = comandiClientiIT;
		  break;
		 }
		 
		 return comandi;
	}
	 
	
	 static String getVenditeTitle()
	 {
		String title = "{TITLE}";
		
		 switch(lang)
		 {
		   case "it_IT":  
		    title =  config.getIt_it().getVendite().getTitolo();
		   break;
		   default:  
		   break;
		 }
		 
		 return title;
	}
	 
	 static String getProdottiTitle()
	 {
		String title = "{TITLE}";
		
		 switch(lang)
		 {
		   case "it_IT":  
		    title = config.getIt_it().getProdotti().getTitolo();
		   break;
		   default:  
		   break;
		 }
		 
		 return title;
	}
	 
	 public static String getProdottiClienteTitle() {
		 String title = "{TITLE}";
			
		 switch(lang)
		 {
		   case "it_IT":  
		    title = config.getIt_it().getProdotti().getClienteTitolo();
		   break;
		   default:  
		   break;
		 }
		 
		 return title;
		}
	 public static String getVenditeClienteTitle() {
		 String title = "{TITLE}";
			
		 switch(lang)
		 {
		   case "it_IT":  
		    title = config.getIt_it().getVendite().getClienteTitolo();
		   break;
		   default:  
		   break;
		 }
		 
		 return title;
		}
	static String getClientiTitle()
	 {
		String title = "{TITLE}";
		
		 switch(lang)
		 {
		   case "it_IT":  
		    title = "Clienti";
		   break;
		   default:  
		   break;
		 }
		 
		 return title;
	}
	 
 static List<ClassProperty> getClassProperties(String name)
	 {
		 List<ClassProperty> properties = new ArrayList();
		 
		 switch(lang)
		 {
		   case "it_IT":  
		    switch(name)
		    {
		    case "cliente":
		    	
		    	properties = clientePropertiesIT;
		    	
		    	break;
		    	default:
		    break;
		    }
		   break;
		   default:  
		   break;
		 }
		 
		 return properties;
	 }

public static List<String> getClientiGridLabels()
{
	List<String> gridLabels = null;
	
	switch(lang)
	{
	case "it_IT" :
		gridLabels = gridCommonLabelsIT;
		break;
	default: break;
	}
	
	return gridLabels;
}
public static List<String> getVenditeGridLabels()
{
	List<String> gridLabels = null;
	
	switch(lang)
	{
	case "it_IT" :
		gridLabels = gridCommonLabelsIT;
		break;
	default: break;
	}
	
	return gridLabels;
}
public static Pager getProdottiPager(int num, int row, int pagine, int finestra, int finestre,String path)
{
    Pager pager = null;
	
	switch(lang)
	{
	case "it_IT" :
		
		pager = gridProdottiPagerIT;
		pager.setPagine(new ArrayList<PagerPage>());
		
		if(finestre==-1 || finestre > (pagine / 2))
		{
			
		 for(int i=1;i<=pagine;i++)
		  {
			PagerPage pagina = new PagerPage();
			pagina.setId(i);
			pagina.setStatus((i-1) == row ? "active" : null);
			pagina.setPath(path == null ? "#" : path.replace("{row}", ""+(i-1)));
			pager.getPagine().add(pagina);
		  }
		 
		 pager.setPathPrecedente("#");
		 pager.setPathSucessivo("#");
		 
		}
		 else
		 {
			 int resto                   = pagine % finestre;
			 int paginePerFinestra       = pagine / finestre;	 
			 int restoNav                = row % paginePerFinestra;
			 int indicePaginaCorrente    = finestra;
			 int indicePaginaPrecedente  = indicePaginaCorrente == 0 ? -1 : (indicePaginaCorrente - 1);
			 int indicePaginaSucessiva   = indicePaginaCorrente < (finestre + (resto > 0 ? 1 : 0) - 1) ? (indicePaginaCorrente + 1) : -1;
			
			 int pageLen =  (finestra == finestre && resto > 0) ? resto :  paginePerFinestra;
		
			 
			 for(int ii=0;ii < pageLen; ii++)
			  {
				int i = (indicePaginaCorrente * paginePerFinestra) + ii;
				PagerPage pagina = new PagerPage();
				pagina.setId(i+1);
				pagina.setStatus(i == row ? "active" : null);
				String curPath = (path == null) ? "#" : path.replace("{row}", ""+i);
				curPath = curPath.equals("#") ? "#" : curPath.replace("{win}", ""+(finestra));
				pagina.setPath(curPath);
				pager.getPagine().add(pagina);
			  }
			 
			 String preP = path == null || indicePaginaPrecedente == -1 ? "#" : path.replace("{row}", ""+(indicePaginaPrecedente * paginePerFinestra));
			 preP = preP.equals("#") ? "#" : preP.replace("{win}", ""+(indicePaginaPrecedente));
			 String sucP = path == null || indicePaginaSucessiva == -1 ? "#" : path.replace("{row}", ""+(indicePaginaSucessiva * paginePerFinestra));
			 sucP = sucP.equals("#") ? "#" : sucP.replace("{win}", ""+(indicePaginaSucessiva));
			 pager.setPathPrecedente(preP);
			 pager.setPathSucessivo(sucP);
		 }
		break;
	default: break;
	}
	
	return pager;
}

public static String getVenditeTimeFormatter() 
{
	String timeFormatter = "YYYY-MM-DD HH:mm:ss";
	
	 switch(lang)
	 {
	   case "it_IT":  
		   timeFormatter = config.getIt_it().getVendite().getTimeformatter();
	   break;
	   default:  
	   break;
	 }
	 
	 return timeFormatter;
	
}

public static String getVenditeProdottoTitle()
{
	String result = "{TITLE}";
	 switch(lang)
	 {
	   case "it_IT":  
		   result = config.getIt_it().getVendite().getProdottoTitolo();
	   break;
	   default:  
	   break;
	 }
	 
	 return result;
}






	
}
