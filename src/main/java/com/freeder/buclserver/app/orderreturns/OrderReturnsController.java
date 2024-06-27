package com.freeder.buclserver.app.orderreturns;

import com.freeder.buclserver.app.ordercancels.dto.OrderCancelResponseDto;
import com.freeder.buclserver.app.orderreturns.vo.OrdReturnReqDto;
import com.freeder.buclserver.app.orderreturns.vo.OrdReturnRespDto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/order-returns")
@Tag(name = "order-returns 관련 API", description = "주문 반품 관련 API")
public class OrderReturnsController {
    private final OrderReturnsService orderReturnsService;

    @PostMapping(path = "/{userId}/{order_code}")
    public BaseResponse<?> addOrderCancel(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "order_code") String orderCode
    ) {
        BaseResponse orderReturn = orderReturnsService.createOrderReturn(userId, orderCode);
        return new BaseResponse<>(orderReturn, HttpStatus.CREATED, "주문 취소 됐습니다.");
    }

    @PostMapping(path = "/{userId}/{order_code}/approval")
    public BaseResponse<OrdReturnRespDto> addOrderReturnApproval(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "order_code") String orderCode,
            @RequestBody OrdReturnReqDto ordReturnReqDto
    ) {
        OrdReturnRespDto ordReturnRespDto = orderReturnsService.createOrderReturnApproval(userId, orderCode,
                ordReturnReqDto);
        return new BaseResponse<>(ordReturnRespDto, HttpStatus.CREATED, orderCode + " 에 대해서 주문 반품 승인됐습니다.");
    }

    @GetMapping("")
    public BaseResponse<?> 주문반품목록조회(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return orderReturnsService.주문반품목록조회(userDetails, page, pageSize);
    }

    // @PutMapping(path = "/approval/{order_code}")
    // public BaseResponse<String> modifyOrderCancelApproval(@PathVariable(name = "order_code") String orderCode) {
    // 	return new BaseResponse<>("ㅋㅋㅋㅋㅋ", HttpStatus.CREATED, "주문 취소 됐습니다.");
    // }
}
