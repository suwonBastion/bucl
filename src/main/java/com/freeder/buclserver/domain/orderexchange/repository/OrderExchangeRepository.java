package com.freeder.buclserver.domain.orderexchange.repository;

import com.freeder.buclserver.domain.orderexchange.entity.OrderExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderExchangeRepository extends JpaRepository<OrderExchange, Long> {
	
}
