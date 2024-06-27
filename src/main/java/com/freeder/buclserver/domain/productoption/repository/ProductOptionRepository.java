package com.freeder.buclserver.domain.productoption.repository;

import java.util.List;
import java.util.Optional;

import com.freeder.buclserver.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freeder.buclserver.domain.productoption.entity.ProductOption;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	//List<ProductOption> findByProduct_productCode(Long productCode);

	@Query("SELECT po FROM ProductOption po " +
			"WHERE po.product.productCode = :productCode " +
			"AND po.product.deletedAt IS NULL " +
			"AND po.product.isExposed = true " +
			"AND po.product.productStatus = com.freeder.buclserver.domain.product.vo.ProductStatus.ACTIVE")
	Optional<List<ProductOption>> findByProductProductCodeWithConditions(@Param("productCode") Long productCode);

	Optional<ProductOption> findByProductAndSkuCodeAndIsExposed(Product product, String skuCode, boolean exposed);
}