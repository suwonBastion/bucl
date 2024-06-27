package com.freeder.buclserver.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -167589468L;

    public static final QUser user = new QUser("user");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final ListPath<com.freeder.buclserver.domain.affiliate.entity.Affiliate, com.freeder.buclserver.domain.affiliate.entity.QAffiliate> affiliates = this.<com.freeder.buclserver.domain.affiliate.entity.Affiliate, com.freeder.buclserver.domain.affiliate.entity.QAffiliate>createList("affiliates", com.freeder.buclserver.domain.affiliate.entity.Affiliate.class, com.freeder.buclserver.domain.affiliate.entity.QAffiliate.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> birthDate = createDateTime("birthDate", java.time.LocalDateTime.class);

    public final NumberPath<Float> businessRewardRate = createNumber("businessRewardRate", Float.class);

    public final StringPath cellPhone = createString("cellPhone");

    public final ListPath<com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder> consumerOrders = this.<com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder>createList("consumerOrders", com.freeder.buclserver.domain.consumerorder.entity.ConsumerOrder.class, com.freeder.buclserver.domain.consumerorder.entity.QConsumerOrder.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath email = createString("email");

    public final EnumPath<com.freeder.buclserver.domain.user.vo.Gender> gender = createEnum("gender", com.freeder.buclserver.domain.user.vo.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAlarmed = createBoolean("isAlarmed");

    public final EnumPath<com.freeder.buclserver.domain.user.vo.JoinType> joinType = createEnum("joinType", com.freeder.buclserver.domain.user.vo.JoinType.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath profilePath = createString("profilePath");

    public final StringPath refreshToken = createString("refreshToken");

    public final ListPath<com.freeder.buclserver.domain.productreview.entity.ProductReview, com.freeder.buclserver.domain.productreview.entity.QProductReview> reviews = this.<com.freeder.buclserver.domain.productreview.entity.ProductReview, com.freeder.buclserver.domain.productreview.entity.QProductReview>createList("reviews", com.freeder.buclserver.domain.productreview.entity.ProductReview.class, com.freeder.buclserver.domain.productreview.entity.QProductReview.class, PathInits.DIRECT2);

    public final ListPath<com.freeder.buclserver.domain.reward.entity.Reward, com.freeder.buclserver.domain.reward.entity.QReward> rewards = this.<com.freeder.buclserver.domain.reward.entity.Reward, com.freeder.buclserver.domain.reward.entity.QReward>createList("rewards", com.freeder.buclserver.domain.reward.entity.Reward.class, com.freeder.buclserver.domain.reward.entity.QReward.class, PathInits.DIRECT2);

    public final ListPath<com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal, com.freeder.buclserver.domain.rewardwithdrawal.entity.QRewardWithdrawal> rewardWithdrawals = this.<com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal, com.freeder.buclserver.domain.rewardwithdrawal.entity.QRewardWithdrawal>createList("rewardWithdrawals", com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal.class, com.freeder.buclserver.domain.rewardwithdrawal.entity.QRewardWithdrawal.class, PathInits.DIRECT2);

    public final EnumPath<com.freeder.buclserver.domain.user.vo.Role> role = createEnum("role", com.freeder.buclserver.domain.user.vo.Role.class);

    public final ListPath<com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress, com.freeder.buclserver.domain.shippingaddress.entity.QShippingAddress> shippingAddresses = this.<com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress, com.freeder.buclserver.domain.shippingaddress.entity.QShippingAddress>createList("shippingAddresses", com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress.class, com.freeder.buclserver.domain.shippingaddress.entity.QShippingAddress.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final EnumPath<com.freeder.buclserver.domain.user.vo.UserGrade> userGrade = createEnum("userGrade", com.freeder.buclserver.domain.user.vo.UserGrade.class);

    public final StringPath userName = createString("userName");

    public final EnumPath<com.freeder.buclserver.domain.user.vo.UserState> userState = createEnum("userState", com.freeder.buclserver.domain.user.vo.UserState.class);

    public final ListPath<com.freeder.buclserver.domain.wish.entity.Wish, com.freeder.buclserver.domain.wish.entity.QWish> wishes = this.<com.freeder.buclserver.domain.wish.entity.Wish, com.freeder.buclserver.domain.wish.entity.QWish>createList("wishes", com.freeder.buclserver.domain.wish.entity.Wish.class, com.freeder.buclserver.domain.wish.entity.QWish.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

