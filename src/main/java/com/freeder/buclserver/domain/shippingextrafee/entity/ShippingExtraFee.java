package com.freeder.buclserver.domain.shippingextrafee.entity;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.shippinginfo.entity.ShippingInfo;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "shipping_extra_fee")
public class ShippingExtraFee {
	@Id
	@Column(name = "shipping_extra_fee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "shipping_info_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ShippingInfo shippingInfo;

	@Column(name = "region_name")
	private String regionName;

	@Column(name = "start_zip")
	private int startZip;

	@Column(name = "end_zip")
	private int endZip;

	@Column(name = "extra_fee")
	private int extraFee;
}
