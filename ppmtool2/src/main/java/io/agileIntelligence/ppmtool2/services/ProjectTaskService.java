package io.agileIntelligence.ppmtool2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileIntelligence.ppmtool2.domain.Backlog;
import io.agileIntelligence.ppmtool2.domain.ProjectTask;
import io.agileIntelligence.ppmtool2.exception.ProjectNotFoundException;
import io.agileIntelligence.ppmtool2.repositories.BacklogRepository;
import io.agileIntelligence.ppmtool2.repositories.ProjectTaskRepository;
//An important thing to understand is that the only role of a service is to extract the logical code from the controller so that the size of the controller is reduced

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectService projectService;
	
	public  ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask,String username){
		
		//try{
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);		//We took from ProjectService rather than BacklogRepository later on because there we already had checks and we saved writing much code
			
			projectTask.setBacklog(backlog);
			
			Integer BacklogSequence = backlog.getPTSequence();
			
			BacklogSequence++;
			
			backlog.setPTSequence(BacklogSequence);
			
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			
			projectTask.setProjectIdentifier(projectIdentifier);
			
			if(projectTask != null && (projectTask.getPriority() == null||projectTask.getPriority() == 0)){
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);
//		}
//		catch(Exception e){
//			throw new ProjectNotFoundException("Project not found");
//		}
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id,String username) {
		projectService.findProjectByIdentifier(backlog_id,username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id,String username){
		//making sure that project actually exists
		//Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		
//		if(backlog == null){
//			throw new ProjectNotFoundException("Project with ID : '" + backlog_id + "' does not exists");
//		}
		
		projectService.findProjectByIdentifier(backlog_id,username);
		
		//making sure that task actually exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		
		if(projectTask == null){
			throw new ProjectNotFoundException("Project Task with ID '" + pt_id + "' not found");
		}
		
		//making sure that tasks belongs to the project
		if(!projectTask.getProjectIdentifier().equalsIgnoreCase(backlog_id)){
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exists in project: '" + backlog_id);
		}
		return projectTask;
	}
	
	public ProjectTask updatePTByProjectSequence(ProjectTask updatedTask,String backlog_id,String pt_id,String username){
		
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
		
		return projectTaskRepository.save(updatedTask);
	};
	
	public void deletePTByProjectSequence(String backlog_id,String pt_id,String username){
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
//		Backlog backlog = projectTask.getBacklog();
//		List<ProjectTask> pts = backlog.getProjectTasks();
//		pts.remove(projectTask);//pts contains list of pointers to the project tasks in the backlog...when the backlog does not contains the 'projectTask' anymore then it can be deleted
		projectTaskRepository.delete(projectTask);
	}
}
