package com.freeder.buclserver.global.webclient;

import com.freeder.buclserver.app.auth.dto.response.KakaoUserInfoResponse;
import com.freeder.buclserver.domain.openbanking.dto.OpenBankingAccessTokenDto;
import com.freeder.buclserver.domain.openbanking.dto.OpenBankingAccountValidDto;
import com.freeder.buclserver.domain.openbanking.dto.ReqApiDto;
import com.freeder.buclserver.domain.openbanking.dto.SendApiDto;
import com.freeder.buclserver.domain.openbanking.vo.BANK_CODE;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.util.DateUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenApiClient {
    @Value("${openbanking.api.base-url}")
    private String openBankingApiBaseUrl;
    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(openBankingApiBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    //OPEN API 금감원 계좌본인인증
    public OpenBankingAccountValidDto accountValid(OpenBankingAccessTokenDto dto, ReqApiDto reqApiDto) {
        String token = "Bearer " + dto.getAccess_token();
        return webClient.post()
                .uri("/v2.0/inquiry/real_name")
                .header("Authorization", token)
                .bodyValue(SendApiDto.merge(dto,reqApiDto))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorMsg -> {
                                    log.error("금감원 API 서버오류 : {}",errorMsg);
                                    return Mono.error(new BaseException(HttpStatus.BAD_REQUEST, 400, "금감원 API 서버오류"));
                                }))
                .bodyToMono(OpenBankingAccountValidDto.class)
                .retry(3)
                .block();
    }
}
