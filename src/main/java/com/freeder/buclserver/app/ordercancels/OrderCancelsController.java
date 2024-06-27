package com.freeder.buclserver.app.ordercancels;

import com.freeder.buclserver.app.ordercancels.dto.OrderCancelResponseDto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/order-cancels")
@Tag(name = "order-cancels 관련 API", description = "주문취소 관련 API")
public class OrderCancelsController {

    private final OrderCancelsService orderCancelsService;

    @PostMapping(path = "/{userId}/{order_code}")
    public BaseResponse<OrderCancelResponseDto> addOrderCancel(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "order_code") String orderCode
    ) {
        OrderCancelResponseDto orderCancelResponseDto = orderCancelsService.createOrderCancel(userId, orderCode);
        return new BaseResponse<>(orderCancelResponseDto, HttpStatus.CREATED, "주문 취소 됐습니다.");
    }

    @PutMapping(path = "/{userId}/{order_code}/approval")
    public BaseResponse<String> modifyOrderCancelApproval(
			@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "order_code") String orderCode
	) {
        orderCancelsService.updateOrderCancelApproval(userId, orderCode);
        return new BaseResponse<>(orderCode, HttpStatus.OK, orderCode + " 주문 취소 승인 완료했습니다.");
    }

    @GetMapping("/api/v1/order-cancels")
    public BaseResponse<?> 주문취소목록조회(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return orderCancelsService.주문취소목록조회(userDetails, page, pageSize);
    }
}
