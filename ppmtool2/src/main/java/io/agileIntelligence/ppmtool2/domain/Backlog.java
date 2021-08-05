package io.agileIntelligence.ppmtool2.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

//We didn't create a service for backlog because we are using this as a middleMan between Project and ProjectTasks
@Entity
public class Backlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer PTSequence = 0;
	
	private String projectIdentifier;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id",nullable = false)//remember here, the column name will be 'project_id' and not 'project' and the project_id column will get the value equal to the id column of Project class since it is annotated by @Id  
	@JsonIgnore		//To stop the JSON infinite recursion problem, when we insert a new backlog into the project.. a new backlog is created and then we update the project property of backlog which tries to create a new project inside this backlog object...since backlog is our child and Project is our parent..we put the JSON ignore in child so that the child does not tries to become a parent...also as the name suggests..this only affects the response body..has no implication in reality(in our project)
	private Project project;
	
	//CASCADE set to REFRESH because when a child gets deleted then we need to refresh our backlog for that project
	//CASCADE for UPDATE not needed because we don't wanna update our backlog in any case relating to ProjectTasks
	//orphanRemoval removes any child of this class from the database along with disconnecting it's relationship with this class (offcourse when the parent(backlog) is deleted)
	//diff between CASCADE DELETE and orphanRemoval is that DELETE only removes the relationship but orphanRemoval is more aggresive and deletes the object from the database as well...so if the project is deleted, then it's backlog will obviously be deleted and hence all the project tasks under it will get erased as well 
	@OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,mappedBy="backlog",orphanRemoval=true)//Backlog is parent to ProjectTask class
	private List<ProjectTask> projectTasks = new ArrayList<>();
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public Backlog() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPTSequence() {
		return PTSequence;
	}

	public void setPTSequence(Integer pTSequence) {
		PTSequence = pTSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	
}
