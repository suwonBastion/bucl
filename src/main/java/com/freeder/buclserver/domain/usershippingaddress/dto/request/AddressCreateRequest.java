package com.freeder.buclserver.domain.usershippingaddress.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressCreateRequest(
	@NotBlank String shippingAddressName,
	@NotBlank String recipientName,
	@NotBlank
	@Pattern(regexp = "^\\d{5}$", message = "올바른 형식의 우편코드가 아닙니다.")
	String zipCode,
	@NotBlank String address,
	@NotBlank String addressDetail,
	@NotBlank
	@Pattern(regexp = "^(\\d{3}-\\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4})$", message = "올바른 전화번호 형식이 아닙니다.")
	String contactNumber,
	boolean isDefaultAddress
) {
}
