package com.tejas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tejas.advisors.UserNotFoundException;
import com.tejas.entity.User;
import com.tejas.helper.AppConstant;
import com.tejas.repository.IUserRepo;

@Service("userService")
public class UserMgmtServiceImp implements IUserMgmtServices {

	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public User saveUser(User user) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setRoleList(List.of(AppConstant.ROLE_USER));
		User savedUser = userRepo.save(user);
		return savedUser;
	}
	
	@Override
	public User getUserById(String id) throws UserNotFoundException {
//		Optional<User> opt = userRepo.findById(id);
//		if(opt.isPresent()) {
//			User user = opt.get();
//			return user;
//		}
//		else {
//			System.out.println("User not found..");
//			return null;
//		}
		return userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found.."));
	}
	
	@Override
	public User updateUser(User user) throws UserNotFoundException {
		 userRepo.findById(user.getUserId()).orElseThrow(()->new UserNotFoundException("User details not found to update user..."));
	     userRepo.save(user);
	     return user;
	}
	
	@Override
	public String deleteUser(String id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User not found to delete.."));
		userRepo.delete(user);
		return id+"User deleted Successfully...";
	}
	
	@Override
	public boolean isUserExist(String id) {
		Optional<User> opt = userRepo.findById(id);
		if(opt.isPresent())
		  return true;
		else
		  return false;
	}
	
	@Override
	public boolean isUserExistsByEmail(String email) {
		Optional<User> opt = userRepo.findByEmail(email);
		
		if(opt.isPresent())
		 return true;
		else
	     return false;
	}
	
	@Override
	public List<User> getAllUser() {
		List<User> list = userRepo.findAll();
		return list;
	}
	
	@Override
	public User getUserByEmail(String email) {

		return userRepo.findByEmail(email).orElse(null);
	}
}
