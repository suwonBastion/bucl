package com.freeder.buclserver.core.security;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeder.buclserver.global.exception.auth.JwtTokenExpiredException;
import com.freeder.buclserver.global.exception.auth.JwtTokenValidException;
import com.freeder.buclserver.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtTokenExpiredException ex) {
			setErrorResponse(response, ex.getErrorMessage());
		} catch (JwtTokenValidException ex) {
			setErrorResponse(response, ex.getErrorMessage());
		}
	}

	private void setErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
		log.error("[JWT_Exception_Filter]: " + errorMessage);

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage);
		new ObjectMapper().writeValue(response.getWriter(), errorResponse);
	}
}
