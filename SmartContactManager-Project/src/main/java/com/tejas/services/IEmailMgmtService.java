package com.tejas.services;

public interface IEmailMgmtService {

	public void sendEmail(String to, String subject, String body);
}
