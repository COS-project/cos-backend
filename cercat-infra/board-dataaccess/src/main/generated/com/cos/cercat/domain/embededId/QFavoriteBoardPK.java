package com.cos.cercat.domain.embededId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteBoardPK is a Querydsl query type for FavoriteBoardPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFavoriteBoardPK extends BeanPath<FavoriteBoardPK> {

    private static final long serialVersionUID = -1652259084L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteBoardPK favoriteBoardPK = new QFavoriteBoardPK("favoriteBoardPK");

    public final com.cos.cercat.domain.QCertificateEntity certificateEntity;

    public final com.cos.cercat.domain.QUserEntity userEntity;

    public QFavoriteBoardPK(String variable) {
        this(FavoriteBoardPK.class, forVariable(variable), INITS);
    }

    public QFavoriteBoardPK(Path<? extends FavoriteBoardPK> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteBoardPK(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteBoardPK(PathMetadata metadata, PathInits inits) {
        this(FavoriteBoardPK.class, metadata, inits);
    }

    public QFavoriteBoardPK(Class<? extends FavoriteBoardPK> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new com.cos.cercat.domain.QCertificateEntity(forProperty("certificateEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new com.cos.cercat.domain.QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

