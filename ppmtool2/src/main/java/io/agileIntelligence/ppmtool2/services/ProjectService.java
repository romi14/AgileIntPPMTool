package io.agileIntelligence.ppmtool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileIntelligence.ppmtool2.domain.Backlog;
import io.agileIntelligence.ppmtool2.domain.Project;
import io.agileIntelligence.ppmtool2.domain.User;
import io.agileIntelligence.ppmtool2.exception.ProjectIdException;
import io.agileIntelligence.ppmtool2.exception.ProjectNotFoundException;
import io.agileIntelligence.ppmtool2.repositories.BacklogRepository;
import io.agileIntelligence.ppmtool2.repositories.ProjectRepository;
import io.agileIntelligence.ppmtool2.repositories.UserRepository;

@Service	// @Service annotation is used with classes that provide some business functionalities. Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used
public class ProjectService {

	@Autowired	//Autowiring feature of spring framework enables you to inject the object dependency implicitly. It internally uses setter or constructor injection. Autowiring can't be used to inject primitive and string values
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project project,String username){
		
		if(project.getId() != null){
			findProjectByIdentifier(project.getProjectIdentifier(),username);
		}
		try{
			User user = userRepository.findByUsername(username);
			
			project.setUser(user);
			
			project.setProjectLeader(user.getUsername());//Setting main user as the project leader too for now(for all projects)
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase()); 
			
			if(project.getId()==null){//When we are updating a project then the Automatically generated id not be null but when we creating for the first time..it will be null until the save method is executed
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);//Doing this is important to mutually map both project and it's backlog with each other, also our backlog is directly being created just by creating the project..so it's important else an error will be thrown...and due to this @JsonIgnore is important in project object of Backlog class because we want to map this project with our backlog but we don't want that in the JSON part as that will cause recursion
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId() != null){	//during update
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);	//When we save our project here, the backlog attached to it will also get saved obviously..and no issue will come because every project must have exactly one backlog and every backlog can have exactly one project..no one can exist without each other..also by cascading we have set that whenever a project gets deleted it's backlog will also be deleted but no need to do this vice versa since on frontend there is no option to delete a backlog
		}
		catch(Exception e){
			throw new ProjectIdException("Project ID : '" + project.getProjectIdentifier() + "' already exists");
		}
	}
	
	public Project findProjectByIdentifier(String projectId,String username){
		
			Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
			if(project == null)
				throw new ProjectIdException("Project ID : '" + projectId + "' does not exists");
		
			if(!project.getProjectLeader().equalsIgnoreCase(username))
				throw new ProjectNotFoundException("Project not found in your account");
			
			return project; 
	}
	
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public void deleteProjectByIdentifier(String projectId,String username){
//		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//		if(project == null)
//			throw new ProjectIdException("Cannot delete project with id : '" + projectId + "'. This project does not exists");
		
		projectRepository.delete(findProjectByIdentifier(projectId,username));
	}
}	

