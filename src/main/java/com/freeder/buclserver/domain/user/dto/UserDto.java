package com.freeder.buclserver.domain.user.dto;

import java.time.LocalDateTime;

import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.vo.Gender;
import com.freeder.buclserver.domain.user.vo.JoinType;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.domain.user.vo.UserGrade;
import com.freeder.buclserver.domain.user.vo.UserState;

public record UserDto(
	Long id,
	String email,
	String nickname,
	String profilePath,
	Boolean isAlarmed,
	String cellPhone,
	Role role,
	JoinType joinType,
	UserState userState,
	UserGrade userGrade,
	Gender gender,
	LocalDateTime birthDate,
	String socialId,
	String refreshToken,
	LocalDateTime deletedAt
) {

	public static UserDto of(
		Long id, String email, String nickname, String profilePath, Boolean isAlarmed, String cellPhone,
		Role role, JoinType joinType, UserState userState, UserGrade userGrade, Gender gender, LocalDateTime birthDate,
		String socialId, String refreshToken, LocalDateTime deletedAt
	) {
		return new UserDto(
			id, email, nickname, profilePath, isAlarmed, cellPhone, role, joinType,
			userState, userGrade, gender, birthDate, socialId, refreshToken, deletedAt
		);
	}

	public static UserDto from(User user) {
		return of(
			user.getId(),
			user.getEmail(),
			user.getNickname(),
			user.getProfilePath(),
			user.getIsAlarmed(),
			user.getCellPhone(),
			user.getRole(),
			user.getJoinType(),
			user.getUserState(),
			user.getUserGrade(),
			user.getGender(),
			user.getBirthDate(),
			user.getSocialId(),
			user.getRefreshToken(),
			user.getDeletedAt()
		);
	}

	public User toEntity() {
		return User.builder()
			.email(this.email)
			.nickname(this.nickname)
			.profilePath(this.profilePath)
			.isAlarmed(this.isAlarmed)
			.cellPhone(this.cellPhone)
			.role(this.role)
			.joinType(this.joinType)
			.userState(this.userState)
			.userGrade(this.userGrade)
			.gender(this.gender)
			.birthDate(this.birthDate)
			.socialId(this.socialId)
			.refreshToken(this.refreshToken)
			.build();
	}
}
