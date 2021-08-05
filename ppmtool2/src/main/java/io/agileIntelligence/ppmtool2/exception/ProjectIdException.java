package io.agileIntelligence.ppmtool2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)//Marks a method or exception class with the status code() and reason() that should be returned
public class ProjectIdException extends RuntimeException{

	public ProjectIdException(String message){
		super(message);
	}

	
}
