package io.agileIntelligence.ppmtool2.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements UserDetails{	//We need to have a User implementing UserDetails.. and only then we can have a service which implements UserDetailsService...UserDetailsService can grab your user and make sure that it actally exists...to make sure that user is trying to login with a valid username and password

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message="username needs to be an email")
	@NotBlank(message = "username is required")
	@Column(unique=true)
	private String username;
	
	@NotBlank(message = "Please enter your full name")
	private String fullName;
	
	@NotBlank(message = "Password field is required")
	private String password;
	
	@Transient	//transient indicates that this field is not required to be saved in the database
	//@JsonIgnore	//Note - It's true that we don't really need confirm password in our response body that we return so adding JsonIgnore would be first thing on mind, but don't forget that adding JsonIgnore would also mean we can't even fetch confirmPassword from the request body coming on our server..but we need it for our validation
	private String confirmPassword;
	
	private Date create_At;
	
	private Date update_At;
	
	@OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,mappedBy = "user",orphanRemoval = true)
	private List<Project> projects = new ArrayList<>();
	
	@PrePersist
	protected void onCreate(){
		this.create_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		this.update_At = new Date();
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;//Changed to true from false because we don't want to add additional logic on account expiry
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;//Changed to true from false because we don't want to add additional logic on account management
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;//same
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;//same
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
}
