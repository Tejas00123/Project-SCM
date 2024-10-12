package com.tejas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

	@RequestMapping("/profile")
	public String showProfilePage() {
		return "user/profile";
	}
	
	@GetMapping("/dashboard")
	public String showDashboardPage() {
		return "user/dashboard";
	}
	
}
