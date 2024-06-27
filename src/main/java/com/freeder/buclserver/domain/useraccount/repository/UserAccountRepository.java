package com.freeder.buclserver.domain.useraccount.repository;

import com.freeder.buclserver.domain.useraccount.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    @Query(value = "SELECT * FROM user_account WHERE user_id = :id LIMIT 1",nativeQuery = true)
    Optional<UserAccount> findByUserID(@Param("id") Long id);
}
