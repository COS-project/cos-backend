package com.cos.cercat.domain.embeddedId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostImageId is a Querydsl query type for PostImageId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostImageId extends BeanPath<PostImageId> {

    private static final long serialVersionUID = -174010461L;

    public static final QPostImageId postImageId = new QPostImageId("postImageId");

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public QPostImageId(String variable) {
        super(PostImageId.class, forVariable(variable));
    }

    public QPostImageId(Path<? extends PostImageId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostImageId(PathMetadata metadata) {
        super(PostImageId.class, metadata);
    }

}

