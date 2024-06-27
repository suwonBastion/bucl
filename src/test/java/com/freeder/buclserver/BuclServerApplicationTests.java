package com.freeder.buclserver;

import com.freeder.buclserver.admin.발주.dto.발주메인페이지Dto;
import com.freeder.buclserver.admin.발주.dto.엑셀다운Dto;
import com.freeder.buclserver.admin.발주.dto.엑셀다운가공전Dto;
import com.freeder.buclserver.app.affiliates.AffiliateService;
import com.freeder.buclserver.app.auth.service.JwtTokenService;
import com.freeder.buclserver.core.security.JwtTokenProvider;
import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.consumerorder.repository.ConsumerOrderRepository;
import com.freeder.buclserver.domain.consumerorder.vo.CsStatus;
import com.freeder.buclserver.domain.openbanking.vo.BANK_CODE;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.productai.entity.ProductAi;
import com.freeder.buclserver.domain.productai.repository.ProductAiRepository;
import com.freeder.buclserver.domain.productcomment.dto.CommentsDto;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productcomment.repository.ProductCommentRepository;
import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shipping.repository.ShippingRepository;
import com.freeder.buclserver.domain.shipping.vo.ShippingStatus;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.util.CryptoAes256;
import com.freeder.buclserver.global.util.PageUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class BuclServerApplicationTests {

    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AffiliateService service;
    @Autowired
    CryptoAes256 cryptoAes256;
    @Autowired
    ProductCommentRepository productCommentRepository;
    @Autowired
    ConsumerOrderRepository consumerOrderRepository;
    @Autowired
    ShippingRepository shippingRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductAiRepository productAiRepository;

    @Test
    void getToken() {
        String accessToken = jwtTokenProvider.createAccessToken(1L, Role.ROLE_USER);
        System.out.println(accessToken);
    }

    @Test
    void test() throws Exception {
        System.out.println(cryptoAes256.decrypt("LtNy26qNo1UXOaBtYd2OdQ=="));
    }

    @Test
    void bank() {
        System.out.println(BANK_CODE.valueOf("카카오뱅크").getBankCode()
        );
    }

    @Test
    void dsl() {
        Product product = new Product();
        product.setId(1L);
        Optional<Page<ProductComment>> byProduct = productCommentRepository.findByProduct(product, PageUtil.paging(1, 10));
        List<ProductComment> content = byProduct.get().getContent();
    }

    @Test
    void random() {
        List<ConsumerOrder> 울보 = consumerOrderRepository.findByConsumerUserName("울보");
        울보.forEach(consumerOrder -> System.out.println(consumerOrder.getConsumer().getUserName()));
    }

    @Test
    void 발주메인페이지() {
        List<Object[]> 발주메인페이지 = consumerOrderRepository.발주메인페이지();
        Object[] a = 발주메인페이지.get(0);
        발주메인페이지Dto b = new 발주메인페이지Dto(
                a[0], a[1], a[2], a[3]
        );
        System.out.println(b);
    }

    @Test
    void 신규주문수() {
//        List<엑셀다운Dto> 주문수찾기 = consumerOrderRepository.주문수찾기();
//        주문수찾기.forEach(System.out::println);
        List<엑셀다운가공전Dto> 주문수찾기 = consumerOrderRepository.주문수찾기(ShippingStatus.PROCESSING);
        주문수찾기.forEach(System.out::println);

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
                            .collect(Collectors.toList());

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
                }).collect(Collectors.toList());
        엑셀리스트.forEach(System.out::println);
    }

    @Test
    void 주문상태업데이트() {
        List<Shipping> 배송리스트 = shippingRepository.ID로조회(Arrays.asList(1L, 2L));
        배송리스트.forEach(shipping -> System.out.println(shipping.getShippingStatus()));
    }

    @Test
    void 자동배송완료테스트() {
        LocalDateTime 타겟일수 = LocalDateTime.now().minusDays(2);
        List<Shipping> 자동발송완료처리 = shippingRepository.자동발송완료처리(타겟일수);

    }

    @Test
    @Transactional
    void 상품에서상품댓글로() {
        List<Product> all = productRepository.findAll();
        all.forEach(product -> {
            List<ProductComment> productComments = product.getProductComments();
            productComments.forEach(System.out::println);
        });
    }

    @Test
    void ai() {
        List<ProductAi> all = productAiRepository.findAll();
        all.forEach(System.out::println);
    }

    /////////////////////////울보 테스트영역////////////////////////

}
