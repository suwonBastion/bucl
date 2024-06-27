package com.freeder.buclserver.domain.grouporder.repository;

import com.freeder.buclserver.domain.grouporder.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupOrderRepository extends JpaRepository<GroupOrder,Long> {
    Optional<GroupOrder> findByProduct_Id(Long productId);
}
