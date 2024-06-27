package com.freeder.buclserver.domain.productoption.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOption is a Querydsl query type for ProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOption extends EntityPathBase<ProductOption> {

    private static final long serialVersionUID = 545117908L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOption productOption = new QProductOption("productOption");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isExposed = createBoolean("isExposed");

    public final NumberPath<Integer> maxOrderQty = createNumber("maxOrderQty", Integer.class);

    public final NumberPath<Integer> minOrderQty = createNumber("minOrderQty", Integer.class);

    public final NumberPath<Integer> optionExtraAmount = createNumber("optionExtraAmount", Integer.class);

    public final EnumPath<com.freeder.buclserver.domain.productoption.vo.OptionKey> optionKey = createEnum("optionKey", com.freeder.buclserver.domain.productoption.vo.OptionKey.class);

    public final NumberPath<Integer> optionSequence = createNumber("optionSequence", Integer.class);

    public final StringPath optionValue = createString("optionValue");

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    public final com.freeder.buclserver.domain.product.entity.QProduct productCode;

    public final NumberPath<Integer> productQty = createNumber("productQty", Integer.class);

    public final StringPath skuCode = createString("skuCode");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductOption(String variable) {
        this(ProductOption.class, forVariable(variable), INITS);
    }

    public QProductOption(Path<? extends ProductOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOption(PathMetadata metadata, PathInits inits) {
        this(ProductOption.class, metadata, inits);
    }

    public QProductOption(Class<? extends ProductOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.productCode = inits.isInitialized("productCode") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("productCode"), inits.get("productCode")) : null;
    }

}

