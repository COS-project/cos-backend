package com.cos.cercat.domain.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteBoard is a Querydsl query type for FavoriteBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteBoard extends EntityPathBase<FavoriteBoard> {

    private static final long serialVersionUID = 140501438L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteBoard favoriteBoard = new QFavoriteBoard("favoriteBoard");

    public final com.cos.cercat.domain.certificate.domain.QCertificate certificate;

    public final com.cos.cercat.domain.board.domain.embededId.QFavoriteBoardPK favoriteBoardPK;

    public final com.cos.cercat.domain.user.domain.QUser user;

    public QFavoriteBoard(String variable) {
        this(FavoriteBoard.class, forVariable(variable), INITS);
    }

    public QFavoriteBoard(Path<? extends FavoriteBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteBoard(PathMetadata metadata, PathInits inits) {
        this(FavoriteBoard.class, metadata, inits);
    }

    public QFavoriteBoard(Class<? extends FavoriteBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificate = inits.isInitialized("certificate") ? new com.cos.cercat.domain.certificate.domain.QCertificate(forProperty("certificate"), inits.get("certificate")) : null;
        this.favoriteBoardPK = inits.isInitialized("favoriteBoardPK") ? new com.cos.cercat.domain.board.domain.embededId.QFavoriteBoardPK(forProperty("favoriteBoardPK")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

