package com.freeder.buclserver.domain.user.dto.response;

import java.time.LocalDateTime;

import org.springframework.web.util.UriComponentsBuilder;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;

public record MyOrderResponse(
	Long orderId,
	Long productId,
	LocalDateTime orderDate,
	String imagePath,
	String brandName,
	String productName,
	int salePrice,
	int productOrderQty,
	CsStatus csStatus,
	ShippingStatus shippingStatus,
	String deliveryTrackingUrl
) {

	public static MyOrderResponse from(ConsumerOrder consumerOrder, int productOrderQty, Shipping shipping) {
		return new MyOrderResponse(
			consumerOrder.getId(),
			consumerOrder.getProduct().getId(),
			consumerOrder.getCreatedAt(),
			consumerOrder.getProduct().getImagePath(),
			consumerOrder.getProduct().getBrandName(),
			consumerOrder.getProduct().getName(),
			consumerOrder.getProduct().getSalePrice(),
			productOrderQty,
			consumerOrder.getCsStatus(),
			shipping.getShippingStatus(),
			shipping != null ? createDeliveryTrackingUrl(shipping) : null
		);
	}

	private static String createDeliveryTrackingUrl(Shipping shipping) {
		return UriComponentsBuilder.newInstance()
			.scheme("https")
			.host("search.naver.com")
			.path("search.naver")
			.queryParam("where", "nexearch")
			.queryParam("sm", "hty")
			.queryParam("fbm", "0")
			.queryParam("ie", "utf8")
			.queryParam("query", shipping.getShippingInfo().getShippingCoName() + shipping.getTrackingNum())
			.toUriString();
	}
}