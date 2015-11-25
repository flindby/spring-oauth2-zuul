package com.example;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Filip Lindby
 *
 */
@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class Ui1Application extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Ui1Application.class, args);
	}

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http
//			.logout()
//		.and().authorizeRequests()
//			.antMatchers("/index.html", "/home.html", "/").permitAll()				
//			.anyRequest().authenticated()
//
//		.and().httpBasic().disable();
		
		
		http
			.logout()
		.and()
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/index.html", "/home.html", "/"/*, "/login"*/).permitAll()
			.anyRequest().authenticated()
	
		// We use OAuth2 SSO, so we don't need basic auth
		.and().httpBasic().disable();
	}
}

//Remove this when upgrading to Spring Boot 1.3.1 (https://github.com/spring-projects/spring-boot/issues/4553)
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class WorkaroundRestTemplateCustomizer implements UserInfoRestTemplateCustomizer {

	@Override
	public void customize(OAuth2RestTemplate template) {
		template.setInterceptors(new ArrayList<>(template.getInterceptors()));
	}

}