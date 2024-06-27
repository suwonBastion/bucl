package com.freeder.buclserver.app.orders.dto;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderDto {
	private ProductDto productDto; // 제품 정보
	private String orderCode; // 주문 코드
	private LocalDateTime orderDate; //주문 날짜
	private int totalOrderAmount; // 총 주문 금액
	private int spentAmount; // 사용자가 낸 금액
	private int rewardUseAmount; // 리워드 사용 금액
	private boolean isConfirmed; // 구매 확정 유무
	private List<PurchaseOrderDto> purchaseOrderDtos; // 상품 주문 코드 정보

	public static OrderDto from(ConsumerOrder consumerOrder) {
		return new OrderDto(
			ProductDto.from(consumerOrder.getProduct()),
			consumerOrder.getOrderCode(),
			consumerOrder.getCreatedAt(),
			consumerOrder.getTotalOrderAmount(),
			consumerOrder.getSpentAmount(),
			consumerOrder.getRewardUseAmount(),
			consumerOrder.isConfirmed(),
			returnPurchaseOrderList(consumerOrder.getConsumerPurchaseOrders())
		);
	}

	private static List<PurchaseOrderDto> returnPurchaseOrderList(List<ConsumerPurchaseOrder> consumerPurchaseOrders) {
		List<PurchaseOrderDto> purchaseOrders = new ArrayList<>();
		for (ConsumerPurchaseOrder consumerPurchaseOrder : consumerPurchaseOrders) {
			purchaseOrders.add(PurchaseOrderDto.from(consumerPurchaseOrder));
		}
		return purchaseOrders;

	}

}
