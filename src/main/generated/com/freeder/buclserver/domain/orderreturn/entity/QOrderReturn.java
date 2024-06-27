package com.freeder.buclserver.domain.orderreturn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderReturn is a Querydsl query type for OrderReturn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderReturn extends EntityPathBase<OrderReturn> {

    private static final long serialVersionUID = -1333706540L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderReturn orderReturn = new QOrderReturn("orderReturn");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final DateTimePath<java.time.LocalDateTime> completedAt = createDateTime("completedAt", java.time.LocalDateTime.class);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final com.freeder.buclserver.domain.orderrefund.entity.QOrderRefund orderRefund;

    public final EnumPath<com.freeder.buclserver.domain.orderreturn.vo.OrderReturnExr> orderReturnExr = createEnum("orderReturnExr", com.freeder.buclserver.domain.orderreturn.vo.OrderReturnExr.class);

    public final NumberPath<Integer> orderReturnFee = createNumber("orderReturnFee", Integer.class);

    public final NumberPath<Long> orderReturnId = createNumber("orderReturnId", Long.class);

    public final EnumPath<com.freeder.buclserver.domain.orderreturn.vo.OrderReturnStatus> orderReturnStatus = createEnum("orderReturnStatus", com.freeder.buclserver.domain.orderreturn.vo.OrderReturnStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrderReturn(String variable) {
        this(OrderReturn.class, forVariable(variable), INITS);
    }

    public QOrderReturn(Path<? extends OrderReturn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderReturn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderReturn(PathMetadata metadata, PathInits inits) {
        this(OrderReturn.class, metadata, inits);
    }

    public QOrderReturn(Class<? extends OrderReturn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
        this.orderRefund = inits.isInitialized("orderRefund") ? new com.freeder.buclserver.domain.orderrefund.entity.QOrderRefund(forProperty("orderRefund"), inits.get("orderRefund")) : null;
    }

}

