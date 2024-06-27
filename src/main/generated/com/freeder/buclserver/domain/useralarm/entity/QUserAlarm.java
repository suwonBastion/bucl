package com.freeder.buclserver.domain.useralarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAlarm is a Querydsl query type for UserAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAlarm extends EntityPathBase<UserAlarm> {

    private static final long serialVersionUID = 1837879124L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAlarm userAlarm = new QUserAlarm("userAlarm");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public QUserAlarm(String variable) {
        this(UserAlarm.class, forVariable(variable), INITS);
    }

    public QUserAlarm(Path<? extends UserAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAlarm(PathMetadata metadata, PathInits inits) {
        this(UserAlarm.class, metadata, inits);
    }

    public QUserAlarm(Class<? extends UserAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

