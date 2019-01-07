package it.realttechnology.magazzino;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
@EnableAutoConfiguration(exclude =
{
      SecurityAutoConfiguration.class
})
@ComponentScan({"it.realttechnology.magazzino.security","it.realttechnology.magazzino.controller","it.realttechnology.magazzino.services","it.realttechnology.magazzino.configuration"})
@EntityScan("it.realttechnology.magazzino.entity")
public class MagazzinoApplication
{

	public static void main(String[] args) {
		SpringApplication.run(MagazzinoApplication.class, args);
	}
	
	   @Bean
	   public BCryptPasswordEncoder passwordEncoder() 
	   {
		   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		   return bCryptPasswordEncoder;
	   }
	   
	   @Bean
	   public  ServletWebServerFactory servletContainer() {
		   TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
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
		    //comment to enable both or set by code
		    //HHTPS port on properties
		    connector.setRedirectPort(8443);
		    
		    return connector;
		  }
}
