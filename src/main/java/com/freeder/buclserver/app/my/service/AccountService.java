package com.freeder.buclserver.app.my.service;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.useraccount.entity.UserAccount;
import com.freeder.buclserver.domain.useraccount.repository.UserAccountRepository;
import com.freeder.buclserver.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final UserAccountRepository userAccountRepository;

    public BaseResponse<?> getUserAccount(CustomUserDetails userDetails) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUserID(Long.parseLong(userDetails.getUserId()));

                return new BaseResponse<>(
                        userAccount.orElse(null),
                        HttpStatus.OK,
                        userAccount.isPresent() ? "계좌있음" : "계좌없음"
                );
    }
}
