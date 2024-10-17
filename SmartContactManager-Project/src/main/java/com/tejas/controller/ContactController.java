package com.tejas.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tejas.entity.Contact;
import com.tejas.entity.User;
import com.tejas.forms.ContactForm;
import com.tejas.helper.AppConstant;
import com.tejas.helper.Helper;
import com.tejas.helper.Message;
import com.tejas.helper.MessageType;
import com.tejas.repository.IContactRepo;
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
    
    @Autowired
	private IContactRepo contactRepo ;
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
		contact.setFavorite(contactForm.isFavorite());
		contact.setId(UUID.randomUUID().toString());
		
	    if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
	    
	    	//String contactImageFileName = contactForm.getContactImage().getOriginalFilename();
			String fileName = UUID.randomUUID().toString();
			
			String imgUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
		
		contact.setPicture(imgUrl);
	    }
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

	@GetMapping("/contacts")
    public String viewAllContact(
    		@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.page_size + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
    		Model model,
    		Authentication authentication) {
		
		String emailOfLoggedUesr = Helper.getEmailOfLoggedUesr(authentication);
		User user = userService.getUserByEmail(emailOfLoggedUesr);
		Page<Contact> pageObj = contactService.getAllContactByPage(user,page,size, sortBy,direction);
		
		model.addAttribute("pageContact", pageObj);
		model.addAttribute("pageSize", AppConstant.page_size);
    	return "user/view_contacts";
    }
	
	
	@GetMapping("/search")
	public String showSearchPage(@RequestParam("select") String field,
	                             @RequestParam("search") String value, Model model) {
	    logger.info("Selected field: {} | Search value: {}", field, value);

	    Page<Contact> records = null;

	    // Select records based on the field value
	    if (field.equalsIgnoreCase("Name")) {
	        logger.info("Searching by name field");
	        records = contactService.getRecordsByName(value, 0, AppConstant.page_size);
	    } else if (field.equalsIgnoreCase("email")) {
	        logger.info("Searching by email field");
	        records = contactService.getRecordsByEmail(value, 0, AppConstant.page_size);
	    } else if (field.equalsIgnoreCase("phoneNo")) {
	        logger.info("Searching by phone number field");
	        records = contactService.getRecordsByPhoneNumber(value, 0, AppConstant.page_size);
	    } else {
	        logger.error("Invalid search field: {}", field);
	        model.addAttribute("error", "Invalid search field: " + field);
	        return "user/search";
	    }

	    // Add records to model
	    model.addAttribute("pageContact", records);
	    model.addAttribute("pageSize", AppConstant.page_size);
	    logger.info("Found {} records", records.getTotalElements());

	    return "user/search";
	}

	@GetMapping("/delete/{contactId}")
	public String deletePage(@PathVariable String contactId,HttpSession session) {
		contactService.deleteContact(contactId);
		session.setAttribute("message", 
				Message
				.builder()
				.content("Contact deleted successuflly...")
				.type(MessageType.success)
				.build());
		return "redirect:/user/contacts/contacts";
	}

	@GetMapping("/view/{contactId}")
    public String showViewPage(@PathVariable String contactId,Model model) {
		logger.info("id ===>"+contactId);
		Contact contact = contactService.getContactById(contactId);
		ContactForm contactForm = new ContactForm();
		//setting values to contactForm from getting contact
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setAddress(contact.getAddress());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.isFavorite());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setPicture(contact.getPicture());
		contactForm.setLinkedInLink(contact.getLinkedInLink());
		model.addAttribute("contactForm",contactForm);
		model.addAttribute("contactId", contactId);
    	return "user/update_contact";
    }
	
	@PostMapping("/update/{contactId}")
	public String updateContact(@PathVariable String contactId,
	        @Valid @ModelAttribute ContactForm contactForm, // Getting data from the object
	        BindingResult bResult, // For checking errors
	        Model model) { // After updating, showing object data to the form

	    // Check for errors in the form
	    if (bResult.hasErrors()) {
	        return "user/update_contact";
	    }

	    // Fetch the existing contact
	    var contact = contactService.getContactById(contactId);

	    // Update contact fields
	    contact.setName(contactForm.getName());
	    contact.setAddress(contactForm.getAddress());
	    contact.setEmail(contactForm.getEmail());
	    contact.setFavorite(contactForm.isFavorite());
	    contact.setDescription(contactForm.getDescription());
	    contact.setLinkedInLink(contactForm.getLinkedInLink());
	    contact.setWebsiteLink(contactForm.getWebsiteLink());
	    contact.setPhoneNumber(contactForm.getPhoneNumber());

	    // Handle the image upload
	    if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
	        logger.info("file is not empty");

	        // Generate a unique filename for the image
	        String fileName = UUID.randomUUID().toString();

	        // Upload the image and retrieve the URL
	        String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);

	        // Set the new image URL in the contact and contact form
	        contact.setPicture(imageUrl);  // Update the contact's picture with the new image URL
	        contactForm.setPicture(imageUrl);  // Update the form with the new image URL

	        logger.info("Image uploaded successfully. URL: " + imageUrl);
	    } else {
	        logger.info("file is empty, not updating the image");
	    }

	    // Update the contact in the database
	    var updatedContact = contactService.updateContact(contact);

	   // logger.info("Updated contact: {}", updatedContact);

	   // model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.success).build());
	    // Redirect to the view page for the updated contact
	    return "redirect:/user/contacts/view/" + contactId;
	}


}
