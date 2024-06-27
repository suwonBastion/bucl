package com.freeder.buclserver.domain.productcomment.entity;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.vo.TaxStatus;
import com.freeder.buclserver.domain.productcomment.dto.SaveComment;
import com.freeder.buclserver.domain.productcomment.vo.Evaluation;
import com.freeder.buclserver.domain.productcomment.vo.Price;
import com.freeder.buclserver.domain.productcomment.vo.Quality;
import com.freeder.buclserver.domain.shippinginfo.entity.ShippingInfo;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.global.mixin.TimestampMixin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "product_comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductComment extends TimestampMixin implements Serializable {

    @Id
    @Column(name = "pcomment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ToString.Exclude
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation")
    private Evaluation evaluation;

    @Enumerated(EnumType.STRING)
    @Column(name = "price")
    private Price price;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality")
    private Quality quality;

    @Column(name = "suggestion")
    private boolean suggestion;

    @Column(name = "comment", length = 100)
    private String comment;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ProductComment setEntity(Long userId, SaveComment saveComment) {
        User newUser = User.builder()
                .id(userId)
                .build();

        Product newProduct = Product.builder()
                .id(saveComment.productId())
                .build();

        return new ProductComment(
                null,
                newUser,
                newProduct,
                saveComment.evaluation(),
                saveComment.price(),
                saveComment.quality(),
                saveComment.suggestion(),
                saveComment.comment()
        );
    }
}
