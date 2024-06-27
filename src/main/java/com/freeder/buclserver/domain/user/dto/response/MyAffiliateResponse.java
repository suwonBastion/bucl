package com.freeder.buclserver.domain.user.dto.response;

import com.freeder.buclserver.domain.affiliate.entity.Affiliate;

public record MyAffiliateResponse(
	Long affiliateId,
	String imagePath,
	String detailImagePath,
	String brandName,
	String productName,
	int receivedRewardAmount,
	String affiliateUrl
) {

	public static MyAffiliateResponse from(Affiliate affiliate, int totalReceivedReward) {
		return new MyAffiliateResponse(
			affiliate.getId(),
			affiliate.getProduct().getImagePath(),
			affiliate.getProduct().getDetailImagePath(),
			affiliate.getProduct().getBrandName(),
			affiliate.getProduct().getName(),
			totalReceivedReward,
			affiliate.getAffiliateUrl()
		);
	}
}
