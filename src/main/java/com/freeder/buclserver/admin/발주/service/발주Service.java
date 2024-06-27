package com.freeder.buclserver.admin.발주.service;

import com.freeder.buclserver.admin.발주.dto.발주메인페이지Dto;
import com.freeder.buclserver.admin.발주.dto.엑셀다운Dto;
import com.freeder.buclserver.admin.발주.dto.엑셀다운Dto.엑셀다운배송Dto;
import com.freeder.buclserver.admin.발주.dto.엑셀다운가공전Dto;
import com.freeder.buclserver.admin.발주.dto.주문상태Dto;
import com.freeder.buclserver.admin.발주.dto.주문상태Dto.엑셀업로드;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.consumerorder.repository.ConsumerOrderRepository;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class 발주Service {
    private final ConsumerOrderRepository consumerOrderRepository;
    private final ShippingRepository shippingRepository;

    public BaseResponse<?> 발주페이지조회(CustomUserDetails userDetails) {
        validRole(userDetails);
        List<Object[]> 발주메인페이지 = consumerOrderRepository.발주메인페이지();

        return new BaseResponse<>(DTO로변환(발주메인페이지.get(0)), HttpStatus.OK, "조회완료");
    }

    @Transactional(readOnly = true)
    public BaseResponse<?> 주문수조회(CustomUserDetails userDetails, ShippingStatus shippingStatus) {
        validRole(userDetails);

        List<엑셀다운가공전Dto> 주문수찾기 = consumerOrderRepository.주문수찾기(shippingStatus);

        List<엑셀다운Dto> 엑셀리스트 = 주문수찾기.stream()
                .collect(Collectors.groupingBy(엑셀다운가공전Dto::getConsumerOrderId))
                .entrySet().stream()
                .map(entry -> {
                    List<엑셀다운가공전Dto> groupedOrders = entry.getValue();
                    엑셀다운가공전Dto representativeOrder = groupedOrders.get(0); // 대표 주문 정보를 가져온다.

                    List<엑셀다운Dto.엑셀다운배송Dto> 배송리스트 = groupedOrders.stream()
                            .map(order -> new 엑셀다운Dto.엑셀다운배송Dto(
                                    order.getShippingId(),
                                    order.getShippingCoName(),
                                    order.getTrackingNum()))
                            .toList();

                    return new 엑셀다운Dto(
                            representativeOrder.getConsumerOrderId(),
                            representativeOrder.getProductId(),
                            representativeOrder.getProductName(),
                            representativeOrder.getProductOptionValue(),
                            representativeOrder.getProductOrderQty(),
                            representativeOrder.getRewardUseAmount(),
                            representativeOrder.getTotalOrderAmount(),
                            representativeOrder.getConsumerName(),
                            representativeOrder.getConsumerAddress(),
                            representativeOrder.getConsumerCellphone(),
                            배송리스트,
                            representativeOrder.getCreateAt()
                    );
                }).toList();
        엑셀리스트.forEach(System.out::println);

        return new BaseResponse<>(엑셀리스트, HttpStatus.OK, "조회완료");
    }

    @Transactional
    public BaseResponse<?> 주문상태업데이트(CustomUserDetails userDetails, 주문상태Dto 주문상태Dto) {
        validRole(userDetails);

        List<Shipping> 배송리스트 = shippingRepository.ID로조회(ID만가공(주문상태Dto));

        if (
                주문상태Dto.getData().get(0).getShippingCoName() == null ||
                        주문상태Dto.getData().get(0).getShippingCoName().equals("")
        ) {
            배송리스트.forEach(shipping -> 배송상태업데이트(shipping, 주문상태Dto));

            return new BaseResponse<>(null, HttpStatus.OK, "업데이트완료");
        }

        배송리스트.forEach(shipping -> {
            주문상태Dto.getData().forEach(엑셀업로드 -> {
                if (shipping.getId() == 엑셀업로드.getShippingId()) {
                    shipping.setShippingCoName(엑셀업로드.getShippingCoName());
                    shipping.setTrackingNum(엑셀업로드.getTrackingNum());
                    shipping.setTrackingNumInputDate(LocalDateTime.now());
                    배송상태업데이트(shipping, 주문상태Dto);
                }
            });
        });

        return new BaseResponse<>(null, HttpStatus.OK, "업데이트완료");
    }

    private void validRole(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            log.error("권한이 없습니다.");
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }

    private 발주메인페이지Dto DTO로변환(Object[] object) {
        return new 발주메인페이지Dto(
                object[0],
                object[1],
                object[2],
                object[3]
        );
    }

    private List<Long> ID만가공(주문상태Dto 주문상태Dto) {
        return 주문상태Dto.getData()
                .stream()
                .map(엑셀업로드::getShippingId)
                .toList();
    }

    private void 배송상태업데이트(Shipping shipping, 주문상태Dto 주문상태Dto) {
        if (shipping.getShippingStatus() == 주문상태Dto.getShippingStatus()) {
            switch (주문상태Dto.getShippingStatus()) {
                case NOT_PROCESSING -> shipping.setShippingStatus(ShippingStatus.PROCESSING);
                case PROCESSING -> shipping.setShippingStatus(ShippingStatus.IN_DELIVERY);
                case IN_DELIVERY -> shipping.setShippingStatus(ShippingStatus.DELIVERED);
            }
        }
    }

    //////////////// OLD CODE///////////

    //    @Transactional(readOnly = true)
//    public BaseResponse<?> 주문수조회(CustomUserDetails userDetails, ShippingStatus shippingStatus) {
//        validRole(userDetails);
//        List<엑셀다운Dto> 주문수찾기 = consumerOrderRepository.주문수찾기(shippingStatus);
//
//        List<Long> 주문ID들 = 주문수찾기.stream().map(엑셀다운Dto::getConsumerOrderId).toList();
//
//        List<Shipping> 주문된택배들 = shippingRepository.findByConsumerOrder_IdInAndShippingStatus(주문ID들, shippingStatus);
//
//        주문수찾기 = 주문수찾기.stream().peek(엑셀다운Dto -> {
//            List<엑셀다운배송Dto> 택배데이터 = 주문된택배들.stream()
//                    .filter(shipping -> Objects.equals(엑셀다운Dto.getConsumerOrderId(), shipping.getConsumerOrder().getId()))
//                    .map(shipping -> new 엑셀다운배송Dto(shipping.getId(), shipping.getShippingCoName(), shipping.getTrackingNum()))
//                    .toList();
//            엑셀다운Dto.setShipping(택배데이터);
//        }).filter(엑셀다운Dto -> !엑셀다운Dto.getShipping().isEmpty()
//        ).toList();
//
//        return new BaseResponse<>(주문수찾기, HttpStatus.OK, "조회완료");
//    }

}
