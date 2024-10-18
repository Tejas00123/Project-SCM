package com.tejas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tejas.services.SecurityUserDetailsService;

@Configuration
public class SecurityConfig {
    @Autowired
	private SecurityUserDetailsService userDetailsService;
    
    @Autowired
    private Oauth2AuthenticationSuccessHandler handler;
    
    @Autowired
    private FailureHandler failureHandler;
    
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		//giving userDetailsService object to daoauthprovider i.e.it gives data from db
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		//and giving password encoder object to authprovider
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	      
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests((authorize)->{
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});
		
 
		//
		httpSecurity.formLogin((formLogin)->{
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/profile");
			//formLogin.failureForwardUrl("/login?error=true");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
	        formLogin.failureHandler(failureHandler);
		});
		
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
	   httpSecurity.oauth2Login((oauth2Login)->{
		   oauth2Login.loginPage("/login");
		   oauth2Login.successHandler(handler);
	   });
	   
		
		
		httpSecurity.logout((logout)->{
			logout.logoutUrl("/logout");
			logout.logoutSuccessUrl("/login?logout=true");
		});
		
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
