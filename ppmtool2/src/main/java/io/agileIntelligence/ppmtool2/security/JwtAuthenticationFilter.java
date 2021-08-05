package io.agileIntelligence.ppmtool2.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.agileIntelligence.ppmtool2.services.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			String jwt = getJWTFromRequest(arg0);
			
			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails,
					null,//because we are not giving any credentials
					Collections.emptyList()//because we are not passing any list of rules
				);
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(arg0));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		}catch(Exception e){
			logger.error("Could not set user authentication in security context",e);
		}
		
		arg2.doFilter(arg0, arg1);
	}
	
	private String getJWTFromRequest(HttpServletRequest request){
		String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);//HEADER_STRING is the key and the value of header will be our generated token...if confused just go to POSTMAN and go to 'HEADERS' tab instead of 'BODY'
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)){	//StringUtils - This class delivers some simple functionality that should really be provided by the core Java String and StringBuffer classes, such as the ability to replace all occurrences of a given substring in a target string.	//hasText method - Check if a String has text.
			return bearerToken.substring(7,bearerToken.length());//'bearer ' - these are 7 chars..our token starts after these chars and that is what we have to return
		}
		
		return null;
	}

	
}
