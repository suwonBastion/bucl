package com.freeder.buclserver.domain.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import com.freeder.buclserver.domain.product.vo.ProductStatus;
import com.freeder.buclserver.domain.product.vo.TaxStatus;
import com.freeder.buclserver.domain.productcategory.entity.ProductCategory;
import com.freeder.buclserver.domain.productoption.entity.ProductOption;
import com.freeder.buclserver.domain.productreview.entity.ProductReview;
import com.freeder.buclserver.domain.shippinginfo.entity.ShippingInfo;
import com.freeder.buclserver.global.mixin.TimestampMixin;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@ToString
@Table(name = "product")
public class Product extends TimestampMixin implements Serializable {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "product_category_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "shipping_info_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ShippingInfo shippingInfo;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @Builder.Default
    private List<ConsumerOrder> consumerOrders = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @Builder.Default
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @Builder.Default
    private List<ProductReview> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @Builder.Default
    private List<ProductComment> productComments = new ArrayList<>();

    private String name;

    @Column(name = "product_code", unique = true)
    private Long productCode;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supply_price")
    private int supplyPrice;
    @Column(name = "consumer_price")
    private int consumerPrice;
    @Column(name = "sale_price")
    private int salePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_status")
    private TaxStatus taxStatus;

    @Column(name = "margin_rate")
    private float marginRate;
    @Column(name = "tax_rate")
    private float taxRate;
    @Column(name = "discount_rate")
    private float discountRate;

    @Column(name = "consumer_reward_rate")
    private float consumerRewardRate;

    @Column(name = "business_reward_rate")
    private float businessRewardRate;

    @Column(name = "image_path", length = 5000)
    private String imagePath;

    @Column(name = "detail_image_path", length = 5000)
    private String detailImagePath;

    @Column(name = "product_state")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "product_priority")
    private int productPriority;

    @ColumnDefault("true")
    @Column(name = "is_exposed")
    private boolean isExposed;

    @Column(name = "is_available_multiple_option")
    private boolean isAvailableMultipleOption;

    @Column(name = "sale_alternatives")
    private String saleAlternatives;

    private int commentReward;

    private LocalDateTime deadline;

    private long otherLowestPrice;

    public List<ProductReview> getReviews() {
        return productReviews;
    }

    public void setIsExposed(boolean isExposed) {
        this.isExposed = isExposed;
    }

    public void setIsAvailableMultipleOption(boolean isAvailableMultipleOption) {
        this.isAvailableMultipleOption = isAvailableMultipleOption;
    }

}
