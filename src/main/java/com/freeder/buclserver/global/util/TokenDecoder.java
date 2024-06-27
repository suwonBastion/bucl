package com.freeder.buclserver.global.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeder.buclserver.app.auth.dto.response.AppleUserInfoResponse;

import java.util.Base64;

public class TokenDecoder {

    public static AppleUserInfoResponse decodePayload(String token, Class<AppleUserInfoResponse> appleUserDtoClass) {

        String[] tokenParts = token.split("\\.");
        String payloadJWT = tokenParts[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(payloadJWT));
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(payload, appleUserDtoClass);
        } catch (Exception e) {
            throw new RuntimeException("Error decoding token payload", e);
        }
    }
}
