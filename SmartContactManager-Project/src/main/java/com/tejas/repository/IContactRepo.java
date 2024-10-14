package com.tejas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.entity.Contact;
import com.tejas.entity.User;

public interface IContactRepo extends JpaRepository<Contact, String> {

	//custom finder method
	public Page<Contact> findByUser(User user,Pageable pageable);
	
	//custom query method
//	@Query("SELECT c FROM CONTACT_TABLE c WHERE c.user.id = :userId")
//	public List<Contact> findByUserId(@Param("userId") String userId);
	
}
