package com.tejas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tejas.repository.IUserRepo;

@Service
public class SecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from db 
		return repo.findUserByEmail(username);
	}
}
