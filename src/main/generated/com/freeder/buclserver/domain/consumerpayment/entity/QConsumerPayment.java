package com.freeder.buclserver.domain.consumerpayment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConsumerPayment is a Querydsl query type for ConsumerPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsumerPayment extends EntityPathBase<ConsumerPayment> {

    private static final long serialVersionUID = -17193772L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConsumerPayment consumerPayment = new QConsumerPayment("consumerPayment");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath consumerAddress = createString("consumerAddress");

    public final StringPath consumerCellphone = createString("consumerCellphone");

    public final StringPath consumerEmail = createString("consumerEmail");

    public final StringPath consumerName = createString("consumerName");

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> paidAt = createDateTime("paidAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> paymentAmount = createNumber("paymentAmount", Integer.class);

    public final StringPath paymentCode = createString("paymentCode");

    public final EnumPath<com.freeder.buclserver.domain.consumerpayment.vo.PaymentMethod> paymentMethod = createEnum("paymentMethod", com.freeder.buclserver.domain.consumerpayment.vo.PaymentMethod.class);

    public final EnumPath<com.freeder.buclserver.domain.consumerpayment.vo.PaymentStatus> paymentStatus = createEnum("paymentStatus", com.freeder.buclserver.domain.consumerpayment.vo.PaymentStatus.class);

    public final EnumPath<com.freeder.buclserver.domain.consumerpayment.vo.PgProvider> pgProvider = createEnum("pgProvider", com.freeder.buclserver.domain.consumerpayment.vo.PgProvider.class);

    public final StringPath pgTid = createString("pgTid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QConsumerPayment(String variable) {
        this(ConsumerPayment.class, forVariable(variable), INITS);
    }

    public QConsumerPayment(Path<? extends ConsumerPayment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConsumerPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConsumerPayment(PathMetadata metadata, PathInits inits) {
        this(ConsumerPayment.class, metadata, inits);
    }

    public QConsumerPayment(Class<? extends ConsumerPayment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
    }

}

