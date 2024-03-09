package com.maradamark99.thesis.auth;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.common.net.HttpHeaders;
import com.maradamark99.thesis.user.UserRepository;
import com.maradamark99.thesis.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null 
				|| (authHeader != null && !authHeader.startsWith(JWTUtil.TOKEN_PREFIX))) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final var token = authHeader.substring(JWTUtil.TOKEN_PREFIX.length());
		final var authentication = getAuthentication(token);

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String encodedToken) {
		var decodedToken = JWTUtil.decodeToken(encodedToken);
		var subject = decodedToken.getSubject();

		var user = userRepository.findByEmail(subject).orElse(null);
		var roles = user.getRoles()
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getRole()))
				.collect(Collectors.toSet());

		return new UsernamePasswordAuthenticationToken(
				user, null,
				user == null ? Collections.emptySet() : roles);
	}

}
