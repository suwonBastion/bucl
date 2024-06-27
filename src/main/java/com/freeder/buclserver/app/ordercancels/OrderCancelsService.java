package com.freeder.buclserver.app.ordercancels;

import com.freeder.buclserver.app.ordercancels.dto.OrderCancelResponseDto;
import com.freeder.buclserver.app.orderexchanges.dto.주문취소목록Dto;
import com.freeder.buclserver.app.payment.PaymentService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerorder.repository.ConsumerOrderRepository;
import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.consumerorder.vo.OrderStatus;
import com.freeder.buclserver.domain.ordercancel.entity.OrderCancel;
import com.freeder.buclserver.domain.ordercancel.repository.OrderCancelRepository;
import com.freeder.buclserver.domain.ordercancel.vo.OrderCancelExr;
import com.freeder.buclserver.domain.ordercancel.vo.OrderCancelStatus;
import com.freeder.buclserver.domain.orderrefund.entity.OrderRefund;
import com.freeder.buclserver.domain.orderrefund.repository.OrderRefundRepository;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.reward.entity.Reward;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.reward.vo.RewardType;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.exception.servererror.BadRequestErrorException;
import com.freeder.buclserver.global.exception.servererror.InternalServerErrorException;
import com.freeder.buclserver.global.exception.servererror.UnauthorizedErrorException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.OrderCancelUtil;
import com.freeder.buclserver.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCancelsService {
    private final OrderRefundRepository orderRefundRepository;
    private final OrderCancelRepository orderCancelRepository;
    private final UserRepository userRepository;
    private final ShippingRepository shippingRepository;
    private final ConsumerOrderRepository consumerOrderRepository;
    private final RewardRepository rewardRepository;

    private final PaymentService paymentService;

    @Transactional
    public OrderCancelResponseDto createOrderCancel(Long userId, String orderCode) throws NullPointerException {
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
            throw new BadRequestErrorException("상품 주문 확정이 되었습니다. 주문 취소를 원하시면 고객센터로 전화 주세요.");
        }
        if (!shipping.getShippingStatus().equals(ShippingStatus.NOT_PROCESSING)) {
            throw new BadRequestErrorException("상품이 이미 준비가 되어서 주문 취소를 할 수 없습니다. 반품 및 교환 신청해주세요.");
        }
        if (consumerOrder.getCsStatus().equals(CsStatus.ORDER_CANCEL)) {
            throw new BadRequestErrorException("이미 주문 취소가 되었습니다.");
        }

        consumerOrder.setCsStatus(CsStatus.ORDER_CANCEL);
        consumerOrder.setOrderStatus(OrderStatus.ORDER_CANCELING);
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
        OrderCancel orderCancel = OrderCancel
                .builder()
                .user(consumer)
                .orderCancelExr(OrderCancelExr.USER)
                .orderCancelStatus(OrderCancelStatus.RECEIVED)
                .orderRefund(newOrderRefund)
                .consumerOrder(consumerOrder)
                .build();

        OrderCancel newOrderCancel = orderCancelRepository.save(orderCancel);
        return OrderCancelResponseDto
                .builder()
                .orderCancelId(newOrderCancel.getOrderCancelId())
                .refundAmount(newOrderRefund.getRefundAmount())
                .rewardUseAmount(newOrderRefund.getRewardUseAmount())
                .build();
    }

    @Transactional
    public void updateOrderCancelApproval(Long userId, String orderCode) {
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

        if (!consumerOrder.getCsStatus().equals(CsStatus.ORDER_CANCEL)) {
            throw new BadRequestErrorException("주문 취소 요청 상태가 아닙니다.");
        }
        if (consumerOrder.getOrderStatus().equals(OrderStatus.ORDER_CANCELED)) {
            throw new BadRequestErrorException("이미 주문 취소가 완료되었습니다.");
        }

        OrderCancel orderCancel = orderCancelRepository.findByConsumerOrder(consumerOrder).orElseThrow(
                () -> new InternalServerErrorException("해당 주문 코드에 대한 주문 취소 정보가 없습니다.")
        );

        OrderRefund orderRefund = orderCancel.getOrderRefund();

        int rewardUseAmount = orderRefund.getRewardUseAmount();
        User consumer = consumerOrder.getConsumer();

        if (rewardUseAmount != 0) {
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

        consumerOrder.setOrderStatus(OrderStatus.ORDER_CANCELED);
        consumerOrder.setCsStatus(CsStatus.NONE);
        orderCancel.setOrderCancelStatus(OrderCancelStatus.COMPLETED);
        orderCancel.setCompletedAt(OrderCancelUtil.getCompletedAt());

        String impUid = consumerOrder.getOrderCode();

        consumerOrderRepository.save(consumerOrder);
        orderCancelRepository.save(orderCancel);
        orderRefundRepository.save(orderRefund);

        paymentService.cancelPayment(impUid);
    }

    public BaseResponse<?> 주문취소목록조회(CustomUserDetails userDetails, int page, int pageSize) {
        권한검증(userDetails);

        Page<OrderCancel> 주문취소목록 = orderCancelRepository.findAll(PageUtil.paging(page, pageSize));

        return new BaseResponse<>(주문취소목록Dto변환(주문취소목록),HttpStatus.OK,"주문취소목록조회성공");

    }


    //////////////////private/////////////////////

    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }

    private List<주문취소목록Dto> 주문취소목록Dto변환(Page<OrderCancel> orderCancels) {
        return orderCancels.stream()
                .map(this::주문취소변환)
                .toList();
    }

    private 주문취소목록Dto 주문취소변환(OrderCancel orderCancel) {
        ConsumerOrder 고객주문 = orderCancel.getConsumerOrder();
        List<Shipping> 택배오더들 = 고객주문.getShippings();

        List<String> recipientName = 택배오더들.stream()
                .map(shipping -> shipping.getShippingAddress().getRecipientName())
                .toList();
        List<String> contactNumber = 택배오더들.stream()
                .map(shipping -> shipping.getShippingAddress().getContactNumber())
                .toList();

        return new 주문취소목록Dto(
                orderCancel.getOrderCancelId(),
                고객주문.getOrderCode(),
                orderCancel.getOrderCancelStatus(),
                orderCancel.getOrderCancelExr(),
                고객주문.getCsStatus(),
                recipientName,
                contactNumber,
                orderCancel.getCreatedAt()
        );
    }

