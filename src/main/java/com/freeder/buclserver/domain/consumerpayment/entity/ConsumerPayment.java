package com.freeder.buclserver.domain.consumerpayment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerpayment.vo.PaymentMethod;
import com.freeder.buclserver.domain.consumerpayment.vo.PaymentStatus;
import com.freeder.buclserver.domain.consumerpayment.vo.PgProvider;
import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "CONSUMER_PAYMENT")
public class ConsumerPayment extends TimestampMixin {
	@Id
	@Column(name = "consumer_payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "consumer_order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConsumerOrder consumerOrder;

	@Column(name = "pg_tid")
	private String pgTid;

	@Enumerated(EnumType.STRING)
	@Column(name = "pg_provider")
	private PgProvider pgProvider;

	@Column(name = "payment_code")
	private String paymentCode;

	@Column(name = "payment_amount")
	private int paymentAmount;

	@Column(name = "consumer_name")
	private String consumerName;

	@Column(name = "consumer_email", length = 320)
	private String consumerEmail;

	@Column(name = "consumer_address")
	private String consumerAddress;

	@Column(name = "consumer_cellphone")
	private String consumerCellphone;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "paid_at")
	private LocalDateTime paidAt;
}
