package com.freeder.buclserver.domain.usershippingaddress.dto.request;


import jakarta.validation.constraints.NotBlank;

public record AddressUpdateRequest(
	@NotBlank String shippingAddressName,
	@NotBlank String recipientName,
	@NotBlank String zipCode,
	@NotBlank String address,
	@NotBlank String addressDetail,
	@NotBlank String contactNumber,
	boolean isDefaultAddress
) {
}
