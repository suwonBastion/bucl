package com.freeder.buclserver.domain.productcomment.dto;

import com.freeder.buclserver.domain.productcomment.vo.Evaluation;
import com.freeder.buclserver.domain.productcomment.vo.Price;
import com.freeder.buclserver.domain.productcomment.vo.Quality;
import jakarta.validation.constraints.NotBlank;

public record SaveComment(
        @NotBlank Long productId,
        @NotBlank Evaluation evaluation,
        @NotBlank Price price,
        @NotBlank Quality quality,
        @NotBlank boolean suggestion,
        @NotBlank String comment
) {
}
