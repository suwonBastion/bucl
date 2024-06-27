package com.freeder.buclserver.app.affiliates;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.affiliate.dto.AffiliateDto;
import com.freeder.buclserver.domain.affiliate.dto.AffiliateReqDto;
import com.freeder.buclserver.domain.affiliate.dto.SellingDto;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.CryptoAes256;
import com.freeder.buclserver.global.util.DateUtils;
import com.freeder.buclserver.global.util.ImageParsing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AffiliateService {
    private final CryptoAes256 cryptoAes256;
    private final ProductRepository productRepository;
    private final ImageParsing imageParsing;
    private final String FRONTURL = "https://bucl.co.kr/";   //TODO: 프론트주소도 config로 뺄지? 일단 하드코딩
    //    @Value("bucl.affiliateEXP")                            //TODO: 정책적으로 판매링크 만료시간 정착요망
    private Long expireTime = -1L;

    public BaseResponse<?> getSellingPage(
            CustomUserDetails userDetails,
            AffiliateReqDto affiliateReqDto
    ) throws Exception {

        return new BaseResponse<>(
                convertSellingDto(
                        productRepository.findByIdForAffiliate(affiliateReqDto.productId()).orElseThrow(() ->
                                new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된productID")
                        ),
                        createAffiliateUrl(userDetails, affiliateReqDto)
                ),
                HttpStatus.OK,
                "요청 성공"
        );
    }

    public BaseResponse<?> getAffiliateUrl(String affiliateEncrypt) throws Exception {

        String[] body = validUrl(affiliateEncrypt);

        return new BaseResponse<>(
                convertAffiliateDto(body[0], body[1]),
                HttpStatus.OK,
                "요청 성공"
        );
    }


    ////////////////////////////////////////private영역/////////////////////////////////////////

    private SellingDto convertSellingDto(
            Product product,
            String url
    ) {

        return SellingDto.builder()
                .brandName(product.getBrandName())
                .name(product.getName())
                .imagePath(imageParsing.getImageList(product.getImagePath()))
                .reward((product.getConsumerPrice() - product.getSalePrice()) / product.getBusinessRewardRate())
                .affiliateUrl(FRONTURL + url)
                .build();
    }

    private String createAffiliateUrl(
            CustomUserDetails userDetails,
            AffiliateReqDto affiliateReqDto
    ) throws Exception {
        return cryptoAes256.encrypt(String.format(
                "%d,%s,%d",
                affiliateReqDto.productId(),
                userDetails.getUserId(),
                DateUtils.nowDate()));
    }

    private String[] validUrl(String affiliateEncrypt) {
        try {

            String[] body = cryptoAes256.decrypt(affiliateEncrypt).split(",");

            if (!DateUtils.isOneWeekPassed(
                    Long.parseLong(body[body.length - 1]),
                    expireTime
            )) {
                throw new BaseException(HttpStatus.BAD_REQUEST, 400, "만료된 링크입니다.");
            }

            return body;

        } catch (Exception e) {
            throw new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된 URL입니다.");
        }

    }


    private AffiliateDto convertAffiliateDto(
            String productId,
            String userId
    ) {
        System.out.println(productId);
        System.out.println(userId);
        return AffiliateDto.builder()
                .productId(Long.valueOf(productId))
                .userId(Long.valueOf(userId))
                .build();
    }
}
