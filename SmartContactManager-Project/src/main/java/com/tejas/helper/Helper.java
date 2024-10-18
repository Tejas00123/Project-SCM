package com.tejas.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {
  public static String getEmailOfLoggedUesr(Authentication authentication) {
	  if(authentication instanceof OAuth2AuthenticationToken) {
		 //typecasting authentication to oatu2authtoken to get client id 
		var oauth2AuthenicationToken = (OAuth2AuthenticationToken)authentication;
		//getting client id to identify which oauth client
		//using user is logged in
		var authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
	   //getting logged user details and storing into oauth2USer
		var oauth2User = (OAuth2User) authentication.getPrincipal();
	     
	    String username ="";
	    
		if(authorizedClientRegistrationId.equalsIgnoreCase("google"))
		{
			System.out.println("user logged using google");
			username = oauth2User.getAttribute("email").toString();
		}
		else if(authorizedClientRegistrationId.equalsIgnoreCase("github"))
		{
			System.out.println("User logged using github");
			username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                    : oauth2User.getAttribute("login").toString() + "@gmail.com";
		}
		return username;
     }//end of if
	  else
     {
    	 return authentication.getName();
     }//end of else
  }
  
  public static String generatingLinkForEmailVerification(String emailToken) {
	  String link = "http://localhost:4041/oauth/verify_email?token="+emailToken;
	  return link;
  }
}
