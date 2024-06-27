package com.freeder.buclserver.domain.usershippingaddress.entity;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.usershippingaddress.dto.request.AddressUpdateRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_shipping_address")
public class UserShippingAddress {
	@Id
	@Column(name = "user_shipping_address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@Column(name = "addr_no")
	private String addrNo;

	@Column(name = "shipping_address_name")
	private String shippingAddressName;

	@Column(name = "recipient_name")
	private String recipientName;

	@Column(name = "zip_code")
	private String zipCode;

	private String address;

	@Column(name = "address_detail", length = 500)
	private String addressDetail;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "is_default_address")
	private boolean isDefaultAddress;

	@Builder
	private UserShippingAddress(
		User user, String shippingAddressName, String recipientName, String zipCode,
		String address, String addressDetail, String contactNumber, boolean isDefaultAddress
	) {
		this.user = user;
		this.shippingAddressName = shippingAddressName;
		this.recipientName = recipientName;
		this.zipCode = zipCode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.contactNumber = contactNumber;
		this.isDefaultAddress = isDefaultAddress;
	}

	public void clearDefaultAddress() {
		this.isDefaultAddress = false;
	}

	public void registerDefaultAddress() {
		this.isDefaultAddress = true;
	}

	public void updateAll(AddressUpdateRequest request) {
		this.shippingAddressName = request.shippingAddressName();
		this.recipientName = request.recipientName();
		this.zipCode = request.zipCode();
		this.address = request.address();
		this.addressDetail = request.addressDetail();
		this.contactNumber = request.contactNumber();
		this.isDefaultAddress = request.isDefaultAddress();
	}
}
