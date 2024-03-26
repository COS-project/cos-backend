package com.cos.cercat.domain.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChildPostComments is a Querydsl query type for ChildPostComments
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QChildPostComments extends BeanPath<ChildPostComments> {

    private static final long serialVersionUID = -1963850645L;

    public static final QChildPostComments childPostComments1 = new QChildPostComments("childPostComments1");

    public final ListPath<PostComment, QPostComment> childPostComments = this.<PostComment, QPostComment>createList("childPostComments", PostComment.class, QPostComment.class, PathInits.DIRECT2);

    public QChildPostComments(String variable) {
        super(ChildPostComments.class, forVariable(variable));
    }

    public QChildPostComments(Path<? extends ChildPostComments> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChildPostComments(PathMetadata metadata) {
        super(ChildPostComments.class, metadata);
    }

}

