package web.jwt;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		
		this.authenticationManager = authenticationManager;
	
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			UsernameAndPasswordAuthenticationRequest authenticationRequest=
			new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
			Authentication authentication= new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
					, authenticationRequest.getPassword());
			return authenticationManager.authenticate(authentication);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String key="123456781234567812345678123456781234567812345678";
		String token=
			Jwts.builder()
		.setSubject(authResult.getName())
		.claim("authorities", authResult.getAuthorities())
		.setIssuedAt(new Date())
		.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
		.signWith(Keys.hmacShaKeyFor(key.getBytes()))
		.compact();
		
		 
		response.addHeader("authorization", "Bearer "+ token);
		
		
		
		
		
	}
	
	
}
