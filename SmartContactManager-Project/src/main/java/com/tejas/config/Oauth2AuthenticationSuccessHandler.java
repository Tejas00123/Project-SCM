package com.tejas.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tejas.entity.Providers;
import com.tejas.entity.User;
import com.tejas.helper.AppConstant;
import com.tejas.repository.IUserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IUserRepo userRepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		Logger logger = LoggerFactory.getLogger(Oauth2AuthenticationSuccessHandler.class);
		
		DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
		
		logger.info("From oauthenticationSuccessHandler");
//		logger.info(user.getName());
//		
//		
//		user.getAttributes().forEach((key,value)->{
//			logger.info("{}=>{}",key,value);
//		});
		
		User user1 = new User();
		user1.setEmail(user.getAttribute("email"));
		user1.setName(user.getAttribute("name"));
		user1.setProfilePic(user.getAttribute("picture"));
		user1.setPassword("Pass@123");
		user1.setUserId(UUID.randomUUID().toString());
		user1.setProvider(Providers.Google);
		user1.setEnabled(true);
		user1.setProviderUserId(user.getName());
		
		user1.setAbout("This account is created by using google");
		user1.setEmailVerified(true);
		user1.setRoleList(List.of(AppConstant.ROLE_USER));
		
		String email=user.getAttribute("email");
		
		User user2 = userRepo.findByEmail(email).orElse(null);
		if(user2==null) {
			userRepo.save(user1);
			logger.info("Saved user "+email);
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}
}
