package com.freeder.buclserver.domain.usershippingaddress.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.usershippingaddress.entity.UserShippingAddress;

public interface UserShippingAddressRepository extends JpaRepository<UserShippingAddress, Long> {

	@Query("SELECT usa FROM UserShippingAddress usa WHERE usa.user.id = :userId "
		+ "AND usa.isDefaultAddress IS TRUE ORDER BY usa.id DESC")
	Optional<UserShippingAddress> findByUserAndIsDefaultAddressIsTrue(Long userId);

	List<UserShippingAddress> findAllByUser(User user);

	boolean existsByUser(User user);

	Optional<UserShippingAddress> findFirstByUserOrderByIdDesc(User user);

	long countByUser(User user);
}
