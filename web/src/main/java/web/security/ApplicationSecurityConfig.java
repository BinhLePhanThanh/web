package web.security;


import java.util.Arrays;
import java.util.Collections;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import web.auth.ApplicationUserService;
import web.entity.Permission;
import web.jwt.JwtTokenVerifier;
import web.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder; 
	@Autowired
	private ApplicationUserService applicationUserService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.cors().and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
		.addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/employee/**")
		.hasAnyRole(web.entity.Role.EMPLOYEE.name(),web.entity.Role.ADMIN.name())
		.antMatchers("/admin/**")
		.hasRole(web.entity.Role.ADMIN.name())
		.anyRequest()
		.authenticated();
		
		
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	{
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
		
	}
	
	
	
}
