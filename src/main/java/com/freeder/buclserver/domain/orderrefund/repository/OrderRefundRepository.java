package com.freeder.buclserver.domain.orderrefund.repository;

import com.freeder.buclserver.domain.orderrefund.entity.OrderRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRefundRepository extends JpaRepository<OrderRefund, Long> {
}
