package com.freeder.buclserver.domain.rewardwithdrawalaccount.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRewardWithdrawalAccount is a Querydsl query type for RewardWithdrawalAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRewardWithdrawalAccount extends EntityPathBase<RewardWithdrawalAccount> {

    private static final long serialVersionUID = -1827448684L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRewardWithdrawalAccount rewardWithdrawalAccount = new QRewardWithdrawalAccount("rewardWithdrawalAccount");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath accountHolderInfo = createString("accountHolderInfo");

    public final StringPath accountHolderName = createString("accountHolderName");

    public final StringPath accountNum = createString("accountNum");

    public final StringPath bankCodeStd = createString("bankCodeStd");

    public final StringPath bankName = createString("bankName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAuthenticated = createBoolean("isAuthenticated");

    public final DateTimePath<java.time.LocalDateTime> lastUsedDate = createDateTime("lastUsedDate", java.time.LocalDateTime.class);

    public final ListPath<com.freeder.buclserver.domain.reward.entity.Reward, com.freeder.buclserver.domain.reward.entity.QReward> rewards = this.<com.freeder.buclserver.domain.reward.entity.Reward, com.freeder.buclserver.domain.reward.entity.QReward>createList("rewards", com.freeder.buclserver.domain.reward.entity.Reward.class, com.freeder.buclserver.domain.reward.entity.QReward.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public QRewardWithdrawalAccount(String variable) {
        this(RewardWithdrawalAccount.class, forVariable(variable), INITS);
    }

    public QRewardWithdrawalAccount(Path<? extends RewardWithdrawalAccount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRewardWithdrawalAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRewardWithdrawalAccount(PathMetadata metadata, PathInits inits) {
        this(RewardWithdrawalAccount.class, metadata, inits);
    }

    public QRewardWithdrawalAccount(Class<? extends RewardWithdrawalAccount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

