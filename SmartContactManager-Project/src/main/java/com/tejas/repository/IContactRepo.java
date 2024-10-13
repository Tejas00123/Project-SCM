package com.tejas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tejas.entity.Contact;
import com.tejas.entity.User;

public interface IContactRepo extends JpaRepository<Contact, String> {

	//custom finder method
	public List<Contact> findByUser(User user);
	
	//custom query method
//	@Query("SELECT c FROM CONTACT_TABLE c WHERE c.user.id = :userId")
//	public List<Contact> findByUserId(@Param("userId") String userId);
	
}
