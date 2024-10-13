package com.tejas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tejas.entity.Contact;
import com.tejas.repository.IContactRepo;

@Service("contactService")
public class ContactMgmtServiceImple implements IContactMgmService {
    @Autowired
	private IContactRepo repo;
	@Override
	
	public Contact saveContact(Contact contact) {
		return repo.save(contact);
	}

	@Override
	public Contact updateContact(Contact contact) {
		return repo.findById(contact.getId()).orElseThrow(null);
	}

	@Override
	public List<Contact> getAllContact() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Contact getContactById(String id) {
		return repo.findById(id).orElseThrow(()->new UsernameNotFoundException("Contact not found"));
	}

	@Override
	public void deleteContact(String id) {
		Contact contact = repo.findById(id).orElseThrow(()->new UsernameNotFoundException("Contact not found"));
		 repo.delete(contact);
    }
	
	@Override
	public List<Contact> getByUserId(String userId) {
		// TODO Auto-generated method stub
		//return repo.findByUserId(userId);
		return null;
	}
	
	@Override
	public List<Contact> search(String name, String email, String phonNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
