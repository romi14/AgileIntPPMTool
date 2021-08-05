package io.agileIntelligence.ppmtool2.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.agileIntelligence.ppmtool2.exception.InvalidLoginResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint	//It is an interface that provides the implementation for method call commands	//This is called whenever an exception is thrown because a user is trying to access a resource that requires authentication
{

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
			throws IOException, ServletException {	//In this commence method we override the Unauthorized exception that spring security throws when an unauthorized user tries to access a resource 
		// TODO Auto-generated method stub
		
		InvalidLoginResponse loginResponse = new InvalidLoginResponse();
		
		String jsonLoginResponse = new Gson().toJson(loginResponse);
		
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setStatus(401);
		httpServletResponse.getWriter().print(jsonLoginResponse);;
	}

	
	
}
