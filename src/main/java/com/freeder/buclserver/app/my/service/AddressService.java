package com.freeder.buclserver.app.my.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.usershippingaddress.dto.UserShippingAddressDto;
import com.freeder.buclserver.domain.usershippingaddress.dto.request.AddressCreateRequest;
import com.freeder.buclserver.domain.usershippingaddress.dto.request.AddressUpdateRequest;
import com.freeder.buclserver.domain.usershippingaddress.dto.response.AddressCreateResponse;
import com.freeder.buclserver.domain.usershippingaddress.entity.UserShippingAddress;
import com.freeder.buclserver.domain.usershippingaddress.repository.UserShippingAddressRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.exception.user.UserIdNotFoundException;
import com.freeder.buclserver.global.exception.usershippingaddress.AddressIdNotFoundException;
import com.freeder.buclserver.global.exception.usershippingaddress.AddressUserNotMatchException;
import com.freeder.buclserver.global.exception.usershippingaddress.DefaultAddressNotFoundException;
import com.freeder.buclserver.global.exception.usershippingaddress.SingleAddressDefaultRegisterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class AddressService {

	private final UserRepository userRepository;
	private final UserShippingAddressRepository userShippingAddressRepository;

	@Transactional
	public AddressCreateResponse createMyAddress(Long userId, AddressCreateRequest request) {
		try {
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			UserShippingAddress userShippingAddress;

			if (!userShippingAddressRepository.existsByUser(user)) {
				userShippingAddress = createUserAddressEntity(user, request, true);
			} else {
				if (request.isDefaultAddress()) {
					clearExistingDefaultAddress(userId);
				}
				userShippingAddress = createUserAddressEntity(user, request, request.isDefaultAddress());
			}

			UserShippingAddress savedUserShippingAddress = userShippingAddressRepository.save(userShippingAddress);
			return AddressCreateResponse.from(savedUserShippingAddress);
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public List<UserShippingAddressDto> getMyAddressList(Long userId) {
		try {
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			return userShippingAddressRepository.findAllByUser(user).stream()
				.map(UserShippingAddressDto::from)
				.collect(Collectors.toUnmodifiableList());
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional
	public UserShippingAddressDto updateMyAddress(Long userId, Long addressId, AddressUpdateRequest request) {
		try {
			System.out.println("야만1");
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			System.out.println("야만");
			UserShippingAddress userAddress = userShippingAddressRepository.findById(addressId)
				.orElseThrow(() -> new AddressIdNotFoundException(addressId));

			if (userAddress.getUser().getId() != userId) {
				throw new AddressUserNotMatchException();
			}

			if (userShippingAddressRepository.countByUser(user) == 1L) {
				if (!request.isDefaultAddress()) {
					throw new SingleAddressDefaultRegisterException();
				}
			}

			if (!request.isDefaultAddress() && userAddress.isDefaultAddress()) {
				clearExistingDefaultAddress(userId);
				registerLatestAddressAsDefault(user);
			}

			if (request.isDefaultAddress() && !userAddress.isDefaultAddress()) {
				clearExistingDefaultAddress(userId);
			}

			userAddress.updateAll(request);

			return UserShippingAddressDto.from(userAddress);
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}

	}

	@Transactional
	public void deleteMyAddress(Long userId, Long addressId) {
		try {
			User user = userRepository.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new UserIdNotFoundException(userId));

			UserShippingAddress deleteUserAddress = userShippingAddressRepository.findById(addressId)
				.orElseThrow(() -> new AddressIdNotFoundException(addressId));

			if (deleteUserAddress.getUser().getId() != userId) {
				throw new AddressUserNotMatchException();
			}

			userShippingAddressRepository.deleteById(addressId);

			if (deleteUserAddress.isDefaultAddress()) {
				registerLatestAddressAsDefault(user);
			}
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public UserShippingAddressDto getMyDefaultAddress(Long userId) {
		try {
			if (!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
				throw new UserIdNotFoundException(userId);
			}

			UserShippingAddress userAddress = userShippingAddressRepository.findByUserAndIsDefaultAddressIsTrue(userId)
				.orElseThrow(DefaultAddressNotFoundException::new);

			return UserShippingAddressDto.from(userAddress);
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}
	}

	@Transactional
	public UserShippingAddressDto updateMyDefaultAddress(Long userId, Long addressId) {
		try {
			if (!userRepository.existsByIdAndDeletedAtIsNull(userId)) {
				throw new UserIdNotFoundException(userId);
			}

			UserShippingAddress userAddress = userShippingAddressRepository.findById(addressId)
				.orElseThrow(() -> new AddressIdNotFoundException(addressId));

			if (userAddress.getUser().getId() != userId) {
				throw new AddressUserNotMatchException();
			}

			clearExistingDefaultAddress(userId);
			userAddress.registerDefaultAddress();

			return UserShippingAddressDto.from(userAddress);
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			log.error(String.format("{ \"type\": \"error\", \"msg\": %s }", e.getMessage()));
			throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage());
		}

	}

	private void clearExistingDefaultAddress(Long userId) {
		userShippingAddressRepository.findByUserAndIsDefaultAddressIsTrue(userId)
			.ifPresent(userAddress -> userAddress.clearDefaultAddress());
	}

	private void registerLatestAddressAsDefault(User user) {
		userShippingAddressRepository.findFirstByUserOrderByIdDesc(user)
			.ifPresent(address -> address.registerDefaultAddress());
	}

	private UserShippingAddress createUserAddressEntity(
		User user, AddressCreateRequest request, boolean isDefaultAddress
	) {
		return UserShippingAddress.builder()
			.user(user)
			.shippingAddressName(request.shippingAddressName())
			.recipientName(request.recipientName())
			.zipCode(request.zipCode())
			.address(request.address())
			.addressDetail(request.addressDetail())
			.contactNumber(request.contactNumber())
			.isDefaultAddress(isDefaultAddress)
			.build();
	}
}
