package com.freeder.buclserver.domain.productai.repository;

import com.freeder.buclserver.domain.productai.entity.ProductAi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAiRepository extends JpaRepository<ProductAi,Long> {
    Optional<ProductAi> findByProductId(Long productId);
}
