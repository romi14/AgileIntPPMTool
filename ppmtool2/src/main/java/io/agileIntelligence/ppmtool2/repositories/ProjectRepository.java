package io.agileIntelligence.ppmtool2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agileIntelligence.ppmtool2.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long>{ //CrudRepository is an interface for generic CRUD operations on a repository for a specific type.
	
	@Override
	Iterable<Project> findAllById(Iterable<Long> iterable);//Iterable - Implementing this interface allows an object to be the target of the "for-each loop" statement		findAllById is a method provided by CrudRepository
	
	Project findByProjectIdentifier(String projectId);
	
	@Override
	Iterable<Project> findAll();//Not being used anymore since we don't want to get projects for every damn user 
	
	Iterable<Project> findAllByProjectLeader(String username);
	
	
}
