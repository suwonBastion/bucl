package com.freeder.buclserver.app.orderexchanges;

import com.freeder.buclserver.app.orderexchanges.dto.OrdExchReqDto;
import com.freeder.buclserver.app.orderexchanges.dto.OrdExchRespDto;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/order-exchanges")
@Tag(name = "order-exchanges 관련 API", description = "주문 교환 관련 API")
public class OrderExchangesController {
	private String testSocialId = "3195839289"; //"3895839289";
	private final OrderExchangesService orderExchangesService;

	@PostMapping(path = "/{order_code}/approval")
	public BaseResponse<OrdExchRespDto> addOrderExchange(
		@PathVariable(name = "order_code") String orderCode,
		@RequestBody OrdExchReqDto ordExchReqDto
	) {
		OrdExchRespDto ordExchRespDto = orderExchangesService.createOrderExchangeApproval(testSocialId, orderCode,
			ordExchReqDto);
		return new BaseResponse<>(ordExchRespDto, HttpStatus.CREATED, orderCode + "에 대한 주문 교환 완료 되었습니다.");
	}

	// @PutMapping(path = "/{order_code}/tracking")
	// public BaseResponse<OrdExchRespDto> modifyOrderExchangeTracking(
	// 	@PathVariable(name = "order_code") String orderCode
	// ) {
	// 	OrdExchRespDto ordExchRespDto = orderExchangesService.modifyOrderExchangeTracking(testSocialId, orderCode);
	// 	return new BaseResponse<>(ordExchRespDto, HttpStatus.CREATED, orderCode + "에 대한 주문 교환 완료 되었습니다.");
	// }
}
