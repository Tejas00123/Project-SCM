package com.tejas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tejas.entity.User;
import com.tejas.helper.Helper;
import com.tejas.services.IUserMgmtServices;

@ControllerAdvice
public class RootController {

	@Autowired
	private IUserMgmtServices userService;
	
	@ModelAttribute
	public void getLoggedUserDetails(Authentication authentication,Model model) {
		if(authentication==null) {
			return;
		}
		String username = Helper.getEmailOfLoggedUesr(authentication);
		System.out.println(username);
		//accessing user data using username
		User user = userService.getUserByEmail(username);
		model.addAttribute("LoggedUser", user);	
	}
}
