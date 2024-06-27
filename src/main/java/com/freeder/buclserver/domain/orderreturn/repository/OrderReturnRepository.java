package com.freeder.buclserver.domain.orderreturn.repository;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.orderreturn.entity.OrderReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {
    Optional<OrderReturn> findByConsumerOrder(ConsumerOrder consumerOrder);
}
