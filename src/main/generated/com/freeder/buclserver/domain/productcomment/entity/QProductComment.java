package com.freeder.buclserver.domain.productcomment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductComment is a Querydsl query type for ProductComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductComment extends EntityPathBase<ProductComment> {

    private static final long serialVersionUID = 537071822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductComment productComment = new QProductComment("productComment");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final EnumPath<com.freeder.buclserver.domain.productcomment.vo.Evaluation> evaluation = createEnum("evaluation", com.freeder.buclserver.domain.productcomment.vo.Evaluation.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.freeder.buclserver.domain.productcomment.vo.Price> price = createEnum("price", com.freeder.buclserver.domain.productcomment.vo.Price.class);

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    public final EnumPath<com.freeder.buclserver.domain.productcomment.vo.Quality> quality = createEnum("quality", com.freeder.buclserver.domain.productcomment.vo.Quality.class);

    public final BooleanPath suggestion = createBoolean("suggestion");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public QProductComment(String variable) {
        this(ProductComment.class, forVariable(variable), INITS);
    }

    public QProductComment(Path<? extends ProductComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductComment(PathMetadata metadata, PathInits inits) {
        this(ProductComment.class, metadata, inits);
    }

    public QProductComment(Class<? extends ProductComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

