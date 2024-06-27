package com.freeder.buclserver.domain.grouporder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupOrder is a Querydsl query type for GroupOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupOrder extends EntityPathBase<GroupOrder> {

    private static final long serialVersionUID = -2010701364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupOrder groupOrder = new QGroupOrder("groupOrder");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final ListPath<com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder> consumerOrders = this.<com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder>createList("consumerOrders", com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder.class, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isEnded = createBoolean("isEnded");

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGroupOrder(String variable) {
        this(GroupOrder.class, forVariable(variable), INITS);
    }

    public QGroupOrder(Path<? extends GroupOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupOrder(PathMetadata metadata, PathInits inits) {
        this(GroupOrder.class, metadata, inits);
    }

    public QGroupOrder(Class<? extends GroupOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

