package com.freeder.buclserver.domain.user.dto.response;

import java.time.LocalDateTime;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerpayment.entity.ConsumerPayment;
import com.freeder.buclserver.domain.consumerpayment.vo.PaymentMethod;
import com.freeder.buclserver.domain.consumerpayment.vo.PaymentStatus;
import com.freeder.buclserver.domain.consumerpayment.vo.PgProvider;
import com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress;

public record MyOrderDetailResponse(
	LocalDateTime orderDate,
	String productName,
	int salePrice,
	int productOrderQty,
	String recipientName,
	String contactNumber,
	String zipCode,
	String address,
	String addressDetail,
	String memoContent,
	PgProvider pgProvider,
	PaymentMethod paymentMethod,
	PaymentStatus paymentStatus,
	int rewardUseAmount,
	int shippingFee,
	int totalOrderAmount
) {

	public static MyOrderDetailResponse from(
		ConsumerOrder consumerOrder, ShippingAddress shippingAddress, ConsumerPayment payment, int productOrderQty
	) {
		return new MyOrderDetailResponse(
			consumerOrder.getCreatedAt(),
			consumerOrder.getProduct().getName(),
			consumerOrder.getProduct().getSalePrice(),
			productOrderQty,
			shippingAddress.getRecipientName(),
			shippingAddress.getContactNumber(),
			shippingAddress.getZipCode(),
			shippingAddress.getAddress(),
			shippingAddress.getAddressDetail(),
			shippingAddress.getMemoContent(),
			payment.getPgProvider(),
			payment.getPaymentMethod(),
			payment.getPaymentStatus(),
			consumerOrder.getRewardUseAmount(),
			consumerOrder.getShippingFee(),
			consumerOrder.getTotalOrderAmount()
		);
	}
}
