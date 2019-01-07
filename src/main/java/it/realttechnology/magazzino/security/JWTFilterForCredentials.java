package it.realttechnology.magazzino.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auth0.jwt.JWT;
//STATIC IMPORT
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.realttechnology.magazzino.services.UsersAuthenticationService;


//GESTISCE I FILTRI DI AUTENTICAZIONE
//PIU TIPI DI FILTRI.. da vdere

public class JWTFilterForCredentials extends AbstractAuthenticationProcessingFilter 
{
	//GESTORE DELL'AUTENTICAZIONE, PRESO DAL WebSecurityConfigurerAdapter
    private final AuthenticationManager authenticationManager;
    
    private final UsersAuthenticationService authenticationService;
    
    public JWTFilterForCredentials(String authPath,AuthenticationManager authenticationManager,UsersAuthenticationService tokenManager)
    {
    	        //IF USING UPAFILTER....
                // By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
    			// In our case, we use "/auth". So, we need to override the defaults.
    			//this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
        //PATH DI AUTHENTICAZIONE
    	super(new AntPathRequestMatcher(authPath));
        this.authenticationManager     = authenticationManager;
      
        this.authenticationService     = tokenManager;
        
    }

    //GESTISCE IL LOGIN NEL SISTEMA
    //2 STEPS WAY anzichè 1 come la GENERIC FILTER BEAN
    //nell'altra non restituisco un auth ma lo setto nel dofilter
    @Override
    public Authentication attemptAuthentication
                                               (
                                                HttpServletRequest req,
                                                HttpServletResponse res
                                               ) 
    {
    	// Do authentication
    	
        try
        {
        	//OGGETTO SERIALIZZATO NELLA RICHIESTA
        	
            LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
            LoggerFactory.getLogger(JWTFilterForCredentials.class).info(creds.getUsername());
            //L'AUTHMANAGER USA LO  USER DETAIL E CRYPTO, LE UATHORITY ME LE PRENDO DALLO USER
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            Collections.emptyList())
            );
            
            //IN PRATICA SE L'UTENTE ESISTE PER NOME
            //(LO CERCO IN UN REPO), POPOLO UN OGGETTO TOKEN DI UTHENTICAZIONE(NON TOKEN JWT!!) CHE MATCHA USERNAME E PASSWORD
            //FORNITE CON QUELLE DELLO USER DETAIL, USANDO IL CRYPTO FORNITO, QUNDI LATO INTERNO DOVREI AVERE COSE GIA CRYPTATE
        } 
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //GENERA IL TOKEN
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException 
    {
    	// Add Bearer token to header
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TokenProperties.EXPIRATION_TIME))
                .sign(HMAC512(TokenProperties.SECRET.getBytes()));
        
        //append the authorization header to the request header
        String username = auth.getName();
        LoggerFactory.getLogger(JWTFilterForCredentials.class).info("User " + username + " authenticated with token " +  token);
        
        
        //store the token, and give the token back both as header and response json
        try
        {
          authenticationService.setUserToken(username,token);

          String wellFormattedToken = TokenProperties.TOKEN_PREFIX + token;
          res.setHeader(TokenProperties.HEADER_STRING,wellFormattedToken);
          //doing this the caller can auto handle the output
          res.setContentType("application/json");
          new ObjectMapper().writeValue(res.getOutputStream(), new LoginResponse(wellFormattedToken));
          
          SecurityContextHolder.getContext().setAuthentication(auth);
        
        }
        catch(UsernameNotFoundException u)
        {
        	   SecurityContextHolder.clearContext();
        	   LoggerFactory.getLogger(JWTFilterForCredentials.class).info("User " + username + " canno't be further autherized " +  u.toString());
        
        }
    }
}