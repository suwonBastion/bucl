package com.freeder.buclserver.app.rewards;

import com.freeder.buclserver.domain.reward.entity.Reward;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.reward.vo.RewardType;
import com.freeder.buclserver.domain.rewardwithdrawal.dto.WithdrawalHistoryDto;
import com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal;
import com.freeder.buclserver.domain.rewardwithdrawal.repository.RewardWithdrawalRepository;
import com.freeder.buclserver.domain.rewardwithdrawal.vo.WithdrawalStatus;
import com.freeder.buclserver.global.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RewardsWithdrawalService {

	@Value("${bucl_system.reward.withdrawal.minimum_amt}")
	private int withdrawalMinimumAmt;
	private final RewardWithdrawalRepository rewardWithdrawalRepository;
	private final RewardRepository rewardRepository;

	@Autowired
	public RewardsWithdrawalService(RewardWithdrawalRepository rewardWithdrawalRepository,
		RewardRepository rewardRepository) {
		this.rewardWithdrawalRepository = rewardWithdrawalRepository;
		this.rewardRepository = rewardRepository;
	}

	@Transactional
	public void withdrawReward(Long userId, String bankCodeStd, String bankName, Integer withdrawalAmount,
		String accountNum, String accountHolderName) {
		try {
			// 현재 리워드 정보 조회
			Reward reward = rewardRepository.findRewardsByUserId(userId)
				.orElseThrow(() -> {
					log.error("해당 사용자에 대한 리워드 정보를 찾을 수 없습니다.");
					return new BaseException(HttpStatus.NOT_FOUND, 404, "해당 사용자에 대한 리워드 정보를 찾을 수 없습니다.");
				});

			Integer currentRewardAmount = reward.getRewardSum();

			// 인출 가능한 최소 리워드는 5000 이상이어야 합니다.
			if (withdrawalAmount < withdrawalMinimumAmt) {
				throw new BaseException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
					"현재 인출 가능한 포인트는 5000P 이상입니다.");
			} else if (currentRewardAmount < withdrawalAmount) {
				throw new BaseException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
					"현재 인출 가능한 포인트는 " + currentRewardAmount + "P 입니다.");
			}

			// 인출 로직
			RewardWithdrawal rewardWithdrawal = RewardWithdrawal.builder()
				.user(reward.getUser())
				.bankCodeStd(bankCodeStd)
				.bankName(bankName)
				.rewardWithdrawalAmount(withdrawalAmount)
				.accountNum(accountNum)
				.accountHolderName(accountHolderName)
				.withdrawalStatus(WithdrawalStatus.WTHDR_WTNG)
				.isWithdrawn(true)
				.lastUsedDate(LocalDateTime.now())
				.build();

			// 리워드 인출 기록 저장
			RewardWithdrawal newRewardWithdrawal = rewardWithdrawalRepository.save(rewardWithdrawal);

			// WITHDRAWAL 리워드 생성 및 저장
			Reward newReward = Reward.builder()
				.user(reward.getUser())
				.previousRewardSum(reward.getRewardSum())
				.receivedRewardAmount(-1 * withdrawalAmount)
				.rewardSum(reward.getRewardSum() - withdrawalAmount)
				.rewardType(RewardType.WITHDRAWAL)
				.spentRewardAmount(withdrawalAmount)
				.rewardWithdrawal(newRewardWithdrawal)
				.build();

			rewardRepository.save(newReward);

			log.info("리워드 인출 성공 - userId: {}, withdrawalAmount: {}", userId, withdrawalAmount);
		} catch (BaseException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			log.error("리워드 인출 실패 - userId: {}, withdrawalAmount: {}, reason: {}", userId, withdrawalAmount,
				e.getMessage());
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage());
		} catch (Exception e) {
			log.error("리워드 인출 실패 - userId: {}, withdrawalAmount: {}", userId, withdrawalAmount, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "리워드 인출 실패");
		}
	}

	public List<WithdrawalHistoryDto> getWithdrawalHistory(Long userId, int page, int pageSize) {
		try {
			PageRequest pageRequest = PageRequest.of(page, pageSize);
			List<RewardWithdrawal> withdrawalList = rewardWithdrawalRepository.findByUserIdOrderByLastUsedDateDesc(
				userId, pageRequest);

			if (withdrawalList != null) {
				List<WithdrawalHistoryDto> withdrawalHistory = withdrawalList
					.stream()
					.map(this::convertToDto)
					.collect(Collectors.toList());

				log.info("리워드 인출 내역 조회 성공 - userId: {}, page: {}, pageSize: {}", userId, page, pageSize);
				return withdrawalHistory;
			} else {
				log.warn("리워드 인출 내역이 존재하지 않습니다. - userId: {}, page: {}, pageSize: {}", userId, page, pageSize);
				return Collections.emptyList();
			}
		} catch (BaseException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw new BaseException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 유형");
		} catch (DataAccessException e) {
			log.error("데이터 액세스 오류 발생 - userId: {}, page: {}, pageSize: {}", userId, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "데이터 액세스 오류");
		} catch (Exception e) {
			log.error("리워드 인출 내역 조회 실패 - userId: {}, page: {}, pageSize: {}", userId, page, pageSize, e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "인출내역 조회 실패");
		}
	}

	private WithdrawalHistoryDto convertToDto(RewardWithdrawal rewardWithdrawal) {
		try {
			WithdrawalHistoryDto dto = new WithdrawalHistoryDto();
			dto.setRewardWithdrawalAmount(rewardWithdrawal.getRewardWithdrawalAmount());
			dto.setWithdrawalStatus(rewardWithdrawal.getWithdrawalStatus());
			dto.setLastUsedDate(rewardWithdrawal.getLastUsedDate());
			return dto;
		} catch (Exception e) {
			log.error("DTO 변환 실패", e);
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "인출내역 조회 - DTO 변환 실패");
		}
	}
}
