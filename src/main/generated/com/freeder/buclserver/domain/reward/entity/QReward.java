package com.freeder.buclserver.domain.reward.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReward is a Querydsl query type for Reward
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReward extends EntityPathBase<Reward> {

    private static final long serialVersionUID = -2075359508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReward reward = new QReward("reward");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder consumerOrder;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.freeder.buclserver.domain.orderrefund.entity.QOrderRefund orderRefund;

    public final NumberPath<Integer> previousRewardSum = createNumber("previousRewardSum", Integer.class);

    public final com.freeder.buclserver.domain.product.entity.QProduct product;

    public final StringPath productBrandName = createString("productBrandName");

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> receivedRewardAmount = createNumber("receivedRewardAmount", Integer.class);

    public final NumberPath<Integer> rewardSum = createNumber("rewardSum", Integer.class);

    public final EnumPath<com.freeder.buclserver.domain.reward.vo.RewardType> rewardType = createEnum("rewardType", com.freeder.buclserver.domain.reward.vo.RewardType.class);

    public final com.freeder.buclserver.domain.rewardwithdrawal.entity.QRewardWithdrawal rewardWithdrawal;

    public final com.freeder.buclserver.domain.rewardwithdrawalaccount.entity.QRewardWithdrawalAccount rewardWithdrawalAccount;

    public final NumberPath<Integer> spentRewardAmount = createNumber("spentRewardAmount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.freeder.buclserver.domain.user.entity.QUser user;

    public QReward(String variable) {
        this(Reward.class, forVariable(variable), INITS);
    }

    public QReward(Path<? extends Reward> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReward(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReward(PathMetadata metadata, PathInits inits) {
        this(Reward.class, metadata, inits);
    }

    public QReward(Class<? extends Reward> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumerOrder = inits.isInitialized("consumerOrder") ? new com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder(forProperty("consumerOrder"), inits.get("consumerOrder")) : null;
        this.orderRefund = inits.isInitialized("orderRefund") ? new com.freeder.buclserver.domain.orderrefund.entity.QOrderRefund(forProperty("orderRefund")) : null;
        this.product = inits.isInitialized("product") ? new com.freeder.buclserver.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.rewardWithdrawal = inits.isInitialized("rewardWithdrawal") ? new com.freeder.buclserver.domain.rewardwithdrawal.entity.QRewardWithdrawal(forProperty("rewardWithdrawal"), inits.get("rewardWithdrawal")) : null;
        this.rewardWithdrawalAccount = inits.isInitialized("rewardWithdrawalAccount") ? new com.freeder.buclserver.domain.rewardwithdrawalaccount.entity.QRewardWithdrawalAccount(forProperty("rewardWithdrawalAccount"), inits.get("rewardWithdrawalAccount")) : null;
        this.user = inits.isInitialized("user") ? new com.freeder.buclserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

