package com.tejas.services;

import java.util.List;

import com.tejas.entity.Contact;

public interface IContactMgmService {
  public Contact saveContact(Contact contact);
  public Contact updateContact(Contact contact);
  public List<Contact> getAllContact();
  public Contact getContactById(String id);
  public void deleteContact(String id);
  //search contact
  List<Contact> search(String name,String email,String phonNo);
  List<Contact> getByUserId(String userId);
}
