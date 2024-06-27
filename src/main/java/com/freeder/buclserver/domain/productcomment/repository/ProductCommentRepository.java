package com.freeder.buclserver.domain.productcomment.repository;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCommentRepository extends JpaRepository<ProductComment,Long> {

    Optional<Page<ProductComment>> findByProduct(Product product, Pageable pageable);

    //MYSQL전용 네이티브쿼리사용. (IFNULL)
    @Query(value = "SELECT COUNT(*), IFNULL(SUM(CASE WHEN pc.suggestion = 1 THEN 1 ELSE 0 END), 0) FROM product_comment pc WHERE product_id = :productId", nativeQuery = true)
    List<Object[]> getCounts(long productId);

    Optional<ProductComment> findFirstByProduct_IdAndUser_Id(Long productId, Long userId);

}
