//You may think that how come errors thown in ProjectService.java are handled according to this class...see ProjectService is autowired in ProjectController which is a controller..so @ControllerAdvice will work in it and this class will be used as an advisor...also since @ExceptionHandler is used inside this class...this class will be used as an ExceptionHandlingAdvisor 
package io.agileIntelligence.ppmtool2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice	//It allows you to write global code that can be applied to a wide range of controllers...here we are using it for global Exception handling...or simply centralizing our error handling logic here
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler//@ExceptionHandler is an annotation for handling exceptions in specific handler classes or handler methods. In Servlet environments, we can combine the @ExceptionHandler annotation with @ResponseStatus to define the response status for the HTTP response.
	public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex,WebRequest req){	//ResponseEntity<Object> is like a JSON object(using only attributes and their values)
		ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);//It will return a JSON object with projectIdentifier attribute of ProjectIdExceptionResponse as the key and it's value(our message value) as the value
//Note - there is not really any difference between a java Object and a JSON object..they both have key value pairs(talking only about the attributes of a class here whose object we are taking for example)
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex,WebRequest req){
		
		ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex,WebRequest req){
		
		UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
}
