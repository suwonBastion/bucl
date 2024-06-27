package com.freeder.buclserver.domain.rewardwithdrawal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRewardWithdrawal is a Querydsl query type for RewardWithdrawal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRewardWithdrawal extends EntityPathBase<RewardWithdrawal> {

    private static final long serialVersionUID = 722456182L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRewardWithdrawal rewardWithdrawal = new QRewardWithdrawal("rewardWithdrawal");

    public final StringPath accountHolderName = createString("accountHolderName");

    public final StringPath accountNum = createString("accountNum");

    public final StringPath bankCodeStd = createString("bankCodeStd");

    public final StringPath bankName = createString("bankName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isWithdrawn = createBoolean("isWithdrawn");

    public final DateTimePath<java.time.LocalDateTime> lastUsedDate = createDateTime("lastUsedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> rewardWithdrawalAmount = createNumber("rewardWithdrawalAmount", Integer.class);

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public final EnumPath<com.freeder.buclserver.domain.rewardwithdrawal.vo.WithdrawalStatus> withdrawalStatus = createEnum("withdrawalStatus", com.freeder.buclserver.domain.rewardwithdrawal.vo.WithdrawalStatus.class);

    public QRewardWithdrawal(String variable) {
        this(RewardWithdrawal.class, forVariable(variable), INITS);
    }

    public QRewardWithdrawal(Path<? extends RewardWithdrawal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRewardWithdrawal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRewardWithdrawal(PathMetadata metadata, PathInits inits) {
        this(RewardWithdrawal.class, metadata, inits);
    }

    public QRewardWithdrawal(Class<? extends RewardWithdrawal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

