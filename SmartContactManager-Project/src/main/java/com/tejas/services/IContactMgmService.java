package com.tejas.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tejas.entity.Contact;
import com.tejas.entity.User;

public interface IContactMgmService {
  public Contact saveContact(Contact contact);
  public Contact updateContact(Contact contact);
  public List<Contact> getAllContact();
  public Contact getContactById(String id);
  public void deleteContact(String id);
  //search contact
  public Page<Contact> getAllContactByPage(User user,int page,int size,String sortBy,String direction);
  List<Contact> getByUserId(String userId);
}
