package com.freeder.buclserver.app.orders.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
	// 배송지 입력
	private String recipientName;
	private String contactNum;
	private String zipCode;
	private String addr;
	private String addrDetail;
	private String memoCnt;

	// 상품 정보
	private String productName;
	private String brandName;
	private int productOrderAmt;
	private int productOrderQty;
	private String productOptKey;
	private String productOptVal;

	// 적립금 사용시
	private int rewardAmt;

	//결제 정보
	private int totalOrdAmt;
	private int shpFee;

	// 결제 수단
	private String pgProvider;
	private String payMethod;
	private String pgTid;
}
