package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTipPostEntity is a Querydsl query type for TipPostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTipPostEntity extends EntityPathBase<TipPostEntity> {

    private static final long serialVersionUID = 85392104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTipPostEntity tipPostEntity = new QTipPostEntity("tipPostEntity");

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

    public QTipPostEntity(String variable) {
        this(TipPostEntity.class, forVariable(variable), INITS);
    }

    public QTipPostEntity(Path<? extends TipPostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTipPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTipPostEntity(PathMetadata metadata, PathInits inits) {
        this(TipPostEntity.class, metadata, inits);
    }

    public QTipPostEntity(Class<? extends TipPostEntity> type, PathMetadata metadata, PathInits inits) {
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

