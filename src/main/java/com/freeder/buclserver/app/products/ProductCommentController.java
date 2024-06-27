package com.freeder.buclserver.app.products;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.productcomment.dto.SaveComment;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/comment")
@Tag(name = "상품 실시간 댓글 관련 API", description = "상품 실시간 댓글 관련 API")
@RequiredArgsConstructor
public class ProductCommentController {
    private final ProductCommentService service;

    @GetMapping("/{product_id}")
    public BaseResponse<?> getComments(
            @PathVariable(name = "product_id") Long productId,
            @Parameter(name = "page", description = "현재페이지")
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(name = "pageSize", description = "페이지사이즈")
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return service.getComment(productId,page,pageSize);
    }

    @PostMapping("")
    public BaseResponse<?> saveComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody SaveComment saveComment
            ){
        return service.saveComment(userDetails,saveComment);
    }
}
