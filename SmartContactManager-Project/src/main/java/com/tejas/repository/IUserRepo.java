package com.tejas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejas.entity.User;

public interface IUserRepo extends JpaRepository<User, String> {

	public User findUserByEmail(String email);
}
