package com.tejas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tejas.advisors.UserNotFoundException;
import com.tejas.entity.User;

public interface IUserMgmtServices {

	public User saveUser(User user);
	public User getUserById(String id) throws UserNotFoundException;
	public User updateUser(User user) throws UserNotFoundException;
	public String deleteUser(String id) throws UserNotFoundException;
	public boolean isUserExist(String id);
	public boolean isUserExistsByEmail(String email);
	public List<User> getAllUser();
}
