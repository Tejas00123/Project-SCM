package com.tejas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tejas.entity.User;
import com.tejas.helper.Message;
import com.tejas.helper.MessageType;
import com.tejas.repository.IUserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/oauth")
public class UserVerificationController {
    @Autowired
	private IUserRepo  userRepo;
	
	@RequestMapping("/verify_email")
	public String emailVerificationProcessing(@RequestParam("token") String email_token,HttpSession session) {
		System.out.println("Verification done...");
		
		User user = userRepo.getByEmailToken(email_token).orElse(null);

		if(user!=null)
		{
		    if(user.getEmailToken().equals(email_token))
		    {
		    	user.setEnabled(true);
		    	user.setEmailVerified(true);
		    	userRepo.save(user);
		    	
		    	Message message = Message.builder()
		    	.type(MessageType.success)
		    	.content("User verified successfully...!")
		    	.build();
		    	session.setAttribute("message", message);
		    	return "success_page";
		    }
		    session.setAttribute("message", Message.builder().content("Email is not verified...").type(MessageType.danger).build());
		    return "error_page";
		}
	    session.setAttribute("message", Message.builder().content("Email is not verified...").type(MessageType.danger).build());

		return "error_page";
	}
}
