package com.tejas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tejas.entity.User;
import com.tejas.helper.Helper;
import com.tejas.services.IUserMgmtServices;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private IUserMgmtServices userService;
	
	@RequestMapping("/profile")
	public String showProfilePage(Authentication authentication,Model model) {
		
		return "user/profile";
	}
	
	@GetMapping("/dashboard")
	public String showDashboardPage() {
		return "user/dashboard";
	}
	
}
