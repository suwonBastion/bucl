package com.freeder.buclserver.domain.user.vo;

public enum Gender {
	MALE,
	FEMALE,
	UNDISCLOSED;

	public static Gender parseGenderEnum(String name) {
		if (name.equalsIgnoreCase("male")) {
			return MALE;
		} else if (name.equalsIgnoreCase("female")) {
			return FEMALE;
		}
		return UNDISCLOSED;
	}
}
