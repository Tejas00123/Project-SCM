package com.tejas;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class SmartContactManagerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerProjectApplication.class, args);
	}
	
	@Bean(name="localeResolver")
	public SessionLocaleResolver createSLResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}
}
