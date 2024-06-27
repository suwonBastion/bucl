package com.freeder.buclserver.domain.usershippingaddress.dto.response;

import com.freeder.buclserver.domain.usershippingaddress.entity.UserShippingAddress;

public record AddressCreateResponse(
	String recipientName,
	String contactNumber,
	String zipCode,
	String address,
	String addressDetail
) {

	public static AddressCreateResponse from(UserShippingAddress userShippingAddress) {
		return new AddressCreateResponse(
			userShippingAddress.getRecipientName(),
			userShippingAddress.getContactNumber(),
			userShippingAddress.getZipCode(),
			userShippingAddress.getAddress(),
			userShippingAddress.getAddressDetail()
		);
	}
}
