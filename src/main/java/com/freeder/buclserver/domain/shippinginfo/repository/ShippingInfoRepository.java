package com.freeder.buclserver.domain.shippinginfo.repository;

import com.freeder.buclserver.domain.shippinginfo.entity.ShippingInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Integer> {
	Optional<ShippingInfo> findById(@NotNull Integer integer);
}
