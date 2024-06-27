package com.freeder.buclserver.domain.shipping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShipping is a Querydsl query type for Shipping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipping extends EntityPathBase<Shipping> {

    private static final long serialVersionUID = 1136702058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShipping shipping = new QShipping("shipping");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final DateTimePath<java.time.LocalDateTime> purchaseOrderInputDate = createDateTime("purchaseOrderInputDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> shippedDate = createDateTime("shippedDate", java.time.LocalDateTime.class);

    public final com.freeder.buclserver.domain.shippingaddress.entity.QShippingAddress shippingAddress;

    public final StringPath shippingCoName = createString("shippingCoName");

    public final com.freeder.buclserver.domain.shippinginfo.entity.QShippingInfo shippingInfo;

    public final StringPath shippingNum = createString("shippingNum");

    public final EnumPath<com.freeder.buclserver.domain.shipping.vo.ShippingStatus> shippingStatus = createEnum("shippingStatus", com.freeder.buclserver.domain.shipping.vo.ShippingStatus.class);

    public final StringPath trackingNum = createString("trackingNum");

    public final DateTimePath<java.time.LocalDateTime> trackingNumInputDate = createDateTime("trackingNumInputDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShipping(String variable) {
        this(Shipping.class, forVariable(variable), INITS);
    }

    public QShipping(Path<? extends Shipping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShipping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShipping(PathMetadata metadata, PathInits inits) {
        this(Shipping.class, metadata, inits);
    }

    public QShipping(Class<? extends Shipping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
        this.shippingAddress = inits.isInitialized("shippingAddress") ? new com.freeder.buclserver.domain.shippingaddress.entity.QShippingAddress(forProperty("shippingAddress"), inits.get("shippingAddress")) : null;
        this.shippingInfo = inits.isInitialized("shippingInfo") ? new com.freeder.buclserver.domain.shippinginfo.entity.QShippingInfo(forProperty("shippingInfo")) : null;
    }

}

