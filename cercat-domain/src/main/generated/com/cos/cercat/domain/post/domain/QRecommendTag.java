package com.cos.cercat.domain.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendTag is a Querydsl query type for RecommendTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendTag extends EntityPathBase<RecommendTag> {

    private static final long serialVersionUID = -1156326832L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendTag recommendTag = new QRecommendTag("recommendTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tagName = createString("tagName");

    public final EnumPath<TagType> tagType = createEnum("tagType", TagType.class);

    public final QTipPost tipPost;

    public QRecommendTag(String variable) {
        this(RecommendTag.class, forVariable(variable), INITS);
    }

    public QRecommendTag(Path<? extends RecommendTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendTag(PathMetadata metadata, PathInits inits) {
        this(RecommendTag.class, metadata, inits);
    }

    public QRecommendTag(Class<? extends RecommendTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tipPost = inits.isInitialized("tipPost") ? new QTipPost(forProperty("tipPost"), inits.get("tipPost")) : null;
    }

}

