package com.freeder.buclserver.app.auth.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.freeder.buclserver.domain.user.dto.UserDto;
import com.freeder.buclserver.domain.user.util.ProfileImage;
import com.freeder.buclserver.domain.user.vo.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppleUserInfoResponse {

    private String sub;

    private String email;

    public UserDto toUserDto() {
        return UserDto.of(
                null,
                this.email,
                null,
                ProfileImage.defaultImageUrl,
                null,
                null,
                Role.ROLE_USER,
                JoinType.APPLE,
                UserState.ACTIVE,
                UserGrade.BASIC,
                Gender.MALE,
                null,
                this.sub,
                null,
                null
        );
    }





}
