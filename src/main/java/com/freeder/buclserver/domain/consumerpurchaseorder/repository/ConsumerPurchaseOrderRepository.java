package com.freeder.buclserver.domain.consumerpurchaseorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder;

public interface ConsumerPurchaseOrderRepository extends JpaRepository<ConsumerPurchaseOrder, Long> {

	@Query("SELECT COALESCE(cpo.productOrderQty, 0) FROM ConsumerPurchaseOrder cpo "
		+ "WHERE cpo.consumerOrder.id = :consumerOrderId")
	int findProductOrderQty(Long consumerOrderId);
}
