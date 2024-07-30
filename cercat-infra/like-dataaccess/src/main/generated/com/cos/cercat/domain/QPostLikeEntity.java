package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLikeEntity is a Querydsl query type for PostLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLikeEntity extends EntityPathBase<PostLikeEntity> {

    private static final long serialVersionUID = 864097552L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLikeEntity postLikeEntity = new QPostLikeEntity("postLikeEntity");

    public final QPostEntity postEntity;

    public final com.cos.cercat.domain.EmbeddedId.QPostLikePK postLikePK;

    public final QUserEntity userEntity;

    public QPostLikeEntity(String variable) {
        this(PostLikeEntity.class, forVariable(variable), INITS);
    }

    public QPostLikeEntity(Path<? extends PostLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLikeEntity(PathMetadata metadata, PathInits inits) {
        this(PostLikeEntity.class, metadata, inits);
    }

    public QPostLikeEntity(Class<? extends PostLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
        this.postLikePK = inits.isInitialized("postLikePK") ? new com.cos.cercat.domain.EmbeddedId.QPostLikePK(forProperty("postLikePK")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

