package com.cos.cercat.comment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostComments is a Querydsl query type for PostComments
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostComments extends BeanPath<PostComments> {

    private static final long serialVersionUID = 146954267L;

    public static final QPostComments postComments1 = new QPostComments("postComments1");

    public final ListPath<PostComment, QPostComment> postComments = this.<PostComment, QPostComment>createList("postComments", PostComment.class, QPostComment.class, PathInits.DIRECT2);

    public QPostComments(String variable) {
        super(PostComments.class, forVariable(variable));
    }

    public QPostComments(Path<? extends PostComments> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostComments(PathMetadata metadata) {
        super(PostComments.class, metadata);
    }

}

