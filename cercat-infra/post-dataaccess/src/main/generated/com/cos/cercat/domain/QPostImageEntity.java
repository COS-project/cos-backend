package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImageEntity is a Querydsl query type for PostImageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostImageEntity extends EntityPathBase<PostImageEntity> {

    private static final long serialVersionUID = -1431290584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImageEntity postImageEntity = new QPostImageEntity("postImageEntity");

    public final com.cos.cercat.entity.QImageEntity imageEntity;

    public final QPostEntity postEntity;

    public final com.cos.cercat.domain.embeddedId.QPostImageId postImageId;

    public QPostImageEntity(String variable) {
        this(PostImageEntity.class, forVariable(variable), INITS);
    }

    public QPostImageEntity(Path<? extends PostImageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImageEntity(PathMetadata metadata, PathInits inits) {
        this(PostImageEntity.class, metadata, inits);
    }

    public QPostImageEntity(Class<? extends PostImageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.imageEntity = inits.isInitialized("imageEntity") ? new com.cos.cercat.entity.QImageEntity(forProperty("imageEntity")) : null;
        this.postEntity = inits.isInitialized("postEntity") ? new QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
        this.postImageId = inits.isInitialized("postImageId") ? new com.cos.cercat.domain.embeddedId.QPostImageId(forProperty("postImageId")) : null;
    }

}

