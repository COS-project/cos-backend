package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteBoardEntity is a Querydsl query type for FavoriteBoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteBoardEntity extends EntityPathBase<FavoriteBoardEntity> {

    private static final long serialVersionUID = 2071401207L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteBoardEntity favoriteBoardEntity = new QFavoriteBoardEntity("favoriteBoardEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.cos.cercat.domain.embededId.QFavoriteBoardPK favoriteBoardPK;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QFavoriteBoardEntity(String variable) {
        this(FavoriteBoardEntity.class, forVariable(variable), INITS);
    }

    public QFavoriteBoardEntity(Path<? extends FavoriteBoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteBoardEntity(PathMetadata metadata, PathInits inits) {
        this(FavoriteBoardEntity.class, metadata, inits);
    }

    public QFavoriteBoardEntity(Class<? extends FavoriteBoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.favoriteBoardPK = inits.isInitialized("favoriteBoardPK") ? new com.cos.cercat.domain.embededId.QFavoriteBoardPK(forProperty("favoriteBoardPK"), inits.get("favoriteBoardPK")) : null;
    }

}

