package com.freeder.buclserver.domain.productai.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductAi is a Querydsl query type for ProductAi
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductAi extends EntityPathBase<ProductAi> {

    private static final long serialVersionUID = -542180428L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductAi productAi = new QProductAi("productAi");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final NumberPath<Float> average = createNumber("average", Float.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mdComment = createString("mdComment");

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    public final StringPath summary = createString("summary");

    public final NumberPath<Long> totalCnt = createNumber("totalCnt", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductAi(String variable) {
        this(ProductAi.class, forVariable(variable), INITS);
    }

    public QProductAi(Path<? extends ProductAi> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductAi(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductAi(PathMetadata metadata, PathInits inits) {
        this(ProductAi.class, metadata, inits);
    }

    public QProductAi(Class<? extends ProductAi> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

