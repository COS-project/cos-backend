package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentLikeEntity is a Querydsl query type for CommentLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentLikeEntity extends EntityPathBase<CommentLikeEntity> {

    private static final long serialVersionUID = 1450823779L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentLikeEntity commentLikeEntity = new QCommentLikeEntity("commentLikeEntity");

    public final QPostCommentEntity comment;

    public final com.cos.cercat.domain.EmbeddedId.QCommentLikePK commentLikePK;

    public final QUserEntity userEntity;

    public QCommentLikeEntity(String variable) {
        this(CommentLikeEntity.class, forVariable(variable), INITS);
    }

    public QCommentLikeEntity(Path<? extends CommentLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentLikeEntity(PathMetadata metadata, PathInits inits) {
        this(CommentLikeEntity.class, metadata, inits);
    }

    public QCommentLikeEntity(Class<? extends CommentLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QPostCommentEntity(forProperty("comment"), inits.get("comment")) : null;
        this.commentLikePK = inits.isInitialized("commentLikePK") ? new com.cos.cercat.domain.EmbeddedId.QCommentLikePK(forProperty("commentLikePK")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

