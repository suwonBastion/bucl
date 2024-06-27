package com.freeder.buclserver.domain.consumerpurchaseorder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConsumerPurchaseOrder is a Querydsl query type for ConsumerPurchaseOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsumerPurchaseOrder extends EntityPathBase<ConsumerPurchaseOrder> {

    private static final long serialVersionUID = 767600212L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConsumerPurchaseOrder consumerPurchaseOrder = new QConsumerPurchaseOrder("consumerPurchaseOrder");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> productAmount = createNumber("productAmount", Integer.class);

    public final com.freeder.buclserver.domain.productoption.entity.QProductOption productOption;

    public final StringPath productOptionValue = createString("productOptionValue");

    public final NumberPath<Integer> productOrderAmount = createNumber("productOrderAmount", Integer.class);

    public final StringPath productOrderCode = createString("productOrderCode");

    public final NumberPath<Integer> productOrderQty = createNumber("productOrderQty", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QConsumerPurchaseOrder(String variable) {
        this(ConsumerPurchaseOrder.class, forVariable(variable), INITS);
    }

    public QConsumerPurchaseOrder(Path<? extends ConsumerPurchaseOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConsumerPurchaseOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConsumerPurchaseOrder(PathMetadata metadata, PathInits inits) {
        this(ConsumerPurchaseOrder.class, metadata, inits);
    }

    public QConsumerPurchaseOrder(Class<? extends ConsumerPurchaseOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
        this.productOption = inits.isInitialized("productOption") ? new com.freeder.buclserver.domain.productoption.entity.QProductOption(forProperty("productOption"), inits.get("productOption")) : null;
    }

}

