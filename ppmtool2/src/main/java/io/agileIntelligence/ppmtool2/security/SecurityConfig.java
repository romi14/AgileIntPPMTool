package io.agileIntelligence.ppmtool2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.agileIntelligence.ppmtool2.services.CustomUserDetailsService;

@Configuration	//@Configuration annotation indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime
@EnableWebSecurity	//Add this annotation to an @Configuration class to have the Spring Security configuration defined in any WebSecurityConfigurer	//used to enable SpringSecurity in our project		//EnableWebSecurity will provide configuration via HttpSecurity. It's the configuration you could find with <http></http> tag in xml configuration, it allows you to configure your access based on urls patterns, the authentication endpoints, handlers etc
@EnableGlobalMethodSecurity	//EnableGlobalMethodSecurity provides AOP(Aspect-Oriented Programming) security on methods. Some of the annotations that it provides are PreAuthorize, PostAuthorize. It also has support for JSR-250		//One of the key components of Spring Framework is the Aspect oriented programming (AOP) framework. Spring AOP module provides interceptors to intercept an application. ... For example, when a method is executed, you can add extra functionality before or after the method execution
(
	securedEnabled = true,
	jsr250Enabled = true,
	prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter	//WebSecurityConfigurerAdapter is a class that implements WebSecurityConfigurer interface which provides the basic security configurations	//The @EnableWebSecurity annotation and WebSecurityConfigurerAdapter work together to provide web based security. By extending WebSecurityConfigurerAdapter and only a few lines of code we are able to do the following-1.Require the user to be authenticated prior to accessing any URL within our application	2.Create a user with the username “user”, password “password”, and role of “ROLE_USER”	3.Enables HTTP Basic and Form based authentication	4.Spring Security will automatically render a login page and logout success page for you
{
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(){return new JwtAuthenticationFilter();}
	
	//The AuthenticationManagerBuilder is a class that hepls in building our Authentication Manager who is responsible  for authenticating our user
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	

	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)	//Spring @Bean annotation indicates that a method produces a bean to be managed by the Spring container. Spring @Bean method can be created within @Configuration and @Component classes. The default scope of a bean is singleton.
	
	//In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.
	
	//Simply put, Inversion of Control, or IoC for short, is a process in which an object defines its dependencies without creating them. This object delegates the job of constructing such dependencies to an IoC container.
	
	//Don't get confused, the 'AUTHENTICATION_MANAGER' is the same that we have configured above using the 'configure(AuthenticationManagerBuilder auth)' method we overrode 
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
		http.cors().and().csrf().disable()	//disabling cors and csrf	//Cross-Origin Resource Sharing (CORS) is an HTTP-header based mechanism that allows a server to indicate any other origins (domain, scheme, or port) than its own from which a browser should permit loading of resources.Eg. Our react server accesses resources from our spring server with cross-origin annotation in our controllers..here we are disabling all securities placed on that kind of transactions	//Cross-Site Request Forgery (CSRF) is an attack that forces an end user to execute unwanted actions on a web application in which they're currently authenticated. In a successful CSRF attack, the attacker causes the victim user to carry out an action unintentionally. For example, this might be to change the email address on their account, to change their password, or to make a funds transfer.
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)	//We are going to define exception handling here by customizing the authentication entry point	//authenticationEntryPoint handles what exception is needed to be thrown when somebody is not authenticated
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)	//This is a REST API, we don't want our Spring security to save sessions or cookies or anything with some state	//A REST API (also known as RESTful API) is an application programming interface (API or web API) that conforms to the constraints of REST architectural style and allows for interaction with RESTful web services	//An API is a set of definitions and protocols for building and integrating application software.	// REST stands for representational state transfer		//Written by me - In REST API, the server does not needs to store sessions, we will provide JSON tokens from our frontend(react server) and if the tokens are valid that means user is still authenticated. Redux is going to hold the state in our application and not spring
			.and()
			//.headers().frameOptions().sameOrigin().and(); //Line to be added if you want to enable h2 database in spring security
			.authorizeRequests()
			.antMatchers(
							"/",
							"/favicon.ico",
							"/**/*.png",
							"/**/*.gif",
							"/**/*.svg",
							"/**/*.jpg",
							"/**/*.html",
							"/**/*.css",
							"/**/*.js"//don't get confused, even with .js or anything at end it's simple path that we enter in POSTMAN or call from react through axios
			).permitAll()
			.antMatchers(SecurityConstants.SIGN_UP_URLS).permitAll()
			//.antMatchers(SecurityConstants.H2_URL).permitAll()	Only for H2 db fans
			.anyRequest().authenticated();	//Implies any request other than that will require authentication
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
