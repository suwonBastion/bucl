package com.freeder.buclserver.domain.ordercancel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderCancel is a Querydsl query type for OrderCancel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderCancel extends EntityPathBase<OrderCancel> {

    private static final long serialVersionUID = 1111070868L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderCancel orderCancel = new QOrderCancel("orderCancel");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final DateTimePath<java.time.LocalDateTime> completedAt = createDateTime("completedAt", java.time.LocalDateTime.class);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final EnumPath<com.freeder.buclserver.domain.ordercancel.vo.OrderCancelExr> orderCancelExr = createEnum("orderCancelExr", com.freeder.buclserver.domain.ordercancel.vo.OrderCancelExr.class);

    public final NumberPath<Long> orderCancelId = createNumber("orderCancelId", Long.class);

    public final EnumPath<com.freeder.buclserver.domain.ordercancel.vo.OrderCancelStatus> orderCancelStatus = createEnum("orderCancelStatus", com.freeder.buclserver.domain.ordercancel.vo.OrderCancelStatus.class);

    public final com.freeder.buclserver.domain.orderrefund.entity.QOrderRefund orderRefund;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public QOrderCancel(String variable) {
        this(OrderCancel.class, forVariable(variable), INITS);
    }

    public QOrderCancel(Path<? extends OrderCancel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderCancel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderCancel(PathMetadata metadata, PathInits inits) {
        this(OrderCancel.class, metadata, inits);
    }

    public QOrderCancel(Class<? extends OrderCancel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
        this.orderRefund = inits.isInitialized("orderRefund") ? new com.freeder.buclserver.domain.orderrefund.entity.QOrderRefund(forProperty("orderRefund")) : null;
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

