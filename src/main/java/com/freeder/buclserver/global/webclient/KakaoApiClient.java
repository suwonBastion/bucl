package com.freeder.buclserver.global.webclient;

import com.freeder.buclserver.app.auth.dto.response.KakaoUserInfoResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class KakaoApiClient {

    private WebClient webClient;

    @PostConstruct
    public void init(){
        this.webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    //카카오 OAuth API
    public Mono<KakaoUserInfoResponse> kakaoGetUser(String raw) {
        String token = "Bearer " + raw;
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorMsg -> Mono.error(new RuntimeException(errorMsg))))
                .bodyToMono(KakaoUserInfoResponse.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(TimeoutException.class, e -> new RuntimeException("카카오로그인 인증서버가 응답이 없습니다."));
    }
}
