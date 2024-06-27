package com.freeder.buclserver.core.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.freeder.buclserver.core.security.JwtAccessDeniedHandler;
import com.freeder.buclserver.core.security.JwtAuthenticationEntryPoint;
import com.freeder.buclserver.core.security.JwtAuthenticationFilter;
import com.freeder.buclserver.core.security.JwtExceptionFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtExceptionFilter jwtExceptionFilter;

	// spring boot 3.2.4 updateCode
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				//.httpBasic(HttpBasicConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				//.formLogin(Customizer.withDefaults())
				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
						SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/login/kakao").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/renewal/tokens").permitAll()
						.requestMatchers("/api/v1/token").permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.accessDeniedHandler(jwtAccessDeniedHandler)
				)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())
				.build();
	}


//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//				.csrf(AbstractHttpConfigurer::disable)
//				.authorizeHttpRequests(authorize -> authorize
//						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
//						.requestMatchers("/api/auth/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
//						.requestMatchers("/v3/api-docs/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
//						.requestMatchers("/swagger-ui/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
//						.requestMatchers(HttpMethod.GET, "/api/post/**").permitAll()
//						.requestMatchers(HttpMethod.GET, "/api/posts").permitAll()
//						.anyRequest().authenticated()
//				);
//
//		http
//				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//				.addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass());
//		return http.build();
//	}

}
