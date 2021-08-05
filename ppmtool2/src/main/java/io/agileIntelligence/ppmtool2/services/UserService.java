package io.agileIntelligence.ppmtool2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.agileIntelligence.ppmtool2.domain.User;
import io.agileIntelligence.ppmtool2.exception.UsernameAlreadyExistsException;
import io.agileIntelligence.ppmtool2.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;//To not store readable password in database
	
	public User saveUser (User newUser){
		
		try{
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			//newUser.setUsername(newUser.getUsername());
			newUser.setConfirmPassword("");//Since we have already used it in validation..storing it now would be bad for the memory of our JSON response body (Although I really think otherwise)
			return userRepository.save(newUser);
		}catch(Exception e){
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}
		
		
	}
}
