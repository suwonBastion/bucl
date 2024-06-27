package com.freeder.buclserver.domain.consumerorder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConsumerOrder is a Querydsl query type for ConsumerOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsumerOrder extends EntityPathBase<ConsumerOrder> {

    private static final long serialVersionUID = 1600625428L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConsumerOrder consumerOrder = new QConsumerOrder("consumerOrder");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final com.freeder.buclserver.domain.user.entity.QUser business;

    public final com.freeder.buclserver.domain.user.entity.QUser consumer;

    public final ListPath<com.freeder.buclserver.domain.consumerpayment.entity.ConsumerPayment, com.freeder.buclserver.domain.consumerpayment.entity.QConsumerPayment> consumerPayments = this.<com.freeder.buclserver.domain.consumerpayment.entity.ConsumerPayment, com.freeder.buclserver.domain.consumerpayment.entity.QConsumerPayment>createList("consumerPayments", com.freeder.buclserver.domain.consumerpayment.entity.ConsumerPayment.class, com.freeder.buclserver.domain.consumerpayment.entity.QConsumerPayment.class, PathInits.DIRECT2);

    public final ListPath<com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder, com.freeder.buclserver.domain.consumerpurchaseorder.entity.QConsumerPurchaseOrder> consumerPurchaseOrders = this.<com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder, com.freeder.buclserver.domain.consumerpurchaseorder.entity.QConsumerPurchaseOrder>createList("consumerPurchaseOrders", com.freeder.buclserver.domain.consumerpurchaseorder.entity.ConsumerPurchaseOrder.class, com.freeder.buclserver.domain.consumerpurchaseorder.entity.QConsumerPurchaseOrder.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.freeder.buclserver.domain.consumerorder.vo.CsStatus> csStatus = createEnum("csStatus", com.freeder.buclserver.domain.consumerorder.vo.CsStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final com.freeder.buclserver.domain.grouporder.entity.QGroupOrder groupOrder;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isConfirmed = createBoolean("isConfirmed");

    public final BooleanPath isRewarded = createBoolean("isRewarded");

    public final StringPath orderCode = createString("orderCode");

    public final NumberPath<Integer> orderNum = createNumber("orderNum", Integer.class);

    public final EnumPath<com.freeder.buclserver.domain.consumerorder.vo.OrderStatus> orderStatus = createEnum("orderStatus", com.freeder.buclserver.domain.consumerorder.vo.OrderStatus.class);

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    public final NumberPath<Integer> rewardUseAmount = createNumber("rewardUseAmount", Integer.class);

    public final NumberPath<Integer> shippingFee = createNumber("shippingFee", Integer.class);

    public final ListPath<com.freeder.buclserver.domain.shipping.entity.Shipping, com.freeder.buclserver.domain.shipping.entity.QShipping> shippings = this.<com.freeder.buclserver.domain.shipping.entity.Shipping, com.freeder.buclserver.domain.shipping.entity.QShipping>createList("shippings", com.freeder.buclserver.domain.shipping.entity.Shipping.class, com.freeder.buclserver.domain.shipping.entity.QShipping.class, PathInits.DIRECT2);

    public final NumberPath<Integer> spentAmount = createNumber("spentAmount", Integer.class);

    public final NumberPath<Integer> totalOrderAmount = createNumber("totalOrderAmount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QConsumerOrder(String variable) {
        this(ConsumerOrder.class, forVariable(variable), INITS);
    }

    public QConsumerOrder(Path<? extends ConsumerOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConsumerOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConsumerOrder(PathMetadata metadata, PathInits inits) {
        this(ConsumerOrder.class, metadata, inits);
    }

    public QConsumerOrder(Class<? extends ConsumerOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.business = inits.isInitialized("business") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("business")) : null;
        this.consumer = inits.isInitialized("consumer") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("consumer")) : null;
        this.groupOrder = inits.isInitialized("groupOrder") ? new com.freeder.buclserver.domain.grouporder.entity.QGroupOrder(forProperty("groupOrder"), inits.get("groupOrder")) : null;
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

