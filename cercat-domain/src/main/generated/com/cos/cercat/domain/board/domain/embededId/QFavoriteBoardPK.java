package com.cos.cercat.domain.board.domain.embededId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavoriteBoardPK is a Querydsl query type for FavoriteBoardPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFavoriteBoardPK extends BeanPath<FavoriteBoardPK> {

    private static final long serialVersionUID = -567377986L;

    public static final QFavoriteBoardPK favoriteBoardPK = new QFavoriteBoardPK("favoriteBoardPK");

    public final NumberPath<Long> certificateId = createNumber("certificateId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QFavoriteBoardPK(String variable) {
        super(FavoriteBoardPK.class, forVariable(variable));
    }

    public QFavoriteBoardPK(Path<? extends FavoriteBoardPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavoriteBoardPK(PathMetadata metadata) {
        super(FavoriteBoardPK.class, metadata);
    }

}

