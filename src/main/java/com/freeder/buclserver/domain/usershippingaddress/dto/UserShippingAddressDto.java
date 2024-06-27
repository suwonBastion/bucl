package com.freeder.buclserver.domain.usershippingaddress.dto;

import com.freeder.buclserver.domain.usershippingaddress.entity.UserShippingAddress;

public record UserShippingAddressDto(
	Long id,
	String shippingAddressName,
	String recipientName,
	String zipCode,
	String address,
	String addressDetail,
	String contactNumber,
	boolean isDefaultAddress
) {

	public static UserShippingAddressDto from(UserShippingAddress shippingAddress) {
		return new UserShippingAddressDto(
			shippingAddress.getId(),
			shippingAddress.getShippingAddressName(),
			shippingAddress.getRecipientName(),
			shippingAddress.getZipCode(),
			shippingAddress.getAddress(),
			shippingAddress.getAddressDetail(),
			shippingAddress.getContactNumber(),
			shippingAddress.isDefaultAddress()
		);
	}
}
