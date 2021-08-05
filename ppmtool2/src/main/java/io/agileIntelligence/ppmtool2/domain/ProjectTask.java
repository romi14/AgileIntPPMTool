package io.agileIntelligence.ppmtool2.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", create_At=" + create_At
				+ ", update_At=" + update_At + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable = false,unique=true)
	private String projectSequence;	//It's just like projectIdentifier of Project class in terms that the 'id' attribute generated can be difficult to keep track of(Seriously I have doubts on this one)..here we want to structure this one like " 'projectIdentifier'-1 or REAC-1"
	
	@NotBlank(message = "Please include a project summary")
	private String summary;
	
	private String acceptanceCriteria;
	
	private String status;
	
	private Integer priority;
	
	@JsonFormat(pattern="yyyy-mm-dd")//Remember applying this, otherwise it will cause errors that are hard to detect..also note that on browser the format you'll see and save in is mm-dd-yyyy but in database it is yyyy-mm-dd...so if it is not working for you then just remember to match this pattern with db's format and that's it
	private Date dueDate;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id",updatable = false,nullable = false)
	//@Cascade(org.hibernate.annotations.CascadeType.REFRESH)
	@JsonIgnore	//To make sure we don't get backlog attr in our project's response body...because backlog contains all the project tasks which if fetched together will cause a mayhem of size being fetched in one request	We, in the first place created backlog because of this functionality..we could also have simply attached the project tasks list with the project itself but then we would have no choice but to fetch them all in one request
	private Backlog backlog;
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;		
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getCreate_At() {
		return create_At;
	}

	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}

	public Date getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}

	private Date create_At;
	
	private Date update_At;
	
	@PrePersist
	protected void onCreate(){
		this.create_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		this.update_At = new Date();
	}
}
