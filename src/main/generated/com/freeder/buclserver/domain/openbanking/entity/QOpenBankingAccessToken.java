package com.freeder.buclserver.domain.openbanking.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOpenBankingAccessToken is a Querydsl query type for OpenBankingAccessToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOpenBankingAccessToken extends EntityPathBase<OpenBankingAccessToken> {

    private static final long serialVersionUID = 738022017L;

    public static final QOpenBankingAccessToken openBankingAccessToken = new QOpenBankingAccessToken("openBankingAccessToken");

    public final StringPath accessToken = createString("accessToken");

    public final StringPath clientUseCode = createString("clientUseCode");

    public final StringPath expireDate = createString("expireDate");

    public final StringPath scope = createString("scope");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath tokenType = createString("tokenType");

    public QOpenBankingAccessToken(String variable) {
        super(OpenBankingAccessToken.class, forVariable(variable));
    }

    public QOpenBankingAccessToken(Path<? extends OpenBankingAccessToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOpenBankingAccessToken(PathMetadata metadata) {
        super(OpenBankingAccessToken.class, metadata);
    }

}

