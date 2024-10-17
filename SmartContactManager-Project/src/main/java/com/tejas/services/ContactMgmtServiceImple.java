package com.tejas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tejas.entity.Contact;
import com.tejas.entity.User;
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
		return repo.save(contact);
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
	
//	@Override
//	public Page<Contact> getAllContactByPage(Pageable pageable) {
//		// TODO Auto-generated method stub
//		return repo.findAll(pageable);
//	}
	
	@Override
	public Page<Contact> getAllContactByPage(User user, int page, int size, String sortBy, String direction) {
		// preparing sort object
		Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
	    var pageable = PageRequest.of(page, size,sort);
		return repo.findByUser(user,pageable);
	}
	
	
	@Override
	public Page<Contact> getRecordsByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page,size);
		return repo.findByName(name, pageable);
	}
	
	@Override
	public Page<Contact> getRecordsByEmail(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page,size);
		return repo.findByEmail(name, pageable);
	}
	
	@Override
	public Page<Contact> getRecordsByPhoneNumber(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page,size);
		return repo.findByPhoneNumber(name, pageable);
	}

}
