package com.freeder.buclserver.domain.affiliate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.freeder.buclserver.domain.affiliate.entity.Affiliate;
import com.freeder.buclserver.domain.user.entity.User;

public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {

	@EntityGraph(attributePaths = {"user", "product"})
	List<Affiliate> findAllByUserOrderByCreatedAtDesc(User user);
}
