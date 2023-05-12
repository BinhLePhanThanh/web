package web.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.var;

public class JwtTokenVerifier extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization=request.getHeader("Authorization");
		if(Strings.isNullOrEmpty(authorization)||!authorization.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		String token=authorization.replace("Bearer ", "");
		try {
			String secretKey ="123456781234567812345678123456781234567812345678";
			
			Jws<Claims> claimJws= 
					Jwts.parser()
					.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
					.parseClaimsJws(token);
			
			Claims body=claimJws.getBody();
			String username=body.getSubject();
			var authorities=(List<Map<String, String>>)body.get("authorities");
			
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities= authorities.stream()
					
					.map(m-> new SimpleGrantedAuthority(m.get("authority")))
					.collect(Collectors.toSet());
			
			
			Authentication authentication= new UsernamePasswordAuthenticationToken(username, 
					null,
					simpleGrantedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			
		} catch (JwtException e) {
			throw new IllegalStateException("token cannot be trusted "+token);
		}
		filterChain.doFilter(request, response);
				
	}
	

}
