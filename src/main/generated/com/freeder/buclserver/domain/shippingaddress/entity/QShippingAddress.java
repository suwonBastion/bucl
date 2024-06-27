package com.freeder.buclserver.domain.shippingaddress.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShippingAddress is a Querydsl query type for ShippingAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShippingAddress extends EntityPathBase<ShippingAddress> {

    private static final long serialVersionUID = -716789740L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShippingAddress shippingAddress = new QShippingAddress("shippingAddress");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath address = createString("address");

    public final StringPath addressDetail = createString("addressDetail");

    public final StringPath contactNumber = createString("contactNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memoContent = createString("memoContent");

    public final StringPath recipientName = createString("recipientName");

    public final com.freeder.buclserver.domain.shipping.entity.QShipping shipping;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public final StringPath zipCode = createString("zipCode");

    public QShippingAddress(String variable) {
        this(ShippingAddress.class, forVariable(variable), INITS);
    }

    public QShippingAddress(Path<? extends ShippingAddress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShippingAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShippingAddress(PathMetadata metadata, PathInits inits) {
        this(ShippingAddress.class, metadata, inits);
    }

    public QShippingAddress(Class<? extends ShippingAddress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shipping = inits.isInitialized("shipping") ? new com.freeder.buclserver.domain.shipping.entity.QShipping(forProperty("shipping"), inits.get("shipping")) : null;
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

