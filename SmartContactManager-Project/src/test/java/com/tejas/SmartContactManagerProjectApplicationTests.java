package com.tejas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tejas.services.IEmailMgmtService;

@SpringBootTest
class SmartContactManagerProjectApplicationTests {
    @Autowired
	private IEmailMgmtService service;
	@Test
	void contextLoads() {
	}

	@Test
	void sendEmial() {
		service.sendEmail("tejasgavali8280@gmail.com", "Testing mail", "Mail from SCM project");
	}
}
