package com.pt.springsocial.example.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pt.springsocial.example.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	AuthenticationManager authenticationManager;
	
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new RegisterValidator(userService));
    }
    
	/** Handle main index page - prepare data for Registration Form
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
    public String hello(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		
		return "index";
	}

	/** Perform user registration
	 * 
	 * @param registerForm
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String register(@Valid RegisterForm registerForm, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {  
			return "index";  
		}
		
		String userName = registerForm.getUserName();
		String password = registerForm.getPassword();
		
		// create new user
		userService.createNewUser(userName, password);

		// automatically authenticate user
		Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/status";
	}
}
