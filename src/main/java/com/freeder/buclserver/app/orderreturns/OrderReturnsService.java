package com.freeder.buclserver.app.orderreturns;

import com.freeder.buclserver.app.ordercancels.dto.OrderCancelResponseDto;
import com.freeder.buclserver.app.orderexchanges.dto.주문취소목록Dto;
import com.freeder.buclserver.app.orderreturns.dto.주문반품Dto;
import com.freeder.buclserver.app.orderreturns.vo.OrdReturnReqDto;
import com.freeder.buclserver.app.orderreturns.vo.OrdReturnRespDto;
import com.freeder.buclserver.app.payment.PaymentService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerorder.repository.ConsumerOrderRepository;
import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.consumerorder.vo.OrderStatus;
import com.freeder.buclserver.domain.ordercancel.entity.OrderCancel;
import com.freeder.buclserver.domain.orderrefund.entity.OrderRefund;
import com.freeder.buclserver.domain.orderrefund.repository.OrderRefundRepository;
import com.freeder.buclserver.domain.orderreturn.entity.OrderReturn;
import com.freeder.buclserver.domain.orderreturn.repository.OrderReturnRepository;
import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnExr;
import com.freeder.buclserver.domain.orderreturn.vo.OrderReturnStatus;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.reward.entity.Reward;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.reward.vo.RewardType;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.exception.servererror.BadRequestErrorException;
import com.freeder.buclserver.global.exception.servererror.InternalServerErrorException;
import com.freeder.buclserver.global.exception.servererror.UnauthorizedErrorException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.OrderReturnUtil;
import com.freeder.buclserver.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReturnsService {
    private final OrderRefundRepository orderRefundRepository;
    private final OrderReturnRepository orderReturnRepository;
    private final UserRepository userRepository;
    private final ShippingRepository shippingRepository;
    private final ConsumerOrderRepository consumerOrderRepository;
    private final RewardRepository rewardRepository;

    private final PaymentService paymentService;

    @Transactional
    public BaseResponse<?> createOrderReturn(Long userId, String orderCode) {
        User consumer = userRepository.findById(userId).orElseThrow(
                () -> new UnauthorizedErrorException("인증 실패 했습니다.")
        );
        ConsumerOrder consumerOrder = consumerOrderRepository.findByOrderCodeAndConsumer(orderCode, consumer)
                .orElseThrow(
                        () -> new BadRequestErrorException("해당 주문코드에 대한 주문내역이 없습니다.")
                );
        Shipping shipping = shippingRepository.findFirstByConsumerOrderAndIsActive(consumerOrder, true).orElseThrow(
                () -> new InternalServerErrorException("배송 정보가 없습니다. 관리자에게 연락주세요.")
        );
        if (consumerOrder.isConfirmed()) {
            throw new BadRequestErrorException("상품 주문 확정이 되었습니다. 주문 반품이 불가능 합니다.");
        }
        if (shipping.getShippingStatus().equals(ShippingStatus.NOT_PROCESSING)) {
            throw new BadRequestErrorException("상품이 배송전입니다. 반품이 아닌 주문취소로 해주세요.");
        }
        if (!consumerOrder.getOrderStatus().equals(OrderStatus.ORDERED)) {
            throw new BadRequestErrorException(orderCode + "에 대해서 이미 CS 접수 된 상태입니다.");
        }

        consumerOrder.setCsStatus(CsStatus.ORDER_RETURN);
        consumerOrder.setOrderStatus(OrderStatus.ORDER_RETURNING);
        consumerOrderRepository.save(consumerOrder);

        int refundAmount = consumerOrder.getSpentAmount();
        int rewardUseAmount = consumerOrder.getRewardUseAmount();

        OrderRefund orderRefund = OrderRefund
                .builder()
                .refundAmount(refundAmount)
                .rewardUseAmount(rewardUseAmount)
                .consumerOrder(consumerOrder)
                .build();

        OrderRefund newOrderRefund = orderRefundRepository.save(orderRefund);

        OrderReturn orderReturn = OrderReturn.builder()
                .consumerOrder(consumerOrder)
                .orderRefund(newOrderRefund)
                .orderReturnExr(OrderReturnExr.USER)
                .orderReturnStatus(OrderReturnStatus.RECEIVED)
                .build();

        orderReturnRepository.save(orderReturn);
        return new BaseResponse<>(null, HttpStatus.OK, "반품신청완료");
    }

    @Transactional
    public OrdReturnRespDto createOrderReturnApproval(Long userId, String orderCode,
                                                      OrdReturnReqDto ordReturnReqDto) throws NullPointerException {
        User admin = userRepository.findById(userId).orElseThrow(
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
            throw new BadRequestErrorException("상품 주문 확정이 되었습니다. 주문 반품이 불가능 합니다.");
        }
        if (!consumerOrder.getOrderStatus().equals(OrderStatus.ORDERED)) {
            throw new BadRequestErrorException(orderCode + "에 대해서 이미 CS 접수 된 상태입니다.");
        }
        if (prevShipping.getShippingStatus().equals(ShippingStatus.NOT_PROCESSING)) {
            throw new BadRequestErrorException("상품 주문 반품이 아니라 주문 취소를 하신 다음에 다시 주문 해주세요.");
        }

        int refundAmount = consumerOrder.getSpentAmount();
        int rewardUseAmount = consumerOrder.getRewardUseAmount();

        OrderReturn orderReturn = orderReturnRepository.findByConsumerOrder(consumerOrder).orElseThrow(
                () -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된 주문번호"));

        OrderRefund orderRefund = orderReturn.getOrderRefund();
        orderRefund.setRefundAmount(refundAmount);
        orderRefund.setRewardUseAmount(rewardUseAmount);

        orderRefundRepository.save(orderRefund);

        if (rewardUseAmount != 0) {
            User consumer = consumerOrder.getConsumer();
            int previousRewardAmt = rewardRepository.findFirstByUserId(consumer.getId()).orElse(0);

            Product product = consumerOrder.getProduct();
            Reward reward = Reward
                    .builder()
                    .user(consumer)
                    .rewardType(RewardType.REFUND)
                    .previousRewardSum(previousRewardAmt)
                    .consumerOrder(consumerOrder)
                    .receivedRewardAmount(rewardUseAmount)
                    .product(product)
                    .productName(product.getName())
                    .productBrandName(product.getBrandName())
                    .rewardSum(previousRewardAmt + rewardUseAmount)
                    .orderRefund(orderRefund)
                    .build();
            rewardRepository.save(reward);
        }

        orderReturn.setOrderReturnStatus(OrderReturnStatus.COMPLETED);
        orderReturn.setCompletedAt(OrderReturnUtil.getCompletedAt());
        orderReturn.setOrderReturnFee(ordReturnReqDto.getReturnFee());

        prevShipping.setShippingStatus(ShippingStatus.PICK_UP);
        consumerOrder.setOrderStatus(OrderStatus.ORDER_RETURNED);

        String impUid = consumerOrder.getOrderCode();

        consumerOrderRepository.save(consumerOrder);
        shippingRepository.save(prevShipping);
        orderReturnRepository.save(orderReturn);
        paymentService.cancelPayment(impUid);

        return OrdReturnRespDto
                .builder()
                .orderReturnId(orderReturn.getOrderReturnId())
                .orderReturnStatus(OrderReturnStatus.COMPLETED)
                .build();
    }
    public BaseResponse<?> 주문반품목록조회(CustomUserDetails userDetails, int page, int pageSize) {
        권한검증(userDetails);

        Page<OrderReturn> 반품목록 = orderReturnRepository.findAll(PageUtil.paging(page, pageSize));

        return new BaseResponse<>(주문반품목록Dto변환(반품목록),HttpStatus.OK,"주문반품목록조회성공");
    }

    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }

    private List<주문반품Dto> 주문반품목록Dto변환(Page<OrderReturn> orderReturns) {
        return orderReturns.stream()
                .map(this::주문반품변환)
                .toList();
    }

    private 주문반품Dto 주문반품변환(OrderReturn orderReturn) {
        ConsumerOrder 고객주문 = orderReturn.getConsumerOrder();
        List<Shipping> 택배오더들 = 고객주문.getShippings();

        List<String> recipientName = 택배오더들.stream()
                .map(shipping -> shipping.getShippingAddress().getRecipientName())
                .toList();
        List<String> contactNumber = 택배오더들.stream()
                .map(shipping -> shipping.getShippingAddress().getContactNumber())
                .toList();

        return new 주문반품Dto(
                orderReturn.getOrderReturnId(),
                orderReturn.getOrderReturnFee(),
                orderReturn.getOrderReturnExr(),
                orderReturn.getOrderReturnStatus(),
                고객주문.getOrderCode(),
                고객주문.getCsStatus(),
                recipientName,
                contactNumber,
                orderReturn.getCreatedAt()
        );
    }
}