//    private List<주문취소목록Dto> 주문취소목록Dto변환(Page<OrderCancel> orderCancels) {
//
////        orderCancels.forEach(orderCancel -> {
////            ConsumerOrder 고객주문 = orderCancel.getConsumerOrder();
////
////            List<Shipping> 택배오더들 = 고객주문.getShippings();
////            택배오더들.forEach(shipping -> {
////                ShippingAddress 배송지정보 = shipping.getShippingAddress();
////				recipientName.add(배송지정보.getRecipientName());
////				contactNumber.add(배송지정보.getContactNumber());
////            });
////        });
//        List<주문취소목록Dto> 주문취소Dto목록들 =
//                orderCancels.stream()
//                        .map(this::주문취소변환)
//                        .toList();
//
//        return 주문취소Dto목록들;
//    }
//
//    private 주문취소목록Dto 주문취소변환(OrderCancel orderCancel) {
//        List<String> recipientName = new ArrayList<>();
//        List<String> contactNumber = new ArrayList<>();
//
//        ConsumerOrder 고객주문 = orderCancel.getConsumerOrder();
//        List<Shipping> 택배오더들 = 고객주문.getShippings();
//
//        택배오더들.forEach(shipping -> {
//            ShippingAddress 배송지정보 = shipping.getShippingAddress();
//            recipientName.add(배송지정보.getRecipientName());
//            contactNumber.add(배송지정보.getContactNumber());
//        });
//
//        return new 주문취소목록Dto(
//                orderCancel.getOrderCancelId(),
//                "orderCode",
//                orderCancel.getOrderCancelStatus(),
//                orderCancel.getOrderCancelExr(),
//                고객주문.getCsStatus(),
//                recipientName,
//                contactNumber,
//                orderCancel.getCreatedAt()
//        );
//    }

}
