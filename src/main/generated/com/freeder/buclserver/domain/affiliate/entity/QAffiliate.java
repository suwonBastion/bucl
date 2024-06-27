package com.freeder.buclserver.domain.affiliate.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAffiliate is a Querydsl query type for Affiliate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAffiliate extends EntityPathBase<Affiliate> {

    private static final long serialVersionUID = 1463396948L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAffiliate affiliate = new QAffiliate("affiliate");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath affiliateUrl = createString("affiliateUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public QAffiliate(String variable) {
        this(Affiliate.class, forVariable(variable), INITS);
    }

    public QAffiliate(Path<? extends Affiliate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAffiliate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAffiliate(PathMetadata metadata, PathInits inits) {
        this(Affiliate.class, metadata, inits);
    }

    public QAffiliate(Class<? extends Affiliate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

