package io.agileIntelligence.ppmtool2.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileIntelligence.ppmtool2.domain.Project;
import io.agileIntelligence.ppmtool2.services.MapValidationErrorService;
import io.agileIntelligence.ppmtool2.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin	//This helps when we try to connect our frontend to our Java server..can be done at global level too but here we are just doing it at controller level
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal)	//'?' means generic since we have to return 2 different things			ResponseEntity represents the whole HTTP response: status code, headers, and body			@RequestBody : Annotation indicating a method parameter should be bound to the body of the HTTP request. 		@ResponseBody annotation can be put on a method and indicates that the return type should be written straight to the HTTP response body		@Valid will make sure that we get a clear response in case the body sent through post is invalid, it's true that even without @Valid, user will not be able send bad data..but with @Valid the errors shown are much more clearer..although if only projectIdentifier is invalid it's not happening right now because unique constraint can check only after going in db really...edit-we setted up mapValidationErrorService and now it's handled as well			BindingResult is an interface that invokes the validator on an object(our result), hence determines whether it has errors or not..the object of BindingResult that we pass to the POST method of our controller simply has all the errors of validation that we have set up in the Entity class(and can be seen without before actually debugger going to the db side)		//Principal is the main user we logged in with 
	{
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null)
			return errorMap;
		Project project1 = projectService.saveOrUpdateProject(project,principal.getName());
		return new ResponseEntity<Project>(project1,HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId,Principal principal){
		Project project = projectService.findProjectByIdentifier(projectId,principal.getName());
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal principal){//In the Principal class, everything is set up as soon as our user is logged in
		return projectService.findAllProjects(principal.getName());
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId,Principal principal){
		projectService.deleteProjectByIdentifier(projectId,principal.getName());
		return new ResponseEntity<String>("Project with ID : '"+projectId.toUpperCase()+"' was deleted",HttpStatus.OK);
	}
	
	
}
