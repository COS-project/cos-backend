package com.cos.cercat.like.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentLike is a Querydsl query type for CommentLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentLike extends EntityPathBase<CommentLike> {

    private static final long serialVersionUID = 1881933935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentLike commentLike = new QCommentLike("commentLike");

    public final com.cos.cercat.comment.domain.QPostComment comment;

    public final com.cos.cercat.like.domain.EmbeddedId.QCommentLikePK commentLikePK;

    public final com.cos.cercat.user.domain.QUser user;

    public QCommentLike(String variable) {
        this(CommentLike.class, forVariable(variable), INITS);
    }

    public QCommentLike(Path<? extends CommentLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentLike(PathMetadata metadata, PathInits inits) {
        this(CommentLike.class, metadata, inits);
    }

    public QCommentLike(Class<? extends CommentLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.cos.cercat.comment.domain.QPostComment(forProperty("comment"), inits.get("comment")) : null;
        this.commentLikePK = inits.isInitialized("commentLikePK") ? new com.cos.cercat.like.domain.EmbeddedId.QCommentLikePK(forProperty("commentLikePK")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

