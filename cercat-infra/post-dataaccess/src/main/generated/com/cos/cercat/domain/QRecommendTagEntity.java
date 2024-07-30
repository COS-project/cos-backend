package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendTagEntity is a Querydsl query type for RecommendTagEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendTagEntity extends EntityPathBase<RecommendTagEntity> {

    private static final long serialVersionUID = 927195575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendTagEntity recommendTagEntity = new QRecommendTagEntity("recommendTagEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tagName = createString("tagName");

    public final EnumPath<com.cos.cercat.domain.post.TagType> tagType = createEnum("tagType", com.cos.cercat.domain.post.TagType.class);

    public final QTipPostEntity tipPost;

    public QRecommendTagEntity(String variable) {
        this(RecommendTagEntity.class, forVariable(variable), INITS);
    }

    public QRecommendTagEntity(Path<? extends RecommendTagEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendTagEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendTagEntity(PathMetadata metadata, PathInits inits) {
        this(RecommendTagEntity.class, metadata, inits);
    }

    public QRecommendTagEntity(Class<? extends RecommendTagEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tipPost = inits.isInitialized("tipPost") ? new QTipPostEntity(forProperty("tipPost"), inits.get("tipPost")) : null;
    }

}

