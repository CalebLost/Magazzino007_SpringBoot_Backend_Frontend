package it.realttechnology.magazzino;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;



@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableAutoConfiguration(exclude =
{
      SecurityAutoConfiguration.class
})
@ComponentScan({"it.realttechnology.magazzino.security","it.realttechnology.magazzino.controller","it.realttechnology.magazzino.services","it.realttechnology.magazzino.configuration"})
@EntityScan("it.realttechnology.magazzino.entity")
public class MagazzinoApplication extends WebMvcConfigurerAdapter
{
    @Value("${server.ssl.enabled}")
	private boolean sslEnabled;
    @Value("${server.port}")
   	private int port;
    //same in mvc controller
    @Value("${views.datatables.language}")
	private String viewsDataTablesLanguage;
    
	public static void main(String[] args) 
	{
		ConfigurableApplicationContext context =  SpringApplication.run(MagazzinoApplication.class, args);
	}
	
	   @Bean
	   public BCryptPasswordEncoder passwordEncoder() 
	   {
		   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		   return bCryptPasswordEncoder;
	   }
	   
	   @Bean
	   public SessionLocaleResolver localeResolver() {
	       SessionLocaleResolver slr = new SessionLocaleResolver();
	       String[] L = viewsDataTablesLanguage.split("_");
	       slr.setDefaultLocale(new Locale(L[0],L[1]));
	       return slr;
	   }
	   @Bean
	   public LocaleChangeInterceptor localeChangeInterceptor() {
	       LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	       lci.setParamName("lang");
	       return lci;
	   }
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	       registry.addInterceptor(localeChangeInterceptor());
	   }
	   @Bean
	   @ConditionalOnProperty(name="server.ssl.enabled",havingValue="true")
	   public  ServletWebServerFactory servletContainer()
	   {
		   Logger.getLogger(MagazzinoApplication.class.getName()).log(Level.INFO,""+sslEnabled+":"+port);
		  
		   TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory()
		   {
	         @Override
	         protected void postProcessContext(org.apache.catalina.Context context)
	         {
	           SecurityConstraint securityConstraint = new SecurityConstraint();
	           securityConstraint.setUserConstraint("CONFIDENTIAL");
	           SecurityCollection collection = new SecurityCollection();
	           collection.addPattern("/*");
	           securityConstraint.addCollection(collection);
	           context.addConstraint(securityConstraint);
	         }
	       };
	      
	     tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());  
	     return tomcat;
	   }
	   
	   private Connector initiateHttpConnector()
	   {
		   //HTTP port
		    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		    connector.setScheme("http");
		    connector.setPort(8080);
		    connector.setSecure(false);

		    connector.setRedirectPort(this.port);
		    
		    return connector;
		  }

}
