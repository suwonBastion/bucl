package com.freeder.buclserver.domain.productcategory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCategory is a Querydsl query type for ProductCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCategory extends EntityPathBase<ProductCategory> {

    private static final long serialVersionUID = -1014271884L;

    public static final QProductCategory productCategory = new QProductCategory("productCategory");

    public final com.freeder.buclserver.global.mixin.QTimestampMixin _super = new com.freeder.buclserver.global.mixin.QTimestampMixin(this);

    public final StringPath categoryName = createString("categoryName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath productDesc = createString("productDesc");

    public final ListPath<com.freeder.buclserver.domain.product.entity.Product, com.freeder.buclserver.domain.product.entity.QProduct> products = this.<com.freeder.buclserver.domain.product.entity.Product, com.freeder.buclserver.domain.product.entity.QProduct>createList("products", com.freeder.buclserver.domain.product.entity.Product.class, com.freeder.buclserver.domain.product.entity.QProduct.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductCategory(String variable) {
        super(ProductCategory.class, forVariable(variable));
    }

    public QProductCategory(Path<? extends ProductCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCategory(PathMetadata metadata) {
        super(ProductCategory.class, metadata);
    }

}

