package com.freeder.buclserver.app.orderexchanges;

import com.freeder.buclserver.app.orderexchanges.dto.OrdExchReqDto;
import com.freeder.buclserver.app.orderexchanges.dto.OrdExchRespDto;
import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerorder.repository.ConsumerOrderRepository;
import com.freeder.buclserver.domain.consumerorder.vo.OrderStatus;
import com.freeder.buclserver.domain.orderexchange.entity.OrderExchange;
import com.freeder.buclserver.domain.orderexchange.repository.OrderExchangeRepository;
import com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeExr;
import com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeStatus;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.servererror.BadRequestErrorException;
import com.freeder.buclserver.global.exception.servererror.InternalServerErrorException;
import com.freeder.buclserver.global.exception.servererror.UnauthorizedErrorException;
import com.freeder.buclserver.global.util.OrderExchangeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderExchangesService {
	private final OrderExchangeRepository orderExchangeRepository;
	private final UserRepository userRepository;
	private final ShippingRepository shippingRepository;
	private final ConsumerOrderRepository consumerOrderRepository;

	@Transactional
	public OrdExchRespDto createOrderExchangeApproval(String socialId, String orderCode,
		OrdExchReqDto ordExchReqDto) throws NullPointerException {
		User admin = userRepository.findBySocialId(socialId).orElseThrow(
			() -> new UnauthorizedErrorException("인증 실패 했습니다.")
		);
		if (!admin.getRole().equals(Role.ROLE_ADMIN)) {
			throw new UnauthorizedErrorException("해당 기능은 관리자만 쓸 수 있습니다.");
		}
		ConsumerOrder consumerOrder = consumerOrderRepository.findByOrderCode(orderCode)
			.orElseThrow(
				() -> new BadRequestErrorException("해당 주문 건은 없습니다.")
			);
		Shipping prevShipping = shippingRepository.findFirstByConsumerOrderAndIsActive(consumerOrder, true).orElseThrow(
			() -> new InternalServerErrorException("배송 정보가 없습니다. 관리자에게 연락주세요.")
		);
		if (consumerOrder.isConfirmed()) {
			throw new BadRequestErrorException("상품 주문 확정이 되었습니다. 주문 교환이 불가능 합니다.");
		}
		if (!consumerOrder.getOrderStatus().equals(OrderStatus.ORDERED)) {
			throw new BadRequestErrorException(orderCode + "에 대해서 이미 CS 접수 된 상태입니다.");
		}
		if (prevShipping.getShippingStatus().equals(ShippingStatus.NOT_PROCESSING)) {
			throw new BadRequestErrorException("상품 주문 교환이 아니라 주문 취소를 하신 다음에 다시 주문 해주세요.");
		}

		consumerOrder.setOrderStatus(OrderStatus.ORDER_EXCHANGING);
		prevShipping.setActiveFalse();
		prevShipping.setShippingStatus(ShippingStatus.PICK_UP);

		Shipping newShipping = createShipping(consumerOrder);

		OrderExchange orderExchange = OrderExchange
			.builder()
			.consumerOrder(consumerOrder)
			.orderExchangeId(OrderExchangeUtil.getExchangeId())
			.orderExchangeExr(OrderExchangeExr.USER)
			.orderExchangeStatus(OrderExchangeStatus.IN_PROCESS)
			.orderExchangeShipping(newShipping)
			.orderExchangeFee(ordExchReqDto.getExchangeFee())
			.build();

		consumerOrderRepository.save(consumerOrder);
		shippingRepository.save(prevShipping);
		shippingRepository.save(newShipping);
		OrderExchange newOrderExchange = orderExchangeRepository.save(orderExchange);

		return OrdExchRespDto
			.builder()
			.orderExchId(newOrderExchange.getOrderExchangeId())
			.orderExchangeStatus(newOrderExchange.getOrderExchangeStatus())
			.build();
	}

	private Shipping createShipping(
		ConsumerOrder consumerOrder) {
		Shipping shipping = Shipping
			.builder()
			.consumerOrder(consumerOrder)
			.shippingStatus(ShippingStatus.NOT_PROCESSING)
			.isActive(true)
			.build();

		return shippingRepository.save(shipping);
	}
}
