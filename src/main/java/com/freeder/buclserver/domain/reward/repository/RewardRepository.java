package com.freeder.buclserver.domain.reward.repository;

import java.util.List;
import java.util.Optional;

import com.freeder.buclserver.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freeder.buclserver.domain.reward.entity.Reward;
import org.springframework.data.repository.query.Param;

public interface RewardRepository extends JpaRepository<Reward, Long> {

	Optional<List<Reward>> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

	@Query(value = "SELECT r.reward_sum FROM Reward r WHERE r.user_id = :userId ORDER BY r.created_at DESC LIMIT 1",
			nativeQuery = true)
	Optional<Integer> findFirstByUserId(@Param("userId") Long userId);

	@Query(value = "SELECT * FROM Reward WHERE user_id = :userId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
	Optional<Reward> findRewardsByUserId(@Param("userId") Long userId);

	@Query("SELECT COALESCE(r.rewardSum, 0) FROM Reward r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
	List<Integer> findUserRewardSum(Long userId, Pageable pageable);

	Optional<Reward> findFirstByUserOrderByCreatedAtDesc(User user);



}
