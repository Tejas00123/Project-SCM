package com.tejas.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
		/*
		 * DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
		 * 
		 * logger.info("From oauthenticationSuccessHandler"); //
		 * logger.info(user.getName()); // // //
		 * user.getAttributes().forEach((key,value)->{ //
		 * logger.info("{}=>{}",key,value); // });
		 * 
		 * User user1 = new User(); user1.setEmail(user.getAttribute("email"));
		 * user1.setName(user.getAttribute("name"));
		 * user1.setProfilePic(user.getAttribute("picture"));
		 * user1.setPassword("Pass@123"); user1.setUserId(UUID.randomUUID().toString());
		 * user1.setProvider(Providers.Google); user1.setEnabled(true);
		 * user1.setProviderUserId(user.getName());
		 * 
		 * user1.setAbout("This account is created by using google");
		 * user1.setEmailVerified(true);
		 * user1.setRoleList(List.of(AppConstant.ROLE_USER));
		 * 
		 * String email=user.getAttribute("email");
		 * 
		 * User user2 = userRepo.findByEmail(email).orElse(null); if(user2==null) {
		 * userRepo.save(user1); logger.info("Saved user "+email); }
		 */
		
		 var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
		String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
		
		logger.info(authorizedClientRegistrationId);
		
		DefaultOAuth2User oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
		
		oauthUser.getAttributes().forEach((key,value)->{
			logger.info(key,value);
		});
		
		User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstant.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");
        
		if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
			 user.setEmail(oauthUser.getAttribute("email").toString());
	         user.setProfilePic(oauthUser.getAttribute("picture").toString());
	         user.setName(oauthUser.getAttribute("name").toString());
	         user.setProviderUserId(oauthUser.getName());
	         user.setProvider(Providers.Google);
	         user.setAbout("This account is created using google.");

		}
		else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
			String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.Github);

            user.setAbout("This account is created using github");
		}
		else {
			logger.info("unknown provider");
		}
		
		
		
		 
		 User user2 = userRepo.findByEmail(user.getEmail()).orElse(null); 
		 if(user2==null) {
		 userRepo.save(user); 
		 logger.info("Saved user "+user.getEmail());
		 }
		 
		 
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}
}
