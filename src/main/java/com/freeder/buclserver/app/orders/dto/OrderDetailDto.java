package com.freeder.buclserver.app.orders.dto;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderDetailDto {

	// 주문 정보
	private OrderDto orderDto;

	// 배송지 정보
	private ShpAddrDto shpAddrDto;

	// 결제 정보
	private int shippingInfoShippingFee;
	private String trackingNum;

	public static OrderDetailDto from(ConsumerOrder consumerOrder, Shipping shipping, ShippingAddress shippingAddress) {
		return new OrderDetailDto(
			OrderDto.from(consumerOrder),
			ShpAddrDto.from(shippingAddress),
			shipping.getShippingInfo().getShippingFee(),
			shipping.getTrackingNum()
		);
	}
}
