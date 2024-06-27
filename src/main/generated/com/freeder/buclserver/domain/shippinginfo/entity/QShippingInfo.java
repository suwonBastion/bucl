package com.freeder.buclserver.domain.shippinginfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShippingInfo is a Querydsl query type for ShippingInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShippingInfo extends EntityPathBase<ShippingInfo> {

    private static final long serialVersionUID = 57327718L;

    public static final QShippingInfo shippingInfo = new QShippingInfo("shippingInfo");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath infoContent = createString("infoContent");

    public final StringPath shippingCoName = createString("shippingCoName");

    public final ListPath<com.freeder.buclserver.domain.shippingextrafee.entity.ShippingExtraFee, com.freeder.buclserver.domain.shippingextrafee.entity.QShippingExtraFee> shippingExtraFees = this.<com.freeder.buclserver.domain.shippingextrafee.entity.ShippingExtraFee, com.freeder.buclserver.domain.shippingextrafee.entity.QShippingExtraFee>createList("shippingExtraFees", com.freeder.buclserver.domain.shippingextrafee.entity.ShippingExtraFee.class, com.freeder.buclserver.domain.shippingextrafee.entity.QShippingExtraFee.class, PathInits.DIRECT2);

    public final NumberPath<Integer> shippingFee = createNumber("shippingFee", Integer.class);

    public final StringPath shippingFeePhrase = createString("shippingFeePhrase");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShippingInfo(String variable) {
        super(ShippingInfo.class, forVariable(variable));
    }

    public QShippingInfo(Path<? extends ShippingInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShippingInfo(PathMetadata metadata) {
        super(ShippingInfo.class, metadata);
    }

}

