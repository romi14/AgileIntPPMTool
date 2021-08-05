package io.agileIntelligence.ppmtool2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agileIntelligence.ppmtool2.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

	User findByUsername(String username);
	//User getByUserName(String username); //could have done this way too
	User getById(Long id); 
	
	//Optional<User> findById(Long id);	//We could have done this way too..although if datatype is long Optional class has to be used with findBy__ for Java 8 and above, so getBy__ is easier
	
	
	
}
