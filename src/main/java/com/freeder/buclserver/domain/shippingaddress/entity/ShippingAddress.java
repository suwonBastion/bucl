package com.freeder.buclserver.domain.shippingaddress.entity;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "shipping_address")
public class ShippingAddress extends TimestampMixin {
	@Id
	@Column(name = "shipping_address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Shipping shipping;

	@Column(name = "recipient_name")
	private String recipientName;

	@Column(name = "zip_code")
	private String zipCode;

	private String address;

	@Column(name = "address_detail", length = 500)
	private String addressDetail;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "memo_content")
	private String memoContent;

	public ShippingAddress updateEntity(
			String recipientName, String zipCode, String address,
			String addressDetail, String contactNumber) {
		this.recipientName = recipientName;
		this.zipCode = zipCode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.contactNumber = contactNumber;
		return this;
	}
}
