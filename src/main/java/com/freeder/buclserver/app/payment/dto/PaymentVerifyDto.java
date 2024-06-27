package com.freeder.buclserver.app.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerifyDto {
	private String impUid;
	private int amount;

	// 배송지 입력
	private String cellPhone;
	private String recipientName;
	private String contactNum;
	private String zipCode;
	private String addr;
	private String addrDetail;
	private String memoCnt;

	// 상품 정보
	private Long productCode;
	private String productName;
	private String brandName;

	private ProductOptionDto productOption;

	// 적립금 사용시
	private int rewardAmt;

	//주문번호
	private String ordCode;

	//결제 정보
	private int totalOrdAmt;
	private int shpFee;

	// 결제 수단
	private String pgProvider;
	private String payMethod;
	private String pgTid;
}
