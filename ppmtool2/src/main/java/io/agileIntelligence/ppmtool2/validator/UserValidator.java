package io.agileIntelligence.ppmtool2.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.agileIntelligence.ppmtool2.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return User.class.equals(arg0);//It means that we are supporting the User class here(for validation) OR it means that we will be validating the User class in the validate method below
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		User user = (User)(arg0);
		if(user.getPassword().length()<6){
			arg1.rejectValue("password", "Length", "Password must be atleast 6 characters");
		}
		System.out.println(user.getConfirmPassword());
		if(!user.getPassword().equals(user.getConfirmPassword())){
			arg1.rejectValue("confirmPassword", "Length", "Passwords must match");
		}
	}

	
}
