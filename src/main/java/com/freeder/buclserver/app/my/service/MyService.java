package com.freeder.buclserver.app.my.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerorder.repository.ConsumerOrderRepository;
import com.freeder.buclserver.domain.consumerpayment.entity.ConsumerPayment;
import com.freeder.buclserver.domain.consumerpayment.repository.ConsumerPaymentRepository;
import com.freeder.buclserver.domain.consumerpurchaseorder.repository.ConsumerPurchaseOrderRepository;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress;
import com.freeder.buclserver.domain.shippingaddress.repository.ShippingAddressRepository;
import com.freeder.buclserver.domain.user.dto.UserDto;
import com.freeder.buclserver.domain.user.dto.response.MyOrderDetailResponse;
import com.freeder.buclserver.domain.user.dto.response.MyOrderResponse;
import com.freeder.buclserver.domain.user.dto.response.MyProfileResponse;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.user.util.ProfileImage;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.exception.auth.LogoutUserWithdrawalException;
import com.freeder.buclserver.global.exception.consumerorder.ConsumerUserNotMatchException;
import com.freeder.buclserver.global.exception.consumerorder.OrderIdNotFoundException;
import com.freeder.buclserver.global.exception.user.UserIdNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MyService {

	private final UserRepository userRepository;
	private final RewardRepository rewardRepository;
	private final ConsumerOrderRepository consumerOrderRepository;
	private final ConsumerPurchaseOrderRepository consumerPurchaseOrderRepository;
	private final ShippingRepository shippingRepository;
	private final ShippingAddressRepository shippingAddressRepository;
	private final ConsumerPaymentRepository consumerPaymentRepository;
	private final ProfileS3Service profileS3Service;

	@Transactional(readOnly = true)
	public Optional<UserDto> findBySocialIdAndDeletedAtIsNull(String socialUid) {
		return userRepository.findBySocialIdAndDeletedAtIsNull(socialUid)
			.map(UserDto::from);
	}

	@Transactional
	public UserDto join(UserDto userDto) {
		User user = userRepository.save(userDto.toEntity());
		return UserDto.from(user);
	}

	@Transactional
	public void deleteRefreshToken(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserIdNotFoundException(userId));

		user.deleteRefreshToken();
	}

	@Transactional
	public void withdrawal(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserIdNotFoundException(userId));

		if (user.getRefreshToken() == null) {
			throw new LogoutUserWithdrawalException();
		}

		user.withdrawal();
	}

	@Transactional(readOnly = true)
	public MyProfileResponse getMyProfile(Long userId) {
		User user = userRepository.findByIdAndDeletedAtIsNull(userId)
			.orElseThrow(() -> new UserIdNotFoundException(userId));

		try {
			List<Integer> rewardSum = rewardRepository.findUserRewardSum(userId, PageRequest.of(0, 1));
			return rewardSum.isEmpty()
				? MyProfileResponse.of(user.getProfilePath(), user.getNickname(), 0)
				: MyProfileResponse.of(user.getProfilePath(), user.getNickname(), rewardSum.get(0));
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "");
		}
	}

	@Transactional
	public void updateProfileImageAsDefault(Long userId) {
		try {
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			profileS3Service.deleteFile(user.getProfilePath());

			user.updateProfilePathAsDefault();
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional
	public void updateProfileImage(Long userId, MultipartFile profileImageFile) {
		try {
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			if (!Objects.equals(user.getProfilePath(), ProfileImage.defaultImageUrl)) {
				profileS3Service.deleteFile(user.getProfilePath());
			}

			String uploadFileUrl = profileS3Service.uploadFile(profileImageFile);
			user.updateProfilePath(uploadFileUrl);
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public List<MyOrderResponse> getMyOrders(Long userId, int page, int pageSize) {
		try {
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			Pageable pageable = PageRequest.of(page - 1, pageSize);

			return consumerOrderRepository.findAllByConsumerOrderByCreatedAtDesc(user, pageable).stream()
				.filter(consumerOrder -> consumerOrder.getConsumer().getId() == userId)
				.map(this::getMyOrderInfo)
				.collect(Collectors.toUnmodifiableList());
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public MyOrderDetailResponse getMyOrderDetail(Long userId, Long consumerOrderId) {
		try {
			if (!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
				throw new UserIdNotFoundException(userId);
			}

			ConsumerOrder consumerOrder = consumerOrderRepository.findById(consumerOrderId)
				.orElseThrow(() -> new OrderIdNotFoundException(consumerOrderId));

			if (consumerOrder.getConsumer().getId() != userId) {
				throw new ConsumerUserNotMatchException();
			}

			int productOrderQty = consumerPurchaseOrderRepository.findProductOrderQty(consumerOrder.getId());
			ConsumerPayment payment = consumerPaymentRepository.findByConsumerOrder(consumerOrder);

			return shippingRepository.findByConsumerOrderAndIsActiveIsTrue(consumerOrder)
				.map(shipping -> {
					ShippingAddress shippingAddress = shippingAddressRepository.findByShipping(shipping);
					return MyOrderDetailResponse.from(consumerOrder, shippingAddress, payment, productOrderQty);
				})
				.orElseGet(() -> MyOrderDetailResponse.from(consumerOrder, null, payment, productOrderQty));
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	private MyOrderResponse getMyOrderInfo(ConsumerOrder consumerOrder) {
		int productOrderQty = consumerPurchaseOrderRepository.findProductOrderQty(consumerOrder.getId());

		return shippingRepository.findByConsumerOrderAndIsActiveIsTrue(consumerOrder)
			.map(shipping -> MyOrderResponse.from(consumerOrder, productOrderQty, shipping))
			.orElseGet(() -> MyOrderResponse.from(consumerOrder, productOrderQty, null));
	}
}
