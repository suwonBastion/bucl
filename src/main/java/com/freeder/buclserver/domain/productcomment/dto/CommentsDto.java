package com.freeder.buclserver.domain.productcomment.dto;

import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productcomment.vo.Evaluation;
import com.freeder.buclserver.domain.productcomment.vo.Price;
import com.freeder.buclserver.domain.productcomment.vo.Quality;
import com.freeder.buclserver.global.util.DateUtils;

public record CommentsDto(
        Long id,
        Evaluation evaluation,
        Price price,
        Quality quality,
        boolean suggestion,
        String comment,
        String createdAt,
        String name,
        String image

) {
    public static CommentsDto setDto(ProductComment productComment){
        return new CommentsDto(
                productComment.getId(),
                productComment.getEvaluation(),
                productComment.getPrice(),
                productComment.getQuality(),
                productComment.isSuggestion(),
                productComment.getComment(),
                DateUtils.convertDate(productComment.getCreatedAt()),
                productComment.getUser().getNickname(),
                productComment.getUser().getProfilePath()
        );
    }
}
