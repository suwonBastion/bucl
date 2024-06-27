package com.freeder.buclserver.domain.shippingaddress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

	ShippingAddress findByShipping(Shipping shipping);

	//Optional<ShippingAddress> findByShipping(Shipping shipping);
}
