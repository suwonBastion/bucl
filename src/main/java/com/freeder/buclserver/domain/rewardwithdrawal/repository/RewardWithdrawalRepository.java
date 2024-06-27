package com.freeder.buclserver.domain.rewardwithdrawal.repository;

import com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardWithdrawalRepository extends JpaRepository<RewardWithdrawal, Long> {
	List<RewardWithdrawal> findByUserIdOrderByLastUsedDateDesc(Long userId, Pageable pageable);
}
