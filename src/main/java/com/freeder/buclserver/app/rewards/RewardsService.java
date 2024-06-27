package com.freeder.buclserver.app.rewards;

import com.freeder.buclserver.domain.reward.dto.RewardDto;
import com.freeder.buclserver.domain.reward.entity.Reward;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.reward.vo.RewardType;
import com.freeder.buclserver.global.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RewardsService {

	@Autowired
	private RewardRepository rewardRepository;

	@Transactional(readOnly = true)
	public Integer getUserRewardCrntAmount(Long userId) {
		try {
			return rewardRepository.findFirstByUserId(userId).orElse(0);
		} catch (DataAccessException e) {
			log.error("데이터 액세스 오류 발생 - userId: {}", userId, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "데이터 액세스 오류");
		} catch (Exception e) {
			log.error("리워드 조회 실패 - userId: {}", userId, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리워드 조회 - 서버 에러");
		}
	}

	@Transactional(readOnly = true)
	public List<RewardDto> getRewardHistoryPageable(Long userId, int page, int pageSize) {
		try {
			Pageable pageable = PageRequest.of(page, pageSize);
			List<Reward> rewardHistory = rewardRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
				.orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 적립금내역을 찾을 수 없음"));

			log.info("적립금 내역 조회 성공 - userId: {}, page: {}, pageSize: {}", userId, page, pageSize);

			return rewardHistory.stream()
				.map(this::mapRewardToDto)
				.collect(Collectors.toList());
		} catch (BaseException e) {
			throw new BaseException(e.getHttpStatus(), e.getErrorCode(), e.getErrorMessage());
		} catch (IllegalArgumentException e) {
			throw new BaseException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 유형");
		} catch (DataAccessException e) {
			log.error("데이터 액세스 오류 발생 - userId: {}, page: {}, pageSize: {}", userId, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "데이터 액세스 오류");
		} catch (Exception e) {
			log.error("적립금 내역 조회 실패 - userId: {}, page: {}, pageSize: {}", userId, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "적립금 내역 조회 - 서버 에러");
		}
	}

	private RewardDto mapRewardToDto(Reward reward) {
		try {
			String productBrandName = reward.getProductBrandName();
			String productName = reward.getProductName();
			int netReward = calculateNetReward(reward);
			RewardType rewardType = reward.getRewardType();
			LocalDateTime createdAt = reward.getCreatedAt();

			return new RewardDto(productBrandName, productName, netReward, rewardType, createdAt);
		} catch (Exception e) {
			log.error("리워드 DTO 변환 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리워드 DTO 변환 실패");
		}
	}

	private int calculateNetReward(Reward reward) {
		try {
			if (RewardType.BUSINESS.equals(reward.getRewardType())
				|| RewardType.CONSUMER.equals(reward.getRewardType())
				|| RewardType.REFUND.equals(reward.getRewardType())) {
				return reward.getReceivedRewardAmount();
			} else if (RewardType.SPEND.equals(reward.getRewardType())
				|| RewardType.WITHDRAWAL.equals(reward.getRewardType())) {
				return -reward.getSpentRewardAmount();
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error("적립금 계산 실패 - userId: {}, rewardId: {}", reward.getUser().getId(), reward.getId(), e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "적립금 계산 실패");
		}
	}

}
