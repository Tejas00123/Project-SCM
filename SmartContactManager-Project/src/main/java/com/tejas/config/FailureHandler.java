package com.tejas.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.tejas.helper.Message;
import com.tejas.helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class FailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
      if(exception instanceof DisabledException) {
    	  HttpSession session = request.getSession();
          session.setAttribute("message", Message.builder()
        		  .content("User is disabled...")
        		  .type(MessageType.danger)
        		  .build());
          
          response.sendRedirect("/login");
      }
      else {
    	  response.sendRedirect("/login?error=true");
      }
	}

}
