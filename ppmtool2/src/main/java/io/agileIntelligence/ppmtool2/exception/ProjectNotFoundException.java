package io.agileIntelligence.ppmtool2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException{

	public ProjectNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		
	}

	
}
