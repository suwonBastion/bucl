package com.freeder.buclserver.domain.usershippingaddress.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserShippingAddress is a Querydsl query type for UserShippingAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserShippingAddress extends EntityPathBase<UserShippingAddress> {

    private static final long serialVersionUID = -1871602860L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserShippingAddress userShippingAddress = new QUserShippingAddress("userShippingAddress");

    public final StringPath address = createString("address");

    public final StringPath addressDetail = createString("addressDetail");

    public final StringPath addrNo = createString("addrNo");

    public final StringPath contactNumber = createString("contactNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDefaultAddress = createBoolean("isDefaultAddress");

    public final StringPath recipientName = createString("recipientName");

    public final StringPath shippingAddressName = createString("shippingAddressName");

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public final StringPath zipCode = createString("zipCode");

    public QUserShippingAddress(String variable) {
        this(UserShippingAddress.class, forVariable(variable), INITS);
    }

    public QUserShippingAddress(Path<? extends UserShippingAddress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserShippingAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserShippingAddress(PathMetadata metadata, PathInits inits) {
        this(UserShippingAddress.class, metadata, inits);
    }

    public QUserShippingAddress(Class<? extends UserShippingAddress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

