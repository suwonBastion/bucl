package com.freeder.buclserver.domain.shippingextrafee.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShippingExtraFee is a Querydsl query type for ShippingExtraFee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShippingExtraFee extends EntityPathBase<ShippingExtraFee> {

    private static final long serialVersionUID = 752107094L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShippingExtraFee shippingExtraFee = new QShippingExtraFee("shippingExtraFee");

    public final NumberPath<Integer> endZip = createNumber("endZip", Integer.class);

    public final NumberPath<Integer> extraFee = createNumber("extraFee", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath regionName = createString("regionName");

    public final com.freeder.buclserver.domain.shippinginfo.entity.QShippingInfo shippingInfo;

    public final NumberPath<Integer> startZip = createNumber("startZip", Integer.class);

    public QShippingExtraFee(String variable) {
        this(ShippingExtraFee.class, forVariable(variable), INITS);
    }

    public QShippingExtraFee(Path<? extends ShippingExtraFee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShippingExtraFee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShippingExtraFee(PathMetadata metadata, PathInits inits) {
        this(ShippingExtraFee.class, metadata, inits);
    }

    public QShippingExtraFee(Class<? extends ShippingExtraFee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shippingInfo = inits.isInitialized("shippingInfo") ? new com.freeder.buclserver.domain.shippinginfo.entity.QShippingInfo(forProperty("shippingInfo")) : null;
    }

}

