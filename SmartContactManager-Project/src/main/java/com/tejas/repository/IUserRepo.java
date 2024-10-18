package com.tejas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.entity.User;

public interface IUserRepo extends JpaRepository<User, String> {

	public Optional<User> findByEmail(String email);
	
	public Optional<User> getByEmailToken(String email_token);
}
