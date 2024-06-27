package com.freeder.buclserver.app.products;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.productcomment.dto.CommentsDto;
import com.freeder.buclserver.domain.productcomment.dto.ProductCommentDto;
import com.freeder.buclserver.domain.productcomment.dto.SaveComment;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productcomment.repository.ProductCommentRepository;
import com.freeder.buclserver.domain.reward.entity.Reward;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.reward.vo.RewardType;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCommentService {

    private final ProductCommentRepository productCommentRepository;
    private final RewardRepository rewardRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public BaseResponse<?> getComment(long productId, int page, int pageSize) {

        Product product = new Product();
        product.setId(productId);

        List<ProductComment> byProduct = productCommentRepository.findByProduct(product, PageUtil.paging(page, pageSize))
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된ProductId 또는 댓글이 없습니다.")).toList();

        List<CommentsDto> list = byProduct.stream()
                .map(CommentsDto::setDto)
                .toList();

        return new BaseResponse<>(
                list,
                HttpStatus.OK,
                "실시간댓글 전송성공."
        );
    }

    @Transactional
    public BaseResponse<?> saveComment(CustomUserDetails userDetails, SaveComment saveComment) {

        ProductComment productComment =
                ProductComment.setEntity(Long.valueOf(userDetails.getUserId()), saveComment);

        Optional<ProductComment> notFirstComment =
                productCommentRepository.findFirstByProduct_IdAndUser_Id(Long.valueOf(userDetails.getUserId()), productComment.getProduct().getId());

        ProductComment save = productCommentRepository.save(productComment);

        Reward reward = null;

        if (notFirstComment.isEmpty()) {
            reward = saveReward(userDetails, saveComment);
        }

        log.info("실시간 댓글 작성 성공 - 유저ID: {}, 댓글ID: {}, 리워드ID: {}", userDetails.getUserId(), save.getId(), notFirstComment.isEmpty() ? reward.getId() : "이미받은유저");

        return new BaseResponse<>(null, HttpStatus.OK, "실시간댓글 저장성공.");
    }


    ////////////////////////////////////private 영역///////////////////////////////////////


    /**
     * 직전 리워드 기록 검색후 로직처리<br>
     * ExceptionController에 예외처리 떠넘기는지 추적
     *
     * @param userDetails
     * @param saveComment
     * @return Reward
     * @auther 류창욱
     */
    private Reward saveReward(CustomUserDetails userDetails, SaveComment saveComment) {

        User user = User.builder().id(Long.valueOf(userDetails.getUserId())).build();
        Product product = Product.builder().id(saveComment.productId()).build();

        Optional<Reward> prevReward = rewardRepository.findRewardsByUserId(Long.valueOf(userDetails.getUserId()));
        Product p = productRepository.findById(saveComment.productId()).orElseThrow(() ->
                new BaseException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "잘못된상품ID 또는 상품이 없습니다."));

        int prevRewardSum = 0;

        if (prevReward.isPresent()) {
            prevRewardSum = prevReward.get().getRewardSum();
        }

        Reward reward = Reward.builder()
                .id(null)
                .user(user)
                .product(product)
                .consumerOrder(null)
                .orderRefund(null)
                .rewardWithdrawal(null)
                .rewardWithdrawalAccount(null)
                .productName(p.getName() + " 실시간댓글 리워드")
                .productBrandName(p.getBrandName())
                .rewardType(RewardType.CONSUMER)
                .receivedRewardAmount(p.getCommentReward())
                .spentRewardAmount(null)
                .previousRewardSum(prevRewardSum)
                .rewardSum(prevRewardSum + p.getCommentReward())
                .build();

        return rewardRepository.save(reward);
    }

}
