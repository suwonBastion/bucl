//package com.freeder.buclserver.admin.orders;
//
//import com.freeder.buclserver.app.orders.OrdersService;
//import com.freeder.buclserver.app.orders.dto.ShpAddrDto;
//import com.freeder.buclserver.global.response.BaseResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/api/v1/admin/orders")
//@Tag(name = "관리자페이지 관련 order API", description = "주문 검색 관련 API")
//public class OrderController {
//
//    private final OrdersService ordersService;
//
////    @GetMapping("/search")
////    public BaseResponse<?> searchOrderByCustomer(
////            @RequestParam(value = "name", required = false) String name,
////            @RequestParam(value = "phoneNumber", required = false) String phoneNumber
////    ) {
////        return ordersService.searchOrderByCustomer(name, phoneNumber);
////    }
//}
