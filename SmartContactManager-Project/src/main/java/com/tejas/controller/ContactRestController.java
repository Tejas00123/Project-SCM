package com.tejas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejas.entity.Contact;
import com.tejas.services.IContactMgmService;

@RestController
@RequestMapping("/api")
public class ContactRestController {
    @Autowired
	IContactMgmService contactService;
    
	@GetMapping("/contacts/{contactId}")
	public Contact getContact(@PathVariable String contactId) {
		return contactService.getContactById(contactId);
	}
	
	
}
