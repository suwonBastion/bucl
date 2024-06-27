package com.freeder.buclserver.domain.productai.entity;

import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.global.mixin.TimestampMixin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@ToString
@Table(name = "product_ai")
public class ProductAi extends TimestampMixin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_ai_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ToString.Exclude
    private Product product;

    @ColumnDefault("0")
    private float average;

    @ColumnDefault("''")
    private String mdComment;

    @ColumnDefault("''")
    private String summary;

    @ColumnDefault("0")
    private Long totalCnt;
}
