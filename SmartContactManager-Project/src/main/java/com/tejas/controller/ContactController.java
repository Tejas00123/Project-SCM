package com.tejas.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tejas.entity.Contact;
import com.tejas.forms.ContactForm;
import com.tejas.helper.Helper;
import com.tejas.helper.Message;
import com.tejas.helper.MessageType;
import com.tejas.services.IContactMgmService;
import com.tejas.services.IImageMgmtService;
import com.tejas.services.IUserMgmtServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
	IContactMgmService contactService;
    
    @Autowired
    IUserMgmtServices userService;
    
    @Autowired
    IImageMgmtService imageService;
   //logger
	 Logger logger = LoggerFactory.getLogger(ContactController.class);

	@GetMapping("/add")
	public String showAddContactPage(Model model) {
		//binding data object to form
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm", contactForm);
		return "user/add_contact";
	}
	
	@PostMapping("/add")
	public String addContactToDB(@Valid @ModelAttribute ContactForm contactForm,
			BindingResult result,Authentication authentication, HttpSession session ) {
		if(result.hasErrors())
		{
			return "user/add_contact";
		}
		
		String contactImageFileName = contactForm.getContactImage().getOriginalFilename();
		String fileName = UUID.randomUUID().toString();
		
		String imgUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
		
		System.out.println(contactForm.toString());
		Contact contact = new Contact();
		
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setPicture(contactForm.getPicture());
		contact.setId(UUID.randomUUID().toString());
		contact.setPicture(imgUrl);
		//using helper method to find out loggeduser email
		String usernmae = Helper.getEmailOfLoggedUesr(authentication);
		//passing username to getUserByEmail
		contact.setUser(userService.getUserByEmail(usernmae));
		//save to contact database
	    contactService.saveContact(contact);
		
	    //createing message obje by passing success message 
	    Message message = Message.builder().content("Registration Successful").type(MessageType.success).build();

	    //setting msg to session
		session.setAttribute("message", message);
		//redirection url
		return "redirect:/user/contacts/add";
	}
}
