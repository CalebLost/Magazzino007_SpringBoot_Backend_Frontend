package it.realttechnology.magazzino;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import it.realttechnology.magazzino.security.FormAuthenticationInterceptor;
//import it.realttechnology.magazzino.security.InMemoryJwtTokenStore;
import it.realttechnology.magazzino.security.TokenUtils;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableAutoConfiguration(exclude =
{
      SecurityAutoConfiguration.class
})
@ComponentScan({"it.realttechnology.magazzino.security","it.realttechnology.magazzino.controller","it.realttechnology.magazzino.services","it.realttechnology.magazzino.configuration"})
@EntityScan("it.realttechnology.magazzino.entity")
public class MagazzinoApplication extends WebMvcConfigurerAdapter
//public class MagazzinoApplication implements WebMvcConfigurer
{
    @Value("${server.ssl.enabled}")
	private boolean sslEnabled;
    @Value("${server.port}")
   	private int port;
    @Value("${server.portoptional}")
   	private int portoptional;
    //same in mvc controller
    @Value("${views.datatables.language}")
	private String viewsDataTablesLanguage;
    
	public static void main(String[] args) 
	{
		ConfigurableApplicationContext context =  SpringApplication.run(MagazzinoApplication.class, args);
	}
/*	  @Bean
	  public ViewResolver viewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setViewClass(JstlView.class);
	    viewResolver.setPrefix("/WEB-INF/views/");
	    viewResolver.setSuffix(".jsp");
	    return viewResolver;
	  }
*/
@Override
public void addCorsMappings(CorsRegistry registry) {

	registry.addMapping("/views/personale/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
}
	   @Bean
	   public BCryptPasswordEncoder passwordEncoder() 
	   {
		   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		   //String result = bCryptPasswordEncoder.encode("TEST");
		  
		   return bCryptPasswordEncoder;
	   }
	   @Autowired
	   FormAuthenticationInterceptor formAuthenticationInterceptor;

	
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
	       registry.addInterceptor(formAuthenticationInterceptor);
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
		    connector.setPort(portoptional);
		    connector.setSecure(false);

		    connector.setRedirectPort(this.port);
		    
		    return connector;
		  }
	   /*
	    @Bean
	    public TokenStore tokenStore() throws Exception {
	        return new InMemoryJwtTokenStore(accessTokenConverter());
	    }
	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() throws Exception {
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        converter.setSigningKey(TokenUtils.SECRET);
	        converter.afterPropertiesSet();
	        return converter;
	    }

	     
	    @Bean
	    @Primary
	    public DefaultTokenServices tokenServices() throws Exception 
	    {
	        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        defaultTokenServices.setSupportRefreshToken(true);
	        return defaultTokenServices;
	    }
	    
	    @Bean
	    public RequestContextListener requestContextListener() {
	        return new RequestContextListener();
	    }
	    */
}
