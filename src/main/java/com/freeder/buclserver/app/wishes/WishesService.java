package com.freeder.buclserver.app.wishes;

import com.freeder.buclserver.app.products.ProductsCategoryService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.grouporder.entity.GroupOrder;
import com.freeder.buclserver.domain.grouporder.repository.GroupOrderRepository;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.wish.dto.WishDto;
import com.freeder.buclserver.domain.wish.entity.Wish;
import com.freeder.buclserver.domain.wish.repository.WishRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.ImageParsing;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WishesService {
    private final WishRepository wishRepository;
    private final GroupOrderRepository groupOrderRepository;
    private final ProductRepository productRepository;
    private final ProductsCategoryService productsCategoryService;
    private final ImageParsing imageParsing;

    public BaseResponse<?> getWishesList(
            CustomUserDetails userDetails,
            int page,
            int pageSize
    ) {

        Page<Wish> wishes = wishRepository.findByUserId(Long.valueOf(userDetails.getUserId()), setPaging(page, pageSize)).orElseThrow(() ->
                new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된 userId 또는 찜목록이 없습니다."));

        List<WishDto> list = wishes.getContent().stream()
                .map(this::convertWishDto)
                .toList();

        return new BaseResponse<>(
                list,
                HttpStatus.OK,
                "요청 성공"
        );
    }

    @Transactional
    public BaseResponse<?> saveWish(
            CustomUserDetails userDetails,
            WishDto.WishCreateReq wishCreateReq
    ) {

        return new BaseResponse<>(
                WishCreateRes(
                        userDetails,
                        wishCreateReq
                ),
                HttpStatus.OK,
                "요청 성공"
        );
    }

    @Transactional
    public BaseResponse<?> deleteWish(
            CustomUserDetails userDetails,
            Long productCode
    ) {

        try {
            wishRepository.deleteByUser_IdAndProduct_ProductCode(
                    Long.valueOf(userDetails.getUserId()),
                    productCode
            );
        } catch (Exception e) {
            throw new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된 product_code");
        }

        return new BaseResponse<>(
                null,
                HttpStatus.OK,
                "요청 성공"
        );
    }


    //private 영역//


    private WishDto convertWishDto(Wish wish) {

        GroupOrder groupOrder = groupOrderRepository.findByProduct_Id(wish.getProduct().getId()).orElseThrow(() ->
                new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된상품ID")
        );

        try {
            return WishDto.builder()
                    .brandName(wish.getProduct().getBrandName())
                    .name(wish.getProduct().getName())
                    .productCode(Objects.requireNonNull(wish.getProduct().getProductCode()))
                    .imagePath(Objects.requireNonNull(imageParsing.getImageList(wish.getProduct().getImagePath())))
                    .consumerPrice(wish.getProduct().getConsumerPrice())
                    .starRate(productsCategoryService.calculateAverageRating(wish.getProduct().getProductReviews()))
                    .consumerOrdersNumber(wish.getProduct().getConsumerOrders().size())
                    .isEnded(Objects.requireNonNull(groupOrder).isEnded())
                    .build();

        } catch (Exception e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "convertWishDto에러 : 백엔드에게 문의 바랍니다.");
        }
    }

    private WishDto.WishCreateRes WishCreateRes(
            CustomUserDetails userDetails,
            WishDto.WishCreateReq wishCreateReq
    ) {

        return WishDto.WishCreateRes.builder()
                .wishId(
                        wishRepository.save(
                                Wish.builder()
                                        .user(
                                                User.builder()
                                                        .id(Long.valueOf(userDetails.getUserId()))
                                                        .build()
                                        )
                                        .product(
                                                Product.builder()
                                                        .id(wishCreateReq.getProductId())
                                                        .build()
                                        )
                                        .build()
                        ).getId()
                )
                .build();
    }

    private Pageable setPaging(
            int page,
            int pageSize
    ) {
        return PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending());
    }
}
