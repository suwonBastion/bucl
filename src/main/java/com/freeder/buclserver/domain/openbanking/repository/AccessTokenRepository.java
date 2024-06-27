package com.freeder.buclserver.domain.openbanking.repository;

import com.freeder.buclserver.domain.openbanking.entity.OpenBankingAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<OpenBankingAccessToken, Long> {

	OpenBankingAccessToken findFirstByExpireDateAfter(String expireDate);

	OpenBankingAccessToken save(OpenBankingAccessToken accessToken);
}
