package com.freeder.buclserver.domain.orderexchange.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderExchange is a Querydsl query type for OrderExchange
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderExchange extends EntityPathBase<OrderExchange> {

    private static final long serialVersionUID = 800002292L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderExchange orderExchange = new QOrderExchange("orderExchange");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final DateTimePath<java.time.LocalDateTime> completedAt = createDateTime("completedAt", java.time.LocalDateTime.class);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final EnumPath<com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeExr> orderExchangeExr = createEnum("orderExchangeExr", com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeExr.class);

    public final NumberPath<Integer> orderExchangeFee = createNumber("orderExchangeFee", Integer.class);

    public final NumberPath<Long> orderExchangeId = createNumber("orderExchangeId", Long.class);

    public final com.freeder.buclserver.domain.shipping.entity.QShipping orderExchangeShipping;

    public final EnumPath<com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeStatus> orderExchangeStatus = createEnum("orderExchangeStatus", com.freeder.buclserver.domain.orderexchange.vo.OrderExchangeStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrderExchange(String variable) {
        this(OrderExchange.class, forVariable(variable), INITS);
    }

    public QOrderExchange(Path<? extends OrderExchange> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderExchange(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderExchange(PathMetadata metadata, PathInits inits) {
        this(OrderExchange.class, metadata, inits);
    }

    public QOrderExchange(Class<? extends OrderExchange> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
        this.orderExchangeShipping = inits.isInitialized("orderExchangeShipping") ? new com.freeder.buclserver.domain.shipping.entity.QShipping(forProperty("orderExchangeShipping"), inits.get("orderExchangeShipping")) : null;
    }

}

