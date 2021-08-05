package io.agileIntelligence.ppmtool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.agileIntelligence.ppmtool2.domain.User;
import io.agileIntelligence.ppmtool2.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(arg0);
		if(user==null) new UsernameNotFoundException("User Not Found");//Remember - We haven't written throw here because we have mentioned in the method that it throws this exception
		return user;
	}

	@Transactional	//One of the key points about @Transactional is that there are two separate concepts to consider, each with it's own scope and life cycle:
//				
//				the persistence context
//				the database transaction
//				The transactional annotation itself defines the scope of a single database transaction
	
	//Transactional annotation provides the application the ability to declaratively control transaction boundaries on CDI managed beans, as well as classes defined as managed beans by the Java EE specification, at both the class and method level where method level annotations override those at the class level.
	
	//The @Transactional annotation is the metadata that specifies the semantics of the transactions on a method. We have two ways to rollback a transaction: declarative and programmatic. In the declarative approach, we annotate the methods with the @Transactional annotation
	
	//When Spring loads your bean definitions, and has been configured to look for @Transactional annotations, it will create these proxy objects around your actual bean. These proxy objects are instances of classes that are auto-generated at runtime
	
	//As most of you probably know, due to the very nature of the solution, Spring's @Transactional annotation does not work on private methods unless you're using the AspectJ mode
	
	//At a high level, when a class declares @Transactional on itself or its members, Spring creates a proxy that implements the same interface(s) as the class you're annotating. ... A proxy provides a way for Spring to inject behaviors before, after, or around method calls into the object being proxied
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.getById(id);
		if(user==null) new UsernameNotFoundException("User Not Found");//Remember - We haven't written throw here because we have mentioned in the method that it throws this exception
		return user;
	}
	
}
