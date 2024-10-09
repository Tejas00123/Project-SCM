package com.tejas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/")
	public String showHomePage() {
		return "home";
	} 
	 
	//about page
	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}  
	
	@GetMapping("/services")
	public String showServicesPage() {
		return "services";
	}
	
	@GetMapping("/contact")
	public String showContactPage() {
		return "contact";
	}
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}
}
