package com.freeder.buclserver.domain.productcomment.dto;


public record ProductCommentDto<T>(
        Object listCount,
        Object suggestionCount,
        T data
) {
}
