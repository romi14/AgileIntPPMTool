package io.agileIntelligence.ppmtool2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Ppmtool2Application {
	
	@Bean	//BCryptPasswordEncoder is not a bean on it's own so we are declaring it as a bean here so that it can be autowired in UserService class
//	A Java bean is just a class which conforms to some conventions:
//
//		1.properties that can be accessed by getters (and setters if those properties are not read-only)
//		2.no-arg public constructor
//		3.serializable
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(Ppmtool2Application.class, args); 
	}

}
