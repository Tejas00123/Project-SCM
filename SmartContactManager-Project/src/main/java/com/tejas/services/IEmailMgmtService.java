package com.tejas.services;

public interface IEmailMgmtService {

	public void sendEmail(String to, String subject, String body);
	
	//below 2 method are for feedback and direct message we using
	//verication controller for below method
	public void feedBackEmail(String name,String email,String message);
	
	public void directMailToContact(String name,String to,String message);
}
