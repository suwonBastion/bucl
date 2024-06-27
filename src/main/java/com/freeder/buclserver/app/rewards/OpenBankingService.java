package com.freeder.buclserver.app.rewards;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.openbanking.dto.OpenBankingAccessTokenDto;
import com.freeder.buclserver.domain.openbanking.dto.OpenBankingAccountValidDto;
import com.freeder.buclserver.domain.openbanking.dto.ReqApiDto;
import com.freeder.buclserver.domain.openbanking.entity.OpenBankingAccessToken;
import com.freeder.buclserver.domain.openbanking.repository.AccessTokenRepository;
import com.freeder.buclserver.domain.useraccount.entity.UserAccount;
import com.freeder.buclserver.domain.useraccount.repository.UserAccountRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.webclient.OpenApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenBankingService {

    private final AccessTokenRepository accessTokenRepository;
    private final UserAccountRepository userAccountRepository;
    private final OpenApiClient openApiClient;

    @Value("${openbanking.client_id}")
    private String clientId;

    @Value("${openbanking.client_secret}")
    private String clientSecret;

    @Value("${openbanking.api.base-url}")
    private String openBankingApiBaseUrl;

    @Value("${openbanking.redirect.uri}")
    private String redirectUri;

    @Transactional
    public void requestOpenApiUserCertification() {
        try {
            String stateValue = UUID.randomUUID().toString().replace("-", "");

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(
                            openBankingApiBaseUrl + "/oauth/2.0/authorize")
                    .queryParam("response_type", "code")
                    .queryParam("client_id", clientId)
                    .queryParam("redirect_uri", redirectUri)
                    .queryParam("scope", "login inquiry transfer")
                    .queryParam("state", stateValue)
                    .queryParam("auth_type", "0");

            // 사용자를 리디렉션하기 위한 URL
            String authorizationUrl = builder.toUriString();

            System.out.println("authorizationUrl = " + authorizationUrl);
        } catch (Exception e) {
            throw new RuntimeException("Open Banking API 인증 요청 중 오류 발생", e);
        }
    }

    @Transactional
    public boolean requestOpenApiAccessToken(CustomUserDetails userDetails, ReqApiDto reqApiDto) {
        try {
            RestTemplate rest = new RestTemplate();
            URI uri = URI.create(openBankingApiBaseUrl + "/oauth/2.0/token");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("scope", "sa");
            body.add("grant_type", "client_credentials");

            String now = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString();

            OpenBankingAccessToken existingToken = accessTokenRepository.findFirstByExpireDateAfter(now);
            if (existingToken != null) {

                OpenBankingAccessTokenDto openBankingAccessTokenDto =
                        mapToDto(existingToken);

                OpenBankingAccountValidDto openBankingAccountValidDto =
                        openApiClient.accountValid(openBankingAccessTokenDto, reqApiDto);

                return userValid(userDetails, reqApiDto, openBankingAccountValidDto);

            } else {
                OpenBankingAccessTokenDto openBankingAccessTokenDto;
                try {
                    openBankingAccessTokenDto = rest.postForObject(
                            uri,
                            new HttpEntity<>(body, headers),
                            OpenBankingAccessTokenDto.class
                    );
                } catch (Exception e) {
                    log.error("Error during token retrieval", e);
                    throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage());
                }
                accessTokenRepository.save(openBankingAccessTokenDto.toEntity());

                OpenBankingAccountValidDto openBankingAccountValidDto =
                        openApiClient.accountValid(openBankingAccessTokenDto, reqApiDto);

                return userValid(userDetails, reqApiDto, openBankingAccountValidDto);
            }
        } catch (Exception e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage());
        }
    }

    private OpenBankingAccessTokenDto mapToDto(OpenBankingAccessToken token) {
        return new OpenBankingAccessTokenDto(
                token.getAccessToken(),
                token.getTokenType(),
                token.getExpireDate(),
                token.getScope(),
                token.getClientUseCode()
        );
    }

    /**
     * 유저 검증 후 계좌내용 DB에 저장
     * @param userDetails
     * @param reqApiDto
     * @param openBankingAccountValidDto
     * @return 문제없으면 결과리턴
     */
    private boolean userValid(
            CustomUserDetails userDetails,
            ReqApiDto reqApiDto,
            OpenBankingAccountValidDto openBankingAccountValidDto
    ) {
        if (!openBankingAccountValidDto.account_holder_name().equals(reqApiDto.name())){
            throw new BaseException(HttpStatus.BAD_REQUEST,400,"계좌 실명이 다릅니다.");
        }
        if (!openBankingAccountValidDto.account_holder_info().equals(reqApiDto.birth())){
            throw new BaseException(HttpStatus.BAD_REQUEST,400,"계좌 생년월일이 다릅니다.");
        }

        Optional<UserAccount> userAccount = userAccountRepository.findById(Long.valueOf(userDetails.getUserId()));

        if (userAccount.isEmpty()){
            userAccountRepository.save(UserAccount.setEntity(userDetails, reqApiDto));
            return true;
        }

        updateUserAccount(userAccount.get(),reqApiDto);

        return true;
    }

    private void updateUserAccount(UserAccount userAccount,ReqApiDto reqApiDto){
        userAccount.setAccount(reqApiDto.account());
        userAccount.setBankName(reqApiDto.bankNm());
    }

}
