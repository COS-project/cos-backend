package com.cos.cercat.domain.EmbeddedId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommentLikePK is a Querydsl query type for CommentLikePK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCommentLikePK extends BeanPath<CommentLikePK> {

    private static final long serialVersionUID = -1491289986L;

    public static final QCommentLikePK commentLikePK = new QCommentLikePK("commentLikePK");

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCommentLikePK(String variable) {
        super(CommentLikePK.class, forVariable(variable));
    }

    public QCommentLikePK(Path<? extends CommentLikePK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommentLikePK(PathMetadata metadata) {
        super(CommentLikePK.class, metadata);
    }

}

