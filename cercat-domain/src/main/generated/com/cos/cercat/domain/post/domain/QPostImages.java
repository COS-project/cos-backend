package com.cos.cercat.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImages is a Querydsl query type for PostImages
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostImages extends BeanPath<PostImages> {

    private static final long serialVersionUID = 442521802L;

    public static final QPostImages postImages1 = new QPostImages("postImages1");

    public final ListPath<PostImage, QPostImage> postImages = this.<PostImage, QPostImage>createList("postImages", PostImage.class, QPostImage.class, PathInits.DIRECT2);

    public QPostImages(String variable) {
        super(PostImages.class, forVariable(variable));
    }

    public QPostImages(Path<? extends PostImages> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostImages(PathMetadata metadata) {
        super(PostImages.class, metadata);
    }

}

