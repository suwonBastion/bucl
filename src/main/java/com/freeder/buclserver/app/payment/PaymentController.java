package com.freeder.buclserver.app.payment;

import com.freeder.buclserver.app.payment.dto.PaymentPrepareDto;
import com.freeder.buclserver.app.payment.dto.PaymentVerifyDto;
import com.freeder.buclserver.global.exception.servererror.BadRequestErrorException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "payment 관련 API", description = "결제 관련  API")
@Slf4j
public class PaymentController {

	private final PaymentService paymentService;

	private String testSocialId = "3895839289";

	@PostMapping("/preparation")
	public PaymentPrepareDto preparePayment(@RequestBody PaymentPrepareDto paymentPrepareDto) {
		paymentService.preparePayment(paymentPrepareDto);
		return paymentPrepareDto;
	}

	@PostMapping("/verification")
	public IamportResponse<Payment> verifyPayment(@ModelAttribute @Valid PaymentVerifyDto paymentVerifyDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			log.info("{\"status\":\"error\", \"msg\":\""
				+ "요청 데이터가 인위적으로 바뀌어서 결제가 취소 되었습니다."
				+ "\", \"cause\":\"" + bindingResult.getAllErrors().toString() + "\"}");
			String impUid = paymentVerifyDto.getImpUid();
			paymentService.cancelPayment(impUid);
			throw new BadRequestErrorException("요청 데이터가 인위적으로 바뀌어서 결제가 취소되었습니다.");
		}
		return paymentService.verifyPayment(testSocialId, paymentVerifyDto);
	}
}
