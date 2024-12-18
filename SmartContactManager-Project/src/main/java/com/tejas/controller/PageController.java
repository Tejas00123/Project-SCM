package com.tejas.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tejas.entity.Providers;
import com.tejas.entity.User;
import com.tejas.forms.UserForm;
import com.tejas.helper.Helper;
import com.tejas.helper.Message;
import com.tejas.helper.MessageType;
import com.tejas.services.IEmailMgmtService;
import com.tejas.services.IImageMgmtService;
import com.tejas.services.IUserMgmtServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private IUserMgmtServices userService;
	
	@Autowired
	private IEmailMgmtService emailService;
	
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
	public String showRegisterPage(Model model) {
		UserForm userForm = new UserForm();
		//userForm.setName("Nataraz");
		model.addAttribute("userForm", userForm);
		return "register";
	}
	
	@PostMapping("/do-register")
	public String gettingDataFromBackend(@Valid @ModelAttribute UserForm userForm,BindingResult bResult,HttpSession session) {
		System.out.println(userForm);
		
		//if any error then redirect to register
		if(bResult.hasErrors()) {
			return "register";
		}
		
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNo());
		user.setProfilePic("https://adfd.com/img/tejas.png");
		user.setProvider(Providers.SELF);
		//generating email token
		String emailToken = UUID.randomUUID().toString();
		user.setEmailToken(emailToken);
		
	    User savedUser = userService.saveUser(user);
		//generating emailLInk
	    String emailLink = Helper.generatingLinkForEmailVerification(emailToken);
		//sending email
	    emailService.sendEmail(savedUser.getEmail(), "Verify your email", emailLink);
		System.out.println("Used saved "+savedUser);
		
		Message message = Message.builder().content("Registration Successful").type(MessageType.success).build();

		session.setAttribute("message", message);
		 
		return "redirect:/register";
	}
	
	
}
