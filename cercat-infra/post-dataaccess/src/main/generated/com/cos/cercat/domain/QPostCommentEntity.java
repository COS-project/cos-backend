package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostCommentEntity is a Querydsl query type for PostCommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostCommentEntity extends EntityPathBase<PostCommentEntity> {

    private static final long serialVersionUID = 2095043756L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostCommentEntity postCommentEntity = new QPostCommentEntity("postCommentEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> parentCommentId = createNumber("parentCommentId", Long.class);

    public final QPostEntity postEntity;

    public final QUserEntity userEntity;

    public QPostCommentEntity(String variable) {
        this(PostCommentEntity.class, forVariable(variable), INITS);
    }

    public QPostCommentEntity(Path<? extends PostCommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostCommentEntity(PathMetadata metadata, PathInits inits) {
        this(PostCommentEntity.class, metadata, inits);
    }

    public QPostCommentEntity(Class<? extends PostCommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postEntity = inits.isInitialized("postEntity") ? new QPostEntity(forProperty("postEntity"), inits.get("postEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

