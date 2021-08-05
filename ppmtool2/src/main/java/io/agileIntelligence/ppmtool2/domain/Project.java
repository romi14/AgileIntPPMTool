package io.agileIntelligence.ppmtool2.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Project name is required")
	private String projectName;
	
	@NotBlank(message = "Project Identifier is required")
	@Size(min=4,max=5,message="Please use 4 or 5 characters")
	@Column(updatable=false,unique=true)//unique will be checked, but since it is checked only once we try to update the db..any normal error handler(like mapValidationErrorService) will not be able to identify it and hence a full blown 500 error will be produced unless we use custom made error/exception handlers(as done in io.agileintelligence.ppmtool2.exception package
	private String projectIdentifier;
	
	@NotBlank(message = "Project Description is required")
	private String description;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date start_date;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date end_date;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Column(updatable=false)
	private Date created_At;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updated_At;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "project")//Notice here that 'mapped by' property is written only in parent class(Project) and not in child class(Backlog)..and the name provided in it should be exatly same as the name of the Project object declared in the Backlog class...also cascade is also only written in Project class because when we delete or update the project, the backlog linked to it should be updated but vice versa is not true  
	@JsonIgnore	
	private Backlog backlog;
	
	@ManyToOne(fetch = FetchType.LAZY)//because we don't want to load the user info when we load the project
	@JsonIgnore
	private User user;
	
	private String projectLeader;	//It will store username of the specific project's lead, will only be useful if we enable teams under our users...like one user will be used by a team and each project's projectLeader has to be stored
	
	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@PrePersist	//it is used to annotate model methods to indicate that the method should be called before the entity is inserted (persisted) into the database
	protected void onCreate(){
		this.created_At = new Date();
	}
	
	@PreUpdate	//it is used to annotate methods in models to indicate an operation that should be triggered before an entity has been updated in the database
	protected void onUpdate(){
		this.updated_At = new Date();
	}
	
	public Project(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public Date getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
