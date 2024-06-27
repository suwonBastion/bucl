package com.freeder.buclserver.domain.shipping.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import org.springframework.data.jpa.repository.Query;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {

	@EntityGraph(attributePaths = {"shippingInfo"})
	Optional<Shipping> findByConsumerOrderAndIsActiveIsTrue(ConsumerOrder consumerOrder);

	Optional<Shipping> findFirstByConsumerOrderAndIsActive(ConsumerOrder consumerOrder, boolean isActive);

	@Query("select s from Shipping s left join fetch s.shippingAddress where s.id IN :ids")
	List<Shipping> ID로조회(List<Long> ids);

	@Query("select s from Shipping s left join fetch s.shippingAddress where s.shippingStatus = com.freeder.buclserver.domain.shipping.vo.ShippingStatus.IN_DELIVERY and s.trackingNumInputDate < :targetDate")
	List<Shipping> 자동발송완료처리(LocalDateTime targetDate);

	List<Shipping> findByConsumerOrder_IdInAndShippingStatus(List<Long> consumerOrderIds, ShippingStatus shippingStatus);
}
