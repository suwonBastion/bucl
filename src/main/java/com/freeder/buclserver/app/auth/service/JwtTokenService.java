package com.freeder.buclserver.app.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeder.buclserver.app.auth.dto.response.TokenResponse;
import com.freeder.buclserver.core.security.JwtTokenProvider;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.auth.RefreshTokenNotFoundException;
import com.freeder.buclserver.global.exception.user.UserIdNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtTokenService {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	@Transactional
	public TokenResponse createJwtTokens(Long userId, Role role) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserIdNotFoundException(userId));

		String accessToken = jwtTokenProvider.createAccessToken(userId, role);
		String refreshToken = jwtTokenProvider.createRefreshToken(userId, role);

		user.updateRefreshToken(refreshToken);

		return TokenResponse.of(accessToken, refreshToken);
	}

	@Transactional
	public TokenResponse renewTokens(String refreshToken) {
		jwtTokenProvider.validateToken(refreshToken);

		User user = userRepository.findByRefreshToken(refreshToken)
			.orElseThrow(RefreshTokenNotFoundException::new);

		user.deleteRefreshToken();

		return createJwtTokens(
			user.getId(),
			Role.valueOf(jwtTokenProvider.getUserRole(refreshToken))
		);
	}
}
