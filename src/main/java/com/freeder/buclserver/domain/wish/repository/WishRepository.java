package com.freeder.buclserver.domain.wish.repository;

import com.freeder.buclserver.domain.wish.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish,Long> {
    Optional<Page<Wish>> findByUserId(Long userId, Pageable pageable);

    void deleteByUser_IdAndProduct_ProductCode(Long userId, Long productCode);

    boolean existsByUser_IdAndProduct_IdAndDeletedAtIsNull(Long userId, Long productId);
}
