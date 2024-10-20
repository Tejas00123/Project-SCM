package com.tejas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Component
public class MyI18NConfig implements WebMvcConfigurer {

	 @Autowired
	 private LocaleChangeInterceptor interceptor;
	 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(interceptor);
	}
}
