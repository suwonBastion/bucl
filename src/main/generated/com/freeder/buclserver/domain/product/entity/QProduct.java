package com.freeder.buclserver.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -638459884L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath brandName = createString("brandName");

    public final NumberPath<Float> businessRewardRate = createNumber("businessRewardRate", Float.class);

    public final NumberPath<Integer> commentReward = createNumber("commentReward", Integer.class);

    public final ListPath<com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder> consumerOrders = this.<com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder>createList("consumerOrders", com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder.class, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder.class, PathInits.DIRECT2);

    public final NumberPath<Integer> consumerPrice = createNumber("consumerPrice", Integer.class);

    public final NumberPath<Float> consumerRewardRate = createNumber("consumerRewardRate", Float.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath detailImagePath = createString("detailImagePath");

    public final NumberPath<Float> discountRate = createNumber("discountRate", Float.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final BooleanPath isAvailableMultipleOption = createBoolean("isAvailableMultipleOption");

    public final BooleanPath isExposed = createBoolean("isExposed");

    public final StringPath manufacturerName = createString("manufacturerName");

    public final NumberPath<Float> marginRate = createNumber("marginRate", Float.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> otherLowestPrice = createNumber("otherLowestPrice", Long.class);

    public final com.freeder.buclserver.domain.productcategory.entity.QProductCategory productCategory;

    public final NumberPath<Long> productCode = createNumber("productCode", Long.class);

    public final ListPath<com.freeder.buclserver.domain.productcomment.entity.ProductComment, com.freeder.buclserver.domain.productcomment.entity.QProductComment> productComments = this.<com.freeder.buclserver.domain.productcomment.entity.ProductComment, com.freeder.buclserver.domain.productcomment.entity.QProductComment>createList("productComments", com.freeder.buclserver.domain.productcomment.entity.ProductComment.class, com.freeder.buclserver.domain.productcomment.entity.QProductComment.class, PathInits.DIRECT2);

    public final ListPath<com.freeder.buclserver.domain.productoption.entity.ProductOption, com.freeder.buclserver.domain.productoption.entity.QProductOption> productOptions = this.<com.freeder.buclserver.domain.productoption.entity.ProductOption, com.freeder.buclserver.domain.productoption.entity.QProductOption>createList("productOptions", com.freeder.buclserver.domain.productoption.entity.ProductOption.class, com.freeder.buclserver.domain.productoption.entity.QProductOption.class, PathInits.DIRECT2);

    public final NumberPath<Integer> productPriority = createNumber("productPriority", Integer.class);

    public final ListPath<com.freeder.buclserver.domain.productreview.entity.ProductReview, com.freeder.buclserver.domain.productreview.entity.QProductReview> productReviews = this.<com.freeder.buclserver.domain.productreview.entity.ProductReview, com.freeder.buclserver.domain.productreview.entity.QProductReview>createList("productReviews", com.freeder.buclserver.domain.productreview.entity.ProductReview.class, com.freeder.buclserver.domain.productreview.entity.QProductReview.class, PathInits.DIRECT2);

    public final EnumPath<com.freeder.buclserver.domain.product.vo.ProductStatus> productStatus = createEnum("productStatus", com.freeder.buclserver.domain.product.vo.ProductStatus.class);

    public final StringPath saleAlternatives = createString("saleAlternatives");

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public final com.freeder.buclserver.domain.shippinginfo.entity.QShippingInfo shippingInfo;

    public final StringPath supplierName = createString("supplierName");

    public final NumberPath<Integer> supplyPrice = createNumber("supplyPrice", Integer.class);

    public final NumberPath<Float> taxRate = createNumber("taxRate", Float.class);

    public final EnumPath<com.freeder.buclserver.domain.product.vo.TaxStatus> taxStatus = createEnum("taxStatus", com.freeder.buclserver.domain.product.vo.TaxStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productCategory = inits.isInitialized("productCategory") ? new com.freeder.buclserver.domain.productcategory.entity.QProductCategory(forProperty("productCategory")) : null;
        this.shippingInfo = inits.isInitialized("shippingInfo") ? new com.freeder.buclserver.domain.shippinginfo.entity.QShippingInfo(forProperty("shippingInfo")) : null;
    }

}

