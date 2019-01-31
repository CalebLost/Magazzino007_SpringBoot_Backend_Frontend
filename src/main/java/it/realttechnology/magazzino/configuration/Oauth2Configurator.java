package it.realttechnology.magazzino.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Oauth2Configurator
{
	@Value("${security.oauth2.client.userAuthorizationUri}")
	private String oAuthAuthUri;
	@Value("${security.oauth2.client.accessTokenUri}")
	private String oAuthTokenUri;
	@Value("${security.oauth2.resource.userInfoUri}")
	private String oAuthUser;
	@Value("${spring.security.oauth2.client.registration.google.clientSecret}")
	private String oAuthSecret;
	@Value("${spring.security.oauth2.client.registration.google.clientId}")
	private String oAuthClient;
	
	@Value("${security.oauth2.client.clientAuthenticationScheme}")
	private String oAuthClienthScheme;
	@Value("${security.oauth2.client.authenticationScheme}")
	private String oAuthScheme;
	@Value("${security.oauth2.client.scope}")
	private String oAuthScope;
	@Value("${server.ssl.enabled}")
	private boolean isSsl;
	@Value("${login.view}")
	private String loginView;
	@Value("${logout.view}")
	private String logoutView;
	@Value("${app.hostname}")
	private String hostname;
	@Value("${security.oauth2.client.roles}")
	private String googleroles;
	@Value("${security.oauth2.client.redirectssl}")
	private boolean redirectssl;
	@Value("${security.oauth2.client.logoutUri}")
	private String logoutUrl;
	
	public final static String HTTPS        =  "https://";
	public final static String HTTP         =  "http://";
	public final static String querysep     =  "?";
	public final static String namevalsep   =  "&";
	public final static String datasep      =  "=";
	public final static String rolesep      =  ",";
	public final static String usersep      =  ":";
	
	public String getoAuthAuthUri() {
		return oAuthAuthUri;
	}
	public void setoAuhtAuthUri(String oAuthUri) {
		this.oAuthAuthUri = oAuthUri;
	}
	
	public String getoAuthTokenUri() {
		return oAuthTokenUri;
	}
	public void setoAuhtTokenUri(String oAuthUri) {
		this.oAuthTokenUri = oAuthUri;
	}
	
	public String getoAuthUser() {
		return oAuthUser;
	}
	public void setoAuthUser(String oAuthUser) {
		this.oAuthUser = oAuthUser;
	}
	public String getoAuthSecret() {
		return oAuthSecret;
	}
	public void setoAuthSecret(String oAuthSecret) {
		this.oAuthSecret = oAuthSecret;
	}
	public String getoAuthClient() {
		return oAuthClient;
	}
	public void setoAuthClient(String oAuthClient) {
		this.oAuthClient = oAuthClient;
	}
	public String getoAuthClienthScheme() {
		return oAuthClienthScheme;
	}
	public void setoAuthClienthScheme(String oAuthClienthScheme) {
		this.oAuthClienthScheme = oAuthClienthScheme;
	}
	public String getoAuthScheme() {
		return oAuthScheme;
	}
	public void setoAuthScheme(String oAuthScheme) {
		this.oAuthScheme = oAuthScheme;
	}
	public String getoAuthScope() {
		return oAuthScope;
	}
	public void setoAuthScope(String oAuthScope) {
		this.oAuthScope = oAuthScope;
	}
	public boolean isSsl() {
		return isSsl;
	}
	public void setSsl(boolean isSsl) {
		this.isSsl = isSsl;
	}
	public String getLoginView() {
		return loginView;
	}
	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getGoogleroles() {
		return googleroles;
	}
	public void setGoogleroles(String googleroles) {
		this.googleroles = googleroles;
	}
	public boolean isRedirectssl() {
		return redirectssl;
	}
	public void setRedirectssl(boolean redirectssl) {
		this.redirectssl = redirectssl;
	}
	
	public String[] getRoles()
	{
		return googleroles.split(rolesep);
	}
	public String getLoginUrl()
	{
		// TODO Auto-generated method stub
		return this.getoAuthAuthUri() + Oauth2Configurator.querysep +
		        "scope"+ Oauth2Configurator.datasep + this.getoAuthScope() + Oauth2Configurator.namevalsep +
		        "client_id"+ Oauth2Configurator.datasep + this.getoAuthClient() + Oauth2Configurator.namevalsep +
		        "response_type"+ Oauth2Configurator.datasep + "code" + Oauth2Configurator.namevalsep +
		        "redirect_uri"+ Oauth2Configurator.datasep +this.getHostBaseUrl()+this.getLoginView();
	}
	public String getLogoutUrl() {
		return logoutUrl;
	}
	public void setLogoutUrl(String logouturl) {
		this.logoutUrl = logouturl;
	}
	public String getHostBaseUrl() 
	{
		return (isSsl || redirectssl ? HTTPS : HTTP) + hostname;
	}
	public String getLogoutView() 
	{
		return logoutView;
	}
}
