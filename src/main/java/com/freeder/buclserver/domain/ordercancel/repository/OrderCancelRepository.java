package com.freeder.buclserver.domain.ordercancel.repository;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.ordercancel.entity.OrderCancel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderCancelRepository extends JpaRepository<OrderCancel, String> {
	Optional<OrderCancel> findByConsumerOrder(ConsumerOrder consumerOrder);
}
