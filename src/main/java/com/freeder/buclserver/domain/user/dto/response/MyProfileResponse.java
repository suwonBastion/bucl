package com.freeder.buclserver.domain.user.dto.response;

public record MyProfileResponse(
	String profilePath,
	String nickname,
	Integer rewardSum
) {

	public static MyProfileResponse of(String profilePath, String nickname, Integer rewardSum) {
		return new MyProfileResponse(profilePath, nickname, rewardSum);
	}
}
