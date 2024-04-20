package com.cos.cercat.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendTags is a Querydsl query type for RecommendTags
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecommendTags extends BeanPath<RecommendTags> {

    private static final long serialVersionUID = -769690403L;

    public static final QRecommendTags recommendTags1 = new QRecommendTags("recommendTags1");

    public final SetPath<RecommendTag, QRecommendTag> recommendTags = this.<RecommendTag, QRecommendTag>createSet("recommendTags", RecommendTag.class, QRecommendTag.class, PathInits.DIRECT2);

    public QRecommendTags(String variable) {
        super(RecommendTags.class, forVariable(variable));
    }

    public QRecommendTags(Path<? extends RecommendTags> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendTags(PathMetadata metadata) {
        super(RecommendTags.class, metadata);
    }

}

