package it.realttechnology.magazzino.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//TODO: conf
public class TokenProperties
{
	public static String SECRET                = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME   = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX    = "Bearer ";
    public static final String HEADER_STRING   = "Authorization";
	public static final String TOKEN_AUTH_PWD  = "TOKEN_AUTH_PWD";
}
