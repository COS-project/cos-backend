package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNormalPostEntity is a Querydsl query type for NormalPostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNormalPostEntity extends EntityPathBase<NormalPostEntity> {

    private static final long serialVersionUID = 739599712L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNormalPostEntity normalPostEntity = new QNormalPostEntity("normalPostEntity");

    public final QPostEntity _super;

    // inherited
    public final QCertificateEntity certificateEntity;

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final EnumPath<com.cos.cercat.domain.post.PostType> postType;

    //inherited
    public final StringPath title;

    // inherited
    public final QUserEntity userEntity;

    public QNormalPostEntity(String variable) {
        this(NormalPostEntity.class, forVariable(variable), INITS);
    }

    public QNormalPostEntity(Path<? extends NormalPostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNormalPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNormalPostEntity(PathMetadata metadata, PathInits inits) {
        this(NormalPostEntity.class, metadata, inits);
    }

    public QNormalPostEntity(Class<? extends NormalPostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPostEntity(type, metadata, inits);
        this.certificateEntity = _super.certificateEntity;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.likeCount = _super.likeCount;
        this.modifiedAt = _super.modifiedAt;
        this.postType = _super.postType;
        this.title = _super.title;
        this.userEntity = _super.userEntity;
    }

}

