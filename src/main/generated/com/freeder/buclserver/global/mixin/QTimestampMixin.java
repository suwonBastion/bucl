package com.freeder.buclserver.global.mixin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimestampMixin is a Querydsl query type for TimestampMixin
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QTimestampMixin extends EntityPathBase<TimestampMixin> {

    private static final long serialVersionUID = -730674070L;

    public static final QTimestampMixin timestampMixin = new QTimestampMixin("timestampMixin");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QTimestampMixin(String variable) {
        super(TimestampMixin.class, forVariable(variable));
    }

    public QTimestampMixin(Path<? extends TimestampMixin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimestampMixin(PathMetadata metadata) {
        super(TimestampMixin.class, metadata);
    }

}

