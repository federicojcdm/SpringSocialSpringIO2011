package com.pt.springsocial.example.controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pt.springsocial.example.entity.User;
import com.pt.springsocial.example.service.UserService;

/** Validator for Registration Form
 * 
 * @author fcaro
 *
 */
public class RegisterValidator implements Validator {
	UserService userService;
	
	public RegisterValidator(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		RegisterForm regForm = (RegisterForm)obj;
		// perform bean validation according to annotations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "field.required");
		
		if (StringUtils.isNotBlank(regForm.getUserName())) {
			User user = userService.findByName(regForm.getUserName());
			
			if (user != null && StringUtils.isNotEmpty(user.getPassword())) {
				errors.rejectValue("userName", "user.alreadyRegistered");
			}
		}
		
		if (!StringUtils.equals(regForm.getPassword(), regForm.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "passwordsNotSame");
		}
	}
}
