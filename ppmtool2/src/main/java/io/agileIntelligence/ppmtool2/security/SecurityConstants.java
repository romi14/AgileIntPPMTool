package io.agileIntelligence.ppmtool2.security;

//This is like a cockpit for all our frequently used security constants
public class SecurityConstants {

	public static final String SIGN_UP_URLS = "/api/users/**";
	
	//public static final String H2_URL = "h2-console/**";	//only for those using h2_database
	
	public static final String SECRET = "SecretKeyToGenJWTs";
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
	public static final long EXPIRATION_TIME = 300_000;//300 secs or 5 min
}
