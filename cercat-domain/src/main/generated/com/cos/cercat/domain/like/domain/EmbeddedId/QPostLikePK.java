package com.cos.cercat.domain.like.domain.EmbeddedId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostLikePK is a Querydsl query type for PostLikePK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostLikePK extends BeanPath<PostLikePK> {

    private static final long serialVersionUID = -1010194286L;

    public static final QPostLikePK postLikePK = new QPostLikePK("postLikePK");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPostLikePK(String variable) {
        super(PostLikePK.class, forVariable(variable));
    }

    public QPostLikePK(Path<? extends PostLikePK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostLikePK(PathMetadata metadata) {
        super(PostLikePK.class, metadata);
    }

}

