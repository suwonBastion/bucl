package com.freeder.buclserver.domain.orderrefund.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderRefund is a Querydsl query type for OrderRefund
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderRefund extends EntityPathBase<OrderRefund> {

    private static final long serialVersionUID = 1661881300L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderRefund orderRefund = new QOrderRefund("orderRefund");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final DateTimePath<java.time.LocalDateTime> completedAt = createDateTime("completedAt", java.time.LocalDateTime.class);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> refundAmount = createNumber("refundAmount", Integer.class);

    public final NumberPath<Integer> rewardUseAmount = createNumber("rewardUseAmount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrderRefund(String variable) {
        this(OrderRefund.class, forVariable(variable), INITS);
    }

    public QOrderRefund(Path<? extends OrderRefund> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderRefund(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderRefund(PathMetadata metadata, PathInits inits) {
        this(OrderRefund.class, metadata, inits);
    }

    public QOrderRefund(Class<? extends OrderRefund> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
    }

}

